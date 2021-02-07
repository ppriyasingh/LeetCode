class Solution {
    public int[] shortestToChar(String s, char c) {
        int len = s.length();
        int[] a = new int[len];
        
        Arrays.fill(a,Integer.MAX_VALUE);
        
        for(int i=0; i<len; i++) {
            if(s.charAt(i)==c) {
                for(int j=0; j<len; j++) {
                    int distance = Math.abs(i-j);
                    a[j]= distance<a[j]? distance: a[j];
                }
            }
        }
        
        return a;
    }
}