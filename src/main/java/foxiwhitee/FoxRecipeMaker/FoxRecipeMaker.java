package foxiwhitee.FoxRecipeMaker;

import cpw.mods.fml.common.Mod;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import foxiwhitee.FoxRecipeMaker.network.NetworkManager;
import foxiwhitee.FoxRecipeMaker.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static foxiwhitee.FoxRecipeMaker.FoxRecipeMaker.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public final class FoxRecipeMaker {

    public static final String MODID = "FoxReciper";
    public static final String NAME = "FoxRecipeMaker";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static FoxRecipeMaker instance;

    @SidedProxy(clientSide = "foxiwhitee.FoxRecipeMaker.proxy.ClientProxy", serverSide = "foxiwhitee.FoxRecipeMaker.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs creativeTab = new CreativeTabs("FoxRecipeMaker") {
        public Item getTabIconItem() {
            return (new ItemStack(ModBlocks.VANILLA_RECIPER)).getItem();
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
        NetworkManager.instance = new NetworkManager(MODID.toLowerCase());
    }
}

