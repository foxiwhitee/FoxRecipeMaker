package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;
import foxiwhitee.FoxRecipeMaker.config.utils.GuiInformation;

public class AddOffset implements Statement {
    private final String name;
    private final int x, y;

    public AddOffset(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        GuiInformation info = ModBlocks.infoGui.get(name);
        if (info != null) {
            info.setOffsetX(x);
            info.setOffsetY(y);
        }
        ContainerInformation cInfo = ModBlocks.infoContainer.get(name);
        if (cInfo != null) {
            cInfo.setOffsetX(x);
            cInfo.setOffsetY(y);
        }
    }

    @Override
    public String toString() {
        return "AddOffset{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
