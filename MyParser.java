package Task2;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * @author 215732
 */
//Systematic approach when writing methods - visualising the Parse Tree
//Simplifies the process and allows a better understanding of the respective methods
/**
 * Various errors were identified which lead to the result of the Parser
 * These errors were not able to be fully due to uncertainty of their origin
 * One of the main problems was that the list(tokens) was accessed by the different methods
 * however they were seemingly not identified properly, leading to NUllPointerExceptions.
 * Furthermore, this error led to a chain of errors within the blckPrse method and to the continuous
 * throwing of a Syntax exception. Through the various method calls, the also caused difficulties with 
 * various main methods that were attempted (at the bottom of the file). The problem probably lies somewhere in the 
 * first method, within the first recursive call.
 * 
 */

public class MyParser implements Parser {
    
    private List<Token> tokens;
    
    
    /**
     *
     * @param input
     * @return MyParser_AST
     * @throws SyntaxException
     * @throws Task2Exception
     */
    //Defining going through tree - printing out tokens
    public Block parse(List<Token> input) throws SyntaxException, Task2Exception {
        //List<Token> tokens = new ArrayList<>(input);
        tokens = input;
        
          //Different approach attempted to find list contents - did not work properly
          //Received various NullPointerException
            //        List<Exp> list = new ArrayList<>();
            //        Block prse_AST = new Block(list);
            //        
            //            return prse_AST;
            //                    }
        

        //Setting up variable AST to represent tree structure
        Block Parse_AST = blckPrse();
        System.out.println(tokens);
        //To remove any tokens after being processed
            //   tokens = tokens.subList(1, tokens.size());
        tokens.remove(0);
        //New Syntax Exception if list is not empty
        try {
            if (!tokens.isEmpty()) {
            }
        } catch (Exception e) {
            throw new SyntaxException("Syntax Error");
        }
        //Return MyParser_AST - method call
        return Parse_AST;
        
    }
    
    
    /**
     *
     * @param input
     * @return null
     * @throws SyntaxException
     * @throws Task2Exception
     */
    //Process of choosing a new token
    public Token newToken() throws SyntaxException, Task2Exception {
        // if (tokens.isEmpty()) {
        //throwing two new exceptions depending on type
        try {
        //get token from index 0    
            return tokens.get(0);
        } //IndexOutOfBoundsException if list is accessed via illegal index
        catch (IndexOutOfBoundsException e) {
            throw new SyntaxException("Syntax error");
        } catch (Exception e) {
            throw new Task2Exception("");
        }
        
    }
    
    
         /**
     *
     * @param input
     * @return MyParser_AST
     * @throws SyntaxException
     * @throws Task2Exception
     * Method that compiles, however not correctly, code doesn't not recognise the left curly bracket
     * Return null required to make it compile properly - failure of first method in class leads to continuous errors
     * if another Syntax Exception is thrown
     * 
     */
    
    //First step in tree, as specified in grammer (Block)
    public Block blckPrse() throws SyntaxException, Task2Exception {
        
        try {
     if (tokens.get(0) instanceof T_LeftCurlyBracket) {
         //Recursive method call
                Block prse = new Block(enePrse());
                if (tokens.get(0) instanceof T_RightCurlyBracket) {
                    return prse;
                }
            }
        } catch (Exception e) {
            throw new SyntaxException("");
        }
        return null;
    }
    
    /**
     *
     * @param input
     * @return MyParser_AST
     * @throws SyntaxException
     * @throws Task2Exception
     * Different approach to method - caused multiple errors that were not identified
     * Caused similar nullpointer exceptions and was unable to work with main method for testing
     * 
     */
//    public Block blckPrse() throws SyntaxException,Task2Exception {
//        try {
//            
//            if(newToken() instanceof T_LeftCurlyBracket) {
//                
//                ///
//                System.out.println("Left Curly Bracket found: ");
//                tokens.remove(0);
//                
//                Block parseBlock = new Block(enePrse());
//                
//                if(newToken() instanceof T_RightCurlyBracket) {
//                    ///
//                    System.out.println("Right Curly Bracket found: ");
//                    return parseBlock;
//                }
//            }
//        }catch(Exception e) {
//                     throw new Task2Exception("Task2Exception error.");
//                    }
//            //   throw new SyntaxException("Closing curly bracket missing");
//        }
//        
//    

 
      /**
     *
     * @param input
     * @return MyParser_AST
     * @throws SyntaxException
     * @throws Task2Exception
     * Method that compiles, however not correctly, code doesn't not recognise the left curly bracket
     * Similar issues to the other method above - recursive calls do not actually carry the list information further
     * NullPointerException
     */
        
//       public Block blckPrse() throws SyntaxException, Task2Exception {
//        //We know that the language/expression has to start with a left curly bracket
//        //System.out.println(newToken());
//        //try {
//            if (tokens.get(0) instanceof T_LeftCurlyBracket) {
//                //Test print
//                //   System.out.println("3!");
//
//                //If we have a new token/expression we add a left curly bracket
//                //   tokens.add(new T_LeftCurlyBracket());
//                Block prse = new Block(enePrse());
//
//                //if a right curly bracket is found the expression ends
//                if (tokens.get(0) instanceof T_RightCurlyBracket) {
//                    System.out.println(prse);
//                    return prse;
//                }
//                
//            }
//            //If no curly bracket is present we throw an additional SyntaxException
//
//
////        }
////        catch(Exception e){
////            throw new SyntaxException("No curly bracket found");
////        }
////        throw new SyntaxException("");
//        return null;
        
    
    
    /**
     *
     * @param input
     * @return exps
     * @throws SyntaxException
     * @throws Task2Exception
     * enePrse method - creates list to differentiate between E and ENE when visualising the Syntax Tree
     */
    //Next step in the "tree" - here we need to consider whether the tree follows
    //E or E; ENE
    public List<Exp> enePrse() throws SyntaxException, Task2Exception {
        //This is represented by a list
        List<Exp> exps = new ArrayList<>();
        //while a Semicolon is present we add a new value of E
        while (newToken() instanceof T_Semicolon) {
            tokens.remove(0);
            //Recursive call to next method
            exps.add(ePrse());
            
        }
        //returns a list
        return exps;
    }
    
    /**
     *
     * @return intLit
     * @return skip
     * @return blkeprse
     * @throws SyntaxException
     * @throws Task2Exception
     */
    //In the final method, there are various options for the "tree"
    //Either a simple int, a skip or a new Block is chosen
    public Exp ePrse() throws SyntaxException, Task2Exception {
        //Simple Int - using IntLiteral from Block Class
        if (newToken() instanceof T_Integer) {
            IntLiteral intLit = new IntLiteral(((T_Integer) tokens.get(0)).n);
            tokens.remove(0);
            //returning IntLiteral
            return intLit;
            
        } //Skip using T_Skip from Token interface
        else if (newToken() instanceof T_Skip) {
            //New Skip object
            Skip skip = new Skip();
            tokens.remove(0);
            //returning skip object
            return skip;
            
        } //Finally a new block object - calling method from before and potentially elongating the expression
        else if (newToken() instanceof T_LeftCurlyBracket) {
            BlockExp blkeprse = new BlockExp(blckPrse());
            tokens.remove(0);
            //returning block object
            return blkeprse;
            
        } else {
            throw new SyntaxException("");
        }
        
    }
     /**
     *
     * @param b
     * printBlock method - 
     * prints out a block, string and expression
     */
    public void printBlock(Block b) {
                System.out.println(b + "" +b.exps);
    }
    
}
    
      /**
     *
     * @param args
     * @throws SyntaxException
     * @throws Task2Exception
     * One of many main methods - creates new Parser
     * simply prints out token objects not actual reference to AST or Block
     * Compiles in the end - but not in the way intended
     */
//    public static void main(String args[]) throws SyntaxException, Task2Exception {
//        MyParser testParser = new MyParser();
//        
//        Token[] someItems = {new T_LeftCurlyBracket(), new T_Integer(1), new T_RightCurlyBracket()};
//        ArrayList<Token> parseArray = new ArrayList<>();
//
//        for (Token item : someItems) {
//            parseArray.add(item);
//
//
//             }
//            Block output = testParser.parse(parseArray);
//        
//            System.out.print(output); 
            
            //Two outputs not functional
            // System.out.print(output.exps.get(0) + "\n");
            // System.out.print("\n" + output.exps.get(0));


    


          
//        MyParser testParser = new MyParser();
//        
//        ArrayList<Token> t1 = new ArrayList<>();
//            t1.addAll(Arrays.asList(new T_LeftCurlyBracket(), new T_Integer(0), new T_RightCurlyBracket()));
//            Block b = testParser.parse(t1);
//            testParser.printBlock(b);
        
     

    
    


//Additional Main Methods that were attempted

//        List<Token> tokens2 = new ArrayList();
//        
//        T_LeftCurlyBracket lcb = new T_LeftCurlyBracket();
//        tokens2.add(lcb);
//        
//        T_Integer tin = new T_Integer(4);
//        tokens2.add(tin);
//        
//        T_RightCurlyBracket rcb = new T_RightCurlyBracket();
//        tokens2.add(rcb);
//        
//        
//        Block output = testParser.parse(tokens2);
//        System.out.println(output);
        
//        System.out.println(tokens2);
//
//        parse(tokens2);


//Another main method used -- also not functional

//    public static void main(String args[]) throws SyntaxException, Task2Exception {
//        MyParser theParser = new MyParser();

//       // List<Token> input = Arrays.asList(new Token[]{new T_LeftCurlyBracket(), new T_Integer(1), new T_RightCurlyBracket()});
//        ArrayList<Token> tl = new ArrayList<>();
//        tl.addAll(Arrays.asList(new T_LeftCurlyBracket(), new T_Integer(0), new T_RightCurlyBracket()));
//        //List<Token> input = Arrays.asList(new Token[]{new T_LeftCurlyBracket(), new T_Integer(1), new T_Semicolon(), new T_LeftCurlyBracket(), new T_Skip(), new T_RightCurlyBracket(), new T_RightCurlyBracket()});
//        Block output = theParser.parse(tl);
//        System.out.println(output);
////        for(int i=0; i < output.exps.size(); i++){
////            System.out.print(output.exps.get(i) + "\n");
////        }
//        //System.out.print(output); //.exps.size() + "\n");
//        //System.out.print("\n" + output.exps.get(0));
//    }


















