class Solution {
    public String getSmallestString(int n, int k) {
        // [ a, a, s, z, z]     val=5           pendingval= 73-5=68
        //   1  1  19  26  26      val= 55      pendingval= 68-25 = 43 -25 = 18-18=0
        
        char[] c = new char[n];
        Arrays.fill(c,'a');
        k-=n;
        
         while(k>0) {
             c[--n] += Math.min(25,k);
             k-=25;
         }
        
        return new String(c);
    }
}