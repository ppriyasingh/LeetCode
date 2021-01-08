class Solution {
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
		
		/*
		Time complexity: O(n)
		*/
		
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        
        for(String s: word1) {
            s1.append(s);
        }
        for(String s: word2) {
            s2.append(s);
        }
        
        //s1.toString() == s2.toString(); //false
        
        return s1.toString().equals(s2.toString());
    }
}

/*

------------------One liner solution--------------------

class Solution {
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        return String.join("", word1).equals(String.join("", word2));
    }
}
*/