package net.tympanic.apoca.screens;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Iterator;
import java.util.Objects;

public class LootGenScreen extends Screen {
    public static MinecraftClient client = MinecraftClient.getInstance();
    private final BlockState state;

    public LootGenScreen(Text title, BlockState state) {
        super(title);
        this.state = state;
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }
    public void init() {
    }


    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        renderBackground(matrices);
        CheckboxWidget respawn = new CheckboxWidget(170, 100, 50,20, Text.of("respawn"),false, true);
        respawn.visible = true;
        respawn.render(matrices, mouseX, mouseY, delta);
        ButtonWidget test = new ButtonWidget
                .Builder(Text.of("test"), (e) -> {System.out.println("test");})
                .position(100, 100)
                .size(50, 20)
        .build();
        test.visible = true;
        test.render(matrices, mouseX, mouseY, delta);
    }
}
