package foxiwhitee.FoxRecipeMaker.config.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final String OPERATOR_CHARS = ".+-*/()=<>!&|{}[];,#^";
    private static final Map<String, TokenType> OPERATORS;

    static {
        OPERATORS = new HashMap<>();

        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);

        OPERATORS.put("[", TokenType.LBRACKET);
        OPERATORS.put("]", TokenType.RBRACKET);

        OPERATORS.put("<", TokenType.LT);
        OPERATORS.put(">", TokenType.GT);

        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.STAR);
        OPERATORS.put("/", TokenType.SLASH);

        OPERATORS.put("^", TokenType.UCASE);
        OPERATORS.put("!^", TokenType.LCASE);

        OPERATORS.put(";", TokenType.SEMICOLON);
        OPERATORS.put(",", TokenType.COMMA);
        OPERATORS.put("!", TokenType.EXCL);
    }

    private static final Map<String, TokenType> KEYWORDS;
    static {
        KEYWORDS = new HashMap<>();
        KEYWORDS.put("true", TokenType.TRUE);
        KEYWORDS.put("false", TokenType.FALSE);

        KEYWORDS.put(":big", TokenType.BIG);
        KEYWORDS.put(":decorate", TokenType.DECORATE);

        KEYWORDS.put(".addIntegration", TokenType.ADD_INTEGRATION);
        KEYWORDS.put(".addBlock", TokenType.ADD_BLOCK);
        KEYWORDS.put(".addGui", TokenType.ADD_GUI);
        KEYWORDS.put(".addContainer", TokenType.ADD_CONTAINER);
        KEYWORDS.put(".addOffset", TokenType.ADD_OFFSET);
        KEYWORDS.put(".addCheckBox", TokenType.ADD_CHECKBOX);
        KEYWORDS.put(".addSlider", TokenType.ADD_SLIDER);
        KEYWORDS.put(".addPattern", TokenType.ADD_PATTERN);
        KEYWORDS.put(".addLocalization", TokenType.ADD_LOCALIZATION);
        KEYWORDS.put(".addImport", TokenType.ADD_IMPORT);

    }

    private final String input;
    private final List<Token> tokens;

    private int pos;
    private final int length;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();

        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        char current;
        while (this.pos < this.length) {
            current = peek(0);
            if (current == '#') {
                tokenizeHex();
            } else if (current == '.' || current == ':' || Character.isLetter(current)) {
                tokenizeWord();
            } else if (Character.isDigit(current)) {
                tokenizeNumber();
            } else if (current == '"') {
                tokenizeString();
            } else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                next();
            }
        }
        return tokens;
    }

    private void tokenizeHex() {
        next();
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        int i = 0;
        while (i < 6) {
            buffer.append(current);
            current = next();
            i++;
        }
        addToken(TokenType.HEX, buffer.toString());
    }

    private void tokenizeString() {
        next();
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '\\') {
                current = next();
                switch (current) {
                    case '"': current = next(); buffer.append('"'); continue;
                    case 'n': current = next(); buffer.append('\n'); continue;
                    case 't': current = next(); buffer.append('\t'); continue;
                }
                buffer.append('\\');
                continue;
            }
            if (current == '"') break;
            if (current == 'Ё' || current == 'ё' || current == 'Ъ' || current == 'ъ' || current == 'Ы' || current == 'ы' || current == 'Э' || current == 'э') {
                System.exit(1);
            }
            buffer.append(current);
            current = next();
        }
        next();

        addToken(TokenType.STRING, buffer.toString());
    }

    private void tokenizeWord() {
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == ',') {
                break;
            }
            if (!Character.isLetterOrDigit(current) && current != '_' && current != '.' && current != ':') {
                break;
            }
            buffer.append(current);
            current = next();
        }

        final String word = buffer.toString();
        if (KEYWORDS.containsKey(word)) {
            addToken(KEYWORDS.get(word));
        } else if (word.equals(":big:decorate")) {
            addToken(TokenType.BIG);
            addToken(TokenType.DECORATE);
        } else {
            addToken(TokenType.WORD, word);
        }
        //addToken(TokenType.COMMA);
    }

    private void tokenizeOperator() {
        char current = peek(0);
        if (current == '/') {
            if (peek(1) == '/') {
                next();
                next();
                tokenizeComment();
                return;
            } else if (peek(1) == '*') {
                next();
                next();
                tokenizeMultilineComment();
                return;
            }
        }
        StringBuilder buffer = new StringBuilder();
        while (true) {
            if (!(buffer.toString().isEmpty()) && !OPERATORS.containsKey(buffer.toString() + current)) {
                addToken(OPERATORS.get(buffer.toString()));
                return;
            }
            buffer.append(current);
            current = next();
        }
    }

    private void tokenizeComment() {
        char current = peek(0);
        while ("\r\n\0".indexOf(current) == -1) {
            current = next();
        }
    }

    private void tokenizeMultilineComment() {
        char current = peek(0);
        while (true) {
            if (current == '\0') throw new RuntimeException("Missing close tag");
            if (current == '*' && peek(1) == '/') break;
            current = next();
        }
        next();
        next();
    }

    private void tokenizeNumber() {
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '.') {
                if (buffer.indexOf(".") != -1) {
                    throw new RuntimeException("Invalid float number");
                }
            } else if (!Character.isDigit(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER, buffer.toString());
    }

    private char next() {
        this.pos++;
        return peek(0);
    }

    private char peek(int relativePosition) {
        final int position = this.pos + relativePosition;
        if (position >= this.length) {
            return '\0';
        }
        return this.input.charAt(position);
    }

    private void addToken(TokenType type) {
        addToken(type, "");
    }

    private void addToken(TokenType type, String text) {
        this.tokens.add(new Token(type, text));
    }
}
