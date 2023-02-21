package net.tympanic.apoca.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item {
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public Integer InvHeight;
    public Integer InvWidth;

    public BackpackItem(Settings settings, Integer InvHeight, Integer InvWidth) {
        super(settings);
        this.InvHeight = InvHeight;
        this.InvWidth = InvWidth;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Screen test = new Screen(Text.of("test")){
            @Override
            public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
                renderBackground(matrices);
                System.out.printf("height: %s, width: %s", InvHeight, InvWidth);
            }

        };

        mc.setScreenAndRender(test);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public static void test(){



    }
}
