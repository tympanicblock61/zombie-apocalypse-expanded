package net.tympanic.apoca.util;

import net.minecraft.nbt.NbtCompound;

public class LootChanceData {
    public static double addLootChance(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        double LootChance = nbt.getDouble("loot-chance");
        if(LootChance + amount >= 100.0) {
            LootChance = 100.0;
        } else {
            LootChance += amount;
        }
        nbt.putDouble("loot-chance", LootChance);
        return LootChance;
    }

    public static double removeLootChance(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        double LootChance = nbt.getDouble("loot-chance");
        if(LootChance - amount <= 0.0) {
            LootChance = 0.0;
        } else {
            LootChance -= amount;
        }
        nbt.putDouble("loot-chance", LootChance);
        return LootChance;
    }
}
