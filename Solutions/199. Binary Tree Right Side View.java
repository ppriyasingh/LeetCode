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

// Best Solution: DFS
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

// BFS
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if(root == null ) return result;
        
        Queue<TreeNode> q = new LinkedList<>();
        
        q.add(root);
        
        while(!q.isEmpty()) {
            
            int size = q.size();
            
            for(int i=0; i<size; i++) {
                TreeNode temp = q.poll();
                if(i == size-1) result.add(temp.val);
                if(temp.left != null) q.add(temp.left);
                if(temp.right != null) q.add(temp.right);
            }
        }
        return result;
    }
}
