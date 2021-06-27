/**
We can rotate digits by 180 degrees to form new digits. When 0, 1, 6, 8, 9 are rotated 180 degrees, they become 0, 1, 9, 8, 6 respectively. When 2, 3, 4, 5 and 7 are rotated 180 degrees, they become invalid.

A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.(Note that the rotated number can be greater than the original number.)

Given a positive integer n, return the number of confusing numbers between 1 and n inclusive.

Input: n = 20
Output: 6
Explanation: 
The confusing numbers are [6,9,10,16,18,19].
6 converts to 9.
9 converts to 6.
10 converts to 01 which is just 1.
16 converts to 91.
18 converts to 81.
19 converts to 61.

Input: n = 100
Output: 19
Explanation: 
The confusing numbers are [6,9,10,16,18,19,60,61,66,68,80,81,86,89,90,91,98,99,100].
*/

// Approach 1
class Solution {
    private static final Map<Character, Character> map = new HashMap<>();
    private static final char[][] ROTATE_SAME_PAIRS = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};
    private static final char[] CANDIDATES = {'0', '1', '6', '8', '9'};
    private String num;
    private int count = 0;
    
    public int confusingNumberII(int N) {
        this.num = String.valueOf(N);
        for (char[] p : ROTATE_SAME_PAIRS) map.put(p[0], p[1]);
        
        
        for (int len = 1; len <= num.length(); len++) {
            dfs(0, len - 1, new char[len], false);
        }
        
        return count;
    }

    private void dfs(int left, int right, char[] arr, boolean valid) {
        if (left > right) {
            if (!valid) return;
            String res = new String(arr);
            if (! (res.length() == num.length() && res.compareTo(num) > 0)) count++;
            return;
        } 
        
        if (left == right) {
            for (char l : CANDIDATES) {
                arr[left] = l;
                dfs(left + 1, right - 1, arr, valid || l == '6' || l == '9');
            }
            return;
        }
        
        
        for (char l : CANDIDATES) {
            if (left == 0 && l == '0') continue;
            
            for (char r : CANDIDATES) {
                arr[left] = l;
                arr[right] = r;
                
                //  two character l & r having r == map.get(l), meaning that l & r forms a pair that's the 
                //  same after rotate (i.e., 0 & 0, 6 & 9), 
                //  Unless r == map.get(l), we know that the final number formed is not "Confusing"
                dfs(left + 1, right - 1, arr, (valid || r != map.get(l)));
            }
        }
        
    }
}

/*
There might be various backtracking approaches. This solution is a direct genearlization of the solution to Leetcode 248. Strobogrammatic Number III. It will be really helpful to get familiar of the key to solve Strobogrammatic Number problems in genearal. I really recommend to try these problems first or revisit them before solving this one.

It's really interesting that this problem is marked as "Hard" while 788. Rotated Digits, a very similar problem, is marked as "Easy".

247. Strobogrammatic Number II
248. Strobogrammatic Number III
788. Rotated Digits
*/


// Solution 2:
public int confusingNumberII(int N) {
	Map<Integer, Integer> map = new HashMap<>();
	map.put(0, 0); map.put(1, 1); map.put(8, 8); map.put(9, 6); map.put(6, 9);

	int result = 0, num, invertedNum;
	for(int i = 6;i<=N;i++) {
		num = i;
		invertedNum = 0;
		while(num != 0) {
			if(map.get(num%10) == null) {
				invertedNum = i += i/num-1;         // Skip next (i/num-1) numbers
				break;
			}
			invertedNum = invertedNum * 10 + map.get(num%10);
			num/=10;
		}
		if(invertedNum != i) result ++;
	}
	return result;
}

//Solution 3:
int[] digits = new int[]{0, 1, 6, 8, 9};
    
   public int confusingNumberII(int N) {
        int res = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
                int n = queue.poll();
                for (int i = 0; i < digits.length; i++) {
                    int nn = n * 10 + digits[i];
                    if (nn > N) {
                        return res;
                    }
                    if (nn != 0) {
                        queue.offer(nn);
                    }
                    if (isConfusing(nn)) {
                        res++;
                    }
                }
        }  
        return -1;
    }
    
    private boolean isConfusing(int N) {
    String s = String.valueOf(N);
    StringBuilder sb = new StringBuilder();
    for(char c: s.toCharArray()){
    if(c == '2' || c == '3' || c == '4' || c == '5' || c == '7') return false;
    if(c == '6')
        sb.append('9');
    else if(c == '9')
        sb.append('6');
    else
        sb.append(c);
    } 
    return !sb.reverse().toString().equals(s);
    }
	
// Solution 4:
class Solution {
    public int confusingNumberII(int N) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,0);
        map.put(1,1);
        map.put(8,8);
        map.put(6,9);
        map.put(9,6);
        return helper(map, 0, 0, N, 1);
    }
    
    int helper(Map<Integer, Integer> map, int num, int rotate, int N, int base) {
        if( num > N || num<0 ) {
            return 0;
        }
        int result =0;
        if(num != rotate && num!=0) {
            result++;
        }
        for(int key : map.keySet()) {
            if((num ==0 && key==0) || num>100000000) {
                continue;
            }
            result+=helper(map, num*10+key, map.get(key)*base+rotate, N, base*10);
        }
        return result;
    }
}
