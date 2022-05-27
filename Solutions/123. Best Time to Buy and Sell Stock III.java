// Approach 1: Bidirectional Dynamic Programming
/*
A naive implementation of the above idea would be to divide the sequences into two and then enumerate each of the subsequences, 
though this is definitely not the most optimized solution.
For a sequence of length NN, we would have NN possible divisions (including no division), each of the elements would be visited once in each division. 
As a result, the overall time complexity of this naive implementation would be O(n^2)

We could do better than the naive O(n^2) implementation. Regarding the algorithms of divide-and-conquer, one common technique that we can apply in order to 
optimize the time complexity is called dynamic programming (DP) where we trade less repetitive calculation with some extra space.
In dynamic programming algorithms, normally we create an array of one or two dimensions to keep the intermediate optimal results. 
In this problem though, we would use two arrays, with one array keeping the results of sequence from left to right and the other array keeping the results 
of sequence from right to left. For the sake of name, we could call it bidirectional dynamic programming.
First, we denote a sequence of prices as Prices[i], with index starting from 0 to N-1. Then we define two arrays, namely left_profits[i] and right_profits[i].

As suggested by the name, each element in the left_profits[i] array would hold the maximum profits that one can gain from doing one single transaction on 
the left subsequence of prices from the index zero to i, (i.e. Prices[0], Prices[1], ... Prices[i]). For instance, for the subsequences of [7, 1, 5], 
the corresponding left_profits[2] would be 4, which is to buy the price of 1 and sell it at the price of 5.

And each element in the right_profits[i] array would hold the maximum profits that one can gain from doing one single transaction on the right subsequence 
of the prices from the index i up to N-1, (i.e. Prices[i], Prices[i+1], ... Prices[N-1]). For example, for the right subsequence of [3, 6, 4], the 
corresponding right_profits[3] would be 3, which is to buy at the price of 3 and then sell it at the price of 6.

Now, if we divide the sequence of prices around the element at the index i, into two subsequences, with left subsequences as Prices[0], Prices[1], ... Prices[i] 
and the right subsequence as Prices[i+1], ... Prices[N-1], then the total maximum profits that we obtain from this division (denoted as max_profits[i]) can be expressed as 
follows: max_profits[i] = left_profits[i] + right_profits[i+1]
*/
class Solution {
  // T(O): O(n)
  // S(O): O(n)
  public int maxProfit(int[] prices) {
    int length = prices.length;
    if (length <= 1) return 0;

    int leftMin = prices[0];
    int rightMax = prices[length - 1];

    int[] leftProfits = new int[length];
    // pad the right DP array with an additional zero for convenience.
    int[] rightProfits = new int[length + 1];

    // construct the bidirectional DP array
    for (int l = 1; l < length; ++l) {
      leftProfits[l] = Math.max(leftProfits[l - 1], prices[l] - leftMin);
      leftMin = Math.min(leftMin, prices[l]);

      int r = length - 1 - l;
      rightProfits[r] = Math.max(rightProfits[r + 1], rightMax - prices[r]);
      rightMax = Math.max(rightMax, prices[r]);
    }

    int maxProfit = 0;
    for (int i = 0; i < length; ++i) {
      maxProfit = Math.max(maxProfit, leftProfits[i] + rightProfits[i + 1]);
    }
    return maxProfit;
  }
}
// Another Implementation
class Solution {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            if (n <2) return 0;

            int[] left = new int[n];
            int[] right = new int[n];

            int minLeft = prices[0];
            for (int i = 1; i < n; i++) {
                left[i] = Math.max(left[i - 1], prices[i] - minLeft);
                minLeft = Math.min(minLeft, prices[i]);
            }

            int maxRight = prices[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                right[i] = Math.max(right[i + 1], maxRight - prices[i]);
                maxRight = Math.max(maxRight, prices[i]);
            }

            int maxProfit = 0;
            for (int i = 0; i < n; i++) {
                maxProfit = Math.max(maxProfit, left[i] + right[i]);
            }

            return maxProfit;
        }
    }

// Approach 2: One-pass Simulation
/*
The intuition is that we can consider the problem as a game, and we as agent could make at most two transactions in order to gain the maximum points 
(profits) from the game.
The two transactions be decomposed into 4 actions: "buy of transaction #1", "sell of transaction #1", "buy of transaction #2" and "sell of transaction #2".

To solve the game, we simply run a simulation along the sequence of prices, at each time step, we calculate the potential outcomes for each of our actions. 
At the end of the simulation, the outcome of the final action "sell of transaction #2" would be the desired output of the problem.

Algorithm

Overall, we run an iteration over the sequence of prices.

Over the iteration, we calculate 4 variables which correspond to the costs or the profits of each action respectively, as follows:

t1_cost: the minimal cost of buying the stock in transaction #1. The minimal cost to acquire a stock would be the minimal price value that we have seen so far at each step.
t1_profit: the maximal profit of selling the stock in transaction #1. Actually, at the end of the iteration, this value would be the answer for the first problem in the series, i.e. Best Time to Buy and Sell Stock.
t2_cost: the minimal cost of buying the stock in transaction #2, while taking into account the profit gained from the previous transaction #1. One can consider this as the cost of reinvestment. Similar with t1_cost, 
         we try to find the lowest price so far, which in addition would be partially compensated by the profits gained from the first transaction.
t2_profit: the maximal profit of selling the stock in transaction #2. With the help of t2_cost as we prepared so far, we would find out the maximal profits with at most two transactions at each step.
*/
class Solution {
  // T(O): O(n)
  // S(O): O(1)
  public int maxProfit(int[] prices) {
    int t1Cost = Integer.MAX_VALUE, 
        t2Cost = Integer.MAX_VALUE;
    int t1Profit = 0,
        t2Profit = 0;

    for (int price : prices) {
        // the maximum profit if only one transaction is allowed
        t1Cost = Math.min(t1Cost, price);
        t1Profit = Math.max(t1Profit, price - t1Cost);
        // reinvest the gained profit in the second transaction
        t2Cost = Math.min(t2Cost, price - t1Profit);
        t2Profit = Math.max(t2Profit, price - t2Cost);
    }

    return t2Profit;
  }
}
