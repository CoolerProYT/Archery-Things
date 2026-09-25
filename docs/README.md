# Archery Things wiki

VitePress site for the mod. Recipes, item names and item textures are read from the mod's resources, so the wiki follows the game.

```bash
cd docs
npm install
npm run dev     # syncs data, then serves http://localhost:5173
npm run build   # syncs data, then builds to .vitepress/dist
```

`npm run sync` (run automatically by `dev` and `build`) writes `.vitepress/data/data.json`, which is git-ignored.

Item icons are not bundled. Vanilla and mod items load from the hosted renders at `https://storage.googleapis.com/coolerpromc/textures/<namespace>/<name>.png`, set in `.vitepress/theme/archerythings.ts`. The quiver is dyeable, so its hosted icons (`archerythings/quiver.png` and the `quiver_<colour>.png` variants used by `DyeSwatches`) are the tinted layer flattened onto the base texture. Upload a new render there when the quiver texture changes.

Pushes to the repository's default branch publish the site to GitHub Pages through `.github/workflows/docs.yml`.
