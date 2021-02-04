class Solution {
    public int findLHS(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res =0;
        
        for(int num : nums) {
            map.put(num, map.getOrDefault(num,0)+1);
        }
        for(int x: map.keySet()) {
            if(map.containsKey(x+1)) {
                res = Math.max(res, map.get(x)+map.get(x+1));
            }
        }
        
        return res;
    }
}