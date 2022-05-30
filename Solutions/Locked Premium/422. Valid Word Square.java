/*
Given an array of strings words, return true if it forms a valid word square.

A sequence of strings forms a valid word square if the kth row and column read the same string, where 0 <= k < max(numRows, numColumns).
Example 1:


Input: words = ["abcd","bnrt","crmy","dtye"]
Output: true
Explanation:
The 1st row and 1st column both read "abcd".
The 2nd row and 2nd column both read "bnrt".
The 3rd row and 3rd column both read "crmy".
The 4th row and 4th column both read "dtye".
Therefore, it is a valid word square.
Example 2:


Input: words = ["abcd","bnrt","crm","dt"]
Output: true
Explanation:
The 1st row and 1st column both read "abcd".
The 2nd row and 2nd column both read "bnrt".
The 3rd row and 3rd column both read "crm".
The 4th row and 4th column both read "dt".
Therefore, it is a valid word square.
Example 3:


Input: words = ["ball","area","read","lady"]
Output: false
Explanation:
The 3rd row reads "read" while the 3rd column reads "lead".
Therefore, it is NOT a valid word square.

*/
class Solution {
  
  // T(O): N*N, n-length of words
  // S(O): 1
    public boolean validWordSquare(List<String> words) {
        if(words == null || words.size() == 0) {
            return true;
        }
        
        int r= words.size();
        
        for(int i=0; i<r; i++) {
            int c= words.get(i).length();
            
            for(int j=0; j<c; j++) {
                if(i>=words.get(j).length() || j>=r || words.get(i).charAt(j)!=words.get(j).charAt(i)) 
                    return false;
            }
        }
        return true;
    }
}

class Solution {
  // T(O): N*N, n-length of words
  // S(O): 1
    public String validWordSquareUtil(int colInd, List<String> words) {
        int r= words.size();
        StringBuilder sb= new StringBuilder();
        
        for(int i=0; i<words.size(); i++) {
            if(colInd<words.get(i).length()) sb.append(words.get(i).charAt(colInd));
        }
        return sb.toString();
    }
    public boolean validWordSquare(List<String> words) {
        if(words == null || words.size() == 0) {
            return true;
        }
      
        int r= words.size();
        
        for(int i=0; i<r; i++) {
            int c= words.get(i).length();
            if(!words.get(i).equals(validWordSquareUtil(i, words))) return false;
        }
        return true;
    }
}
