package foxiwhitee.FoxRecipeMaker.config.parser;

import foxiwhitee.FoxRecipeMaker.config.parser.expressions.*;
import foxiwhitee.FoxRecipeMaker.config.parser.statements.*;
import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> tokens;

    private int pos;
    private final int size;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;

        this.size = tokens.size();
    }

    public Statement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            result.addStatement(statement());
        }
        return result;
    }

    private Statement block() {
        final BlockStatement block = new BlockStatement();
        consume(TokenType.LPAREN);
        while (!match(TokenType.RPAREN)) {
            block.addStatement(statement());
        }
        return block;
    }

    private Statement statement() {
        if (match(TokenType.ADD_INTEGRATION)) {
            return addIntegrationStatement();
        }
        if (match(TokenType.ADD_BLOCK)) {
            return addBlockStatement();
        }
        if (match(TokenType.ADD_GUI)) {
            return addGuiStatement();
        }
        if (match(TokenType.ADD_CONTAINER)) {
            return addContainerStatement();
        }
        if (match(TokenType.ADD_OFFSET)) {
            return addOffset();
        }
        if (match(TokenType.ADD_CHECKBOX)) {
            return addCheckBox();
        }
        if (match(TokenType.ADD_SLIDER)) {
            return addSlider();
        }
        if (match(TokenType.ADD_PATTERN)) {
            return addPattern();
        }
        if (match(TokenType.ADD_LOCALIZATION)) {
            return addLocalization();
        }
        if (match(TokenType.ADD_INTEGRATION)) {
            return addImport();
        }
        throw new RuntimeException("Unknown statement: " + get(0));
    }

    private Statement addImport() {
        consume(TokenType.LPAREN);
        String integration = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        String text = consume(TokenType.STRING).getValue();
        match(TokenType.RPAREN);
        return new AddImportStatement(integration, text);
    }

    private Statement addLocalization() {
        consume(TokenType.LPAREN);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        String lang = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        String text = consume(TokenType.STRING).getValue();
        match(TokenType.RPAREN);
        return new AddLocalizationStatement(blockName, lang, text);
    }

    private Statement addPattern() {
        consume(TokenType.LPAREN);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        String pattern = consume(TokenType.STRING).getValue();
        match(TokenType.RPAREN);
        return new AddPatternStatement(blockName, pattern);
    }

    private Statement addSlider() {
        consume(TokenType.LPAREN);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        int id = (int) expression().eval();
        consume(TokenType.COMMA);
        int x = (int) expression().eval();
        consume(TokenType.COMMA);
        int y = (int) expression().eval();
        consume(TokenType.COMMA);
        int width = (int) expression().eval();
        consume(TokenType.COMMA);
        int height = (int) expression().eval();
        consume(TokenType.COMMA);
        String prefix = consume(TokenType.STRING).getValue();
        consume(TokenType.COMMA);
        String suf = consume(TokenType.STRING).getValue();
        consume(TokenType.COMMA);
        int min = (int) expression().eval();
        consume(TokenType.COMMA);
        int max = (int) expression().eval();
        consume(TokenType.COMMA);
        int defaultValue = (int) expression().eval();
        match(TokenType.RPAREN);
        return new AddSliderStatement(blockName, id, x, y, width, height, min, max, defaultValue, prefix, suf);
    }

    private Statement addCheckBox() {
        consume(TokenType.LPAREN);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        int id = (int) expression().eval();
        consume(TokenType.COMMA);
        int x = (int) expression().eval();
        consume(TokenType.COMMA);
        int y = (int) expression().eval();
        consume(TokenType.COMMA);
        boolean value = (boolean) expression().eval();
        consume(TokenType.COMMA);
        String text = consume(TokenType.STRING).getValue();
        match(TokenType.RPAREN);
        return new AddCheckboxStatement(blockName, id, x, y, value, text);
    }

    private Statement addOffset() {
        consume(TokenType.LPAREN);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        int x = (int) expression().eval();
        consume(TokenType.COMMA);
        int y = (int) expression().eval();
        match(TokenType.RPAREN);
        return new AddOffset(blockName, x, y);
    }

    private Statement addContainerStatement() {
        consume(TokenType.LPAREN);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        consume(TokenType.LBRACKET);
        int playerX = (int) expression().eval();
        consume(TokenType.COMMA);
        int playerY = (int) expression().eval();
        consume(TokenType.RBRACKET);
        boolean playerDecorate = match(TokenType.DECORATE);
        consume(TokenType.COMMA);
        ContainerInformation.Slot out = (ContainerInformation.Slot) primary().eval();
        consume(TokenType.COMMA);
        List<ContainerInformation.Slot> slots = new ArrayList<>();
        while (!match(TokenType.RPAREN)) {
            slots.add((ContainerInformation.Slot) primary().eval());
            match(TokenType.COMMA);
        }
        match(TokenType.RPAREN);
        return new AddContainerStatement(blockName, playerX, playerY, playerDecorate, slots, out);
    }

    private Statement addGuiStatement() {
        consume(TokenType.LPAREN);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        int width = (int) expression().eval();
        consume(TokenType.COMMA);
        int height = (int) expression().eval();
        int arrowX = Integer.MIN_VALUE, arrowY = Integer.MIN_VALUE;
        consume(TokenType.COMMA);
        int buttonX = (int) expression().eval();
        consume(TokenType.COMMA);
        int buttonY = (int) expression().eval();
        if (get(0).getType() == TokenType.COMMA) {
            consume(TokenType.COMMA);
            arrowX = (int) expression().eval();
            consume(TokenType.COMMA);
            arrowY = (int) expression().eval();
        }
        consume(TokenType.RPAREN);
        return new AddGuiStatement(blockName, width, height, buttonX, buttonY, arrowX, arrowY);
    }

    private Statement addBlockStatement() {
        consume(TokenType.LPAREN);
        String integrationName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        String blockName = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        String color = consume(TokenType.HEX).getValue();
        consume(TokenType.COMMA);
        String colorTop = consume(TokenType.HEX).getValue();
        consume(TokenType.RPAREN);
        return new AddBlockStatement(integrationName, blockName, color, colorTop);
    }

    private Statement addIntegrationStatement() {
        consume(TokenType.LPAREN);
        String modId = consume(TokenType.WORD).getValue();
        consume(TokenType.COMMA);
        String fileName = consume(TokenType.WORD).getValue();
        consume(TokenType.RPAREN);
        return new AddIntegrationStatement(modId, fileName);
    }

    private Expression expression() {
        return additive();
    }

    private Expression additive() {
        Expression expression = multiplicative();
        while (true) {
            if (match(TokenType.PLUS)) {
                expression = new BinaryExpression(BinaryExpression.Operator.ADD, expression, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                expression = new BinaryExpression(BinaryExpression.Operator.SUBTRACT, expression, multiplicative());
                continue;
            }
            break;
        }
        return expression;
    }

    private Expression multiplicative() {
        Expression expression = unary();
        while (true) {
            if (match(TokenType.STAR)) {
                expression = new BinaryExpression(BinaryExpression.Operator.MULTIPLY, expression, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                expression = new BinaryExpression(BinaryExpression.Operator.DIVIDE, expression, unary());
                continue;
            }
            break;
        }
        return expression;
    }

    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression((NumberExpression) caseOperation());
        }
        if (match(TokenType.EXCL)) {
            return new UnaryBoolExpression((BooleanExpression) caseOperation());
        }
        return caseOperation();
    }

    private Expression caseOperation() {
        if (match(TokenType.UCASE)) {
            return new CaseExpression(CaseExpression.Operation.TO_UPPER_CASE, (StringExpression) primary());
        }
        if (match(TokenType.LCASE)) {
            return new CaseExpression(CaseExpression.Operation.TO_LOWER_CASE, (StringExpression) primary());
        }
        return primary();
    }

    private Expression primary() {
        Token current = get(0);
        if (match(TokenType.LT)) {
            return slotExpression();
        }
        if (match(TokenType.HEX)) {
            return new HexExpression(current.getValue());
        }
        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Integer.parseInt(current.getValue()));
        }
        if (match(TokenType.TRUE)) {
            return new BooleanExpression(true);
        }
        if (match(TokenType.FALSE)) {
            return new BooleanExpression(false);
        }
        if (match(TokenType.STRING)) {
            return new StringExpression(current.getValue());
        }
        if (match(TokenType.LPAREN)) {
            Expression expression = expression();
            match(TokenType.RPAREN);
            return expression;
        }
        throw new RuntimeException("Unknown expression");
    }

    private Expression slotExpression() {
        int id = Integer.MIN_VALUE;
        int x = (int) expression().eval();
        consume(TokenType.COMMA);
        int y = (int) expression().eval();

        if (match(TokenType.COMMA)) {
            id = x;
            x = y;
            y = (int) expression().eval();
        }
        consume(TokenType.GT);
        boolean big = false, decorate = false;
        if (match(TokenType.BIG)) {
            big = true;

            if (match(TokenType.DECORATE)) {
                decorate = true;
            }
        }
        if (id == Integer.MIN_VALUE) {
            return new SlotExpression(x, y, big, decorate);
        } else {
            return new SlotExpression(id, x, y);
        }
    }

    private Token consume(TokenType type) {
        Token current = get(0);
        if (type != current.getType()) {
            throw new RuntimeException("Token " + current + "doesn't match " + type);
        }
        this.pos++;
        return current;
    }

    private boolean match(TokenType type) {
        Token current = get(0);
        if (type != current.getType()) {
            return false;
        }
        this.pos++;
        return true;
    }

    private boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    private Token get(int relativePosition) {
        int position = this.pos + relativePosition;
        if (position >= this.size) {
            return EOF;
        }
        return tokens.get(position);
    }
}
