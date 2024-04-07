package com.sindercube.regisseur.mixin;

import com.mojang.serialization.Codec;
import com.sindercube.regisseur.utils.CustomDamageData;
import com.sindercube.regisseur.utils.RegisseurCodecs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow @Mutable @Final public static Codec<ItemStack> RECIPE_RESULT_CODEC;
    @Shadow @Nullable private NbtCompound nbt;
    @Shadow public abstract Item getItem();
    @Shadow public abstract int getDamage();
    @Shadow public abstract int getMaxDamage();
    @Shadow public abstract boolean isDamaged();

    @Shadow public abstract boolean isEmpty();

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void replaceResultCodec(CallbackInfo ci) {
        RECIPE_RESULT_CODEC = RegisseurCodecs.ITEM_STACK_CODEC;
    }


    @Inject(method = "isDamageable", at = @At("RETURN"), cancellable = true)
    private void isCustomDamageable(CallbackInfoReturnable<Boolean> cir) {
        if (this.isEmpty() || this.getMaxDamage() == 0) cir.setReturnValue(false);

        CustomDamageData data = new CustomDamageData(nbt);
        if (nbt != null && !data.exists() && nbt.contains("Unbreakable")) cir.setReturnValue(false);

        cir.setReturnValue(true);
    }

    @Inject(method = "isDamaged", at = @At("RETURN"), cancellable = true)
    private void isCustomDamaged(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.getDamage() > 0);
    }

    @Inject(method = "isItemBarVisible", at = @At("RETURN"), cancellable = true)
    private void isCustomItemBarVisible(CallbackInfoReturnable<Boolean> cir) {
        CustomDamageData data = new CustomDamageData(nbt);
        if (data.exists()) cir.setReturnValue(this.isDamaged());
    }

    @Inject(method = "getDamage", at = @At("RETURN"), cancellable = true)
    private void getCustomDamage(CallbackInfoReturnable<Integer> cir) {
        CustomDamageData data = new CustomDamageData(nbt);
        if (data.exists()) cir.setReturnValue(data.damage);
    }

    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void getCustomMaxDamage(CallbackInfoReturnable<Integer> cir) {
        CustomDamageData data = new CustomDamageData(nbt);
        if (data.exists()) cir.setReturnValue(data.maxDamage);
    }


    @Inject(method = "getItemBarStep", at = @At("RETURN"), cancellable = true)
    private void getCustomItemBarStep(CallbackInfoReturnable<Integer> cir) {
        int result = Math.round(13.0F - (float)this.getDamage() * 13.0F / (float)this.getMaxDamage());
        cir.setReturnValue(result);
    }

    @Inject(method = "getItemBarColor", at = @At("RETURN"), cancellable = true)
    private void getCustomItemBarColor(CallbackInfoReturnable<Integer> cir) {
        float f = Math.max(0.0F, ((float)this.getMaxDamage() - (float)this.getDamage()) / (float)this.getMaxDamage());
        int result = MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
        cir.setReturnValue(result);
    }

}
