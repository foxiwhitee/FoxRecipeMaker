package foxiwhitee.FoxRecipeMaker.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import foxiwhitee.FoxRecipeMaker.FoxRecipeMaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class CustomButton extends GuiButton {
    private final int buttonId;
    private int u;
    private int v;

    public CustomButton(int buttonId, int x, int y) {
        this(buttonId, x, y, 0, 0, true, true);
    }
    public CustomButton(int buttonId, int x, int y, int u, int v) {
        this(buttonId, x, y, u, v, true, true);
    }

    public CustomButton(int buttonId, int x, int y, int u, int v, boolean enabled, boolean visible) {
        super(buttonId, x, y, 32, 32, "");
        this.enabled = enabled;
        this.visible = visible;
        this.buttonId = buttonId;
        this.u = u;
        this.v = v;
    }

    @SideOnly(Side.CLIENT)
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(new ResourceLocation(FoxRecipeMaker.MODID.toLowerCase(), "textures/gui/confirm.png"));
            this.enabled = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            int k = getHoverState(this.enabled);

            drawTexturedModalRect(this.xPosition, this.yPosition, this.u + 16 * k, this.v, 32, 32);
        }
    }
}

