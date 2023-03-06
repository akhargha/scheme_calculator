import java.util.Scanner;
import java.util.Stack;
public class SchemeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String line = sc.nextLine();
            String[] linearray = line.split(" "); //creating tokens out of input
            System.out.println(infixTopostfix(linearray))
        }
    }

    private static double infixTopostfix(String[] linearray){
        Stack<Double> opnstack = new Stack<>();
        Stack<Double> temp = new Stack<>();
        Stack<Character> oprstack = new Stack<>();
    }

    //basic calculator to apply operators
    private static void calculator(char op, double a, double b) {
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
    private static boolean isDigit(String s)
    {
        try
        {
            Double.parseDouble(s);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return false;
    }

}