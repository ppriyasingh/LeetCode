class Solution {
    public boolean canFormArray(int[] arr, int[][] pieces) {
        
        Map<Integer, int[]> map = new HashMap<>();
        for(int[] piece: pieces) {
            map.put(piece[0], piece);
        }
        
        for(int i=0; i<arr.length; i++) {
            if(map.containsKey(arr[i])) {
                
                if(map.get(arr[i]).length>1 && i!=arr.length-1) {
                    
                    int[] a = map.get(arr[i]);                
                    for(int j=1; j<a.length; j++) {
                        if(arr[++i]!= a[j])
                            return false;
                    }
                }                
                
            } else return false;
        }
        return true;
        
    }
}