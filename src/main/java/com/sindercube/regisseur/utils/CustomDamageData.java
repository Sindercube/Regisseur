package com.sindercube.regisseur.utils;

import net.minecraft.nbt.NbtCompound;

public class CustomDamageData {

    public int damage = 0;
    public int durability = 0;
    public int maxDamage = 0;

    public CustomDamageData(NbtCompound nbt) {
        if (nbt == null) return;
        if (!nbt.contains("nucleus")) return;

        NbtCompound nucleusData = nbt.getCompound("nucleus");
        if (!nucleusData.contains("durability")) return;

        int[] data = nucleusData.getIntArray("durability");
        if (data.length < 1) return;

        this.durability = data[0];
        this.maxDamage = data[1];
        this.damage = maxDamage - durability;
    }

    public boolean exists() {
        return this.damage != 0 && this.durability != 0;
    }

}
