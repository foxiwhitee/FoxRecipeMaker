package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;

import java.util.AbstractMap;

public class AddLocalizationStatement implements Statement {
    public final String blockName, lang, text;

    public AddLocalizationStatement(String blockName, String lang, String text) {
        this.blockName = blockName;
        this.lang = lang;
        this.text = text;
    }


    @Override
    public void execute() {
        ModBlocks.localization.put(blockName, new AbstractMap.SimpleEntry<String, String>(lang, text));
    }

    @Override
    public String toString() {
        return "AddLocalizationStatement{" +
                "blockName='" + blockName + '\'' +
                ", lang='" + lang + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
