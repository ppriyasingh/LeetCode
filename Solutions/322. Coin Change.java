// Approach #1 (Brute force) [Time Limit Exceeded]
public class Solution {
  // T(O): amount^number of coins
  // S(O): number of coins

  public int coinChange(int[] coins, int amount) {
    return coinChange(0, coins, amount);
  }

  private int coinChange(int idxCoin, int[] coins, int amount) {
    if (amount == 0)
      return 0;
    if (idxCoin < coins.length && amount > 0) {
      int maxVal = amount/coins[idxCoin];
      int minCost = Integer.MAX_VALUE;
      for (int x = 0; x <= maxVal; x++) {
        if (amount >= x * coins[idxCoin]) {
          int res = coinChange(idxCoin + 1, coins, amount - x * coins[idxCoin]);
          if (res != -1)
            minCost = Math.min(minCost, res + x);
        }
      }
      return (minCost == Integer.MAX_VALUE)? -1: minCost;
    }
    return -1;
  }
}

// Approach #2 (Dynamic programming - Top down) [Accepted]
// The idea of the algorithm is to build the solution of the problem from top to bottom. It applies the idea described above. It use backtracking and cut 
//the partial solutions in the recursive tree, which doesn't lead to a viable solution. Тhis happens when we try to make a change of a coin with a value 
//greater than the amount SS. To improve time complexity we should store the solutions of the already calculated subproblems in a table.
public class Solution {

  // T(O): amount * number of coins
  // S(O): number of coins
  public int coinChange(int[] coins, int amount) {
    if (amount < 1) return 0;
    return coinChange(coins, amount, new int[amount]);
  }

  private int coinChange(int[] coins, int rem, int[] count) {
    if (rem < 0) return -1;
    if (rem == 0) return 0;
    if (count[rem - 1] != 0) return count[rem - 1];
    int min = Integer.MAX_VALUE;
    for (int coin : coins) {
      int res = coinChange(coins, rem - coin, count);
      if (res >= 0 && res < min)
        min = 1 + res;
    }
    count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
    return count[rem - 1];
  }
}

//T(O): Time complexity : O(S*n). where S is the amount, n is denomination count. In the worst case the recursive tree of the algorithm has height 
//of S and the algorithm solves only S subproblems because it caches precalculated solutions in a table. Each subproblem is computed with nn iterations, 
//one by coin denomination. Therefore there is O(S*n) time complexity.

// Approach #3 (Dynamic programming - Bottom up) [Accepted]
public class Solution {
  
  // T(O): amount * number of coins
  // S(O): number of coins
  public int coinChange(int[] coins, int amount) {
    int max = amount + 1;
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, max);
    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
      for (int j = 0; j < coins.length; j++) {
        if (coins[j] <= i) {
          dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
        }
      }
    }
    return dp[amount] > amount ? -1 : dp[amount];
  }
}
