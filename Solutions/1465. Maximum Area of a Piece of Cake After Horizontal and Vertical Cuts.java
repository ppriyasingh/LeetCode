class Solution {
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        int hLen = horizontalCuts.length, vLen = verticalCuts.length;
        
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        
        long maxh = Math.max(horizontalCuts[0], h-horizontalCuts[hLen-1]);
        long maxv = Math.max(verticalCuts[0], w-verticalCuts[vLen-1]);
        
        for(int i=1; i<hLen; i++) {
            maxh = Math.max(maxh, horizontalCuts[i]-horizontalCuts[i-1]);
        }
        for(int i=1; i<vLen; i++) {
            maxv = Math.max(maxv, verticalCuts[i]-verticalCuts[i-1]);
        }
        return (int)((maxh * maxv) % 1000000007);
    }
}
