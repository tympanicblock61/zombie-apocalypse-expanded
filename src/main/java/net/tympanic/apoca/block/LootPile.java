package net.tympanic.apoca.block;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.tympanic.apoca.util.WeightedRandom;

import java.util.ArrayList;
import java.util.Iterator;

import static net.minecraft.item.Items.*;


public class LootPile extends Block {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static ArrayList<ArrayList<Object>> respawns = new ArrayList<>();
    public static Iterator<ArrayList<Object>> it = respawns.iterator();
    private static final IntProperty Tier = IntProperty.of("tier", 0, 2);
    private static final BooleanProperty Respawn = BooleanProperty.of("respawn");
    private static final IntProperty RespawnTicks = IntProperty.of("respawn_ticks", 100, 500);

    public static ArrayList<NbtCompound> Tier1 = new ArrayList<>();
    public static ArrayList<NbtCompound> Tier2 = new ArrayList<>();
    public static ArrayList<NbtCompound> Tier3 = new ArrayList<>();

    public static void registerTiers() {
        Tier1.add(makeTierItem(DIRT, 100.0, new NbtCompound(), 1, true));
        Tier1.add(makeTierItem(COBBLESTONE, 10.0, new NbtCompound(), 5, true));
        Tier2.add(makeTierItem(IRON_INGOT, 100.0, new NbtCompound(), 5, true));
        Tier3.add(makeTierItem(ENCHANTED_GOLDEN_APPLE, 90.0, new NbtCompound(), 64, true));
        ServerTickEvents.START_SERVER_TICK.register(world1 -> {
            if (!respawns.isEmpty()) {
                for (Iterator<ArrayList<Object>> iterator = respawns.iterator(); iterator.hasNext(); ) {
                    try {ArrayList<Object> next = iterator.next();
                    System.out.println(next.toString());
                    if ((int) next.get(0) >= ((BlockState) next.get(3)).get(RespawnTicks)) {
                        System.out.println("ticks: " + next.get(0));
                        ((World) next.get(1)).setBlockState((BlockPos) next.get(2), (BlockState) next.get(3));
                        iterator.remove();
                    } else {
                        next.set(0, (int) next.get(0) + 1);
                    }} catch(Exception ignored) {}
                }
            }
        });
    }

    public static int calculateDropItem(ArrayList<NbtCompound> Tier) {
        WeightedRandom<Integer> Drops = new WeightedRandom<>();
        int num = 0;
        for (NbtCompound item : Tier) {
            if (item.getBoolean("unlocked")) {
                Drops.addEntry(num, item.getDouble("chance"));
                System.out.println(num);
                num += 1;
            }
        }
        int item = Drops.getRandom();
        System.out.println("drops: "+item);
        return item;
    }

    private static NbtCompound makeTierItem(Item item, double chance, NbtCompound nbt, int amount, boolean defaultUnlocked) {
        NbtCompound thing = new NbtCompound();
        thing.putInt("item", Item.getRawId(item));
        thing.putDouble("chance", chance);
        thing.putInt("amount", amount);
        thing.putBoolean("unlocked", defaultUnlocked);
        thing.put("nbt", nbt);
        return thing;
    }

    private static void dropItem(World world, BlockPos pos, ArrayList<NbtCompound> Tier, int index) {
        System.out.println(index);
        ItemStack item = new ItemStack(Item.byRawId(Tier.get(index).getInt("item")));
        item.setCount(Tier.get(index).getInt("amount"));
        item.setNbt((NbtCompound) Tier.get(index).get("nbt"));
        ItemEntity entity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), item);
        world.spawnEntity(entity);
    }

    public LootPile(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Tier, 0));
        setDefaultState(getDefaultState().with(Respawn, false));
        setDefaultState(getDefaultState().with(RespawnTicks, 100));
    }

    @Override
    public void onBreak(World w, BlockPos p, BlockState b, PlayerEntity player) {
        switch (b.get(Tier)) {
            default -> dropItem(w, p, Tier1, calculateDropItem(Tier1));
            case 1 -> dropItem(w, p, Tier2, calculateDropItem(Tier2));
            case 2 -> dropItem(w, p, Tier3, calculateDropItem(Tier3));
        }
        if (b.get(Respawn)) {
            ArrayList<Object> block = new ArrayList<>();
            block.add(0);
            block.add(w);
            block.add(p);
            block.add(b);
            respawns.add(block);
        }
        player.sendMessage(Text.of("broken"));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //try {client.setScreen(new LootGenScreen(Text.of("test"), state));} catch(Exception ignored) {}
        if (player.hasPermissionLevel(2)) {
            if (player.isSneaking()) {
                world.setBlockState(pos, state.with(Respawn, !state.get(Respawn)));
                player.sendMessage(Text.of("loot gen respawning set to %s".formatted(world.getBlockState(pos).get(Respawn))), true);
            } else {
                player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
                if (state.get(Tier) >= 2) {
                    world.setBlockState(pos, state.with(Tier, 0));
                } else {
                    world.setBlockState(pos, state.with(Tier, state.get(Tier) + 1));
                }
                player.sendMessage(Text.of("blocks drop tier is now %s".formatted(world.getBlockState(pos).get(Tier))));
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.2f, 1f);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Tier);
        builder.add(Respawn);
        builder.add(RespawnTicks);
    }
}
