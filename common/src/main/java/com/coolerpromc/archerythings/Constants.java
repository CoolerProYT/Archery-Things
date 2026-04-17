package com.coolerpromc.archerythings;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MODID = "archerythings";
	public static final Logger LOG = LoggerFactory.getLogger(MODID);

	public static Identifier id(String path){
		return Identifier.fromNamespaceAndPath(MODID, path);
	}
}