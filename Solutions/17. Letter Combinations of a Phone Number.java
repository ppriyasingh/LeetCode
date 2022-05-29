
// My Solution
class Solution {
    String[] phone = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
    List<String> res= new LinkedList<>();
    
    public void letterCombinationsUtil(String digits, int ind, int n, String str) {
        if(ind == n) {
            res.add(str);
            return;
        }
        String tempStr= phone[digits.charAt(ind)-'0'];
        
        for(int j=0; j<tempStr.length(); j++) {
            letterCombinationsUtil(digits, ind+1, n, str+tempStr.charAt(j));
        }
    }
    
    public List<String> letterCombinations(String digits) {
        if(digits.length()==0) return new LinkedList<>();
        letterCombinationsUtil( digits, 0, digits.length(), "");
        return res;
    }
}
