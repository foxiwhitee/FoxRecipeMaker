package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class NumberExpression implements Expression {
    private final Integer value;

    public NumberExpression(Integer value) {
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
