# Torchlight Fix

TorchlightFix is a small Fabric mod that helps prevent darkness-based events from triggering in mods that check Minecraft's light levels.

## ## What does it do?

When you hold a light source in your main hand or off-hand, Torch Light Fix makes supported light-level checks return at least light level 5 (configurable) when the queried position is at your feet or eye height. Torches, lanterns, campfires, glowstone and more all count, and you can add or remove items in the config.

It only changes what other mods read. No real light is added to the world, and nothing is placed or removed.
This can help with mods that use light levels to determine whether a player is in darkness, such as The Murk – Darkness Consumes.

Only the host or server needs this mod. Players joining don't need it, and it works in singleplayer and on LAN worlds too.

## Important

- TorchlightFix does **not** create real light or illuminate the surrounding area.
- It changes the light value returned by the checks it hooks into.
- Compatibility depends on how another mod checks light levels.
- New default items won’t appear in an existing config. Delete torchlightfix.json to regenerate it.

## Recommended setup

Use TorchlightFix alongside a mod that provides dynamic lighting for held torches. The dynamic-lighting mod makes the torch visibly illuminate your surroundings, while TorchlightFix adjusts the light-level checks used by compatible mods.

## Requirements

- Minecraft **26.2**
- Fabric Loader
- Any dependencies required by your chosen dynamic-lighting mod

## Installation

1. Install Fabric for Minecraft 26.2.
2. Download the compiled `torchlightfix` JAR.
3. Place the JAR in your Minecraft `mods` folder.
4. Install any required dependencies for your other mods.
5. Launch the game once. The mod creates `config/torchlightfix.json` on first launch.
   Edit it to change the light level and which items count as light sources, then restart the game.
   Test it with the mods you want to use.

## Compatibility

TorchlightFix has been tested with **The Murk – Darkness Consumes**. Other mods may behave differently depending on which light-level methods they use.

## Feedback

If a mod that relies on light levels still triggers darkness-based events while you're holding a torch, report which mod and Minecraft version you're using, along with your log if available.

## Bug Reports

Found a bug or compatibility issue? [Open an issue Here](https://github.com/kook1023/mctorchfix/issues).

Please include your Minecraft version, Fabric Loader version, the mod you're using TorchlightFix with, and what happened. Crash logs are helpful if available.

I'll look into reports as time allows.
