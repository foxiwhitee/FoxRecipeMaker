package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class BooleanExpression implements Expression {
    private final Boolean value;

    public BooleanExpression(Boolean value) {
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
