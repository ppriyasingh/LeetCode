/**
Given a string num which represents an integer, return true if num is a strobogrammatic number.
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Input: num = "69"
Output: true

Input: num = "88"
Output: true

Input: num = "962"
Output: false

Input: num = "1"
Output: true
*/

// Approach 1: Make a Rotated Copy
class Solution {

    public boolean isStrobogrammatic(String num) {
        
        // Note that using a String here and appending to it would be
        // poor programming practice.
        StringBuilder rotatedStringBuilder = new StringBuilder();
        
        // Remember that we want to loop backwards through the string
        for (int i = num.length() - 1; i >= 0; i--) {
            char c = num.charAt(i);
            if (c == '0' || c == '1' || c == '8') {
                rotatedStringBuilder.append(c);
            } else if (c == '6') {
                rotatedStringBuilder.append('9');
            } else if (c == '9') {
                rotatedStringBuilder.append('6');
            } else { // This must be an invalid digit.
                return false;
            }
        }
        
        String rotatedString = rotatedStringBuilder.toString();
        return num.equals(rotatedString);
    }
}

//Here is the code using a Hash Map to avoid the need for a complex conditional statement.
class Solution {

    public boolean isStrobogrammatic(String num) {
        
        // Initialise a map with the five-digit rotation rules
        Map<Character, Character> rotatedDigits = new HashMap<> (
            Map.of('0', '0', '1', '1', '6', '9', '8', '8', '9', '6'));
        
        StringBuilder rotatedStringBuilder = new StringBuilder();
        
        // Remember that we want to loop backwards through the string
        for (int i = num.length() - 1; i >= 0; i--) {
            char c = num.charAt(i);
            if (!rotatedDigits.containsKey(c)) {
                return false; // This must be an invalid digit.
            }
            rotatedStringBuilder.append(rotatedDigits.get(c));
        }
        
        String rotatedString = rotatedStringBuilder.toString();
        return num.equals(rotatedString);
    }
}

/*
Alternatively, we could use an Array instead of a Hash Map; the indexes act as keys. It is simplest in code to map the non-rotatable characters to empty strings instead of explicitly checking for them during the string building process. If they were present, then the rotated string will be of a different length to the original, and therefore would be correctly flagged as not strobogrammatic in the final check. You could also check for the invalid characters in the same way we did for the hash map approach

This approach is nice in that the code is very compact—the conditional inside the loop has been eliminated—but not so nice in that it is somewhat confusing to understand. The Hash Map approach is probably safer in an interview.
*/
class Solution {

    public boolean isStrobogrammatic(String num) {
        
        // In Java, we need to put '\0' to represent an empty character
        char[] rotatedDigits = new char[]{'0', '1', '\0', '\0', '\0', '\0', '9', '\0', '8', '6'};

        StringBuilder rotatedStringBuilder = new StringBuilder();
        
        // Remember that we want to loop backwards through the string
        for (int i = num.length() - 1; i >= 0; i--) {
            char c = num.charAt(i);
            int charIndex = Character.getNumericValue(c);
            rotatedStringBuilder.append(rotatedDigits[charIndex]);
        }
        
        String rotatedString = rotatedStringBuilder.toString();
        return num.equals(rotatedString);
    }
}
// T: O(N), S: O(N)

//Approach 2: Two Pointers
class Solution {

    public boolean isStrobogrammatic(String num) {
        
        Map<Character, Character> rotatedDigits = new HashMap<> (
            Map.of('0', '0', '1', '1', '6', '9', '8', '8', '9', '6'));
         
        // Java allows us to have more than one iteration variable. 
        for (int left = 0, right = num.length() - 1; left <= right; left++, right--) {
            char leftChar = num.charAt(left);
            char rightChar = num.charAt(right);            
            if (!rotatedDigits.containsKey(leftChar) || rotatedDigits.get(leftChar) != rightChar) {
                return false;
            }
        }
        return true;
        
    }
}
//Let N be the length of the input string.
// T: O(N/2) = O(N), For each of the N digits in the string, we're doing a single lookup and comparison.
// S: O(1), We are only using constant extra space. This is an in-place algorithm.