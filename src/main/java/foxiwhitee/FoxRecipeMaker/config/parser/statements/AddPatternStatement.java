package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.blocks.BlockCustomReciper;

public class AddPatternStatement implements Statement {
    public final String blockName, pattern;

    public AddPatternStatement(String blockName, String pattern) {
        this.blockName = blockName;
        this.pattern = pattern;
    }

    @Override
    public void execute() {
        ModBlocks.patterns.put(blockName, pattern);
    }

    @Override
    public String toString() {
        return "AddaPatternStatement{" +
                "blockName='" + blockName + '\'' +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}
