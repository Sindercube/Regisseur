package com.sindercube.regisseur;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Regisseur implements ModInitializer {

	public static final String MOD_ID = "regisseur";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized!");
	}

}