/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/


/**
 * @author 215732
 */
 
package Task1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyLexer implements Lexer {
    
    List<Token> tokens;
    String input;
    //Use of enum to simplifiy process of defining - regex strings
    public static enum tokType {
        //RegEx tokens - as defined in the language specifications
        DEF("Def"), IF("If"), THEN("Then"), ELSE("Else"), SKIP("Skip"), WHILE("While"), DO("Do"), REPEAT("Repeat"), UNTIL("Until"), BREAK("Break"), CONTINUE("Continue"),
        INTEGER("[0-9]"), //Integer tokens
        IDENTIFIER("([a-z]|[A-Z])[0-9]_"),//Identifier tokens
        BLANK("\\⎵"),NEWLINE("\n"),FORMFEED("\f"),CARRIAGE("\r"),TAB("\t"),//Whitspace
        //Operators
        SEMICOLON("\\;"), LEFTBRACKET("\\("), RIGHTBRACKET("\\)"), EQUALDEFINES("\\=="), EQUAL("\\="), LESSTHAN("\\<"), GREATERTHAN("\\>"), LESSEQ("\\<="),
        GREATEREQ("\\>="), COMMA("\\,"), LEFTCURLYBRACKET("\\{"), RIGHTCURLYBRACKET("\\}"), ASSIGN("\\:="), PLUS("\\+"), TIMES("\\*"), MINUS("\\-"), DIV("\\/");
        
        //Declaring pattern as type string - later use in while loop and list
        public final String pattern;
        
        private tokType(String pattern) {
            //reference to current object - pattern
            this.pattern = pattern;
        }
        
    }
    
    //Static class token type, describes data type and tokType
    public static class TokenType {
        //two parameters - referring to our enum
        public tokType type;
        public String data;
        
        /**
         *
         * @param type
         * @param data
         */
        public TokenType(tokType type, String data) {
            //reference to current objects
            this.type = type;
            this.data = data;
        }
        
        /**
         *
         * @return String.format()
         */
        //to String for use in while loop - to print out string
        public String toString() {
            //string formating example
            return String.format("(%s %s)", type.name(), data);
        }
    }
    
    /**
     *
     * @param input
     * @return tokens
     * @throws LexicalException
     * @throws Task1Exception
     * lex method makes use of RegularExpression matching to identify tokens and throw exceptions accordingly
     * As defined in the enum, the different RegEx are used and are checked for within the input
     */
    @Override
    public List<Token> lex(String input) throws LexicalException,Task1Exception{
        // New list of tokens - Array List
        List<Token> tokens = new ArrayList<>();
        
        //Use of Task1Exception to check if list is empty before we add tokens
        try {
            if (!tokens.isEmpty()) {
            }
        } catch (Exception e) {
            throw new Task1Exception("List is not empty before tokens are added");
        }
        
        // Using StringBuffer and using Pattern import
        StringBuffer tokPttnBuffer = new StringBuffer();
        //StringBuffer covers various functionalities for Strings e.g. substring - as seen later
        for (tokType tokenType : tokType.values()) {
            //Use of string format once more to set up pattern structure depending on parameters
            //Appending this format to the StringBuffer - then applied to the Pattern itself
            tokPttnBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }
        //Specific pattern that is later analysed by matcher
        //Pattern.compile used to insert StringBuffer object
        Pattern tokPttn = Pattern.compile(new String(tokPttnBuffer.substring(1)));
        
        // Token matching - while loop
        Matcher mtchr = tokPttn.matcher(input);
        while (mtchr.find()) {
            //Looping through all different lexems and given regular expressions
            //If the given token is not equal to null - add new Token
            if (mtchr.group(tokType.DEF.name()) != null) {
                tokens.add(new T_Def());
            } else if (mtchr.group(tokType.IF.name()) != null) {
                tokens.add(new T_If());
            } else if (mtchr.group(tokType.THEN.name()) != null) {
                tokens.add(new T_Then());
            } else if (mtchr.group(tokType.ELSE.name()) != null) {
                tokens.add(new T_Else());
            } else if (mtchr.group(tokType.SKIP.name()) != null) {
                tokens.add(new T_Skip());
            } else if (mtchr.group(tokType.WHILE.name()) != null) {
                tokens.add(new T_While());
            } else if (mtchr.group(tokType.DO.name()) != null) {
                tokens.add(new T_Do());
            } else if (mtchr.group(tokType.REPEAT.name()) != null) {
                tokens.add(new T_Repeat());
            } else if (mtchr.group(tokType.UNTIL.name()) != null) {
                tokens.add(new T_Until());
            } else if (mtchr.group(tokType.BREAK.name()) != null) {
                tokens.add(new T_Break());
            } else if (mtchr.group(tokType.CONTINUE.name()) != null) {
                tokens.add(new T_Continue());
            } else if (mtchr.group(tokType.INTEGER.name()) != null) {
                tokens.add(new T_Integer(mtchr.groupCount()));
            } else if (mtchr.group(tokType.IDENTIFIER.name()) != null) {
                tokens.add(new T_Identifier(mtchr.group(input)));
                //Whitespace tokens - followed by continue - as no specifc tokens are added
            } else if (mtchr.group(tokType.BLANK.name()) != null) {
                continue;
            } else if (mtchr.group(tokType.NEWLINE.name()) != null) {
                continue;
            }else if (mtchr.group(tokType.FORMFEED.name()) != null) {
                continue;
            }else if (mtchr.group(tokType.CARRIAGE.name()) != null) {
                continue;
            }else if (mtchr.group(tokType.TAB.name()) != null) {
                continue;
            }else if (mtchr.group(tokType.SEMICOLON.name()) != null) {
                tokens.add(new T_Semicolon());
            } else if (mtchr.group(tokType.LEFTBRACKET.name()) != null) {
                tokens.add(new T_LeftBracket());
            } else if (mtchr.group(tokType.RIGHTBRACKET.name()) != null) {
                tokens.add(new T_RightBracket());
            } else if (mtchr.group(tokType.EQUALDEFINES.name()) != null) {
                tokens.add(new T_EqualDefines());
            } else if (mtchr.group(tokType.EQUAL.name()) != null) {
                tokens.add(new T_Equal());
            } else if (mtchr.group(tokType.LESSTHAN.name()) != null) {
                tokens.add(new T_LessThan());
            } else if (mtchr.group(tokType.GREATERTHAN.name()) != null) {
                tokens.add(new T_GreaterThan());
            } else if (mtchr.group(tokType.LESSEQ.name()) != null) {
                tokens.add(new T_LessEq());
            } else if (mtchr.group(tokType.GREATEREQ.name()) != null) {
                tokens.add(new T_GreaterEq());
            } else if (mtchr.group(tokType.COMMA.name()) != null) {
                tokens.add(new T_Comma());
            } else if (mtchr.group(tokType.LEFTCURLYBRACKET.name()) != null) {
                tokens.add(new T_LeftCurlyBracket());
            } else if (mtchr.group(tokType.RIGHTCURLYBRACKET.name()) != null) {
                tokens.add(new T_RightCurlyBracket());
            } else if (mtchr.group(tokType.ASSIGN.name()) != null) {
                tokens.add(new T_Assign());
            } else if (mtchr.group(tokType.PLUS.name()) != null) {
                tokens.add(new T_Plus());
            } else if (mtchr.group(tokType.TIMES.name()) != null) {
                tokens.add(new T_Times());
            } else if (mtchr.group(tokType.MINUS.name()) != null) {
                tokens.add(new T_Minus());
            } else if (mtchr.group(tokType.DIV.name()) != null) {
                tokens.add(new T_Div());
                
            } //Lexical Exception if a pattern/token does not apply to the language rules
            else {
                try {
                    //If one of the tokens is not present - throw a lexical exception
                    if (mtchr.group(tokType.DEF.name()) == null) {
                        //similar to calls from else if statements above
                    }
                } catch (Exception e) {
                    throw new LexicalException("Token not found");
                }
            }
            
        }
        
        return tokens;
    }
    //Main method for testing - use of different TokenLists to identify if tokens are properly printed ou
//    public static void main(String args[]) throws LexicalException, Task1Exception {
//       MyLexer testLexer = new MyLexer();
       //different outputs including different strings
      // List<Token> output = testLexer.lex("0 + 9 - 22");
      // List<Token> output = testLexer.lex("def f(x,y,z) = { if x == y then { z } else { 0 } }"); 
//         List<Token> output = testLexer.lex(";{{{}}};10 10 15 if if then then then else until");
//        
//       System.out.println(output);
    }
    
//}
    
    

//white space : String[] split = input.split("\\s+");






