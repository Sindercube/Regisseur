package com.sindercube.regisseur.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getItemBarStep", at = @At("RETURN"), cancellable = true)
    private void getCustomItemBarStep(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int result = Math.round(13.0F - (float)stack.getDamage() * 13.0F / (float)stack.getMaxDamage());
        cir.setReturnValue(result);
    }

    @Inject(method = "getItemBarColor", at = @At("RETURN"), cancellable = true)
    private void getCustomItemBarColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        float f = Math.max(0.0F, ((float)stack.getMaxDamage() - (float)stack.getDamage()) / (float)stack.getMaxDamage());
        int result = MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
        cir.setReturnValue(result);
    }

}
