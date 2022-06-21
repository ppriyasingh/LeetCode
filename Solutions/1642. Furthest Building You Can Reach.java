// Approach 1: Min-Heap
class Solution {
  // T(O): O(N log N) or  O(N log L), N is numbers of heights and L is number of ladders
  // S(O): O(N) or O(L)
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // Create a priority queue with a comparator that makes it behave as a min-heap.
        Queue<Integer> ladderAllocations = new PriorityQueue<>((a, b) -> a - b);
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            // If this is actually a "jump down", skip it.
            if (climb <= 0) {
                continue;
            }
            // Otherwise, allocate a ladder for this climb.
            ladderAllocations.add(climb);
            // If we haven't gone over the number of ladders, nothing else to do.
            if (ladderAllocations.size() <= ladders) {
                continue;
            }
            // Otherwise, we will need to take a climb out of ladder_allocations
            bricks -= ladderAllocations.remove();
            // If this caused bricks to go negative, we can't get to i + 1
            if (bricks < 0) {
                return i;
            }
        }
        // If we got to here, this means we had enough materials to cover every climb.
        return heights.length - 1;
    }
}
// Approach 2: Max-Heap
class Solution {
  // T(O): O(N log N), N is numbers of heights and L is number of ladders
  // S(O): O(N) 
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // Create a priority queue with a comparator that makes it behave as a max-heap.
        Queue<Integer> brickAllocations = new PriorityQueue<>((a, b) -> b - a);
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            // If this is actually a "jump down", skip it.
            if (climb <= 0) {
                continue;
            }            
            // Otherwise, allocate a ladder for this climb.
            brickAllocations.add(climb);
            bricks -= climb;
            // If we've used all the bricks, and have no ladders remaining, then 
            // we can't go any further.
            if (bricks < 0 && ladders == 0) {
                return i;
            }
            // Otherwise, if we've run out of bricks, we should replace the largest
            // brick allocation with a ladder.
            if (bricks < 0) {
                bricks += brickAllocations.remove();
                ladders--;
            }
        }
        // If we got to here, this means we had enough materials to cover every climb.
        return heights.length - 1;
    }
}
// Approach 3: Binary Search for Final Reachable Building
class Solution {
   // T(O): O(N log^2N), N is numbers of heights and L is number of ladders
   // S(O): O(N) 
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // Do a binary search on the heights array to find the final reachable building.
        int lo = 0;
        int hi = heights.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (isReachable(mid, heights, bricks, ladders)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return hi; // Note that return lo would be equivalent.
    }
    
    private boolean isReachable(int buildingIndex, int[] heights, int bricks, int ladders) {
        // Make a list of all the climbs we need to do to reach buildingIndex.
        List<Integer> climbs = new ArrayList<>();
        for (int i = 0; i < buildingIndex; i++) {
            int h1 = heights[i];
            int h2 = heights[i + 1];
            if (h2 <= h1) {
                continue;
            }
            climbs.add(h2 - h1);
        }
        Collections.sort(climbs);
        
        // And now determine whether or not all of these climbs can be covered with the
        // given bricks and ladders.
        for (int climb : climbs) {
            // If there are bricks left, use those.
            if (climb <= bricks) {
                bricks -= climb;
            // Otherwise, you'll have to use a ladder.
            } else if (ladders >= 1) {
                ladders -= 1;
            // And if there are no ladders either, we can't reach buildingIndex.
            } else {
                return false;
            }
        }
        return true;
    }
}
// Approach 4: Improved Binary Search for Final Reachable Building
class Solution {
    // T(O): O(N log N), N is numbers of heights and L is number of ladders
   // S(O): O(N) 
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // Make a sorted list of all the climbs.
        List<int[]> sortedClimbs = new ArrayList<>();
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            if (climb <= 0) {
                continue;
            }
            sortedClimbs.add(new int[]{climb, i + 1});
        }
        Collections.sort(sortedClimbs, (a,b) -> a[0] - b[0]);
        
        // Now do the binary search, same as before.
        int lo = 0;
        int hi = heights.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (isReachable(mid, sortedClimbs, bricks, ladders)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return hi; // Note that return lo would be equivalent.
    }
    
    private boolean isReachable(int buildingIndex, List<int[]> climbs, int bricks, int ladders) {
        for (int[] climbEntry : climbs) {
            // Extract the information for this climb
            int climb = climbEntry[0];
            int index = climbEntry[1];
            // Check if this climb is within the range.
            if (index > buildingIndex) {
                continue;
            }
            // Allocate bricks if enough remain; otherwise, allocate a ladder if
            // at least one remains.
            if (climb <= bricks) {
                bricks -= climb;
            } else if (ladders >= 1) {
                ladders -= 1;
            } else {
                return false;
            }
        }
        return true;
    }
}
// Approach 5: Binary Search on Threshold (Advanced)
class Solution {
    // T(O): O(N log (maxClimb)), N is numbers of heights and L is number of ladders
    // S(O): O(1) 
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        int lo = Integer.MAX_VALUE;
        int hi = Integer.MIN_VALUE;
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            if (climb <= 0) {
                continue;
            }
            lo = Math.min(lo, climb);
            hi = Math.max(hi, climb);   
        }
        if (lo == Integer.MAX_VALUE) {
            return heights.length - 1;
        }
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int[] result = solveWithGivenThreshold(heights, bricks, ladders, mid);
            int indexReached = result[0];
            int laddersRemaining = result[1];
            int bricksRemaining = result[2];
            if (indexReached == heights.length - 1) {
                return heights.length - 1;
            }
            if (laddersRemaining > 0) {
                hi = mid - 1;
                continue;
            }
            // Otherwise, check whether this is the "too low" or "just right" case.
            int nextClimb = heights[indexReached + 1] - heights[indexReached];
            if (nextClimb > bricksRemaining && mid > bricksRemaining) {
                return indexReached;
            } else {
                lo = mid + 1;
            }
        }
        return -1; // It always returns before here. But gotta keep Java happy.
    }
    
    public int[] solveWithGivenThreshold(int[] heights, int bricks, int ladders, int K) {
        int laddersUsedOnThreshold = 0;
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            if (climb <= 0) {
                continue;
            }
            // Make resource allocations
            if (climb == K) {
                laddersUsedOnThreshold++;
                ladders--;
            } else if (climb > K) {
                ladders--;
            } else {
                bricks -= climb;
            }
            // Handle negative resources
            if (ladders < 0) {
                if (laddersUsedOnThreshold >= 1) {
                    laddersUsedOnThreshold--;
                    ladders++;
                    bricks -= K;
                } else {
                    return new int[]{i, ladders, bricks};
                }
            }
            if (bricks < 0) {
                return new int[]{i, ladders, bricks};
            }
        }
        return new int[]{heights.length - 1, ladders, bricks};
    }
}
