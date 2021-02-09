/**** OPTIMISED SOLUTION with single stack *****/

class MinStack {

    Stack<Integer> stack;
    int min;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
        min = Integer.MAX_VALUE;
    }
    
    public void push(int x) {
        if(x<=min) {
            stack.push(min);
            min=x;
        }
        stack.push(x);
    }
    
    public void pop() {
        if(min == stack.pop()) {
            min=stack.pop();
        }
    }
    
    public int top() {
        
        return stack.peek();
    }
    
    public int getMin() {
        return min;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */



/**** Two stack solution*****/ 


class MinStack {

    Stack<Integer> s;    
    Stack<Integer> mini;

    /** initialize your data structure here. */
    public MinStack() {
        s = new Stack<>();    
        mini = new Stack<>();
    }
    
    public void push(int x) {
        s.push(x);
        
        if(mini.empty() || mini.peek() >= x) {
            mini.push(x);
        }
    }
    
    public void pop() {
        int deleteItem = s.pop();
        if(deleteItem == mini.peek())
            mini.pop();        
    }
    
    public int top() {
        return s.peek();
    }
    
    public int getMin() {
        return mini.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */


