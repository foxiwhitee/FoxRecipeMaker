package foxiwhitee.FoxRecipeMaker.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxRecipeMaker.proxy.ClientProxy;
import foxiwhitee.FoxRecipeMaker.tiles.TileReciper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockReciper extends Block implements ITileEntityProvider {
    protected final String name;
    private final Class<? extends TileReciper> tile;

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;

    public BlockReciper(String name, Class<? extends TileReciper> tile) {
        super(Material.wood);
        this.name = name;
        this.tile = tile;
        setBlockName(name);
        setLightLevel(1f);
        setLightOpacity(1);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        try {
            return tile.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return new TileEntity();
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int i) {
        if (side == 1) {
            return this.iconTop;
        } else if (side == 0) {
            return iconBottom;
        } else {
            return side != 2 && side != 4 ? this.blockIcon : this.iconFront;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iIconRegister) {
        this.blockIcon = iIconRegister.registerIcon(this.getTextureName() + "_Side");
        this.iconTop = iIconRegister.registerIcon(this.getTextureName() + "_Top");
        this.iconFront = iIconRegister.registerIcon(this.getTextureName() + "_Front");
        this.iconBottom = iIconRegister.registerIcon(this.getTextureName() + "_Bottom");
    }

    protected abstract String getTextureName();

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileReciper) {
            TileReciper te = (TileReciper) tileEntity;
            if (entityliving == null) {
                te.facing = 2;
            } else {

                int l = MathHelper.floor_double((entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
                te.facing = (byte) (l == 0 ? 2 : l == 1 ? 5 : l == 2 ? 3 : 4);
                te.getWorldObj().markBlockForUpdate(x, y, z);
            }
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ClientProxy.renderId;
    }
}
