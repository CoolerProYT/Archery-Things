<script setup lang="ts">
import { computed, ref } from 'vue'
import { itemName } from '../archerythings'
import ItemSlot from './ItemSlot.vue'

/**
 * A playable copy of the quiver menu's top row.
 * `items` lists the nine slots, comma separated, as `id` or `id*count`; leave a slot blank for empty.
 */
const props = withDefaults(defineProps<{ items?: string; selected?: number; interactive?: boolean }>(), {
  items: 'arrow*64,spectral_arrow*15,,firework_rocket*16',
  selected: 0,
  interactive: true,
})

const slots = computed(() =>
  Array.from({ length: 9 }, (_, i) => {
    const raw = props.items.split(',')[i]?.trim() ?? ''
    if (!raw) return { id: null, count: 1 }
    const [id, count] = raw.split('*')
    return { id: id.includes(':') ? id : `minecraft:${id}`, count: Number(count ?? 1) }
  }),
)

const current = ref(Math.min(Math.max(props.selected, 0), 8))
const collect = ref(true)

function select(index: number) {
  if (props.interactive) current.value = (index + 9) % 9
}

function onKey(event: KeyboardEvent) {
  if (event.key >= '1' && event.key <= '9') {
    select(Number(event.key) - 1)
    event.preventDefault()
  } else if (event.key === 'ArrowRight') {
    select(current.value + 1)
    event.preventDefault()
  } else if (event.key === 'ArrowLeft') {
    select(current.value - 1)
    event.preventDefault()
  }
}

// Same as in game: scrolling down moves to the next slot, wrapping around.
function onWheel(event: WheelEvent) {
  if (!props.interactive || event.deltaY === 0) return
  event.preventDefault()
  select(current.value + (event.deltaY > 0 ? 1 : -1))
}

const status = computed(() => {
  const slot = slots.value[current.value]
  return slot.id ? `Next shot: ${itemName(slot.id)}` : 'No arrow in selected quiver slot.'
})
</script>

<template>
  <figure class="at-quiver">
    <div
      class="panel"
      :tabindex="interactive ? 0 : undefined"
      :aria-label="interactive ? 'Quiver menu demo. Press 1 to 9, use the arrow keys or scroll to change the selected slot.' : 'Quiver menu'"
      @keydown="interactive && onKey($event)"
      @wheel="onWheel"
    >
      <div class="header">
        <span class="title">Quiver</span>
        <button
          class="toggle"
          type="button"
          :title="collect ? 'Arrows collected go to the quiver' : 'Arrows collected go to the inventory'"
          :disabled="!interactive"
          @click="collect = !collect"
        >
          {{ collect ? 'Q' : 'I' }}
        </button>
      </div>
      <div class="row">
        <button
          v-for="(slot, i) in slots"
          :key="i"
          class="cell"
          type="button"
          tabindex="-1"
          :aria-pressed="i === current"
          :aria-label="`Slot ${i + 1}${slot.id ? `: ${itemName(slot.id)}` : ', empty'}`"
          @click="select(i)"
        >
          <ItemSlot :id="slot.id" :count="slot.count" :size="40" :selected="i === current" />
        </button>
      </div>
    </div>
    <figcaption v-if="interactive" class="caption">
      <span class="status" :class="{ empty: !slots[current].id }">{{ status }}</span>
      <span class="hint">
        Click a slot, press <kbd>1</kbd>–<kbd>9</kbd> or scroll. Pickup:
        <strong>{{ collect ? 'quiver' : 'inventory' }}</strong>
      </span>
    </figcaption>
  </figure>
</template>

<style scoped>
.at-quiver {
  margin: 16px 0;
}

.panel {
  display: inline-block;
  max-width: 100%;
  padding: 6px 8px 10px;
  background: var(--at-panel);
  border: 3px solid;
  border-color: #fff #555 #555 #fff;
  border-radius: 4px;
  box-shadow: 0 0 0 2px #000;
  overflow-x: auto;
  outline: none;
}

.panel:focus-visible {
  box-shadow:
    0 0 0 2px #000,
    0 0 0 5px var(--vp-c-brand-1);
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 4px;
}

.title {
  font: 18px/1 'Minecraft', var(--vp-font-family-mono);
  color: #404040;
}

.toggle {
  width: 22px;
  height: 22px;
  font: 12px/1 'Minecraft', var(--vp-font-family-mono);
  color: #fff;
  text-shadow: 1px 1px 0 #3f3f3f;
  background: #6f6f6f;
  border: 2px solid;
  border-color: #aaa #2b2b2b #2b2b2b #aaa;
  cursor: pointer;
}

.toggle:hover:not(:disabled) {
  background: #7e8bbd;
}

.row {
  display: flex;
}

.cell {
  display: flex;
  padding: 0;
  background: none;
  border: 0;
  cursor: pointer;
}

@media (max-width: 420px) {
  .panel {
    --at-slot-size: 34px;
    padding: 4px 5px 8px;
  }
}

.caption {
  display: flex;
  flex-wrap: wrap;
  gap: 4px 16px;
  margin-top: 8px;
  font-size: 14px;
  color: var(--vp-c-text-2);
}

.status {
  font-weight: 600;
  color: var(--vp-c-text-1);
}

.status.empty {
  color: var(--vp-c-danger-1);
}
</style>
