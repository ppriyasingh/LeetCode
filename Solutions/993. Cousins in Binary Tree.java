class Solution {
    public int findDepth(TreeNode root, int x, int level) {
        if(root==null) return 0;
        if(root.val ==x) return level;
        
        return findDepth(root.left, x, level+1)| findDepth(root.right, x, level+1);
        
    }
    public TreeNode findParent(TreeNode root, int x) {
        if(root==null || (root.left==null && root.right==null)) return null;
        
        if((root.left!= null && root.left.val== x) || (root.right!= null && root.right.val== x)) return root;
        
        return findParent(root.left, x) || findParent(root.right, x);
        
    }
    
    public boolean isCousins(TreeNode root, int x, int y) {
        if(root.left==null && root.right==null) return true;
        
        int dx= findDepth(root, x, 0);
        int dy= findDepth(root, y, 0);
        
        TreeNode px= findParent(root, x);        
        TreeNode py= findParent(root, y);
        
        return (dx==dy && px!=py);


    }
}