package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.config.utils.GuiInformation;

public class AddCheckboxStatement implements Statement {
    private final String blockName;
    private final int id, x, y;
    private final boolean value;
    private final String text;

    public AddCheckboxStatement(String blockName, int id, int x, int y, boolean value, String text) {
        this.blockName = blockName;
        this.id = id;
        this.x = x;
        this.y = y;
        this.value = value;
        this.text = text;
    }

    @Override
    public void execute() {
        GuiInformation info = ModBlocks.infoGui.get(blockName);
        if (info != null) {
            info.checkboxes.add(new GuiInformation.Checkbox(blockName, id, x, y, value, text));
        }
    }

    @Override
    public String toString() {
        return "AddCheckboxStatement{" +
                "blockName='" + blockName + '\'' +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", value=" + value +
                '}';
    }
}
