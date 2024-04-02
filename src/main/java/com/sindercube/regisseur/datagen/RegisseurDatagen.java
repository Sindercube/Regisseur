package com.sindercube.regisseur.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.MinecraftClient;

public class RegisseurDatagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		System.out.println(MinecraftClient.getInstance().getServer());
		System.out.println(RegisseurServerDatagen.SERVER_INSTANCE);
	}

}
