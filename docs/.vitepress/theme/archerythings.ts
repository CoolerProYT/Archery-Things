// @ts-ignore
import raw from '../data/data.json'

export interface Recipe {
  id: string
  type: string
  result: { id: string; count: number }
  pattern?: string[]
  key?: Record<string, string>
  ingredients?: string[]
}

export const data = raw as unknown as {
  names: Record<string, string>
  textures: Record<string, string>
  recipes: Recipe[]
}

/** Mod items use their in-game name; vanilla ids are turned into readable names. */
export function itemName(id: string): string {
  if (data.names[id]) return data.names[id]
  const path = id.replace(/^#/, '').split(':').pop() ?? id
  return path
    .split('_')
    .map((word) => (['of', 'the'].includes(word) ? word : word.charAt(0).toUpperCase() + word.slice(1)))
    .join(' ')
}

/** Hosted renders, one PNG per item id: `<namespace>/<path>.png`. Mojang's textures are not bundled here. */
const HOSTED_ICONS = 'https://storage.googleapis.com/coolerpromc/textures'

/**
 * Where to load an item's icon from. Mod items use the hosted textures listed by the sync script,
 * vanilla items use the hosted renders. Dyed quiver variants (`archerythings:quiver_red`) are hosted too.
 */
export function itemIcon(id: string): string | null {
  if (data.textures[id]) return data.textures[id]
  const [namespace, path] = id.includes(':') ? id.split(':') : ['minecraft', id]
  if (namespace !== 'minecraft' && namespace !== 'archerythings') return null
  return `${HOSTED_ICONS}/${namespace}/${path}.png`
}
