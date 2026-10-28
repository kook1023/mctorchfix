package com.example.torchlightfix;

import net.fabricmc.api.ModInitializer;

public class TorchLightFix implements ModInitializer {
    @Override
    public void onInitialize() {
        TorchLightConfig.load();
    }
}
