/**
Given two strings str1 and str2 of the same length, determine whether you can transform str1 into str2 by doing zero or more conversions.

In one conversion you can convert all occurrences of one character in str1 to any other lowercase English character.

Return true if and only if you can transform str1 into str2.

Input: str1 = "aabcc", str2 = "ccdee"
Output: true
Explanation: Convert 'c' to 'e' then 'b' to 'd' then 'a' to 'c'. Note that the order of conversions matter.

Input: str1 = "leetcode", str2 = "codeleet"
Output: false
Explanation: There is no way to transform str1 to str2.
*/

// Solution 1
class Solution {
    public boolean canConvert(String str1, String str2) {
        Set<Character> set= new HashSet<>();
		Map<Character, Character> m= new HashMap<>();
		
		for(int i=0; i<str1.length; i++) {
			set.add(str2.charAt(i));
			if(m.containsKey(str1.charAt(i)) && m.get(str1.charAt(i))!=str2.charAt(i)) return false;
			m.put(str1.charAt(i), str2.charAt(i));
		}
		if(!str1.equals(str2) && m.size()==26 && set.size()==26) return false;
		
        return true;
        
    }
}
