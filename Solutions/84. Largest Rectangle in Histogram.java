public class Solution {
    // T(O): O(n^3)
    // S(O): O(1)
    public int largestRectangleArea(int[] heights) {
        int maxarea = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i; j < heights.length; j++) {
                int minheight = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++)
                    minheight = Math.min(minheight, heights[k]);
                maxarea = Math.max(maxarea, minheight * (j - i + 1));
            }
        }
        return maxarea;
    }
}

// Approach 2: Better Brute Force
public class Solution {
    // T(O): O(n^2)
    // S(O): O(1)
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        int length = heights.length;
        for (int i = 0; i < length; i++) {
            int minHeight = Integer.MAX_VALUE;
            for (int j = i; j < length; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                maxArea = Math.max(maxArea, minHeight * (j - i + 1));
            }
        }
        return maxArea;
    }
}

// Approach 3: Divide and Conquer Approach
/*
Algorithm

This approach relies on the observation that the rectangle with maximum area will be the maximum of:

The widest possible rectangle with height equal to the height of the shortest bar.

The largest rectangle confined to the left of the shortest bar(subproblem).

The largest rectangle confined to the right of the shortest bar(subproblem).

Let's take an example:

[6, 4, 5, 2, 4, 3, 9]
Here, the shortest bar is of height 2. The area of the widest rectangle using this bar as height is 2x7=14. Now, we need to look for cases 2 and 3 mentioned above. Thus, we repeat the same process to the left and right of 2. In the left of 2, 4 is the minimum, forming an area of rectangle 4x3=12. Further, rectangles of area 6x1=6 and 5x1=5 exist in its left and right respectively. Similarly we find an area of 3x3=9, 4x1=4 and 9x1=9 to the left of 2. Thus, we get 14 as the correct maximum area. See the figure below for further clarification:
*/
public class Solution {
    // T(O): Avg: O(n logn). 
    // Worst Case: O(n^2) If the numbers in the array are sorted, we don't gain the advantage of divide and conquer.
    // S(O): O(n) - recursion with worst depth n
    public int calculateArea(int[] heights, int start, int end) {
        if (start > end)
            return 0;
        int minindex = start;
        for (int i = start; i <= end; i++)
            if (heights[minindex] > heights[i])
                minindex = i;
        return Math.max(heights[minindex] * (end - start + 1),
                        Math.max(calculateArea(heights, start, minindex - 1),
                                calculateArea(heights, minindex + 1, end))
                );
    }

    public int largestRectangleArea(int[] heights) {
        return calculateArea(heights, 0, heights.length - 1);
    }
}


//Approach 4: Better Divide and Conquer
/*Algorithm

You can observe that in the Divide and Conquer Approach, we gain the advantage, since the large problem is divided into substantially smaller subproblems. But, we won't gain much advantage with that approach if the array happens to be sorted in either ascending or descending order, since every time we need to find the minimum number in a large subarray O(n). Thus, the overall complexity becomes O(n^2) in the worst case. We can reduce the time complexity by using a Segment Tree to find the minimum every time which can be done in O(logn) time.

For implementation, click here(https://leetcode.com/problems/largest-rectangle-in-histogram/discuss/28941/segment-tree-solution-just-another-idea-onlogn-solution).

Complexity Analysis

Time complexity: O(nlogn). Segment tree takes log n for a total of nn times.

Space complexity: O(n). Space required for Segment Tree.
                               */
// Approach 5: Using Stack
