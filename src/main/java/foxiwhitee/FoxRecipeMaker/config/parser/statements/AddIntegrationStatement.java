package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.config.ConfigManager;

public class AddIntegrationStatement implements Statement {
    private final String modId, fileName;

    public AddIntegrationStatement(String modId, String fileName) {
        this.modId = modId;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        ConfigManager.INTEGRATIONS.put(modId, fileName);
    }

    @Override
    public String toString() {
        return "AddIntegrationStatement{" +
                "modId='" + modId + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
