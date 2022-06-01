/*
Solution
Overview
It is one of the classical problems related to intervals, and we have some similar problems such as Meeting Rooms II at LeetCode. Below, two approaches are introduced: the simple Time Stamp approach, and the Bucket Sort approach.

Approach 1: Time Stamp
Intuition

A simple idea is to go through from the start to end, and check if the actual capacity exceeds capacity.

To know the actual capacity, we just need the number of passengers changed at each timestamp.

We can save the number of passengers changed at each time, sort it by timestamp, and finally iterate it to check the actual capacity.

Algorithm

We will initialize a list to store the number of passengers changed and the corresponding timestamp and then sort it.

Note that in Java, we do not have a nice API to do this. However, we can use a TreeMap, which can help us to sort during insertion. You can use a PriorityQueue instead.

Finally, we just need to iterate from the start timestamp to the end timestamp and check if the actual capacity meets the condition.
*/
class Solution {
  // T(O): N(logN)
  // S(O): N
    public boolean carPooling(int[][] trips, int capacity) {
        Map<Integer, Integer> timestamp = new TreeMap<>();
        for (int[] trip : trips) {
            int startPassenger = timestamp.getOrDefault(trip[1], 0) + trip[0];
            timestamp.put(trip[1], startPassenger);

            int endPassenger = timestamp.getOrDefault(trip[2], 0) - trip[0];
            timestamp.put(trip[2], endPassenger);
        }
        int usedCapacity = 0;
        for (int passengerChange : timestamp.values()) {
            usedCapacity += passengerChange;
            if (usedCapacity > capacity) {
                return false;
            }
        }
        return true;
    }
}

// Approach 2: Bucket Sort
/*Intuition

Note that in the problem there is a interesting constraint:

0 <= trips[i][1] < trips[i][2] <= 1000
What pops into the mind is Bucket Sort, which is a sorting algorithm in \mathcal{O}(N)O(N) time but requires some prior knowledge for the range of the data.

We can use it instead of the normal sorting in this method.

What we do is initial 1001 buckets, and put the number of passengers changed in corresponding buckets, and collect the buckets one by one.

Algorithm

We will initial 1001 buckets, iterate trip, and save the number of passengers changed at i mile in the i-th bucket.
*/

class Solution {
  // T(O): max(N, 1001) since we need to iterate over trips and then iterate over our 1001 buckets. Assume N is the length of trip.
  // S(O): O(1001) = O(1)
    public boolean carPooling(int[][] trips, int capacity) {
        int[] timestamp = new int[1001];
        for (int[] trip : trips) {
            timestamp[trip[1]] += trip[0];
            timestamp[trip[2]] -= trip[0];
        }
        int usedCapacity = 0;
        for (int number : timestamp) {
            usedCapacity += number;
            if (usedCapacity > capacity) {
                return false;
            }
        }
        return true;
    }
}
