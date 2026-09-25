// Pulls wiki data straight from the mod so the docs never drift from the game:
// recipes, item names and the mod's own textures.
import { existsSync, mkdirSync, readdirSync, readFileSync, writeFileSync } from 'node:fs'
import { basename, dirname, join } from 'node:path'
import { fileURLToPath } from 'node:url'

const docs = join(dirname(fileURLToPath(import.meta.url)), '..')
const root = join(docs, '..')
const resources = join(root, 'common/src/main/resources')
const assets = join(resources, 'assets/archerythings')
const data = join(resources, 'data/archerythings')

if (!existsSync(assets)) {
  console.error(`No mod assets at ${assets}.`)
  process.exit(1)
}

const readJson = (file) => JSON.parse(readFileSync(file, 'utf8'))
const jsonFiles = (dir) => (existsSync(dir) ? readdirSync(dir).filter((f) => f.endsWith('.json')).sort() : [])

const lang = readJson(join(assets, 'lang/en_us.json'))

// Item names for mod items; vanilla names are prettified on the page.
const names = {}
for (const [key, value] of Object.entries(lang)) {
  const match = key.match(/^(item|block)\.archerythings\.([a-z0-9_]+)$/)
  if (match) names[`archerythings:${match[2]}`] = value
}

// Mod textures are hosted at 1024x1024 alongside the vanilla renders; upload new ones there before syncing.
// The quiver is dyeable, so its hosted icon is the flattened default colour, not the raw grey layer.
// Tint layers (`*_layer1`) are not items and are skipped.
const HOSTED_TEXTURES = 'https://storage.googleapis.com/coolerpromc/textures/archerythings'
const itemTextures = join(assets, 'textures/item')
const textures = {}
for (const file of existsSync(itemTextures) ? readdirSync(itemTextures) : []) {
  const name = basename(file, '.png')
  if (!file.endsWith('.png') || /_layer\d+$/.test(name)) continue
  textures[`archerythings:${name}`] = `${HOSTED_TEXTURES}/${name}.png`
}

const ingredient = (value) => {
  if (typeof value === 'string') return value
  if (Array.isArray(value)) return ingredient(value[0])
  if (value?.item) return value.item
  if (value?.tag) return `#${value.tag}`
  return '?'
}

const recipes = jsonFiles(join(data, 'recipe')).map((file) => {
  const json = readJson(join(data, 'recipe', file))
  const recipe = { id: `archerythings:${basename(file, '.json')}`, type: json.type, result: { id: json.result?.id, count: json.result?.count ?? 1 } }
  if (json.type === 'minecraft:crafting_shaped') {
    recipe.pattern = json.pattern
    recipe.key = Object.fromEntries(Object.entries(json.key).map(([symbol, value]) => [symbol, ingredient(value)]))
  } else if (json.type === 'minecraft:crafting_shapeless') {
    recipe.ingredients = json.ingredients.map(ingredient)
  }
  return recipe
})

mkdirSync(join(docs, '.vitepress/data'), { recursive: true })
writeFileSync(join(docs, '.vitepress/data/data.json'), JSON.stringify({ names, textures, recipes }, null, 2))
console.log(`Synced ${recipes.length} recipes, ${Object.keys(textures).length} textures.`)
