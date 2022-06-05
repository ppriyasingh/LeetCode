class Solution {
    public int[][] merge(int[][] intervals) {
        if(intervals.length == 0 || intervals.length == 1) return intervals;
        
        Arrays.sort(intervals, (a, b)-> a[0]-b[0]);
        List<int[]> ans = new LinkedList<>();
        int start = intervals[0][0], end = intervals[0][1], n = intervals.length;
        
        for(int i = 0; i < n; i++) {
            
            if(intervals[i][0] <= end) {
                end = Math.max(end, intervals[i][1]);
            } else {
                ans.add(new int[]{start, end});
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }
        ans.add(new int[]{start, end});
        
        return ans.toArray(new int[ans.size()][]);
    }
}
