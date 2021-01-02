class Solution {
    TreeNode ans;
    
    public void getTargetCopyUtil(TreeNode original, TreeNode cloned, TreeNode target) {
        // using DFS Approach
		
        if(original == target) {
            ans = cloned;
            return;
        }
        
        if(original.left != null) getTargetCopyUtil(original.left, cloned.left, target);
        if(original.right != null) getTargetCopyUtil(original.right, cloned.right, target);
    }
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if(original == null)
            return null;
        
        getTargetCopyUtil(original, cloned, target);
        return ans;
    }
}

class Solution {
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
		// using BFS Approach
		
        int found= target.val;
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(cloned);
		
        while(!queue.isEmpty())
        {
            TreeNode temp=queue.poll();
            if(temp.val==found)
                return temp;
            if(temp.left!=null)
                queue.add(temp.left);
            if(temp.right!=null)
                queue.add(temp.right);
        }
        return null;
    }
}