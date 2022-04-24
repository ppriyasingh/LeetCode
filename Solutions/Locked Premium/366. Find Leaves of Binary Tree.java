/*
Given the root of a binary tree, collect a tree's nodes as if you were doing this:

Collect all the leaf nodes.
Remove all the leaf nodes.
Repeat until the tree is empty.
 

Example 1:


Input: root = [1,2,3,4,5]
Output: [[4,5,3],[2],[1]]
Explanation:
[[3,5,4],[2],[1]] and [[3,4,5],[2],[1]] are also considered correct answers since per each level it does not matter the order on which elements are returned.
Example 2:

Input: root = [1]
Output: [[1]]
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    List<List<Integer>> ans= new LinkedList<>();
    public int fillDepth(TreeNode r) {
        if(r==null) { return -1; }
        int currDepth= Math.max(fillDepth(r.left), fillDepth(r.right))+1;
        
        if(ans.size()<currDepth+1) ans.add(new LinkedList<>());
        List<Integer> temp= ans.get(currDepth);
        temp.add(r.val);
        // ans.add(currDepth, temp);
        return currDepth;
    }
    public List<List<Integer>> findLeaves(TreeNode root) {
        fillDepth(root);
        return ans;
    }
}
