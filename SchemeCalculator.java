/**
 * @Author: Anupam Khargharia
 * This program uses three stacks to compute scheme expressions.
 * This program has three methods— schemesolver (main scheme calculator), calculator (basic operator calculator)
 * and isDigit (check if expression is double).
 *
 * Note: All expressions and exceptions are working except complex brackets where I have to—
 * compute multiple expressions inside brackets— example: ( + ( - 3 2 ( / 4 5 ) ) )
 *
 * The 3rd standard input: ( + ( - 6 ) ( * 2 3 4 ) ( / ( + 3 ) ( * ) ( - 2 3 1 ) ) )
 * cannot be solved by my program and gives out a wrong answer.
 *
 * The developed 3rd standard input CAN be solved by my program: ( + ( - 6 ) ( * 2 3 4 ) ( / 3 1 -2 ) )
 */



import java.util.Scanner;
import java.util.Stack;

public class SchemeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String line = sc.nextLine();
            System.out.println("Expression: " + line);
            String[] linearray = line.split(" "); //creating tokens out of input
            System.out.println("Answer: " + schemesolver(linearray));
        }
    }

    private static double schemesolver(String[] linearray){

        Stack<Double> opnstack = new Stack<>(); //operand stack
        Stack<Double> temp = new Stack<>(); //temp stack created to reverse order of elements in oprstack to facilitate subtraction & division
        Stack<Character> oprstack = new Stack<>(); //operator stack
        int opr_count = 0; //counter to capture first operator
        int exp_count = 0; //counter to consider exceptions like ( * )
        int opn_count = 0; //counter for number of operands
        int temp_sz = temp.size(); //size of temp Stack---> Assigned seperately because temp.size() keeps updating
        char oprcnt = '*'; //counter helper to capture first operator

        for (int i = 0; i < linearray.length; i++){
            String point = linearray[i];

            //Check if it is a digit and push to temporary stack
            if (isDigit(point) && exp_count==0){
                double num = Double.parseDouble(point);
                temp.push(num);
                opn_count++;
            }

            //Check if "(" open para and push to opr stack and push temp elements to opnstack
            else if (point.equals("(")){
                oprstack.push(point.charAt(0));
                temp_sz = temp.size();

                for (int j = 0; j < temp_sz; j++){
                    opnstack.push(temp.pop());
                }
                opn_count = 0;
            }

            //Check if point (element) is an operator and push to oprstack while taking exceptions into account
            else if (point.equals("+") || point.equals("-") || point.equals("*") || point.equals("/")){

                //exceptions
                if (linearray[i+1].equals(")")){
                    if (point.equals("+")){
                        temp.push(0.0);
                    }
                    else if (point.equals("*")){
                        temp.push(1.0);
                    }
                    exp_count++;
                }
                else if (linearray[i+2].equals(")")){
                    if (point.equals("+")){
                        double num = Double.parseDouble(linearray[i+1]);
                        temp.push(num);
                    }
                    if (point.equals("/")){
                        double num = Double.parseDouble(linearray[i+1]);
                        temp.push(1/num);
                    }
                    if (point.equals("-")){
                        double num = Double.parseDouble(linearray[i+1]);
                        temp.push(num*-1);
                    }
                    exp_count++;
                }

                //normal
                else {
                    oprstack.push(point.charAt(0));
                    if (opr_count == 0){
                        oprcnt = point.charAt(0);
                        opr_count++;
                    }
                }
            }

            //Check if ")" closed para and do operations.
            else if (point.equals(")")){
                temp_sz = temp.size();

                //push temp elements to opnstack
                for (int k = 0; k < temp_sz; k++){
                    opnstack.push(temp.pop());
                }

                if (exp_count == 0) {
                    //perform operations
                    for (int l = 0; l < opn_count-1; l++){
                        char op = oprstack.peek();
                        double b = opnstack.pop();
                        double a = opnstack.pop();
                        opnstack.push(calculator(op, b, a));
                    }
                    //remove i.e pop "(" and ")"
                    oprstack.pop();
                    oprstack.pop();
                    opn_count = 0;
                }
                //for exceptions
                if (exp_count == 1){
                    exp_count--;
                }
            }
        }

        //to do final calculations with the first operator
        int sz = opnstack.size();
        if (sz >= 2){
            for (int m = 0; m < sz - 1; m++){
                double b = opnstack.pop();
                double a = opnstack.pop();
                if (oprcnt == '/'){
                    opnstack.push(calculator(oprcnt, b, a));
                }
                else{
                    opnstack.push(calculator(oprcnt, a, b));
                }
            }
        }

        return opnstack.pop();

    }

    //basic calculator to apply operators
    private static double calculator(char op, double a, double b) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    //checking if the output is double by catching a numberformatexception
    private static boolean isDigit(String point)
    {
        try
        {
            Double.parseDouble(point);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

}