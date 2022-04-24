/*
Implement a basic calculator to evaluate a simple expression string.
The expression string contains only non-negative integers, '+', '-', '*', '/' operators, and open '(' and closing parentheses ')'. The integer division should truncate toward zero.
You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().

 
Example 1:

Input: s = "1+1"
Output: 2

Example 2:
Input: s = "6-4/2"
Output: 4

Example 3:
Input: s = "2*(5+5*2)/3+(6/2+8)"
Output: 21
*/

public int calculate(String s) {
        //time:O(N), space:O(N)
        if (s == null || s.length() == 0) return 0; 
        Stack<Integer> nums = new Stack<>();    // the stack that stores numbers
        Stack<Character> ops = new Stack<>();   // the stack that stores operators (including parentheses)
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (Character.isDigit(c)) {
                num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = 10 * num + (s.charAt(i + 1) - '0');
                    i++;
                }
                nums.push(num);
                num = 0; // reset the number to 0 before next calculation
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    nums.push(operation(nums.pop(), ops.pop(), nums.pop()));
                }
                ops.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!ops.isEmpty() && hasPrecedence(ops.peek(), c)) {
                    nums.push(operation(nums.pop(), ops.pop(), nums.pop()));
                }
                // Dealing with the negative number
                if (c == '-') {
                    if (nums.isEmpty()) { // case1: 1st non-empty characer is the negative number 
                        nums.push(0);
                    } else { // case2: 1st non-empty characer in parentheses is the negative number 
                        int index = i - 1;
                        while (index >= 0 && s.charAt(index) == ' ') {
                            index--;
                        }
                        if (s.charAt(index) == '(') {
                            nums.push(0);
                        }
                    }
                }
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) {
           nums.push(operation(nums.pop(), ops.pop(), nums.pop()));
        }
        return nums.pop();
    }
    // Notice b is before a, since we pop b first
    private int operation(int b, char op, int a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b; //assume b is not 0
        }
        return 0;
    }
    // helper function to check precedence of the uppermost operator in the ops stack and current operator
    private boolean hasPrecedence(char op1, char op2) {
        if (op1 == '(' || op1 == ')') {
            return false;
        }
        if ((op1 == '+' || op1 == '-') && (op2 == '*' || op2 == '/')) {
            return false;
        }
        return true;
    }
