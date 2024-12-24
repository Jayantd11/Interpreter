import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * CS 3304 Fall 2024
 * Project 1
 * Updated Main Java class to handle the entire input as one program.
 *
 * Author: Jayant Dulani
 */
public class PR1 {
    /**
     * Main method to parse and execute the input program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws Exception {
        // Read the entire input from System.in
        CharStream input = CharStreams.fromStream(System.in);
        // Create a lexer that feeds off of input CharStream
        PR1Lexer lexer = new PR1Lexer(input);
        // Create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Create a parser that feeds off the tokens buffer
        PR1Parser parser = new PR1Parser(tokens);
        // Begin parsing at program rule
        ParseTree tree = parser.program();
        // Create a visitor to evaluate the parse tree
        PR1FullVisitor visitor = new PR1FullVisitor();
        // Visit the parse tree and execute the program
        visitor.visit(tree);
    }
}
