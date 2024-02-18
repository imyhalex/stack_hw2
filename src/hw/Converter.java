package hw;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    private String infix;
    private ArrayStack<String> myStack;

    public Converter(String infix) {
        this.infix = infix;
        this.myStack = new ArrayStack<>();
    }

    public String toPostFix() {
        char[] arr = infix.toCharArray();
        StringBuilder postfix = new StringBuilder();
        
        List<String> list = new ArrayList<>();

        list = ParserHelper.parse(arr);

        for (String item : list) {
            // for numbers
            if (item.matches("\\d+"))
                postfix.append(item).append(" ");

            // for the operators within parenthese
            else if (item.equals("(")) {
                myStack.push(item);
            }
            else if (item.equals(")")) {
                while (!myStack.isEmpty() && !myStack.top().equals("(")) {
                    postfix.append(myStack.pop()).append(" ");
                }
                if (!myStack.isEmpty() && myStack.top().equals("(")) {
                    myStack.pop(); // Pop the "(" without evaluating its precedence
                }
            } 
            
            else {
                // For tokens that has lower precedence
                while (!myStack.isEmpty() && precedences(item) <= precedences(myStack.top())) {
                    postfix.append(myStack.pop()).append(" ");
                }
                // If tokens has higher precedence
                myStack.push(item);
            }
        }

        // enter the rest of the strings
        while (!myStack.isEmpty()) {
            postfix.append(myStack.pop()).append(" ");
        }

        return postfix.toString();
    }

    // helper method
    private int precedences(String operator) {
        switch(operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return -1;
        }
    }
}
