grammar PR1;

/*
 * CS 3304 Fall 2024
 * Project 1
 * grammar with operator precedence and more
 *
 * Author: Jayant Dulani
 */

// parser rules start
program : (statement)* EOF ;

statement
    : variableDeclaration
    | functionDeclaration
    | assignment
    | ifStatement
    | whileStatement
    | returnStatement
    | block
    | writeStatement
    | expression ';'
    | breakStatement
    | continueStatement
    ;

breakStatement
    : 'break' ';'
    ;

continueStatement
    : 'continue' ';'
    ;

returnStatement
    : 'return' expression ';'
    ;

writeStatement
    : 'write' '(' expression ')' ';'
    ;

variableDeclaration
    : 'var' variableList ';'
    ;

variableList
    : variable (',' variable)*
    ;

variable
    : identifier ('=' expression)?
    ;

assignment
    : identifier '=' expression ';'
    | identifier '+=' expression ';'
    | identifier '-=' expression ';'
    | identifier '*=' expression ';'
    | identifier '/=' expression ';'
    ;

ifStatement
    : 'if' '(' expression ')' block ('else' block)?
    ;

whileStatement
    : 'while' '(' expression ')' block
    ;

functionDeclaration
    : 'function' identifier '(' parameterList? ')' block
    ;

functionCall
    : identifier '(' expressionList? ')'
    ;

expressionList
    : expression (',' expression)*
    ;

parameterList
    : identifier (',' identifier)*
    ;

block
    : '{' (statement)* '}'
    ;

// Define expressions with precedence from lowest to highest

expression
    : logicalOrExpr
    ;

logicalOrExpr
    : logicalOrExpr '||' logicalAndExpr   // Lowest precedence (logical OR)
    | logicalAndExpr
    ;

logicalAndExpr
    : logicalAndExpr '&&' equalityExpr    // Logical AND
    | equalityExpr
    ;

equalityExpr
    : equalityExpr ('==' | '!=') relationalExpr   // Equality and inequality
    | relationalExpr
    ;

relationalExpr
    : relationalExpr ('<' | '>' | '<=' | '>=') additiveExpr   // Relational operators
    | additiveExpr
    ;

additiveExpr
    : additiveExpr ('+' | '-') multiplicativeExpr  // Addition and subtraction
    | multiplicativeExpr
    ;

multiplicativeExpr
    : multiplicativeExpr ('*' | '/') factor        // Multiplication and division
    | factor
    ;

factor
    : '(' expression ')'                           // Parenthesized expression
    | identifier                                   // Variable or function name
    | constant                                     // Literal values (integers, floats, strings, booleans)
    | STRING                                       // String literals
    | BOOLEAN                                      // Boolean literals
    | 'read' '(' ')'                               // read() function call
    | functionCall
    | '!' factor                                   // Unary NOT operator (highest precedence)
    ;

// Define identifiers and constants

identifier
    : NAME
    ;

constant
    : '-'? INTEGER
    | '-'? FLOAT
    ;

// Lexer rules

BOOLEAN : 'true' | 'false' ;

NAME    : [a-zA-Z_$][0-9a-zA-Z_$]* ;

FLOAT   : [0-9]+ '.' [0-9]* | '.' [0-9]+ ;

INTEGER : '0' | [1-9][0-9]* ;

STRING  : '"' (ESC | ~["\\])* '"' ;

fragment ESC : '\\' [btnfr"\\] ;

WS : [ \t\r\n]+ -> skip ;
