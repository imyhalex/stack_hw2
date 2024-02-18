package ys4689.hw;

import java.util.ArrayList;
import java.util.List;

public class PostfixCalculator {

    public static double evaluate(String postfix) {
        char[] arr = postfix.toCharArray();
        ArrayStack<Double> myStack = new ArrayStack<>();

        List<String> list = new ArrayList<>();

        list = ParserHelper.parse(arr);

        for (String item : list) {
            if (!isOperator(item)) {
                myStack.push(Double.parseDouble(item));
            } else {
                Double right = myStack.pop();
                Double left = myStack.pop();
                Double result = 0.0;

                switch(item) {
                    case "+":
                        result = left + right;
                        break;
                    case "-":
                        result = left - right;
                        break;
                    case "*":
                        result = left * right;
                        break;
                    case "/":
                        if (right == 0)
                            throw new UnsupportedOperationException("Invalid ");
                        result = left / right;
                        break;
                    case "^":
                        result = Math.pow(left, right);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator " + item);
                }
                // push the result back
                myStack.push(result);
            }
        }

        return myStack.pop();
    }

    private static boolean isOperator(String item) {
        return item.equals("+") || item.equals("-") || item.equals("*")
                || item.equals("/") || item.equals("^");
    }
}
