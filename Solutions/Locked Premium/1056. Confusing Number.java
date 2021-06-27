/**
Given a number n, return true if and only if it is a confusing number, which satisfies the following condition:

We can rotate digits by 180 degrees to form new digits. When 0, 1, 6, 8, 9 are rotated 180 degrees, they become 0, 1, 9, 8, 6 respectively. When 2, 3, 4, 5 and 7 are rotated 180 degrees, they become invalid. A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.

Input: n = 6
Output: true
Explanation: 
We get 9 after rotating 6, 9 is a valid number and 9!=6.

Input: n = 89
Output: true
Explanation: 
We get 68 after rotating 89, 86 is a valid number and 86!=89.

Input: n = 11
Output: false
Explanation: 
We get 11 after rotating 11, 11 is a valid number but the value remains the same, thus 11 is not a confusing number.

Input: n = 25
Output: false
Explanation: 
We get an invalid number after rotating 25.
*/

// Solution 1
class Solution {
    public boolean confusingNumber(int N) {
        int temp = N;
        if (temp == 0) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        while (temp != 0) {
            int digit = temp % 10;
            if (digit == 1) {
                sb.append(1);
            } else if (digit == 6) {
                sb.append(9);
            } else if (digit == 9) {
                sb.append(6);
            } else if (digit == 0) {
                sb.append(0);
            } else if (digit == 8) {
                sb.append(8);
            } else {
                return false;
            }
            temp /= 10;
        }
        return Integer.parseInt(sb.toString()) != N;
        
    }
}


// Solution 2:
class Solution {
    public boolean confusingNumber(int N) {
        String n = "" + N;
        StringBuilder buf = new StringBuilder();
        for (int i = n.length() - 1; i >= 0; --i)
        {
            char c = n.charAt(i);
            if (is_valid(c))
            {
                if (c == '6') buf.append('9');
                else if (c == '9') buf.append('6');
                else buf.append(c);
            }
            else return false;
        }
        
        return !buf.toString().equals(n);
    }
    
    boolean is_valid(char c)
    {
        return c == '0' || c == '1' || c == '9' ||
               c == '8' || c == '6';
    }
}

//Solution 3:
private int[] rotations = new int[]{0, 1, -1, -1, -1, -1, 9, -1, 8, 6};
    public boolean confusingNumber(int N) {
        int copy = N, place = 1, result = 0;
        while (copy > 0) {
            int digit = copy % 10;
            copy /= 10;
            if (rotations[digit] == -1) return false;
            result = result * 10 + rotations[digit]; // cause of this line we don't need a stack
        }
        return result != N;
    }
	
// Solution 4:
public boolean confusingNumber(int N) {
	Map<Integer, Integer> map = new HashMap<>();
	map.put(0, 0);
	map.put(1, 1);
	map.put(8, 8);
	map.put(9, 6);
	map.put(6, 9);

	int num = N;
	int invertedNum = 0;
	while(num != 0) {
		if(map.get(num%10) == null) return false;
		invertedNum = invertedNum * 10 + map.get(num%10);
		num/=10;
	}
	return invertedNum != N;
}
