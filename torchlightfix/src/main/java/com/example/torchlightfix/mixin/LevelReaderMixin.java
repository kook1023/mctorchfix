package com.example.torchlightfix.mixin;

import com.example.torchlightfix.HeldTorchLight;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LevelReader.class, remap = false)
public interface LevelReaderMixin {

    @Inject(method = "getMaxLocalRawBrightness(Lnet/minecraft/core/BlockPos;)I",
            at = @At("RETURN"), cancellable = true, require = 0)
    private void torchlightfix$max1(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(HeldTorchLight.apply(this, pos, cir.getReturnValue()));
    }

    @Inject(method = "getMaxLocalRawBrightness(Lnet/minecraft/core/BlockPos;I)I",
            at = @At("RETURN"), cancellable = true, require = 0)
    private void torchlightfix$max2(BlockPos pos, int amount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(HeldTorchLight.apply(this, pos, cir.getReturnValue()));
    }
}
