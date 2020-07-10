class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ll = new LinkedList<>();
            
        Arrays.sort(nums);        
        int n = nums.length; 
        
        for(int i=0; i<n-2; i++) {
            
            if(nums[i] >0) continue;
            if(i!=0 && nums[i]==nums[i-1]) continue;
            
            int x= -nums[i];
            int j= i+1, k= n-1;
            
            while(j<k) {
                if((nums[j]+nums[k])==x){
                    List<Integer> l= Arrays.asList(nums[i], nums[j], nums[k]);
                    if(!ll.contains(l))
                        ll.add(l);
                    while(j<k && nums[j]==nums[j+1]) j++;
                    while(j<k && nums[k]==nums[k-1]) k--;
                    j++; k--;
                }
                else if((nums[j]+nums[k])<x) j++;
                else  k--;     
            }
        }
        return ll;
    }
}