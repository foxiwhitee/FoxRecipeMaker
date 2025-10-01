package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.config.utils.GuiInformation;

public class AddSliderStatement implements Statement {
    public final String blockName;
    public final int id, x, y, width, height, min, max, defaultValue;
    public final String prefix, suf;

    public AddSliderStatement(String blockName, int id, int x, int y, int width, int height, int min, int max, int defaultValue, String prefix, String suf) {
        this.blockName = blockName;
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
        this.prefix = prefix;
        this.suf = suf;
    }

    @Override
    public void execute() {
        GuiInformation info = ModBlocks.infoGui.get(blockName);
        if (info != null) {
            info.sliders.add(new GuiInformation.Slider(blockName, id, x, y, width, height, min, max, defaultValue, prefix, suf));
        }
    }

    @Override
    public String toString() {
        return "AddSliderStatement{" +
                "blockName='" + blockName + '\'' +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", min=" + min +
                ", max=" + max +
                ", defaultValue=" + defaultValue +
                ", prefix='" + prefix + '\'' +
                ", suf='" + suf + '\'' +
                '}';
    }
}
