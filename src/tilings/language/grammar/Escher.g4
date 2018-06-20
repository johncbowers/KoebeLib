grammar Escher ;

/*
 * Parser Rules
 */

file            : mainline+ ;
mainline        : phrase END ;
subline         : expression END ;
phrase          : definition | expression ;

definition            : tileDefinition | subdivisionDefinition ;
tileDefinition        : TILETYPE ID '{' NUMBER (',' NUMBER)* '}' ;
subdivisionDefinition : SUBDIVISION ID '{' subline+ '}' ;

expression      : function | assignment ;
function        : tileFunction | splitFunction | connectFunction ;
tileFunction    : TILE '(' ID ',' NUMBER ')' ;
splitFunction   : SPLIT '(' node ',' node ',' NUMBER ')' ;
connectFunction : CONNECT '(' node ',' node ')' ;

assignment          : vertexAssignment | edgeAssignment | childAssignment ;
vertexAssignment    : VERTEX ID (',' ID)* ('=' (splitFunction | node))? ;
edgeAssignment      : EDGE ID (('=' connectFunction) | ( '(' node ',' node ')')) ;
childAssignment     : CHILD ID '=' ID '(' childList+ ')' ;
graphAssignment     : GRAPH ID '=' graphDeclaration ;

graphDeclaration    : tileFunction ;

childList           : '[' node (',' node)* ']' ;
node                : ID ('.' 'vertex' '[' NUMBER ']')? ;
face                : ID ('.' 'face' '[' NUMBER ']')? ;

 /*
  * Lexer Rules
  */
  
fragment LETTER : ('a'..'z' | 'A'..'Z')+ ;
fragment DIGIT  : '0'..'9' ;

GRAPH           : 'GRAPH' ;
CHILD           : 'CHILD' ;
SUBDIVISION     : 'SUBDIVISION' ;
TILETYPE        : 'TILETYPE' ;
VERTEX          : 'VERTEX' ;
EDGE            : 'EDGE' ;

TILE            : 'tile' ;
SPLIT           : 'split' ;
CONNECT         : 'connect' ;

END             : ';' ;
WHITESPACE      : [ \n\t\r]+ -> skip ;
NUMBER          : DIGIT+ ;
ID              : LETTER (LETTER | DIGIT)* ;

