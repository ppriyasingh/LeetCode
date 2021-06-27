/**
Given strings s1 and s2, return the minimum contiguous substring part of s1, so that s2 is a subsequence of the part.

If there is no such window in s1 that covers all characters in s2, return the empty string "". If there are multiple such minimum-length windows, return the one with the left-most starting index.

Input: s1 = "abcdebdde", s2 = "bde"
Output: "bcde"
Explanation: 
"bcde" is the answer because it occurs before "bdde" which has the same length.
"deb" is not a smaller window because the elements of s2 in the window must occur in order.

Input: s1 = "jmeqksfrsdcmsiwvaovztaqenprpvnbstl", s2 = "u"
Output: ""
*/

// Solution 1
public String minWindow(String S, String T) {
	int l = 0, r = 0, n = S.length(), m = T.length();
	int len = n + 1;
	String ans = "";
	while (r < n) {
		int tIndex = 0;
		while (r < n) {
			if (S.charAt(r) == T.charAt(tIndex)) {
				tIndex++;
			}
			if (tIndex == m)
				break;
			r++;
		}
		if (r == n)
			break;
		l = r;
		tIndex = m - 1;
		while (tIndex >= 0) {
			while (T.charAt(tIndex) != S.charAt(l))
				l--;
			tIndex--;
			l--;
		}
		l++;
		if (r - l + 1 < len) {
			len = r - l + 1;
			ans = S.substring(l, r + 1);
		}
		r = l + 1;
	}
	return ans;
}

//Solution 2:
class Solution {
    int min=Integer.MAX_VALUE;
    int index=-1;
    public String minWindow(String s, String T) {
        int dp[][]=new int[T.length()+1][s.length()+1];
        for(int r=1;r<dp.length;r++)for(int c=0;c<dp[0].length;c++)dp[r][c]=-1;
        for(int r=1;r<dp.length;r++){
            char tc=T.charAt(r-1);
            for(int c=1;c<dp[0].length;c++){
                char sc=s.charAt(c-1);
                if(tc==sc&&dp[r-1][c-1]!=-1){
                    dp[r][c]=1+dp[r-1][c-1];
                }else{
                    if(dp[r][c-1]!=-1)dp[r][c]=1+dp[r][c-1];
                }
            }
            dp[r][0]=-1;
        }
        
        for(int i=1;i<dp[0].length;i++){
            if(s.charAt(i-1)==T.charAt(T.length()-1)){
                if(dp[T.length()][i]!=-1){
                    if(min>dp[T.length()][i]){
                        min=dp[T.length()][i];
                        index=i-1;
                    }
                }
            }
        }
        if(min==Integer.MAX_VALUE)return "";
        return s.substring(index-min+1,index+1);
    }
    
    public void print(int dp[][]){
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                System.out.print(dp[i][j]+" ");
            }System.out.println();
        }
    }
}

//Solution 3:
class Solution {
    public String minWindow(String S, String T) {
        int minLen = Integer.MAX_VALUE, index = 0, left = 0;
        int[] countS = new int[26];
        int[] countT = new int[26];
        for (char c : T.toCharArray()) countT[c - 'a']++;

        for (int right = 0; right < S.length(); right++) {
            countS[S.charAt(right) - 'a']++;
            while (isContain(countS, countT) && isSubSeq(S.substring(left, right + 1), T)) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    index = left;
                }

                countS[S.charAt(left) - 'a']--;
                left++;
            }
        }

        if (minLen == Integer.MAX_VALUE) return "";
        return S.substring(index, index + minLen);
    }

    private boolean isSubSeq(String a, String b) {
        if (b.length() > a.length()) return false;
        int i = 0, j = 0;
        while (i < a.length() && j < b.length()) {
            if (a.charAt(i) == b.charAt(j)) {
                i++; j++;
            } else {
                i++;
            }
        }
        return j == b.length();
    }

    private boolean isContain(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] < b[i]) return false;
        }
        return true;
    }
}

// Solution 4: 
class Solution {
    public String minWindow(String S, String T) {
        if (S == null || T == null) {
            return "";
        }
        int n = S.length();
        int m = T.length();
        // states: f[i][j] means the min length of substring in S which ending with S[i - 1] and contains T[0 : j - 1]
        int[][] f = new int[n + 1][m + 1];  
        // initialization
        for (int j = 1; j <= m; j++) {
            f[0][j] = Integer.MAX_VALUE / 2;
        }
        // function
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (S.charAt(i - 1) == T.charAt(j - 1)) {
                    f[i][j] = f[i - 1][j - 1] + 1;
                } else {
                    f[i][j] = f[i - 1][j] + 1;
                }
            }
        }
        // answer
        int minLen = Integer.MAX_VALUE;
        int idx = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (f[i][m] < minLen) {
                minLen = f[i][m];
                idx = i;
            }
        }
        // trick: S.length() is less than 20000
        return minLen > n ? "" : S.substring(idx - minLen, idx);
    }
}

// Solution 5: DFS
class Solution 
{
    public String minWindow(String S, String T) 
    {
        TreeSet<Integer>[] map = new TreeSet[26];
        for (int i = 0; i < S.length(); ++i)
        {
            if (T.contains("" + S.charAt(i)))
            {
                if (map[S.charAt(i) - 'a'] == null)
                {
                    map[S.charAt(i) - 'a'] = new TreeSet();
                }
                map[S.charAt(i) - 'a'].add(i);
            }
        }
        
        if (map[T.charAt(0) - 'a'] != null)
        {
            for (int start : map[T.charAt(0) - 'a'])
            {
                dfs(T, 1, start, start, map);
            }
        }
        
        return min_end_ - min_start_ + 1 <= S.length() 
            ? S.substring(min_start_, min_end_ + 1)
            : "";
    }
    
    int min_start_ = 0;
    int min_end_ = 20001;
    void dfs(String t, int pos, int start, int end, TreeSet<Integer>[] map)
    {
        if (pos == t.length())
        {
            if (end - start < min_end_ - min_start_)
            {
                min_start_ = start;
                min_end_ = end;
            }
            return;
        }
        
        int idx = t.charAt(pos) - 'a';
        if (map[idx] != null)
        {
            Integer next = map[idx].higher(end);
            if (next != null)
            {
                dfs(t, pos + 1, start, next, map);
            }
        }
    }
}