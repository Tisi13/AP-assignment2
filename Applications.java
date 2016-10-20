package assignment2;

import java.math.BigInteger;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Applications {

    PrintStream out;
    HashMap<Identifier, Set<BigInteger>> map = new HashMap<Identifier,Set<BigInteger>>();


    public Applications(){
        out = new PrintStream(System.out);
    }

    //=========Statements==========//
    private void assignment(Scanner in) throws APException{
        Identifier identifier = parseIdentifierAssignment(in);

        Set<BigInteger> result = parseExpression(in);

        eoln(in);
        /*-------- ADD identifier and set to HashMap-------*/
        map.put(identifier,result);
    }

    private void print_statement(Scanner in) throws APException {
        /* After '?' we either encounter an identifier or a set followed by an operator */
        /*----skip '?'-------*/
        nextChar(in);
        if (in.hasNext()){
            Set<BigInteger> set = parseExpression(in);
            print_set(set);
        }else
            throw new APException("Please insert a valid print statement");


    }
    private void print_set(Set<BigInteger> set) {
        //TO DO
        while(!set.isEmpty()){
            out.printf("%s ", set.getElement().toString());

        }

    }

    private void comment(Scanner in) throws APException {
        /*------skip '/'---------*/
        nextChar(in);
        in.nextLine();

        eoln(in);


    }

    Set<BigInteger> factor(Scanner in) throws APException {
        /* factor reads, if possible, a correct factor of the input.
           if this succeeds the factor is evaluated and the resulting
           set is returned. If this fails, then an error-message is given
           and an APException is thrown.
         */

        if (nextCharIsLetter(in)) {
            /* read an identifier and retrieve the set that belongs to it */
            Identifier identifier = parseIdentifier(in);
            return map.get(identifier);
        } else
        if(nextCharIs(in, '{')) {
            /*read a set*/
            return parseSet(in);
        } else
        if (nextCharIs(in, '(')) {
            /*----skip '('----------*/
            nextChar(in);
            /*determine the set that is the result of the complex factor*/
            Set<BigInteger> result = parseExpression(in);
            parseWhitespace(in);
            if (!nextCharIs(in, ')')){
                throw new APException("A complex operator must end with a )\n");

            }
            return result;

        } else
            throw new APException("Error reading in factor\n");
    }



    //========Scanner method=======//
    boolean nextCharIsLetter(Scanner in) {
        return in.hasNext("[a-zA-Z]+");
    }

    public char nextChar(Scanner in) {
        return in.next().charAt(0);
    }

    public boolean nextCharIs(Scanner in, char c) {
        return in.hasNext(Pattern.quote(""+c));
    }

    public boolean nextCharIsAlphanumeric(Scanner in) {
        return in.hasNext("[a-zA-Z0-9]");
    }

    boolean eof(Scanner in) { return !in.hasNextLine(); }

    public boolean nextCharIsBigInteger(Scanner in) {return in.hasNextBigInteger();}

    boolean multiplicative_operator(Scanner in) {return nextCharIs(in,'*');}

    boolean additive_operator(Scanner in) {return nextCharIs(in, '+')|| nextCharIs(in, '-')|| nextCharIs(in, '|');}

    boolean zero (Scanner in) { return nextCharIs(in, '0'); }

    void eoln(Scanner in) throws APException{
        parseWhitespace(in);
        //make sure all spaces are read
        if(in.hasNext()){
            throw new APException("something");
        }
    }


    //========Parse method=========//

    /*--------parse statement--------*/
    private void parse_statement(Scanner in) throws APException{
        if (nextCharIsLetter(in)){
            assignment(in);

        }else if (nextCharIs(in,'?')){
            print_statement(in);

        }else if (nextCharIs(in, '/')) {
            /* do nothing i don't really know why we need to input a comment*/
            comment(in);

        } else {
            throw new APException("The input is not a valid statement\n");
        }

    }
    /*--------------parse identifier -----------*/
    private Identifier parseIdentifier(Scanner in) throws APException {
        Identifier identifier = new Identifier();

        identifier.init(nextChar(in));

        while (in.hasNext()) {
            if(nextCharIsAlphanumeric(in)){
                identifier.addChar(nextChar(in));
            }else
            if (nextCharIs(in, ' ')) {
                parseWhitespace(in);
                if (!nextCharIsAlphanumeric(in)) {
                    break;
                } else {
                    throw new APException("Spaces between Identifier are not allowed\n");
                }
            }else
            if (additive_operator(in)||multiplicative_operator(in)){
                break;

            }else {
                throw new APException("Identifier consists of only alphanumerical characters\n");
            }
        }
        return identifier;
    }

    /*--------------parse identifier for assignment------------*/
    private Identifier parseIdentifierAssignment(Scanner in) throws APException {
        Identifier identifier = new Identifier();

        identifier.init(nextChar(in));

        /*adding alphanumeric to identifier until we reach '='*/
        while (in.hasNext()) {
            if(nextCharIsAlphanumeric(in)){
                identifier.addChar(nextChar(in));
            } else
            if (nextCharIs(in, '=')) {
                break;
            }else
            if (nextCharIs(in, ' ')) {
                parseWhitespace(in);
                if (!nextCharIs(in, '=')){
                    throw new APException("Spaces between Identifier are not allowed\n");
                }else {
                    break;
                }
            }else {
                throw new APException("Identifier consists of only alphanumerical characters\n");
            }

        }
        /*-------skip '='------*/
        nextChar(in);

        return identifier;

    }
    /*-----------parse Set--------------------*/
    private Set<BigInteger> parseSet(Scanner in) throws APException {
        Set<BigInteger> set = new Set<BigInteger>();
        /*-----------skip '{'---------*/
        nextChar(in);

        while (in.hasNext()) {

            parseWhitespace(in);

            if(in.hasNextBigInteger()) {
                /*add Biginteger or something---*/
                



            }



        }


        return set;
    }


    /*------------parse Expression------------*/
    private Set<BigInteger> parseExpression(Scanner in) throws APException {
        /*--------read term --------*/
        Set<BigInteger> result = term(in);

        parseWhitespace(in);

        while (additive_operator(in)) {
                char operator = nextChar(in);
                result = performOperation(operator, result, term(in));

        }

        eoln(in);

        return result;
    }

    /*--------- terms seperated by additive_operator--------*/
    Set<BigInteger> term(Scanner in) throws APException {
        parseWhitespace(in);

        Set<BigInteger> result = factor(in);

        while (multiplicative_operator(in)) {
            /*-----skip '*'------*/
            nextChar(in);
            result = (Set<BigInteger>) result.intersection(factor(in));
        }

        return result;

    }
    /*------------perform operator---------*/
    Set<BigInteger> performOperation(char operator, SetInterface<BigInteger> first, SetInterface<BigInteger> second) throws APException {
        if( operator == '+') {
            return (Set<BigInteger>) first.union((Set<BigInteger>) second);
        } else
        if (operator == '-') {
            return (Set<BigInteger>) first.complement((Set<BigInteger>) second);
        } else
            return (Set<BigInteger>) first.symmetricDifference((Set<BigInteger>) second);

    }

    /*----------parse whitespace----------*/
    private void parseWhitespace(Scanner in){
        while (nextCharIs(in, ' ')) {
            nextChar(in);
        }
    }

    //========Read method==========//


    private void start() throws APException {
        Scanner in = new Scanner(System.in);

        while(!eof(in)) {
            Scanner line = new Scanner(in.nextLine());
            parse_statement(line);
        }

    }

    public static void main(String[] argv) throws APException {
        new Applications().start();
        System.out.println();

    }
}
