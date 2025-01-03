This code defines a simple interpreter for the programming language defined by the PR1 grammar.
The provided code can parse and execute common programming constructs: variables, arithmetic operations, conditionals, loops, and functions.
All these methods in this visitor class refer to a particular grammar rule; that is, each method parses a certain type of statement or expression found in source code.

The visitProgram method is the entry point. It iterates over each of the statements in the program. It passes every statement to visitStatement, 
where the kind of the statement is determined. For example, if it's a variable declaration, assignment, if statement, or while loop, then visitStatement will
call the proper method to handle it. This design allows the code to interpret lots of different kinds of statements one at a time. Statements like break, continue and 
return use custom exceptions to control the flow in a loop or function.

Expressions are handled by specific methods—visitAdditiveExpr for addition and subtraction, for example, or visitLogicalOrExpr and visitLogicalAndExpr for logical operations.
Each method evaluates an expression and, through a result, returns it according to the operator used and with regard to the data types. For example, if both operands are numbers, 
it will do addition or subtraction accordingly. If one operand is a string, it will concatenate the values instead.

The variable handling is done by a symbolTable that keeps track of the name of variables and their values. The code also creates a new local scope upon entering or exiting functions or 
blocks like those inside a loop or if statement so that the variables defined in that scope do not interfere with those outside the scope. In addition, 
the visitor can also read user input with readInput for interactive programs; function calls and function definitions allow the code to handle reusable logic through parameters and local scopes. 
