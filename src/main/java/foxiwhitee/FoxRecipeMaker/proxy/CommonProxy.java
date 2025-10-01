package foxiwhitee.FoxRecipeMaker.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.config.ConfigManager;

import java.io.IOException;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        try {
            ConfigManager.run();
        } catch (IOException e) {}
        ModBlocks.registerBlocks();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
