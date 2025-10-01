package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class StringExpression implements Expression {
    private final String value;

    public StringExpression(String value) {
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
