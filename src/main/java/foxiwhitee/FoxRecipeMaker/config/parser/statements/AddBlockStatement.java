package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.config.ConfigManager;
import foxiwhitee.FoxRecipeMaker.config.parser.expressions.HexExpression;
import foxiwhitee.FoxRecipeMaker.config.parser.expressions.StringExpression;
import foxiwhitee.FoxRecipeMaker.config.utils.BlockInformation;

public class AddBlockStatement implements Statement {
    private final String integrationName, blockID;
    private final int color, colorTop;

    public AddBlockStatement(String integrationName, String blockID, String color, String colorTop) {
        this.integrationName = integrationName;
        this.blockID = blockID;
        this.color = Integer.parseInt(color, 16);
        this.colorTop = Integer.parseInt(colorTop, 16);
    }

    @Override
    public void execute() {
        ModBlocks.info.add(new BlockInformation(integrationName, blockID, color, colorTop));
    }

    @Override
    public String toString() {
        return "AddBlockStatement{" +
                "integrationName='" + integrationName + '\'' +
                ", blockID='" + blockID + '\'' +
                ", color=" + color +
                ", colorTop=" + colorTop +
                '}';
    }
}