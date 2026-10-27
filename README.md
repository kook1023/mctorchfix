# Torchlight Fix

TorchlightFix is a small Fabric mod that helps prevent darkness-based events from triggering in mods that check Minecraft's light levels.

## What does it do?

When you hold a torch in your main hand or off-hand, TorchlightFix makes supported light-level checks return at least **light level 5** when the queried position is at your feet or eye height.

This can help with mods that use light levels to determine whether a player is in darkness, such as **The Murk – Darkness Consumes**.

## Important

- TorchlightFix does **not** create real light or illuminate the surrounding area.
- It changes the light value returned by the checks it hooks into.
- Compatibility depends on how another mod checks light levels.

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
5. Launch the game and test it with the mods you want to use.

## Compatibility

TorchlightFix has been tested with **The Murk – Darkness Consumes**. Other mods may behave differently depending on which light-level methods they use.

## Feedback

If a mod that relies on light levels still triggers darkness-based events while you're holding a torch, report which mod and Minecraft version you're using, along with your log if available.

## Bug Reports

Found a bug or compatibility issue? [Open an issue Here](https://github.com/kook1023/mctorchfix/issues).

Please include your Minecraft version, Fabric Loader version, the mod you're using TorchlightFix with, and what happened. Crash logs are helpful if available.

I'll look into reports as time allows.
