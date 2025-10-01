package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class UnaryExpression implements Expression {

    public final NumberExpression expression;

    public UnaryExpression(NumberExpression expression) {
        this.expression = expression;
    }

    @Override
    public Object eval() {
        return -((int) expression.eval());
    }

    @Override
    public String toString() {
        return String.format("%s", expression);
    }

}
