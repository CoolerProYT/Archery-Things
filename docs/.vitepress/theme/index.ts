import DefaultTheme from 'vitepress/theme'
import type { Theme } from 'vitepress'
import DyeSwatches from './components/DyeSwatches.vue'
import ItemSlot from './components/ItemSlot.vue'
import QuiverMenu from './components/QuiverMenu.vue'
import RecipeCard from './components/RecipeCard.vue'
import './style.css'

export default {
  extends: DefaultTheme,
  enhanceApp({ app }) {
    app.component('DyeSwatches', DyeSwatches)
    app.component('ItemSlot', ItemSlot)
    app.component('QuiverMenu', QuiverMenu)
    app.component('RecipeCard', RecipeCard)
  },
} satisfies Theme
