package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.blocks.BlockCustomReciper;
import foxiwhitee.FoxRecipeMaker.config.utils.GuiInformation;

public class AddGuiStatement implements Statement {
    private final String name;
    private final int width, height, buttonX, buttonY, arrowX, arrowY;

    public AddGuiStatement(String name, int width, int height, int buttonX, int buttonY, int arrowX, int arrowY) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.arrowX = arrowX;
        this.arrowY = arrowY;
        this.buttonX = buttonX;
        this.buttonY = buttonY;
    }

    @Override
    public void execute() {
        ModBlocks.infoGui.put(name, new GuiInformation(name, width, height, buttonX, buttonY, arrowX, arrowY, arrowX != Integer.MIN_VALUE && arrowY != Integer.MIN_VALUE));
    }

    @Override
    public String toString() {
        return "AddGuiStatement{" +
                "name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", buttonX=" + buttonX +
                ", buttonY=" + buttonY +
                ", arrowX=" + arrowX +
                ", arrowY=" + arrowY +
                '}';
    }
}
