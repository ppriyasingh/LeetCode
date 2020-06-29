class Solution {
    public String removeKdigits(String num, int k) {
        if(num.length()==k) return "0";
        
        Stack<Character> s= new Stack<>();
        int i=0;        
        while(i<num.length()) {            
            while(k>0 && !s.isEmpty() && s.peek()>num.charAt(i)){
                s.pop(); k--;
            }
            s.push(num.charAt(i));
            i++;
        }
        while(k>0) { s.pop(); k--; }
        StringBuilder res= new StringBuilder();
        while(!s.isEmpty()){
            res.append(s.pop());
        }
        while(1<res.length() && res.charAt(res.length()-1)=='0')
        { res.deleteCharAt(res.length()-1);  }  
        res.reverse();              
        return res.toString();
    }
}