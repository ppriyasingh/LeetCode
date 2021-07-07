/**
Given a rows x cols screen and a sentence represented as a list of strings, return the number of times the given sentence can be fitted on the screen.
The order of words in the sentence must remain unchanged, and a word cannot be split into two lines. A single space must separate two consecutive words in a line.

Input: sentence = ["hello","world"], rows = 2, cols = 8
Output: 1
Explanation:
hello---
world---
The character '-' signifies an empty space on the screen.

Input: sentence = ["a", "bcd", "e"], rows = 3, cols = 6
Output: 2
Explanation:
a-bcd- 
e-a---
bcd-e-
The character '-' signifies an empty space on the screen.

Input: sentence = ["i","had","apple","pie"], rows = 4, cols = 5
Output: 1
Explanation:
i-had
apple
pie-i
had--
The character '-' signifies an empty space on the screen.
*/

// Approach 1: https://medium.com/@rebeccahezhang/leetcode-418-sentence-screen-fitting-9d6258ce116e 

class Solution {
    public int wordsTyping(String[] sentence, int rows, int cols) {
        StringBuilder str = new StringBuilder();
        for (String s : sentence) {
            s = s + " ";
            str.append(s);
        }
        
        int start = 0;
        for (int i = 0; i < rows; i++) {
            start = start + cols;
            if (str.charAt(start % str.length()) == ' ') {
                start++;
            } else {
                while (start > 0 && str.charAt((start-1) % str.length())  != ' ') {
                    start--;
                }
            }
        }
        return start / str.length();
    }
}