grammar FormRules;

@header {
package com.example.formdsl.parser;
}

rules
    : formRule+ ;

formRule
    : WHEN condition THEN actionBlock SEMICOLON ;

condition
    : expression (LOGIC_OP expression)* ;

expression
    : field=IDENTIFIER op=OPERATOR val=value ;

actionBlock
    : action (COMMA action)* ;

action
    : target=IDENTIFIER DOT property=IDENTIFIER ASSIGN val=value ;

value
    : STRING
    | BOOLEAN
    | NUMBER
    ;

WHEN        : 'WHEN' ;
THEN        : 'THEN' ;

OPERATOR    : '==' | '!=' ;
LOGIC_OP    : 'AND' | 'OR' ;

ASSIGN      : '=' ;
DOT         : '.' ;
COMMA       : ',' ;
SEMICOLON   : ';' ;

BOOLEAN     : 'true' | 'false' ;
STRING      : '\'' (~['\\] | '\\' .)*? '\'' ;
NUMBER      : [0-9]+ ;

IDENTIFIER  : [a-zA-Z_][a-zA-Z0-9_]* ;

WS          : [ \t\r\n]+ -> skip ;
