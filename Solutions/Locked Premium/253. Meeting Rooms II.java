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

class Solution {
    public int minMeetingRooms(Interval[] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        int rooms = 0, endsItr = 0;
        for (int i = 0; i < starts.length; i++) {
            if (starts[i] < ends[endsItr]) {
                rooms++;
            } else {
                endsItr++;
            }
        }
        return rooms;
    }
}

// Approach 1: Priority Queues
/*
We can't really process the given meetings in any random order. The most basic way of processing the meetings is in increasing order of their start times 
and this is the order we will follow. After all, it makes sense to allocate a room to the meeting that is scheduled for 9 a.m. in the morning before you 
worry about the 5 p.m. meeting, right?

Let's do a dry run of an example problem with sample meeting times and see what our algorithm should be able to do efficiently.

We will consider the following meeting times for our example (1, 10), (2, 7), (3, 19), (8, 12), (10, 20), (11, 30). The first part of the tuple is the 
start time for the meeting and the second value represents the ending time. We are considering the meetings in a sorted order of their start times. The 
first diagram depicts the first three meetings where each of them requires a new room because of collisions.

The next 3 meetings start to occupy some of the existing rooms. However, the last one requires a new room altogether and overall we have to use 4 
different rooms to accommodate all the meetings.

Sorting part is easy, but for every meeting how do we find out efficiently if a room is available or not? At any point in time we have multiple rooms 
that can be occupied and we don't really care which room is free as long as we find one when required for a new meeting.
A naive way to check if a room is available or not is to iterate on all the rooms and see if one is available when we have a new meeting at hand.
However, we can do better than this by making use of Priority Queues or the Min-Heap data structure.
Instead of manually iterating on every room that's been allocated and checking if the room is available or not, we can keep all the rooms in a min heap
where the key for the min heap would be the ending time of meeting.
So, every time we want to check if any room is free or not, simply check the topmost element of the min heap as that would be the room that would get 
free the earliest out of all the other rooms currently occupied.
If the room we extracted from the top of the min heap isn't free, then no other room is. So, we can save time here and simply allocate a new room.

Let us look at the algorithm before moving onto the implementation.

Algorithm

Sort the given meetings by their start time.
Initialize a new min-heap and add the first meeting's ending time to the heap. We simply need to keep track of the ending times as that tells us when a 
meeting room will get free.
For every meeting room check if the minimum element of the heap i.e. the room at the top of the heap is free or not.
If the room is free, then we extract the topmost element and add it back with the ending time of the current meeting we are processing.
If not, then we allocate a new room and add it to the heap.
After processing all the meetings, the size of the heap will tell us the number of rooms allocated. This will be the minimum number of rooms needed to 
accommodate all the meetings.
*/
class Solution {
  // Time O(NlogN)
  // Space O(N)
    public int minMeetingRooms(int[][] intervals) {
        
    // Check for the base case. If there are no intervals, return 0
    if (intervals.length == 0) {
      return 0;
    }

    // Min heap
    PriorityQueue<Integer> allocator =
        new PriorityQueue<Integer>(
            intervals.length,
            new Comparator<Integer>() {
              public int compare(Integer a, Integer b) {
                return a - b;
              }
            });

    // Sort the intervals by start time
    Arrays.sort(
        intervals,
        new Comparator<int[]>() {
          public int compare(final int[] a, final int[] b) {
            return a[0] - b[0];
          }
        });

    // Add the first meeting
    allocator.add(intervals[0][1]);

    // Iterate over remaining intervals
    for (int i = 1; i < intervals.length; i++) {

      // If the room due to free up the earliest is free, assign that room to this meeting.
      if (intervals[i][0] >= allocator.peek()) {
        allocator.poll();
      }

      // If a new room is to be assigned, then also we add to the heap,
      // If an old room is allocated, then also we have to add to the heap with updated end time.
      allocator.add(intervals[i][1]);
    }

    // The size of the heap tells us the minimum rooms required for all the meetings.
    return allocator.size();
  }
}

// Approach 2: Chronological Ordering
/*
Intuition

The meeting timings given to us define a chronological order of events throughout the day. We are given the start and end timings for the meetings which 
can help us define this ordering.
Arranging the meetings according to their start times helps us know the natural order of meetings throughout the day. However, simply knowing when a 
meeting starts doesn't tell us much about its duration.
We also need the meetings sorted by their ending times because an ending event essentially tells us that there must have been a corresponding starting 
event and more importantly, an ending event tell us that a previously occupied room has now become free.
A meeting is defined by its start and end times. However, for this specific algorithm, we need to treat the start and end times individually. This might 
not make sense right away because a meeting is defined by its start and end times. If we separate the two and treat them individually, then the identity 
of a meeting goes away. This is fine because:
When we encounter an ending event, that means that some meeting that started earlier has ended now. We are not really concerned with which meeting has 
ended. All we need is that "some" meeting ended thus making a room available.

Let us consider the same example as we did in the last approach. We have the following meetings to be scheduled: (1, 10), (2, 7), (3, 19), (8, 12), 
(10, 20), (11, 30). As before, the first diagram show us that the first three meetings are colliding with each other and they have to be allocated 
separate rooms.

Algorithm

1. Separate out the start times and the end times in their separate arrays.
2. Sort the start times and the end times separately. Note that this will mess up the original correspondence of start times and end times. They will be 
treated individually now.
3. We consider two pointers: s_ptr and e_ptr which refer to start pointer and end pointer. The start pointer simply iterates over all the meetings and the 
end pointer helps us track if a meeting has ended and if we can reuse a room.
4. When considering a specific meeting pointed to by s_ptr, we check if this start timing is greater than the meeting pointed to by e_ptr. If this is the 
case then that would mean some meeting has ended by the time the meeting at s_ptr had to start. So we can reuse one of the rooms. Otherwise, we have to 
allocate a new room.
5. If a meeting has indeed ended i.e. if start[s_ptr] >= end[e_ptr], then we increment e_ptr.
6. Repeat this process until s_ptr processes all of the meetings.

*/
 class Solution {
  // Time O(NlogN)
  // Space O(N)
    public int minMeetingRooms(int[][] intervals) {
        
    // Check for the base case. If there are no intervals, return 0
    if (intervals.length == 0) {
      return 0;
    }

    Integer[] start = new Integer[intervals.length];
    Integer[] end = new Integer[intervals.length];

    for (int i = 0; i < intervals.length; i++) {
      start[i] = intervals[i][0];
      end[i] = intervals[i][1];
    }

    // Sort the intervals by end time
    Arrays.sort(
        end,
        new Comparator<Integer>() {
          public int compare(Integer a, Integer b) {
            return a - b;
          }
        });

    // Sort the intervals by start time
    Arrays.sort(
        start,
        new Comparator<Integer>() {
          public int compare(Integer a, Integer b) {
            return a - b;
          }
        });

    // The two pointers in the algorithm: e_ptr and s_ptr.
    int startPointer = 0, endPointer = 0;

    // Variables to keep track of maximum number of rooms used.
    int usedRooms = 0;

    // Iterate over intervals.
    while (startPointer < intervals.length) {

      // If there is a meeting that has ended by the time the meeting at `start_pointer` starts
      if (start[startPointer] >= end[endPointer]) {
        usedRooms -= 1;
        endPointer += 1;
      }

      // We do this irrespective of whether a room frees up or not.
      // If a room got free, then this used_rooms += 1 wouldn't have any effect. used_rooms would
      // remain the same in that case. If no room was free, then this would increase used_rooms
      usedRooms += 1;
      startPointer += 1;

    }

    return usedRooms;
  }
}
