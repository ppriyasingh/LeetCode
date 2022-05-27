//Approach 1: Brute Force
//In this case, we simply calculate the profit corresponding to all the possible sets of transactions and find out the maximum profit out of them.
class Solution {
  // T(O): n^n. Recursive function is called n^n times.
  // S(O): n. Depth of recursion is n.
    public int maxProfit(int[] prices) {
        return calculate(prices, 0);
    }

    public int calculate(int prices[], int s) {
        if (s >= prices.length)
            return 0;
        int max = 0;
        for (int start = s; start < prices.length; start++) {
            int maxprofit = 0;
            for (int i = start + 1; i < prices.length; i++) {
                if (prices[start] < prices[i]) {
                    int profit = calculate(prices, i + 1) + prices[i] - prices[start];
                    if (profit > maxprofit)
                        maxprofit = profit;
                }
            }
            if (maxprofit > max)
                max = maxprofit;
        }
        return max;
    }
}

//Approach 2: Peak Valley Approach
/*
If we analyze the graph, we notice that the points of interest are the consecutive valleys and peaks.
The key point is we need to consider every peak immediately following a valley to maximize the profit. 
In case we skip one of the peaks (trying to obtain more profit), we will end up losing the profit over one of the transactions leading to an overall 
lesser profit.

For example, in the above case, if we skip peak and valley trying to obtain more profit by considering points with more difference in heights, 
the net profit obtained will always be lesser than the one obtained by including them, since CC will always be lesser than A+B.
*/
class Solution {
  // T(O): n
  // S(O): 1
    public int maxProfit(int[] prices) {
        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxprofit = 0;
        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1])
                i++;
            valley = prices[i];
            while (i < prices.length - 1 && prices[i] <= prices[i + 1])
                i++;
            peak = prices[i];
            maxprofit += peak - valley;
        }
        return maxprofit;
    }
}

// Approach 3: Simple One Pass
class Solution {
  // T(O): n
  // S(O): 1
    public int maxProfit(int[] prices) {
        int maxprofit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                maxprofit += prices[i] - prices[i - 1];
        }
        return maxprofit;
    }
}
