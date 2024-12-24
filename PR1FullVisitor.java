import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * CS 3304 Fall 2024
 * Project 1
 * Updated Full Visitor to handle more things.
 *
 * Author: Jayant Dulani
 */

public class PR1FullVisitor extends PR1BaseVisitor<Object> {
    HashMap<String, Object> symbolTable = new HashMap<>();
    HashMap<String, Function> functions = new HashMap<>();

    public static class BreakException extends RuntimeException {}
    public static class ContinueException extends RuntimeException {}
    public static class ReturnException extends RuntimeException {
        public final Object value;
        public ReturnException(Object value) {
            this.value = value;
        }
    }

    @Override
    public Object visitProgram(PR1Parser.ProgramContext ctx) {
        return visitChildren(ctx);// Visit all children in the program context
    }

    /*
     * Handles different types of statements, including break, continue,
     * variable declarations, assignments, if/while statements, and more.
     */
    @Override
    public Object visitStatement(PR1Parser.StatementContext ctx) {
        if (ctx.getText().equals("break;")) {
            throw new BreakException();
        }
        if (ctx.getText().equals("continue;")) {
            throw new ContinueException();
        }
        if (ctx.variableDeclaration() != null) {
            return visitVariableDeclaration(ctx.variableDeclaration());
        } else if (ctx.assignment() != null) {
            return visitAssignment(ctx.assignment());
        } else if (ctx.ifStatement() != null) {
            return visitIfStatement(ctx.ifStatement());
        } else if (ctx.whileStatement() != null) {
            return visitWhileStatement(ctx.whileStatement());
        } else if (ctx.writeStatement() != null) {
            return visitWriteStatement(ctx.writeStatement());
        } else if (ctx.functionDeclaration() != null) { 
            return visitFunctionDeclaration(ctx.functionDeclaration());
        } else if (ctx.block() != null) {  
            return visitBlock(ctx.block());
        } else if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        else if (ctx.returnStatement() != null) {  
            return visitReturnStatement(ctx.returnStatement());
        }
        return null;
    }

    /* 
     * Evaluates constants for integer or floating-point types.
     */
    @Override
    public Object visitConstant(PR1Parser.ConstantContext ctx) {
        if (ctx.INTEGER() != null) {
            return Integer.parseInt(ctx.INTEGER().getText());
        } else if (ctx.FLOAT() != null) {
            return Double.parseDouble(ctx.FLOAT().getText());
        }
        return null;
    }

    /*
     * Handles variable declarations, initializing with "Undefined" if no expression is assigned.
     */
    @Override
    public Object visitVariableDeclaration(PR1Parser.VariableDeclarationContext ctx) {
        for (PR1Parser.VariableContext varCtx : ctx.variableList().variable()) {
            String varName = varCtx.identifier().getText();
            Object value = varCtx.expression() != null ? visit(varCtx.expression()) : "Undefined";
            symbolTable.put(varName, value);
        }
        return null;
    }

    /* 
     * Processes assignment statements, storing the evaluated value in the symbol table.
     */
    @Override
    public Object visitAssignment(PR1Parser.AssignmentContext ctx) {
        String varName = ctx.identifier().getText();
        Object value = visit(ctx.expression());
        symbolTable.put(varName, value);// Update symbol table with new value
        return value;
    }

    /* 
     * Implements 'write' statements to output evaluated expressions.
     * basically a print function
     */
    @Override
    public Object visitWriteStatement(PR1Parser.WriteStatementContext ctx) {
        Object value = visit(ctx.expression());
        if (value == null || "Undefined".equals(value)) {
            System.out.println("Undefined");
        } else {
            System.out.println(value.toString());
        }
        return null;
    }

    /* 
     * Processes addition and subtraction, handling string concatenation or numeric operations.
     */
    @Override
    public Object visitExpression(PR1Parser.ExpressionContext ctx) {
        return visit(ctx.getChild(0));
    }

   /*
     * Visit logical OR expressions, returning true if any operand is true
     */
    @Override
    public Object visitLogicalOrExpr(PR1Parser.LogicalOrExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            return (Boolean) visit(ctx.getChild(0)) || (Boolean) visit(ctx.getChild(2));
        }
        return visit(ctx.getChild(0));
    }

    /*
     * Visit logical AND expressions, returning true only if both operands are true
     */
    @Override
    public Object visitLogicalAndExpr(PR1Parser.LogicalAndExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            return (Boolean) visit(ctx.getChild(0)) && (Boolean) visit(ctx.getChild(2));
        }
        return visit(ctx.getChild(0));
    }

    /*
     * Visit equality expressions to compare left and right operands for equality or inequality
     */
    @Override
    public Object visitEqualityExpr(PR1Parser.EqualityExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            Object left = visit(ctx.getChild(0));
            Object right = visit(ctx.getChild(2));
            String operator = ctx.getChild(1).getText();
            if (left == null || right == null) return false;
            if ("==".equals(operator)) {
                return left.equals(right);
            } else if ("!=".equals(operator)) {
                return !left.equals(right);
            }
        }
        return visit(ctx.getChild(0));
    }

        /*
     * Visit relational expressions, handling <, >, <=, and >= comparisons between operands
     */
    @Override
    public Object visitRelationalExpr(PR1Parser.RelationalExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            double left = ((Number) visit(ctx.getChild(0))).doubleValue();
            double right = ((Number) visit(ctx.getChild(2))).doubleValue();
            String operator = ctx.getChild(1).getText();
            switch (operator) {
                case "<": return left < right;
                case ">": return left > right;
                case "<=": return left <= right;
                case ">=": return left >= right;
            }
        }
        return visit(ctx.getChild(0));
    }
    /* 
    * Processes addition and subtraction, handling string concatenation or numeric operations.
    */
    @Override
    public Object visitAdditiveExpr(PR1Parser.AdditiveExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            Object left = visit(ctx.getChild(0));
            Object right = visit(ctx.getChild(2));
            String operator = ctx.getChild(1).getText();
            if ("+".equals(operator)) {
                if (left instanceof String || right instanceof String) {
                    return left.toString() + right.toString();
                } else if (left instanceof Number && right instanceof Number) {
                    if (left instanceof Double || right instanceof Double) {
                        return ((Number) left).doubleValue() + ((Number) right).doubleValue();
                    } else {
                        return ((Number) left).intValue() + ((Number) right).intValue();
                    }
                }
                } else if ("-".equals(operator) && left instanceof Number && right instanceof Number) {
                    if (left instanceof Double || right instanceof Double) {
                        return ((Number) left).doubleValue() - ((Number) right).doubleValue();
                    } else {
                        return ((Number) left).intValue() - ((Number) right).intValue();
                    }
                }
        }
        return visit(ctx.getChild(0));
    }

    /*
     * Implements multiplication and division operations with type handling for integer and double.
     */
    @Override
    public Object visitMultiplicativeExpr(PR1Parser.MultiplicativeExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            Object left = visit(ctx.getChild(0));
            Object right = visit(ctx.getChild(2));
            String operator = ctx.getChild(1).getText();
            if ("*".equals(operator)) {
                if (left instanceof Number && right instanceof Number) {
                    if (left instanceof Double || right instanceof Double) {
                        return ((Number) left).doubleValue() * ((Number) right).doubleValue();
                    } else {
                        return ((Number) left).intValue() * ((Number) right).intValue();
                    }
                }
            } else if ("/".equals(operator) && left instanceof Number && right instanceof Number) {
                double rightVal = ((Number) right).doubleValue();
                
                // Check for division by zero
                if (rightVal == 0.0) {
                    return Double.POSITIVE_INFINITY;
                }
                
                // Return a double if either operand is a double
                if (left instanceof Double || right instanceof Double) {
                    return ((Number) left).doubleValue() / rightVal;
                } else {
                    return ((Number) left).intValue() / ((Number) right).intValue();
                }
            }
        }
        return visit(ctx.getChild(0));
    }

     /*
     * Handles function calls by invoking stored function declarations with passed arguments.
     */
    @Override
    public Object visitFunctionCall(PR1Parser.FunctionCallContext ctx) {
        String funcName = ctx.identifier().getText();
        List<Object> args = new ArrayList<>();
        if (ctx.expressionList() != null) {
            for (var expr : ctx.expressionList().expression()) {
                args.add(visit(expr));
            }
        }
        Function function = functions.get(funcName);
        if (function != null) {
            return function.invoke(args);
        }
        return "Undefined function: " + funcName;
    }

    /*
     * Stores function declarations in the functions map for later invocation.
     */
    @Override
    public Object visitFunctionDeclaration(PR1Parser.FunctionDeclarationContext ctx) {
        String funcName = ctx.identifier().getText();
        functions.put(funcName, new Function(ctx.parameterList(), ctx.block()));
        return null;
    }
    /*
     * Base case to evaluate literals, identifiers, function calls, and read input.
     */
    @Override
    public Object visitFactor(PR1Parser.FactorContext ctx) {
        if (ctx.BOOLEAN() != null) {
            return Boolean.parseBoolean(ctx.BOOLEAN().getText());
        } else if (ctx.identifier() != null) {
            String varName = ctx.identifier().getText();
            if (!symbolTable.containsKey(varName)) {
                return "Undefined";
            }
            return symbolTable.get(varName);
        } else if (ctx.functionCall() != null) {
            return visit(ctx.functionCall());
        } else if (ctx.constant() != null) {
            return visit(ctx.constant());
        } else if (ctx.STRING() != null) {
            return ctx.STRING().getText().replace("\"", "");
        } else if (ctx.getChild(0).getText().equals("!")) {
            Object value = visit(ctx.getChild(1));
            if (value instanceof Boolean) {
                return !(Boolean) value;
            } else {
                return "Undefined";
            }
        } else if (ctx.getText().equals("read()")) {
            return readInput();
        }
        return visit(ctx.expression());
    }

    //tried a lot of methods for read input but none work for me
    /* 
     * Reads input from standard input for the 'read()' function.
     */
    private Object readInput() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e){
            throw new RuntimeException("error reading");
        }
    }


    /*
     * Processes if statements by evaluating the condition and visiting the appropriate block.
     */ 
    @Override
    public Object visitIfStatement(PR1Parser.IfStatementContext ctx) {
        if ((Boolean) visit(ctx.expression())) {
            visit(ctx.block(0));
        } else if (ctx.block().size() > 1) {
            visit(ctx.block(1));
        }
        return null;
    }

    /*
     * Executes a while loop by repeatedly evaluating the condition and visiting the block.
     */
    @Override
    public Object visitWhileStatement(PR1Parser.WhileStatementContext ctx) {
        while (true) {
            Object condition = visit(ctx.expression());
            if (!(condition instanceof Boolean)) {
                throw new RuntimeException("Condition in while statement must be boolean");
            }
            if (!(Boolean) condition) {
                break;
            }
            try {

                visit(ctx.block());
            } catch (BreakException be) {

                break;
            } catch (ContinueException ce) {

                continue;
            }
            condition = visit(ctx.expression());
        }
        return null;
    }
    
    
    /*
     * Manages scoping within a block by creating a local scope.
     */
    @Override
    public Object visitBlock(PR1Parser.BlockContext ctx) {
        // Create a new local scope that only contains new variable declarations
        HashMap<String, Object> previousScope = symbolTable;
        symbolTable = new HashMap<>(symbolTable);
    
        try {
            for (PR1Parser.StatementContext stmtCtx : ctx.statement()) {
                visit(stmtCtx);
            }
        } finally {
            //merge the changes back into the previous scope, important to handle while statemetns otherwise the variables wont update
            previousScope.putAll(symbolTable);
            symbolTable = previousScope;
        }
        return null;
    }

    /*
     * Evaluates and returns the result from a function by throwing a ReturnException.
     */
    @Override
    public Object visitReturnStatement(PR1Parser.ReturnStatementContext ctx) {
        Object returnValue = visit(ctx.expression());  
        throw new ReturnException(returnValue);  
    }

    /*
     * Represents a function with parameters and a block.
     */
    class Function {
        PR1Parser.ParameterListContext parameters;
        PR1Parser.BlockContext block;
    
        Function(PR1Parser.ParameterListContext parameters, PR1Parser.BlockContext block) {
            this.parameters = parameters;
            this.block = block;
        }
    
        Object invoke(List<Object> args) {
            // Save the current symbol table as the previous scope
            HashMap<String, Object> previousScope = new HashMap<>(symbolTable);
            // Bind arguments to parameters in the new scope
            for (int i = 0; i < parameters.identifier().size(); i++) {
                symbolTable.put(parameters.identifier(i).getText(), args.get(i));
            }
    
            try {
                // Execute the function's block and capture the result if there's a return
                return visit(block);
            } catch (ReturnException e) {
                // Capture the return value from the thrown ReturnException
                return e.value;
            } finally {
                // Restore the symbol table to its previous state after the function completes
                symbolTable = previousScope;
            }
        }
    }
    
}
