class Solution {
    public int findKthPositive(int[] a, int k) {
        int c =1, m=0, i=0;
        
        while(i<a.length) {
            if(a[i]>c) {
                m = a[i]-c;
                
                if(k>m) {
                    k-=m;
                }else {
                    if(i==0) 
                        return k;
                    else return a[i-1]+k;
                }
            }
            c = a[i]+1;
            i++;
        }
        return a[i-1]+k;
    }
}