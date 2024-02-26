package availity_java;

public class ParanthesesValidator {

	public static boolean isValid(String code) {
        int balance = 0;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                if (balance == 0) {
                    return false; // A closing parenthesis without a matching opening one
                }
                balance--;
            }
        }
        return balance == 0; // True if all opened parentheses are closed
    }

    public static void main(String[] args) {
        String input = "(define (square x) (* x x))"; 
        boolean isValid = isValid(input);
        System.out.println("Is the LISP code valid? " + isValid);
    }

}
