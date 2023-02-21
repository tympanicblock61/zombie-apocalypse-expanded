package net.tympanic.apoca.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.text.Text;
import net.tympanic.apoca.item.ModItemGroup;
import net.tympanic.apoca.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tympanic.apoca.screens.LootGenScreen;

public class ModItems {
    public static Item BACKPACK = registerItem("backpack_1", new BackpackItem(new FabricItemSettings(), 10, 10));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Main.ModId, name), item);
    }

    public static void addItemsToItemGroup() {
        //addToItemGroup(ItemGroups.INGREDIENTS, BACKPACK);

        addToItemGroup(ModItemGroup.BACKPACK, BACKPACK);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }
    public static void registerModItems() {
        Main.LOGGER.info("Registering Mod Items for " + Main.ModId);
        addItemsToItemGroup();
    }
}