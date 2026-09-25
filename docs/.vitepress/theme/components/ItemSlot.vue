<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { itemIcon, itemName } from '../archerythings'

const props = withDefaults(
  defineProps<{ id?: string | null; count?: number; label?: boolean | string; size?: number; selected?: boolean }>(),
  { id: null, count: 1, label: false, size: 36, selected: false },
)

const name = computed(() => (props.id ? itemName(props.id) : ''))
const labelText = computed(() => (typeof props.label === 'string' ? props.label : name.value))
const src = computed(() => (props.id ? itemIcon(props.id) : null))

// Falls back to initials when an item has no icon or the hosted icon fails to load.
const failed = ref(false)
watch(src, () => (failed.value = false))
const initials = computed(() =>
  name.value
    .split(' ')
    .filter((word) => /^[A-Z]/.test(word))
    .slice(0, 2)
    .map((word) => word[0])
    .join(''),
)
</script>

<template>
  <span class="at-item" :class="{ 'with-label': label }">
    <span
      class="at-slot"
      :class="{ selected }"
      :style="{ '--default-size': `${size}px` }"
      :title="name"
      :aria-label="name"
      role="img"
    >
      <img v-if="src && !failed" class="pixelated" :src="src" alt="" loading="lazy" @error="failed = true" />
      <span v-else-if="id" class="at-initials">{{ initials }}</span>
      <span v-if="count > 1" class="at-count">{{ count }}</span>
    </span>
    <span v-if="label && id" class="at-label">{{ labelText }}</span>
  </span>
</template>

<style scoped>
.at-item {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  vertical-align: middle;
}

/* A parent can set --at-slot-size to resize every slot inside it, e.g. on narrow screens. */
.at-slot {
  --size: var(--at-slot-size, var(--default-size));
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: var(--size);
  height: var(--size);
  flex: none;
  background: var(--at-slot-bg);
  border: 2px solid;
  border-color: var(--at-slot-dark) var(--at-slot-light) var(--at-slot-light) var(--at-slot-dark);
}

/* The in-game selection outline. */
.at-slot.selected {
  outline: 2px solid var(--at-selected);
  outline-offset: -1px;
  z-index: 1;
}

.at-slot img {
  width: calc(var(--size) - 4px);
  height: calc(var(--size) - 4px);
}

.at-initials {
  font: 600 12px/1 var(--vp-font-family-mono);
  color: #fff;
  text-shadow: 1px 1px 0 #3f3f3f;
}

.at-count {
  position: absolute;
  right: 1px;
  bottom: -1px;
  font: 16px/1 'Minecraft', var(--vp-font-family-mono);
  color: #fff;
  text-shadow: 2px 2px 0 #3f3f3f;
}

.at-label {
  font-weight: 500;
}
</style>
