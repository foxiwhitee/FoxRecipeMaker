package foxiwhitee.FoxRecipeMaker.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxRecipeMaker.FoxRecipeMaker;
import foxiwhitee.FoxRecipeMaker.client.render.CustomBlockRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class ClientProxy extends CommonProxy {
    public static int renderId;

    public static final Map<String, Integer> rendersIds = new HashMap<>();

    private static final Map<String, Map.Entry<Integer, Integer>> blocks = new HashMap<>();

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        renderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new CustomBlockRenderer(0xa26a42, 0xa26a42, renderId));
        blocks.forEach((string, integerIntegerEntry) -> {
            rendersIds.put(string, RenderingRegistry.getNextAvailableRenderId());
            RenderingRegistry.registerBlockHandler(new CustomBlockRenderer(integerIntegerEntry.getKey(), integerIntegerEntry.getValue(), rendersIds.get(string)));
        });
    }

    public static void addReciperRender(String id, int color, int colorTop) {
        blocks.put(id, new AbstractMap.SimpleEntry<>(color, colorTop));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
