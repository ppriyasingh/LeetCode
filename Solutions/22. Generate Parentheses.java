// My Solution 1
class Solution {
    public void generateParenthesisUtil(int n, List<String> ans, int open, int close, int ind, char[] ch) {
        if(ind==(2*n)) {
            ans.add(new String(ch));
            return;
        }
        
        if(open < n) {
            ch[ind] = '(';
            generateParenthesisUtil(n, ans, open+1, close, ind+1, ch);
        
        }
        if(close<open) {
            ch[ind] = ')';
            generateParenthesisUtil(n, ans, open, close+1, ind+1, ch);
        }
        
    }
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        char[] ch= new char[2*n];
        generateParenthesisUtil(n, ans, 0, 0, 0, ch);
        return ans;
    }
}

// My Solution 2
class Solution {
    public void generateParenthesisUtil(int n, List<String> ans, int open, int close, int ind, String str) {
        if(str.length()==2*n) {
            ans.add(str);
            return;
        }
        if(open < n) 
            generateParenthesisUtil(n, ans, open+1, close, ind+1, str+"(");
        if(close<open) 
            generateParenthesisUtil(n, ans, open, close+1, ind+1, str+")");
    }
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        generateParenthesisUtil(n, ans, 0, 0, 0, "");
        return ans;
    }
}
