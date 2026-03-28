# 🐝 PollinatorPlus

**PollinatorPlus** is a lightweight Spigot/Paper plugin that fixes natural mob
behaviors being blocked by other plugins — most notably **PlotSquared**.

Developed by **Baumkrieger69**.

---

## ❓ Why does this plugin exist?

**PlotSquared** cancels many entity events inside and around plots to protect
builds and prevent griefing. Unfortunately this also blocks completely natural
and harmless mob behaviors:

| Mob | Blocked Behavior | Event |
|---------|----------------------------------|--------------------------|
| 🐝 Bee | Entering beehive / nest | `EntityEnterBlockEvent` |
| 🐝 Bee | Spawning from beehive | `CreatureSpawnEvent` |
| 🐸 Frog | Laying frogspawn eggs | `EntityChangeBlockEvent` |
| 🐸 Frog | Hatching from eggs | `CreatureSpawnEvent` |
| 🐢 Turtle | Laying turtle eggs | `EntityChangeBlockEvent` |
| 🐢 Turtle | Hatching from eggs | `CreatureSpawnEvent` |

PollinatorPlus listens at `HIGHEST` priority **after** PlotSquared has cancelled
these events and simply un-cancels them — so the mobs behave naturally again
without compromising plot protection.

---

## ✨ Features

- ✅ Fixes cancelled **Bee** hive entering (`EntityEnterBlockEvent`)
- ✅ Fixes cancelled **Bee** spawning from beehives (`CreatureSpawnEvent`)
- ✅ Fixes cancelled **Frog** & **Turtle** egg laying (`EntityChangeBlockEvent`)
- ✅ Fixes cancelled **Frog** & **Turtle** spawning (`CreatureSpawnEvent`)
- ✅ In-game config reload command
- ✅ Per-event toggle in config
- ✅ Debug logging mode

---

## 📋 Requirements

- Spigot / Paper **1.21+**
- Java **17+**
- PlotSquared *(or any other plugin that cancels these events)*

---

## 🚀 Installation

1. Download the `PollinatorPlus.jar`
2. Place it in your server's `/plugins` folder
3. Restart the server
4. Done — no PlotSquared configuration needed!

---

## ⚙️ Configuration (`config.yml`)
```yaml
debug: false
handle-entity-change-block: true
handle-creature-spawn: true
handle-entity-enter-block: true
```

| Option                       | Default | Description                                    |
|------------------------------|---------|------------------------------------------------|
| `debug`                      | `false` | Enables detailed debug logging in console      |
| `handle-entity-change-block` | `true`  | Fixes Frog/Turtle egg laying & Bee hive events |
| `handle-creature-spawn`      | `true`  | Fixes Frog/Turtle hatching & Bee hive spawning |
| `handle-entity-enter-block`  | `true`  | Fixes Bee entering beehive/nest                |

---

## 💬 Commands

| Command             | Description         | Permission              |
|---------------------|---------------------|-------------------------|
| `/pollinatorplus`   | Reloads the config  | `pollinatorplus.reload` |

---

## 🔐 Permissions

| Permission               | Default | Description                  |
|--------------------------|---------|------------------------------|
| `pollinatorplus.reload`  | OP      | Allows reloading the config  |

---

## 🔧 How it works

PollinatorPlus registers all its events at `HIGHEST` priority with
`ignoreCancelled = false`. This means it runs **after** PlotSquared
(which uses `NORMAL` or `HIGH` priority) and can selectively un-cancel
only the specific harmless natural behaviors listed above.
Plot protection for players remains completely intact. ✅

---

## 🧪 Tested Configuration

This plugin was developed and tested under the following specific server setup.
**Other configurations may or may not work as expected.**

| Setting | Value | Where |
|---|---|---|
| `doMobSpawning` gamerule | `true` | Minecraft Gamerule |
| Mob Spawning | `deny` | WorldGuard Region Flag |

> ⚠️ **Important:** The plugin was tested with **mob spawning enabled via gamerule**
> but **blocked by WorldGuard** on a per-region basis, combined with PlotSquared.
> This is the exact setup it was built to fix.
> Behavior under other configurations (e.g. `doMobSpawning false`, no WorldGuard,
> different protection plugins) has **not been tested** and may differ.

---

## 🔧 Compatibility

| Plugin / Setting | Status |
|---|---|
| PlotSquared | ✅ Fully compatible & designed for it |
| WorldGuard (`mob-spawning deny`) | ✅ Tested & working |
| WorldGuard (`mob-spawning allow`) | ⚠️ Not tested |
| GriefPrevention | ⚠️ Not tested |
| `doMobSpawning true` | ✅ Tested |
| `doMobSpawning false` | ⚠️ Not tested — likely breaks behavior |
| Any other protection plugin | ⚠️ Untested, may work |
