package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {

    public final List<Statement> statements = new ArrayList<>();

    public BlockStatement() {}

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    @Override
    public void execute() {
        statements.forEach(Statement::execute);
    }

}
