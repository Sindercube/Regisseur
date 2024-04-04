package com.sindercube.regisseur.utils;

import net.minecraft.nbt.NbtCompound;

public class DamageData {

    public static int[] customDamageData(NbtCompound nbt) {
        if (nbt == null) return new int[]{};
        if (!nbt.contains("nucleus")) return new int[]{};
        NbtCompound nucleusData = nbt.getCompound("nucleus");
        if (!nucleusData.contains("durability")) return new int[]{};
        return nucleusData.getIntArray("durability");
    }

}
