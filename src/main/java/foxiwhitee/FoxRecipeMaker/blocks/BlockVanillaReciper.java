package foxiwhitee.FoxRecipeMaker.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxRecipeMaker.FoxRecipeMaker;
import foxiwhitee.FoxRecipeMaker.tiles.TileVanillaReciper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVanillaReciper extends BlockReciper {
    public BlockVanillaReciper() {
        super("ReciperVanilla", TileVanillaReciper.class);
    }

    @Override
    protected String getTextureName() {
        return FoxRecipeMaker.MODID.toLowerCase() + ":CraftingTable";
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileVanillaReciper) {
            FMLNetworkHandler.openGui(player, FoxRecipeMaker.instance, 0, world, x, y, z);
        }
        return true;
    }
}
