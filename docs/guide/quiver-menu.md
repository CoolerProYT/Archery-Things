# Quiver menu

The quiver menu is where you fill the quiver, choose your ammo and set where picked-up arrows go.

<QuiverMenu items="arrow*64,spectral_arrow*15,arrow*32,,firework_rocket*32" :selected="1" />

In game it looks like this:

![Quiver menu in game](/quiver_screen.png){.pixelated}

## Opening it

| You are… | Do this |
| --- | --- |
| Holding a quiver | Right-click |
| Holding a chestplate or leggings with a [bound quiver](./armor) | Right-click |
| Wearing a quiver, or armor with a bound quiver | Press <kbd>R</kbd> |
| Using a [Curios or Trinkets](./compat) quiver slot | Press <kbd>R</kbd> |

Sneak and right-click to equip the item instead of opening the menu.

You can change the <kbd>R</kbd> key under **Options › Controls › Key Binds › Archery Things › Open Quiver Menu**.

::: warning Chestplate first
If both your chestplate and your leggings have a quiver bound to them, <kbd>R</kbd> always opens the chestplate's quiver.
:::

## Selecting a slot

While the menu is open:

- press <kbd>1</kbd>–<kbd>9</kbd> to select that slot, or
- scroll down to move to the next slot and up to move back. It wraps from the last slot to the first.

The selected slot has an **orange outline**, and it's saved on the quiver. If it's empty, bows fall back to using arrows from your inventory. See [Shooting](./quiver#shooting).

## Arrow pickup

The small button in the top-right corner of the menu sets where arrows go when you pick them up:

| Button | Picked-up arrows go to |
| --- | --- |
| **Q** | The quiver (default) |
| **I** | Your inventory, like vanilla |

With **Q**, a picked-up arrow first tops up a slot with the same kind of arrow. If there isn't one, it goes in the first empty slot. If the quiver is full, it goes to your inventory. Each quiver remembers its own setting. Arrow pickup into the quiver is turned off in Creative mode.
