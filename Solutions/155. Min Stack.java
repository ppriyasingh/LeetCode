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