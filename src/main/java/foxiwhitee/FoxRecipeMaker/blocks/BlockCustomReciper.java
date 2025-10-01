package foxiwhitee.FoxRecipeMaker.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxRecipeMaker.FoxRecipeMaker;
import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;
import foxiwhitee.FoxRecipeMaker.config.utils.GuiInformation;
import foxiwhitee.FoxRecipeMaker.proxy.ClientProxy;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileVanillaReciper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class BlockCustomReciper extends BlockReciper {
    private final String integration;
    private GuiInformation infoGui;
    private ContainerInformation infoContainer;
    private String pattern;
    public final Map<String, String> localization = new HashMap<>();

    public BlockCustomReciper(String name, String integration) {
        super(name, TileCustomReciper.class);
        this.integration = integration;
    }

    @Override
    protected String getTextureName() {
        return FoxRecipeMaker.MODID.toLowerCase() + ":CraftingTable";
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileCustomReciper) {
            FMLNetworkHandler.openGui(player, FoxRecipeMaker.instance, 1, world, x, y, z);
        }
        return true;
    }

    @Override
    public String getLocalizedName() {
        String lang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        if (localization.containsKey(lang)) {
            return localization.get(lang);
        } else if (localization.containsKey("en_US")) {
            return localization.get("en_US");
        }
        return super.getLocalizedName();
    }

    @Override
    public int getRenderType() {
        return ClientProxy.rendersIds.get(name);
    }

    public String getIntegration() {
        return integration;
    }

    public GuiInformation getInfoGui() {
        return infoGui;
    }

    public ContainerInformation getInfoContainer() {
        return infoContainer;
    }

    public void setInfoGui(GuiInformation infoGui) {
        this.infoGui = infoGui;
    }

    public void setInfoContainer(ContainerInformation infoContainer) {
        this.infoContainer = infoContainer;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}