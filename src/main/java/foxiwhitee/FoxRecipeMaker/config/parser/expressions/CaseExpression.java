package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class CaseExpression implements Expression {
    public enum Operation { TO_LOWER_CASE, TO_UPPER_CASE }

    private final Operation operation;
    private final StringExpression expression;

    public CaseExpression(Operation operation, StringExpression expression) {
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public Object eval() {
        String result;
        if (operation == Operation.TO_LOWER_CASE) {
            result = ((String) expression.eval()).toLowerCase();
        } else {
            result = ((String) expression.eval()).toUpperCase();
        }
        return new StringExpression(result);
    }

    @Override
    public String toString() {
        return String.format("%c%s", operation, expression);
    }

}
