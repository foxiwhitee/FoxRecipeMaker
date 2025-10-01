package foxiwhitee.FoxRecipeMaker.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxRecipeMaker.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class CustomBlockRenderer implements ISimpleBlockRenderingHandler {
    private final int color;
    private final int colorTop;
    private final int renderId;

    public CustomBlockRenderer(int color, int colorTop, int renderId) {
        this.color = color;
        this.colorTop = colorTop;
        this.renderId = renderId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        GL11.glPushMatrix();

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 1.0F, 0.0F);

        for (int side = 0; side < 6; side++) {
            if (side == 1) {
                tess.setColorOpaque_I(colorTop);
            } else {
                tess.setColorOpaque_I(color);
            }
            if (side == 0) renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(side, metadata));
            if (side == 1) renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(side, metadata));
            if (side == 2) renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(side, metadata));
            if (side == 3) renderer.renderFaceZPos(block, 0, 0, 0, block.getIcon(side, metadata));
            if (side == 4) renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(side, metadata));
            if (side == 5) renderer.renderFaceXPos(block, 0, 0, 0, block.getIcon(side, metadata));
        }

        tess.draw();
        GL11.glPopMatrix();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));

        for (int side = 0; side < 6; side++) {
            if (side == 1) {
                tess.setColorOpaque_I(colorTop);
            } else {
                tess.setColorOpaque_I(color);
            }
            if (side == 0) renderer.renderFaceYNeg(block, x, y, z, block.getIcon(side, world.getBlockMetadata(x, y, z)));
            if (side == 1) renderer.renderFaceYPos(block, x, y, z, block.getIcon(side, world.getBlockMetadata(x, y, z)));
            if (side == 2) renderer.renderFaceZNeg(block, x, y, z, block.getIcon(side, world.getBlockMetadata(x, y, z)));
            if (side == 3) renderer.renderFaceZPos(block, x, y, z, block.getIcon(side, world.getBlockMetadata(x, y, z)));
            if (side == 4) renderer.renderFaceXNeg(block, x, y, z, block.getIcon(side, world.getBlockMetadata(x, y, z)));
            if (side == 5) renderer.renderFaceXPos(block, x, y, z, block.getIcon(side, world.getBlockMetadata(x, y, z)));
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderId;
    }
}
