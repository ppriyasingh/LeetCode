// Approach 1: Brute Force
public class Solution {
  // T(O): n*n, loop runs n(n-1)/2.
  // S(O): 1
    public int maxProfit(int prices[]) {
        int maxprofit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxprofit)
                    maxprofit = profit;
            }
        }
        return maxprofit;
    }
}

// Approach 2: One Pass
/*The points of interest are the peaks and valleys in the given graph. We need to find the largest peak following the smallest valley. 
We can maintain two variables - minprice and maxprofit corresponding to the smallest valley and maximum profit (maximum difference between selling price 
and minprice) obtained so far respectively.
*/
public class Solution {
  // T(O): n
  // S(O): 1
    public int maxProfit(int prices[]) {
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice)
                minprice = prices[i];
            else if (prices[i] - minprice > maxprofit)
                maxprofit = prices[i] - minprice;
        }
        return maxprofit;
    }
}
