package foxiwhitee.FoxRecipeMaker.client.gui;

import foxiwhitee.FoxRecipeMaker.container.ContainerCustomReciper;
import foxiwhitee.FoxRecipeMaker.container.FoxBaseContainer;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;

public abstract class GuiReciper extends FoxBaseGui {
    private final int buttonX, buttonY;
    protected final FoxBaseContainer container;

    public GuiReciper(FoxBaseContainer container, int xSize, int ySize, int buttonX, int buttonY) {
        super(container, xSize, ySize);
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.container = container;

        setTextureSizeX(256);
        setTextureSizeY(256);
    }

    @Override
    public void initGui() {
        super.initGui();

        this.buttonList.clear();
        int x = 0, y = 0;
        if (container instanceof ContainerCustomReciper) {
            x = ((TileCustomReciper)container.getTileEntity()).getBlock().getInfoGui().getOffsetX();
            y = ((TileCustomReciper)container.getTileEntity()).getBlock().getInfoGui().getOffsetY();
        }
        this.buttonList.add(new CustomButton(-1, this.guiLeft + x + buttonX, this.guiTop + y + buttonY));
    }
}
