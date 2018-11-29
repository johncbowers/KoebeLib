grammar Escher ;

/*
 * Parser Rules
 */

file            : mainline+ ;
mainline        : tileDefinition | expression;

definition            : tileDefinition;
tileDefinition        : TYPE ID '(' ID (',' (ID))* ')' ;
vertexSetDefinition   : VERTEX '{' tileFunction+ (',' expression)* '}' ;
subdivisionDefinition : SUBTILE '{' expression (',' expression)* '}' ;

expression      : function | assignment ;
function        : tileFunction | splitFunction;
tileFunction    : TILE '(' ID ',' NUMBER ')' ;
splitFunction   : ID (',' ID)+ '=' SPLIT '(' ID ',' ID ')' ; //DISCUSS THIS

assignment      : vertexAssignment ;

vertexAssignment    : ID (',' ID)* ('=' (splitFunction | ID))? ;

 /*
  * Lexer Rules
  */
  

fragment DIGIT  : '0'..'9' ;

SUBTILE         : 'subtiles' | 'subtile';
TYPE            : 'Type' ;
VERTEX          : 'vertex' | 'vertices';

TILE            : 'tile' ;
SPLIT           : 'split' ;
CONNECT         : 'connect' ;

LETTER          : ('a'..'z' | 'A'..'Z')+ ;
END             : '\n' ; //I THINK THIS SHOULD WORK?
WHITESPACE      : [\n\t\r]+ -> skip ;
NUMBER          : DIGIT+ ;
ID              : (LETTER | DIGIT) (LETTER | DIGIT)* ;
