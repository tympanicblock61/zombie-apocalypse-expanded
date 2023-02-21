package net.tympanic.apoca.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.tympanic.apoca.Main;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup BACKPACK;

    public static void registerItemGroups() {
        BACKPACK = FabricItemGroup.builder(new Identifier(Main.ModId, "backpack_1")).displayName(Text.translatable("itemgroup.test")).icon(() -> new ItemStack(ModItems.BACKPACK)).build();
    }
}