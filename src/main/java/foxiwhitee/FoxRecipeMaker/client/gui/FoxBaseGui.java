package foxiwhitee.FoxRecipeMaker.client.gui;

import foxiwhitee.FoxRecipeMaker.FoxRecipeMaker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class FoxBaseGui extends GuiContainer {
    private int textureSizeX = 512, textureSizeY = 512;

    public FoxBaseGui(Container container) {
        super(container);
    }

    public FoxBaseGui(Container container, int xSize, int ySize) {
        super(container);
        this.ySize = ySize;
        this.xSize = xSize;
    }

    public void drawForeground(int offsetX, int offsetY, int mouseX, int mouseY) {}

    public void drawBackground(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FoxRecipeMaker.MODID.toLowerCase(), this.getBackground());
        Utility.drawTexture(offsetX, offsetY, 0, 0, xSize, ySize, xSize, ySize, textureSizeX, textureSizeY);
    }

    public void bindTexture(String base, String file) {
        ResourceLocation loc = new ResourceLocation(base, "textures/" + file);
        this.mc.getTextureManager().bindTexture(loc);
    }

    protected abstract String getBackground();

    protected final void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        int ox = this.guiLeft;
        int oy = this.guiTop;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawBackground(ox, oy, x, y);
    }

    protected final void drawGuiContainerForegroundLayer(int x, int y) {
        int ox = this.guiLeft;
        int oy = this.guiTop;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawForeground(ox, oy, x, y);
    }

    public final void drawIfInMouse(int mouseX, int mouseY, int x, int y, int w, int h, String... str) {
        if (mouseX >= this.guiLeft + x && mouseX <= this.guiLeft + x + w && mouseY >= this.guiTop + y && mouseY <= this.guiTop + y + h)
            drawHoveringText(new ArrayList<>(Arrays.asList(Arrays.copyOf((Object[]) str, str.length))), mouseX, mouseY, this.mc.fontRenderer);
    }

    protected void setTextureSizeX(int textureSizeX) {
        this.textureSizeX = textureSizeX;
    }

    protected void setTextureSizeY(int textureSizeY) {
        this.textureSizeY = textureSizeY;
    }
}
