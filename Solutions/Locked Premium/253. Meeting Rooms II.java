// Almost similar to: 1094. Car Pooling: https://leetcode.com/problems/car-pooling/
/*
Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.

 

Example 1:

Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2
Example 2:

Input: intervals = [[7,10],[2,4]]
Output: 1

*/

/*
// My Solution
Intuition
Track the change of room numbers in time order.

Explanation:

Save all time points and the change on current meeting rooms.
Sort all the changes on the key of time points.
Track the current number of using rooms cur and update result res.
*/
class Solution {
  // Time O(NlogN)
  // Space O(N)
    public int minMeetingRooms(int[][] intervals) {
        if(intervals.length == 0 || intervals.length == 1) return intervals.length;
        
        Map<Integer, Integer> map = new TreeMap<>();
        
        for(int[] interval: intervals) {
            map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
            map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
        }
        int roomsRequired=0, currRoom=0;
        for(int val: map.values()) {
            roomsRequired = Math.max(roomsRequired, currRoom += val);
        }
        return roomsRequired;
    }
}
