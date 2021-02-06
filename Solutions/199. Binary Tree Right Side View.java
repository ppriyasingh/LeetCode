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
    List<Integer> res = new ArrayList<>();
    public List<Integer> rightSideView(TreeNode root) {
        if(root==null)
            return res;
        
        rightSideViewUtil(root, 0);
        return res;
    }
    public void rightSideViewUtil(TreeNode root, int level) {
        if(level == res.size()) 
            res.add(root.val);
            
        if(root.right!=null) rightSideViewUtil(root.right, level+1);
        if(root.left!=null) rightSideViewUtil(root.left, level+1);
    }
}