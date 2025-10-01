package foxiwhitee.FoxRecipeMaker.config.parser;

public enum TokenType {
    NUMBER,
    WORD,
    STRING,
    HEX,

    ADD_INTEGRATION,
    ADD_BLOCK,
    ADD_GUI,
    ADD_CONTAINER,
    ADD_OFFSET,
    ADD_CHECKBOX,
    ADD_SLIDER,
    ADD_PATTERN,
    ADD_LOCALIZATION,
    ADD_IMPORT,

    BIG,
    DECORATE,

    TRUE,
    FALSE,

    MINUS, //-
    PLUS, // +,
    STAR, // *
    SLASH, // /

    UCASE, // ^
    LCASE, // !^

    LT, // <
    GT, // >
    LPAREN, // (
    RPAREN, // )
    LBRACKET, // [
    RBRACKET, // ]

    SEMICOLON, // ;
    COMMA, // ,
    EXCL, // !

    EOF
}
