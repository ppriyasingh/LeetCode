class Solution {
    static public int getAllSet(int n) {
        
        int i=0, sum=0;
        
        while(n>0){
            // sum+=Math.pow(2,i);
            // i++;
            
            sum=(sum<<1) |1;
            n>>=1;
        }
        return sum;
    }
    public int findComplement(int num) {
        
        if(num==0) return 1;        
        if(num==1) return 0;
        
        int m= getAllSet(num);
        
        return num^m;
    }
}