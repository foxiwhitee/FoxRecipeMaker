package foxiwhitee.FoxRecipeMaker.client.gui;

import cpw.mods.fml.client.config.GuiCheckBox;
import foxiwhitee.FoxRecipeMaker.container.FoxBaseContainer;
import foxiwhitee.FoxRecipeMaker.network.NetworkManager;
import foxiwhitee.FoxRecipeMaker.network.packets.VanillaCraftPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.tileentity.TileEntity;

import java.io.IOException;

public class GuiVanillaReciper extends GuiReciper {
    private GuiCheckBox shaped;
    private GuiCheckBox mirror;
    private GuiCheckBox ore;
    private GuiCheckBox tag;

    public GuiVanillaReciper(FoxBaseContainer container) {
        super(container, 256, 256, -145, -61);
    }

    @Override
    protected String getBackground() {
        return "gui/vanilla.png";
    }

    public void actionPerformed(GuiButton guiButton) {
        if (guiButton.id == -1) {
            TileEntity tile = container.getTileEntity();
            try {
                NetworkManager.instance.sendToServer(new VanillaCraftPacket(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, shaped.isChecked(), mirror.isChecked(), ore.isChecked(), tag.isChecked()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(this.shaped = new GuiCheckBox(8, this.width / 2 - 80 + 12, this.height / 2 - 12 - 1, "Uniform", true));
        this.buttonList.add(this.mirror = new GuiCheckBox(8, this.width / 2 - 80 + 4, this.height / 2 - 79 - 5, "Mirror", false));
        this.buttonList.add(this.ore = new GuiCheckBox(8, this.width / 2 + 50 + 4, this.height / 2 - 79 - 5, "Ore", true));
        this.buttonList.add(this.tag = new GuiCheckBox(8, this.width / 2 + 10 + 4, this.height / 2 - 79 - 5, "Tag", true));
    }
}
