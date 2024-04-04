package com.sindercube.regisseur.mixin;

import com.mojang.serialization.Codec;
import com.sindercube.regisseur.utils.DamageData;
import com.sindercube.regisseur.utils.RegisseurCodecs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void replaceResultCodec(CallbackInfo ci) {
        RECIPE_RESULT_CODEC = RegisseurCodecs.ITEM_STACK_CODEC;
    }

    @Inject(method = "isDamaged", at = @At("RETURN"), cancellable = true)
    private void isCustomDamaged(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.getDamage() > 0);
    }

    @Inject(method = "isItemBarVisible", at = @At("RETURN"), cancellable = true)
    private void isCustomItemBarVisible(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(DamageData.customDamageData(nbt).length > 0);
    }

    @Inject(method = "getDamage", at = @At("RETURN"), cancellable = true)
    private void getCustomDamage(CallbackInfoReturnable<Integer> cir) {
        int[] durabilityData = DamageData.customDamageData(nbt);
        if (durabilityData.length > 0) cir.setReturnValue(durabilityData[1]-durabilityData[0]);
    }

    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void getCustomMaxDamage(CallbackInfoReturnable<Integer> cir) {
        int[] durabilityData = DamageData.customDamageData(nbt);
        if (durabilityData.length > 0) cir.setReturnValue(durabilityData[1]);
    }

}
