<script setup lang="ts">
import { computed, ref } from 'vue'
import ItemSlot from './ItemSlot.vue'

/** A few single-dye results. Each one is a hosted, pre-flattened render of the tinted quiver. */
const DYES = [
  { id: 'archerythings:quiver', dye: null, name: 'Undyed', color: '#a06540' },
  { id: 'archerythings:quiver_red', dye: 'minecraft:red_dye', name: 'Red', color: '#b02e26' },
  { id: 'archerythings:quiver_yellow', dye: 'minecraft:yellow_dye', name: 'Yellow', color: '#fed83d' },
  { id: 'archerythings:quiver_green', dye: 'minecraft:green_dye', name: 'Green', color: '#5e7c16' },
  { id: 'archerythings:quiver_blue', dye: 'minecraft:blue_dye', name: 'Blue', color: '#3c44aa' },
  { id: 'archerythings:quiver_purple', dye: 'minecraft:purple_dye', name: 'Purple', color: '#8932b8' },
]

const active = ref(1)
const choice = computed(() => DYES[active.value])
</script>

<template>
  <div class="at-dyes">
    <div class="preview">
      <ItemSlot :id="choice.id" :size="88" />
      <div class="formula">
        <template v-if="choice.dye">
          <ItemSlot id="archerythings:quiver" />
          <span>+</span>
          <ItemSlot :id="choice.dye" />
          <span>➜</span>
          <ItemSlot :id="choice.id" label="Quiver" />
        </template>
        <span v-else class="at-muted">The default leather brown, before any dye.</span>
      </div>
    </div>
    <div class="swatches" role="radiogroup" aria-label="Dye colour">
      <button
        v-for="(dye, i) in DYES"
        :key="dye.id"
        type="button"
        role="radio"
        :aria-checked="i === active"
        :class="{ active: i === active }"
        @click="active = i"
      >
        <span class="dot" :style="{ background: dye.color }" />
        {{ dye.name }}
      </button>
    </div>
  </div>
</template>

<style scoped>
.at-dyes {
  margin: 16px 0;
  padding: 16px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 10px;
  background: var(--vp-c-bg-soft);
}

.preview {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 20px;
}

.formula {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;
  font-size: 20px;
  color: var(--vp-c-text-2);
}

.swatches {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 16px;
}

.swatches button {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 12px;
  font-size: 14px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 999px;
  background: var(--vp-c-bg);
  cursor: pointer;
  transition: border-color 0.2s;
}

.swatches button:hover,
.swatches button.active {
  border-color: var(--vp-c-brand-1);
}

.swatches button.active {
  background: var(--vp-c-brand-soft);
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.25);
}
</style>
