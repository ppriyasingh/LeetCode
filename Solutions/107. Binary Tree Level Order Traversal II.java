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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        
        List<List<Integer>> ll = new ArrayList<>();
        
        if(root == null) return ll;
        
        Queue<TreeNode> q= new LinkedList<>();
        q.add(root);
        q.add(null);
        
        List<Integer> l = new ArrayList<>();
                
        while(!q.isEmpty()) {
            
            TreeNode temp = q.remove();
            
            if(temp!=null) {
                
                l.add(temp.val);
                if(temp.left!=null) q.add(temp.left);                
                if(temp.right!=null) q.add(temp.right);
                
            }
            else {                
                ll.add(l);
                l= new ArrayList<>();
                
                if(q.isEmpty()) 
                    break;
                else 
                    q.add(null);
            }
        }
        
        Collections.reverse(ll);
        return ll;
    }
}