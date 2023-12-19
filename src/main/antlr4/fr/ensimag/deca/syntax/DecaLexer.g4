lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

LETTER : 'a' .. 'z' | 'A' .. 'Z';
DIGIT : '0' .. '9';
EOL : '\n';

IDENT : (LETTER | '$' | '_') (LETTER | DIGIT | '$' | '_')*;

STRING_CAR : ~ ('\n' | '\\' | '"');

STRING : '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING : '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';


NUM : DIGIT+;
SIGN : '+' | '-';
EXP : ('E' | 'e') SIGN NUM;
DEC : NUM '.' NUM;
FLOATDEC : (DEC | DEC EXP) ('F' | 'f');
DIGITHEX : '0' ..'9' | 'A' .. 'F' | 'a' .. 'f';
NUMHEX : DIGITHEX+;
FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f');
FLOAT : FLOATDEC | FLOATHEX;

OBRACE : '{';
CBRACE : '}';
SEMI : ';';
COMMA : ',';
EQUALS : '=';
PRINT : 'print';
PRINTLN : 'println';
PRINTLNX : 'printlnx';
PRINTX : 'printx';
WHILE : 'while';
RETURN : 'return';
OPARENT : '(';
CPARENT : ')';
ELSE : 'else';
ELSEIF : 'elseif';
IF : 'if';
SPACE : ' ';
OR : '||';
AND : '&&';
EQEQ : '==';
NEQ : '!=';
LEQ : '<=';
GEQ : '>=';
GT : '>';
LT : '<';
INSTANCEOF : 'instanceof';
PLUS : '+' ;
MINUS : '-' ;
TIMES : '*' ;
SLASH : '/';
PERCENT : '%';
EXCLAM : '!';
DOT : '.';
READINT : 'readInt';
READFLOAT : 'readFloat';
NEW : 'new';
INT : DIGIT+;
TRUE : 'true';
FALSE : 'false';
THIS : 'this';
NULL : 'null';
CLASS : 'class';
EXTENDS : 'extends';
PROTECTED : 'protected';
ASM : 'asm';

