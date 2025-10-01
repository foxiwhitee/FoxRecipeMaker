package foxiwhitee.FoxRecipeMaker.client.gui;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;


public class Utility {
    public static void drawTexture(double x, double y, double u, double v, double width, double height,
                                   double uWidth, double uHeight) {
        drawTexture(x, y, u, v, width, height, uWidth, uHeight, 512, 512, 0, null, null, null);
    }

    public static void drawTexture(double x, double y, double u, double v, double width, double height,
                                   double uWidth, double uHeight, double textureWidth, double textureHeight) {
        drawTexture(x, y, u, v, width, height, uWidth, uHeight, textureWidth, textureHeight, 0, null, null, null);
    }

    public static void drawTexture(double x, double y, double u, double v, double width, double height,
                                   double uWidth, double uHeight, double textureWidth, double textureHeight, int z) {
        drawTexture(x, y, u, v, width, height, uWidth, uHeight, textureWidth, textureHeight, z, null, null, null);
    }

    public static void drawTexture(double x, double y, double u, double v, double width, double height,
                                   double uWidth, double uHeight, double textureWidth, double textureHeight,
                                   FoxBaseGui gui, String modid, String texture) {
        drawTexture(x, y, u, v, width, height, uWidth, uHeight, textureWidth, textureHeight, 0, gui, modid, texture);
    }

    public static void drawTexture(double x, double y, double u, double v, double width, double height,
                                   double uWidth, double uHeight, double textureWidth, double textureHeight, int z,
                                   FoxBaseGui gui, String modid, String texture) {
        if (gui != null && modid != null && texture != null) {
            gui.bindTexture(modid, texture);
        }
        double px = 1.0 / textureWidth;
        double py = 1.0 / textureHeight;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        tessellator.addVertexWithUV(x, y + height, z, u * px, (v + uHeight) * py);
        tessellator.addVertexWithUV(x + width, y + height, z, (u + uWidth) * px, (v + uHeight) * py);
        tessellator.addVertexWithUV(x + width, y, z, (u + uWidth) * px, v * py);
        tessellator.addVertexWithUV(x, y, z, u * px, v * py);

        tessellator.draw();

        GL11.glDisable(GL11.GL_BLEND);
    }

}
