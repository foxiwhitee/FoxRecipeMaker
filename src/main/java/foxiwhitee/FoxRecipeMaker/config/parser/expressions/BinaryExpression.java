package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

public class BinaryExpression implements Expression {

    public enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/");

        private final String name;

        Operator(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public final Expression firstExpression;
    public final Expression secondExpression;
    private final Operator operation;

    public BinaryExpression(Operator operation, Expression firstExpression, Expression secondExpression) {
        if (!(firstExpression instanceof NumberExpression || firstExpression instanceof StringExpression)
                || !(secondExpression instanceof NumberExpression || secondExpression instanceof StringExpression)) {
            throw new RuntimeException("Operation not possible. Operations (+, -, *, /) are only available for Numbers, operation + is available for String");
        }
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public Object eval() {
        Object first = firstExpression.eval();
        Object second = secondExpression.eval();
        if (first instanceof String) {
            String result = "";
            if (operation == Operator.ADD) {
                result = first + (String) second;
            } else {
                throw new RuntimeException("Operation not possible for type String: " + operation.name);
            }
            return new StringExpression(result);
        } else {
            int result;
            switch (operation) {
                case SUBTRACT: result = (int) first - (int) second;
                case MULTIPLY: result = (int) first * (int) second;
                case DIVIDE: result = (int) first / (int) second;
                default: result = (int) first + (int) second;
            }
            return new NumberExpression(result);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", firstExpression, operation, secondExpression);
    }

}
