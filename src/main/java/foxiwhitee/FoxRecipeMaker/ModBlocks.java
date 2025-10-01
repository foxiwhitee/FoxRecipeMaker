package foxiwhitee.FoxRecipeMaker;

import foxiwhitee.FoxRecipeMaker.blocks.BlockCustomReciper;
import foxiwhitee.FoxRecipeMaker.blocks.BlockVanillaReciper;
import foxiwhitee.FoxRecipeMaker.config.utils.BlockInformation;
import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;
import foxiwhitee.FoxRecipeMaker.config.utils.GuiInformation;
import foxiwhitee.FoxRecipeMaker.proxy.ClientProxy;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileVanillaReciper;
import foxiwhitee.FoxRecipeMaker.utils.RegisterUtils;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModBlocks {
    public static final Block VANILLA_RECIPER = new BlockVanillaReciper();

    public static final Map<String, Block> resipers = new HashMap<>();
    public static final Map<String, ContainerInformation> infoContainer = new HashMap<>();
    public static final Map<String, GuiInformation> infoGui = new HashMap<>();
    public static final Map<String, String> patterns = new HashMap<>();
    public static final Map<String, Map.Entry<String, String>> localization = new HashMap<>();

    public static final Set<BlockInformation> info = new HashSet<>();

    public static void registerBlocks() {
        RegisterUtils.registerBlock(VANILLA_RECIPER);
        RegisterUtils.registerTile(TileVanillaReciper.class);
        RegisterUtils.registerTile(TileCustomReciper.class);

        info.forEach(blockInformation -> {
            String id = blockInformation.getBlockID();
            BlockCustomReciper block = new BlockCustomReciper(id, blockInformation.getIntegrationName());
            resipers.put(id, block);
            RegisterUtils.registerBlock(block);
            ClientProxy.addReciperRender(id, blockInformation.getColor(), blockInformation.getColorTop());

            if (infoGui.containsKey(id)) {
                block.setInfoGui(infoGui.get(id));
            }
            if (infoContainer.containsKey(id)) {
                block.setInfoContainer(infoContainer.get(id));
                if (infoGui.containsKey(id)) {
                    infoGui.put(id, infoGui.get(id).setInfo(infoContainer.get(id)));
                }
            }
            if (patterns.containsKey(id)) {
                block.setPattern(patterns.get(id));
            }
            if (localization.containsKey(id)) {
                block.localization.put(localization.get(id).getKey(), (localization.get(id).getValue()));
            }
        });

    }
}
