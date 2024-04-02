package com.sindercube.regisseur.mixin;

import com.mojang.serialization.Codec;
import com.sindercube.regisseur.utils.RegisseurCodecs;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ingredient.StackEntry.class)
public abstract class IngredientStackEntryMixin {

    @Shadow @Mutable @Final static Codec<Ingredient.StackEntry> CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void replaceResultCodec(CallbackInfo ci) {
        CODEC = RegisseurCodecs.STACK_ENTRY_CODEC;
    }

}
