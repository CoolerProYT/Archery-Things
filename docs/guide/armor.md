# Binding to armor

A quiver takes up your chest slot. To wear a chestplate and still use a quiver, **bind** the quiver to your armor at an anvil.

## Binding a quiver

Put a chestplate, leggings or anything else worn on the chest or legs (such as an elytra) in the left slot of an anvil, and the quiver in the right slot.

<div class="at-anvil">
  <ItemSlot id="minecraft:iron_chestplate" />
  <span class="op">+</span>
  <ItemSlot id="archerythings:quiver" />
  <span class="op">Anvil ➜</span>
  <ItemSlot id="minecraft:iron_chestplate" label="Iron Chestplate with Quiver" />
</div>

- Binding costs **1 level**, plus 1 more if you rename the item at the same time.
- The quiver keeps its arrows, selected slot, pickup setting and colour.
- Each piece of armor can hold **one** quiver.

A bound item has **with Quiver** after its name, lists the quiver's contents in its tooltip, and shows a small quiver icon in the corner of its slot.

## Using it

Armor with a bound quiver works just like a worn quiver:

- wear it and press <kbd>R</kbd> to open the quiver menu
- hold it and right-click to open the menu (sneak and right-click to equip it)
- shoot, and the bow uses the quiver's selected slot
- picked-up arrows go into the bound quiver

If both your chestplate and leggings have a quiver, the chestplate's is used first. See [which quiver is used](./quiver#shooting).

## Getting the quiver back

Drop the armor on the ground and **drop an anvil on it**. When the falling anvil lands, the quiver pops out as its own item, with its arrows still inside. The armor keeps its name and enchantments, and is not damaged.

<style scoped>
.at-anvil {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 12px;
  margin: 16px 0;
  padding: 12px 16px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 10px;
  background: var(--vp-c-bg-soft);
}
.at-anvil .op {
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 0.04em;
  color: var(--vp-c-text-2);
}
</style>
