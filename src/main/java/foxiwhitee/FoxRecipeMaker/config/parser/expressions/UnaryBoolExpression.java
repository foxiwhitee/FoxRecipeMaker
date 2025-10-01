package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class UnaryBoolExpression implements Expression {

    public final BooleanExpression expression;

    public UnaryBoolExpression(BooleanExpression expression) {
        this.expression = expression;
    }

    @Override
    public Object eval() {
        return new BooleanExpression(!((boolean) expression.eval()));
    }

    @Override
    public String toString() {
        return String.format("%s", expression);
    }

}
