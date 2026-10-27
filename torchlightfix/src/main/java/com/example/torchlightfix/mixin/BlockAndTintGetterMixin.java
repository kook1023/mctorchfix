package com.example.torchlightfix.mixin;

import com.example.torchlightfix.HeldTorchLight;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockAndTintGetter.class, remap = false)
public interface BlockAndTintGetterMixin {

    @Inject(method = "getRawBrightness(Lnet/minecraft/core/BlockPos;I)I",
            at = @At("RETURN"), cancellable = true, require = 0)
    private void torchlightfix$raw(BlockPos pos, int amount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(HeldTorchLight.apply(this, pos, cir.getReturnValue()));
    }

    @Inject(method = "getBrightness(Lnet/minecraft/world/level/LightLayer;Lnet/minecraft/core/BlockPos;)I",
            at = @At("RETURN"), cancellable = true, require = 0)
    private void torchlightfix$layer(LightLayer layer, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (layer == LightLayer.BLOCK) {
            cir.setReturnValue(HeldTorchLight.apply(this, pos, cir.getReturnValue()));
        }
    }
}
