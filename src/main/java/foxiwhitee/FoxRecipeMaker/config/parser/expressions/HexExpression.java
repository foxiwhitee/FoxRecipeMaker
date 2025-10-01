package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class HexExpression implements Expression {
    private final Integer value;

    public HexExpression(String value) {
        this.value = Integer.parseInt(value, 16);
        System.out.println(this.value);
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