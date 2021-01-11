class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i=m-1, j=n-1, p = m+n-1;
                
        while(i>=0 && j>=0) {
            
            if(nums2[j]>= nums1[i]){
                nums1[p] = nums2[j]; 
                j--;
            } else {
                nums1[p] = nums1[i];
                nums1[i] =0;
                i--;
            }
            p--;
        }
        
        if(i<0 && j!=-1) {
            for(int k=j; k>=0; k--) {
                nums1[p--] = nums2[k];
            }
            return;
        }
    }
}