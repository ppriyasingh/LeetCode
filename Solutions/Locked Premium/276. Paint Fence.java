/**
You are painting a fence of n posts with k different colors. You must paint the posts following these rules:

Every post must be painted exactly one color.
There cannot be three or more consecutive posts with the same color.
Given the two integers n and k, return the number of ways you can paint the fence.

Input: n = 3, k = 2
Output: 6
Explanation: All the possibilities are shown.
Note that painting all the posts red or all the posts green is invalid because there cannot be three posts in a row with the same color.

Input: n = 1, k = 1
Output: 1

Input: n = 7, k = 2
Output: 42
*/

Solution
Overview
Realizing This is a Dynamic Programming Problem

There are two parts to this problem that tell us it can be solved with dynamic programming.

First, the question is asking for the "number of ways" to do something.

Second, we need to make decisions that may depend on previously made decisions. In this problem, we need to decide what color we should paint a given post, which may change depending on previous decisions. For example, if we paint the first two posts the same color, then we are not allowed to paint the third post the same color.

Both of these things are characteristic of dynamic programming problems.

A Framework to Solve Dynamic Programming Problems

A dynamic programming algorithm typically has 3 components. Learning these components is extremely valuable, as most dynamic programming problems can be solved this way.

First, we need some function or array that represents the answer to the problem for a given state. For this problem, let's say that we have a function totalWays, where totalWays(i) returns the number of ways to paint i posts. Because we only have one argument, this is a one-dimensional dynamic programming problem.

Second, we need a way to transition between states, such as totalWays(3) and totalWays(4). This is called a recurrence relation and figuring it out is usually the hardest part of solving a problem with dynamic programming. We'll talk about the recurrence relation for this problem below.

The third component is establishing base cases. If we have one post, there are k ways to paint it. If we have two posts, then there are k * k ways to paint it (since we are allowed to paint have two posts in a row be the same color). Therefore, totalWays(1) = k, totalWays(2) = k * k.

Finding The Recurrence Relation

We know the values for totalWays(1) and totalWays(2), now we need a formula for totalWays(i), where 3 <= i <= n. Let's think about how many ways there are to paint the i^{th}i 
th
  post. We have two options:

Use a different color than the previous post. If we use a different color, then there are k - 1 colors for us to use. This means there are (k - 1) * totalWays(i - 1) ways to paint the i^{th}i 
th
  post a different color than the (i - 1)^{th}(i−1) 
th
  post.

Use the same color as the previous post. There is only one color for us to use, so there are 1 * totalWays(i - 1) ways to paint the i^{th}i 
th
  post the same color as the (i - 1)^{th}(i−1) 
th
  post. However, we have the added restriction of not being allowed to paint three posts in a row the same color. Therefore, we can paint the i^{th}i 
th
  post the same color as the (i - 1)^{th}(i−1) 
th
  post only if the (i - 1)^{th}(i−1) 
th
  post is a different color than the (i - 2)^{th}(i−2) 
th
  post.

So, how many ways are there to paint the (i - 1)^{th}(i−1) 
th
  post a different color than the (i - 2)^{th}(i−2) 
th
  post? Well, as stated in the first option, there are (k - 1) * totalWays(i - 1) ways to paint the i^{th}i 
th
  post a different color than the (i - 1)^{th}(i−1) 
th
  post, so that means there are 1 * (k - 1) * totalWays(i - 2) ways to paint the (i - 1)^{th}(i−1) 
th
  post a different color than the (i - 2)^{th}(i−2) 
th
  post.

Adding these two scenarios together gives totalWays(i) = (k - 1) * totalWays(i - 1) + (k - 1) * totalWays(i + 2), which can be simplified to:

totalWays(i) = (k - 1) * (totalWays(i - 1) + totalWays(i - 2))

This is our recurrence relation which we can use to solve the problem from the base cases.


Approach 1: Top-Down Dynamic Programming (Recursion + Memoization)
Intuition

Top-down dynamic programming starts from the top and works its way down to the base cases. Typically, this is implemented with recursion and then made efficient using memoization. Memoization refers to storing the results of expensive function calls to avoid duplicate computations - we'll soon see why this is important for this problem. If you're new to recursion, check out the recursion explore card.

We can implement the function totalWays(i) as follows - first, check for the base cases we defined above totalWays(1) = k, totalWays(2) = k * k. If i >= 3, use our recurrence relation: totalWays(i) = (k - 1) * (totalWays(i - 1) + totalWays(i - 2)). However, we will run into a major problem - repeated computation. If we call totalWays(5), that function call will also call totalWays(4) and totalWays(3). The totalWays(4) call will call totalWays(3) again, as illustrated below, we are calculating totalWays(3) twice.

This may not seem like a big deal with i = 5, but imagine if we called totalWays(6). This entire tree would be one child, and we would have to call totalWays(4) twice. As n increases, the size of the tree grows exponentially - imagine how expensive a call such as totalWays(50) would be. This can be solved with memoization. When we compute the value of a given totalWays(i), let's store that value in memory. Next time we need to call totalWays(i), we can refer to the value stored in memory instead of having to call the function again and going through the repeated computations.

Algorithm

Define a hash map memo, where memo[i] represents the number of ways you can paint i fence posts.

Define a function totalWays where totalWays(i) will determine the number of ways you can paint i fence posts.

In the function totalWays, first check for the base cases. return k if i == 1, and return k * k if i == 2. Next, check if the argument i has already been calculated and stored in memo. If so, return memo[i]. Otherwise, use the recurrence relation to calculate memo[i], and then return memo[i].

Simply call and return totalWays(n)

//Solution 1: 
// T: O(n), S: O(n)
class Solution {
    private HashMap<Integer, Integer> memo = new HashMap<Integer, Integer>();
    
    private int totalWays(int i, int k) {
        if (i == 1) return k;
        if (i == 2) return k * k;
        
        // Check if we have already calculated totalWays(i)
        if (memo.containsKey(i)) {
            return memo.get(i);
        }
        
        // Use the recurrence relation to calculate totalWays(i)
        memo.put(i, (k - 1) * (totalWays(i - 1, k) + totalWays(i - 2, k)));
        return memo.get(i);
    }
    
    public int numWays(int n, int k) {
        return totalWays(n, k);
    }
}

Approach 2: Bottom-Up Dynamic Programming (Tabulation)
Intuition

Bottom-up dynamic programming is also known as tabulation and is done iteratively. Instead of using a function like in top-down, let's use an array totalWays instead, where totalWays[i] represents the number of ways you can paint i fence posts.

As the name suggests, we now start at the bottom and work our way up to the top (n). Initialize the base cases totalWays[1] = k, totalWays[2] = k * k, and then iterate from 3 to n, using the recurrence relation to populate totalWays.

Bottom-up algorithms are generally considered superior to top-down algorithms. Typically, a top-down implementation will use more space and take longer than the equivalent bottom-up approach.

Algorithm

Define an array totalWays of length n + 1, where totalWays[i] represents the number of ways you can paint i fence posts. Initialize totalWays[1] = k and totalWays[2] = k * k.

Iterate from 3 to n, using the recurrence relation to populate totalWays: totalWays[i] = (k - 1) * (totalWays[i - 1] + totalWays[i - 2]).

At the end, return totalWays[n].

Implementation

// Solution 2: 
// T: O(n), S: O(n)
class Solution {
    public int numWays(int n, int k) {
        // Base cases for the problem to avoid index out of bound issues
        if (n == 1) return k;
        if (n == 2) return k * k;
        
        int totalWays[] = new int[n + 1];
        totalWays[1] = k;
        totalWays[2] = k * k;
        
        for (int i = 3; i <= n; i++) {
            totalWays[i] = (k - 1) * (totalWays[i - 1] + totalWays[i - 2]);
        }
        
        return totalWays[n];
    }
}

Approach 3: Bottom-Up, Constant Space
Intuition

You may have noticed that our recurrence relation from the previous two approaches only cares about 2 steps below the current step. For example, if we are trying to calculate totalWays[11], we only care about totalWays[9] and totalWays[10]. While we would have needed to calculate totalWays[3] through totalWays[8] as well, at the time of the actual calculation for totalWays[11], we no longer care about any of the previous steps.

Therefore, instead of using O(n)O(n) space to store an array, we can improve to O(1)O(1) space by using two variables to store the results from the last two steps.

Algorithm

Initialize two variables, twoPostsBack and onePostBack, that represent the number of ways to paint the previous two posts. Since we start iteration from post three, twoPostsBack initially represents the number of ways to paint one post, and onePostBack initially represents the number of ways to paint two posts. Set their values twoPostsBack = k, onePostBack = k * k, because they are equivalent to our base cases..

Iterate n - 2 times. At each iteration, simulate moving i up by one. Use the recurrence relation to calculate the number of ways for the current step and store it in a variable curr. "Moving up" means twoPostsBack will now refer to onePostBack, so update twoPostsBack = onePostBack. onePostBack will now refer to the current step, so update onePostBack = curr.

In the end, return onePostBack, since "moving up" after the last step would mean onePostBack is the number of ways to paint n fence posts.

// Solution 3:
// T: O(n), S: O(1)
class Solution {
    public int numWays(int n, int k) {
        if (n == 1) return k;
        
        int twoPostsBack = k;
        int onePostBack = k * k;
        
        for (int i = 3; i <= n; i++) {
            int curr = (k - 1) * (onePostBack + twoPostsBack);
            twoPostsBack = onePostBack;
            onePostBack = curr;
        }
        
        return onePostBack;
    }
}


Closing Notes
If you're new to dynamic programming, hopefully you learned something from this article. Please post any questions you may have in the comment section below. For additional practice, here's a list of similar dynamic programming questions that are good for beginners.

70. Climbing Stairs (Easy)

198. House Robber (Medium)

256. Paint House (Medium)

509. Fibonacci Number (Easy)

746. Min Cost Climbing Stairs (Easy)

931. Minimum Falling Path Sum (Medium)