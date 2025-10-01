package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.config.ConfigManager;

public class AddImportStatement implements Statement {
    public final String integration, text;

    public AddImportStatement(String integration, String text) {
        this.integration = integration;
        this.text = text;
    }


    @Override
    public void execute() {
        ConfigManager.IMPORTS.put(integration, text);
    }

    @Override
    public String toString() {
        return "AddImportStatement{" +
                "integration='" + integration + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
