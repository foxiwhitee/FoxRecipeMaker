package foxiwhitee.FoxRecipeMaker;

import cpw.mods.fml.common.network.IGuiHandler;
import foxiwhitee.FoxRecipeMaker.client.gui.GuiCustomReciper;
import foxiwhitee.FoxRecipeMaker.client.gui.GuiVanillaReciper;
import foxiwhitee.FoxRecipeMaker.container.ContainerCustomReciper;
import foxiwhitee.FoxRecipeMaker.container.ContainerReciper;
import foxiwhitee.FoxRecipeMaker.container.ContainerVanillaReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileVanillaReciper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int i, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null) {
            System.out.println("null");
            return null;
        }
        switch (i) {
            case 0: {
                if (tileEntity instanceof TileVanillaReciper) {
                    return new ContainerVanillaReciper(player.inventory, (TileReciper) tileEntity);
                }
                break;
            }
            case 1: {
                if (tileEntity instanceof TileCustomReciper) {
                    return new ContainerCustomReciper(player.inventory, (TileCustomReciper) tileEntity);
                }
                break;
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int i, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null) {
            return null;
        }

        switch (i) {
            case 0: {
                if (tileEntity instanceof TileVanillaReciper) {
                    return new GuiVanillaReciper(new ContainerVanillaReciper(player.inventory, (TileReciper) tileEntity));
                }
                break;
            }
            case 1: {
                if (tileEntity instanceof TileCustomReciper) {
                    return new GuiCustomReciper(new ContainerCustomReciper(player.inventory, (TileCustomReciper) tileEntity));
                }
                break;
            }
        }

        return null;
    }
}
