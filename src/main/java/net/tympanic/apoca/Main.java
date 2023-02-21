package net.tympanic.apoca;

import net.fabricmc.api.ModInitializer;
import net.tympanic.apoca.block.LootPile;
import net.tympanic.apoca.block.ModBlocks;
import net.tympanic.apoca.item.ModItems;
import net.tympanic.apoca.item.ModItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final String ModId = "apoca";
	public static final Logger LOGGER = LoggerFactory.getLogger(ModId);

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		LootPile.registerTiers();
	}
}
