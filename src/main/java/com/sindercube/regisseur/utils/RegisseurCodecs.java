package com.sindercube.regisseur.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public class RegisseurCodecs {

    public static ItemStack getItemStack(ItemConvertible item, int count, Optional<NbtCompound> nbt) {
        ItemStack stack = new ItemStack(item, count);
        nbt.ifPresent(stack::setNbt);
        return stack;
    }

    public static Ingredient.StackEntry getStackEntry(ItemStack stack, Optional<NbtCompound> nbt) {
        nbt.ifPresent(stack::setNbt);
        return new Ingredient.StackEntry(stack);
    }

    public static final Codec<ItemStack> ITEM_STACK_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ItemStack.ITEM_CODEC.fieldOf("item").forGetter(ItemStack::getItem),
            Codecs.createStrictOptionalFieldCodec(Codecs.POSITIVE_INT, "count", 1).forGetter(ItemStack::getCount),
            NbtCompound.CODEC.optionalFieldOf("data").forGetter((stack) -> Optional.ofNullable(stack.getNbt()))
    ).apply(instance, RegisseurCodecs::getItemStack));

    public static final Codec<Ingredient.StackEntry> STACK_ENTRY_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ItemStack.INGREDIENT_ENTRY_CODEC.fieldOf("item").forGetter(Ingredient.StackEntry::stack),
            NbtCompound.CODEC.optionalFieldOf("data").forGetter(value -> Optional.ofNullable(value.stack().getNbt()))
    ).apply(instance, RegisseurCodecs::getStackEntry));

}
