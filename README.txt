Name: Oscar Rojas
UR ID: 28304814
CSC-254: Compiler Project Part 5

How to use:
    1) cd Tokenizer/bin
    2) java Driver ../../Oct-20-input-programs/<testfile>

    How to use the ParserAST class:
        1) Create an instance of a ParserAST and pass a file name as the only argument. 
        2) The parser constructor will automatically call the Analyzer.startScanning() method and set up a list of tokens. 
        3) Calling the program() method on a ParserAST instance will cause the ParserAST object to parse the tokens and output a representation of the AST.

The output for each test file can be found in the output directory. I do ask that the previous code generation assignments be regraded based on my current work. 

What Works. 
	The parser generates code for every test file without ecountering any fatal errors. It also evalautes expressions correctly. As of now, it doesn't handle nested conditions - which causes mandel.c to fail - but the code does seem reasonably correct. ab.c, loop_while.c, binrep.c, and tax.c all seem to behave correctly. fibonacci.c has a bug where instead of the fibonacci numbers it will print the array index of each element. 


	



LL(1) CFG
<program> --> <globalData> <func list> 
<globalData> --> <typeName> <idListB> SEMICOLON <globalData> | funcDecl funcTail <globalData>
<func list> --> EMPTY | <func> <func list>
<func> --> <func decl> <func decl_tail>
<func decl_tail> --> SEMICOLON | LEFT_BRACE <data decls> <stmts> RIGHT_BRACE
<type name> --> INT | VOID
<parameter list> --> EMPTY | <non-empty list>
<non-empty list> --> <type name> ID <non-empty listB>
<non-empty listB> --> EMPTY | , <type name> ID
<data decls> --> EMPTY | <type name> <id list> SEMICOLON <data decls>
<id list> --> <id> <id listB>
<id listB> EMPTY | , <id> <idlistB>
<id> --> ID <id_tail>
<id_tail> -- > EMPTY | LEFT_BRACKET <expression> RIGHT_BRACKET
<block stmts> --> LEFT_BRACE <stmts> RIGHT_BRACE
<stmts> --> EMPTY | <stmt> <stmts> 
<stmt> --> <assignment> | <general func call> | <printf func call> | <scanf func call> | <if stmts> | <while stmt> | <return stmt> | <break stmt> | <continue stmt> 
<assignment> --> EQUAL_SIGN <expression> SEMICOLON
<general func call> --> ID LEFT_PARANTHESIS <expr list> RIGHT_PARANTHESIS SEMICOLON
<printf func call> ID(printf) LEFT_PARAN STRING <printf_tail>
<printf_tail> --> RIGHT_PARAN SEMICOLON | , <expr> RIGHT_PARAN SEMICOLON 
<scanf func call> --> ID LEFT_PARAN STRING COMMA AND_SIGN <expr> RIGHT_PARANTHESIS SEMICOLON
<expr list> --> EMPTY | <non-empty expr list>
<non-empty expr list> --> <expression> <non-empty expr listB>
<non-empty expr lsitB> --> EMPTY | COMMA <expression> <non-empty expr listB>
<if stmt> --> IF LEFT_PARAN <condition expression> RIGHT_PARAN <block stmts> <if stmt_tail>
<if stmt_tail> --> EMPTY | ELSE <block stmts>
<condition expr> --> <condition> <condition_tail>
<condition_tail> --> EMPTY | <condition op> <condition>
<condition op> --> &&  | ||
<condition> --> <expression> <comparison op> <expresssion>
<comparison op> --> == | != | > | >= | < | <=
<while stmt> --> WHILE LEFT_PARAN <condition expr> RIGHT_PARAN <block stmts>
<return stmt> --> RETURN <return stmt_tail>
<return stmt_tail> SEMICOLON | <expression> SEMICOLON
<break stmt> --> BREAK SEMICOLON
<continue stmt> --> CONTINUE SEMICOLON
<expression> --> <term> <expressionB>
<expressionB> --> EMPTY | <addop> <term> <expressionB>
<addop> --> + | -
<term> --> <factor> <termB>
<termB> --> <mulop> <factor> <termB> | EMPTY
<mulop> --> * | /
<factor> --> ID <factor_tail> | NUMBER | MINUS NUMBER | LEFT_PARAN <expression> RIGHT PARAN
<factor_tail> --> LEFT_BRACKET <expression RIGHT_BRACKET | LEFT_PARAN <expression list> RIGHT_PARAN
