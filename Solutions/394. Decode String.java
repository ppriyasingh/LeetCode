// Solution 1:
class Solution {
    public String decodeString(String s) {
        Deque<Character> queue = new LinkedList<>();
        for (char c : s.toCharArray()) queue.offer(c);
        return helper(queue);
    }
    
    public String helper(Deque<Character> queue) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (!queue.isEmpty()) {
            char c= queue.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '[') {
                String sub = helper(queue);
                for (int i = 0; i < num; i++) sb.append(sub);   
                num = 0;
            } else if (c == ']') {
                break;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

// Solution 2:
class Solution {
    public String decodeString(String s) {
        StringBuilder sb= new StringBuilder(s);
        
        while(sb.indexOf("[") != -1) {
            int close= sb.indexOf("]"), open=sb.lastIndexOf("[",close);
            StringBuilder temp= new StringBuilder();
            String subs= sb.substring(open+1, close);
            
            int numRight=open-1, numLeft=open-1;
            while(numLeft>=0 && Character.isDigit(sb.charAt(numLeft))) --numLeft;
            int num= Integer.parseInt(sb.substring(numLeft+1, numRight+1));
            
            while(num-->0) temp.append(subs);
            sb.replace(numLeft+1, close+1, temp.toString());
        }
        
        return sb.toString();
    }
}
