# The quiver

<ItemSlot id="archerythings:quiver" :size="56" label />

The quiver stores up to nine stacks of ammo and lets you choose which one you shoot. It doesn't stack, and you can wear it in your chest slot, where it shows on your back.

## Crafting

<RecipeCard id="quiver" />

## What it holds

Each of the nine slots takes one stack of:

<p>
  <ItemSlot id="minecraft:arrow" label /> &nbsp;
  <ItemSlot id="minecraft:spectral_arrow" label /> &nbsp;
  <ItemSlot id="minecraft:firework_rocket" label="Firework Rocket (crossbows)" />
</p>

Tipped arrows and arrows from other mods also fit. Other items can't go in.

Hover over a quiver in your inventory to see what's in it. The tooltip lists the first five stacks and counts the rest.

## Shooting

When you draw a bow or load a crossbow with a quiver equipped, it uses the ammo in the **selected slot**. Firing uses one item from that slot, unless your bow has Infinity.

If the selected slot is empty, you'll see **No arrow in selected quiver slot.** above your hotbar and the weapon goes back to the vanilla behaviour: it uses the first arrow it finds in your inventory.

::: tip Which quiver is used
If you wear more than one quiver, the mod checks them in this order and uses the first one whose selected slot has ammo:

1. a quiver in your chest slot
2. a quiver [bound to your chestplate](./armor)
3. a quiver bound to your leggings
4. a quiver in a [Curios or Trinkets slot](./compat)
:::

## Dyeing

Dye a quiver just like leather armor: put it in a crafting grid with one or more dyes. Mixing dyes blends their colours, and you can dye it again to change it. The [armor colour calculator](https://minecraft.wiki/w/Calculators/Armor_color) shows which dyes make the colour you want.

<DyeSwatches />
