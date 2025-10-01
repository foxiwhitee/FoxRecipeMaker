package foxiwhitee.FoxRecipeMaker.config.parser;

public class Token {
    private String value;
    private TokenType type;

    public Token() {}

    public Token(TokenType type, String value) {
        this.value = value;
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return type + " " + value;
    }
}
