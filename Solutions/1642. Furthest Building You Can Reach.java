/*
Overview
This is one of my favorite questions on LeetCode. There are several very different approaches for solving it; each requires looking at the problem from a 
slightly different angle. I've covered five approaches in this article. The first and second are closely related and are the "standard" solutions to this
problem. The third is quite different, and the fourth is a clever optimization of the third. The fifth approach is an optional extra aimed at those keen 
on a good challenge.

Remember that to best prepare for your interviews, you should look at multiple ways of solving problems. For this problem, I recommend that, at a minimum,
you look at approaches 1 and 3. In addition to that, I also recommend trying to code up approach 2 as independently as you can to help you ensure you 
understood approach 1.

Warning: The "greedy solution" mentioned on the forum does not work!

When this question was first released, the test cases were not adequate and allowed an incorrect greedy algorithm with a time complexity of O(N) 
(where N is the number of buildings) and a space complexity of O(1) to pass. I've not included this algorithm here, as it's incorrect, and there is 
no shortage of forum posts on it anyway.

Unfortunately, this has meant, at least at the time of writing this article, that a lot of the sample solutions for the lower runtimes are buggy, and 
there are numerous incorrect forum posts. It also means that it’s very difficult to write a correct algorithm that gets a good comparative run time, as 
it has to compete against the numerous incorrect submissions.

*/

// Approach 1: Min-Heap
/*
Intuition

To get started, here's an example where it's actually possible to get all the way across. You have 8 bricks, 2 ladders, and heights
[2, 7, 9, 3, 1, 2, 5, 9, 4, 6]. Determine where to put the bricks and ladders before reading further (remember, you do not need to use bricks or ladders
to go down).
If you solved the problem correctly, this is what you will have gotten.
We'll refer to movements between buildings where a ladder or bricks are needed as climbs.

You might have already realized that the best strategy is to use the ladders for the longest climbs and the bricks for the shortest climbs. This shouldn’t
seem too surprising; a ladder is most valuable in the cases where we would have to use a lot of bricks.

The above example was made easy by me telling you that it was possible to get all the way across. Given an input, determining whether or not it is
possible to go all the way across is an easy problem; you could simply make a list of all the climbs, min-sort the list, and then cover as many climbs as 
you can with bricks, and then cover the rest with ladders. If this covered the entire climb list, then you’d know that it was possible to get all the way 
across.

However, this problem requires determining how far we can get. We don't know which climbs we need to cover because we don't know the final building we can
reach.

The solution is to move along the buildings sequentially, one climb at a time. At all times, we should ensure ladders have been allocated to the longest
climbs seen so far and bricks to the shortest. This might sometimes involve going back and changing an earlier allocation.

The simplest way of doing this is to begin by using all of the available ladders; in the example we're working with, recall that you have 2 ladders. Once
you've made 2 climbs (ignoring the places where you could jump down), you know that you've allocated your 2 ladders to the 2 longest climbs you've seen so 
far. What should you do next to ensure that the ladders remain on the 2 longest climbs?
You should use a brick; this next climb is smaller than either of the ladder climbs.
How about the next climb?
For this climb, you should replace the second ladder with 2 bricks and then use the reclaimed ladder.
To do the next climb, you'll need to either use bricks or reclaim a ladder by replacing one of the earlier ladder climbs with bricks.
This strategy should be repeated until we reach the final building, or we can't go any further (i.e., there aren't enough bricks to do the next climb or 
replace a previous ladder). Here is an animation of it in action.
1 / 14

Algorithm
The outer loop of the algorithm needs to iterate over all climbs, going from left to right.
To find all climbs, we'll need to check the difference between each adjacent (side-by-side) pair of buildings to see if there is a climb there or a jump 
down/ walk across. Remember, we only need to put a ladder or bricks on the actual climbs. Here is the pseudocode to do that.
define function furthestBuilding(heights, bricks, ladders):
    for each i from 0 to heights.length - 2 (including the end point):
        current_height = heights[i]
        next_height = heights[i + 1]
        difference = next_height - current_height
        if difference is 0 or difference is negative:
            continue to next iteration (jumping down is free)
        otherwise, difference is a climb 
This code loops over all buildings, starting from the first and ending at the second-to-last. For each building, it calculates the difference between the 
next building and itself. If this difference is non-positive, then we know this is not a climb and therefore should be skipped. Otherwise, it's a climb 
that we will need to allocate bricks or a ladder to.

Note that when you code this bit up, you'll need to be quite careful; it's very easy to make off-by-one errors here if you're inexperienced. There are
N buildings, but there are only N - 1 gaps between them. Make sure your code isn't trying to define a gap stating from the last building.

Anyway, the next thing to do is to decide how we're going to handle the allocation of ladders and bricks. We decided above that the strategy we'll use
is to use a ladder if we have one available. If we're out of ladders, we'll replace the most wasteful ladder allocation with bricks. In code, this means 
we'll need a data structure that we can insert climbs into, and then when needed, retrieve the smallest climb. The data structure we use for this is a 
heap, also known as a priority queue.

Recall that a heap is a data structure that allows us to efficiently perform two operations: inserting items with a priority and retrieving and/or 
removing the item with the highest priority. In some cases, such as this problem, the priority is calculated from the inserted item itself with a priority
function, removing the need for an explicit priority to be inserted.

We refer to a heap that simply returns the smallest item as a min-heap.

Putting all of the logic we've discussed so far into pseudocode, you would get something like this.
define function furthestBuilding(heights, bricks, ladders):
    ladder_allocations = a new min heap
    for each i from 0 to heights.length - 2 (including the end point):
        current_height = heights[i]
        next_height = heights[i + 1]
        difference = next_height - current_height
        if difference is 0 or difference is negative:
            continue
        rename difference to climb
        if there are ladders remaining:
            add climb to ladder_allocations
            subtract 1 from ladders
        else: (There are no ladders remaining)
            smallest_ladder_allocation = peek at the minimum in ladder_allocations
            if smallest_ladder_allocation is null or climb is smaller:
                subtract climb from bricks
            else: (smallest_ladder_allocation is smaller)
                pop smallest_ladder_allocation from ladder_allocations
                add climb to ladder_allocations
                subtract smallest_ladder_allocation from bricks
            if bricks is now negative:
                return i (we didn't have enough bricks to climb to i + 1)
    return heights.length - 1 (we must have covered all of the climbs)
Essentially, we allocate a ladder if one is available. Otherwise, we look at the smallest ladder allocation in the heap. If the heap is empty or the current 
climb is shorter than the smallest in the heap, we subtract bricks for this climb. Otherwise, we reclaim a ladder from the smallest ladder allocation 
in the heap and subtract bricks to replace the ladder. If this results in bricks going negative, then we must not have had enough bricks to go any further.

This algorithm would be acceptable for you to now code up; however, there is a nicer way that we can design the algorithm that removes most of the 
conditionals.

Coming up with these really elegant designs is a skill that takes practice, so don't worry too much if your own code is a bit longer.

define function furthestBuilding(heights, bricks, ladders):
    ladder_allocations = a new min heap
    for each i from 0 to heights.length - 2 (including the end point):
        current_height = heights[i]
        next_height = heights[i + 1]
        difference = next_height - current_height
        if difference is 0 or difference is negative:
            continue
        rename difference to climb
        add climb to ladder_allocations
        if climbs in ladder_allocations is now greater than ladders:
            smallest_ladder_allocation = remove minimum from ladder_allocations
            subtract smallest_ladder_allocation from bricks
            if bricks is now negative:
                return i (we didn't have enough bricks to climb to i + 1)
    return heights.length - 1 (we must have covered all of the climbs)
On each iteration, we always add the current climb to the heap. If this causes the heap to become larger than the number of ladders available, then we
need to remove the smallest climb from the heap (which could be the one just inserted) and replace it with bricks. If there aren't enough bricks for that,
then we can't go any further.

This algorithm is a lot better, as we no longer have to handle the empty-heap case, and we don't have to handle the current climb needing bricks as a 
special case. Like I said, though, don't worry too much if you didn't come up with it on your own; practice makes perfect (and it wasn't the first version
I wrote either)!
*/
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
/*
Intuition

This approach is similar to Approach 1, except instead of initially allocating ladders, we allocate bricks. When we run out of bricks, we replace the 
longest climb with a ladder. This was in contrast to before when we were replacing the shortest climb with bricks. Because we now need to retrieve
maximums instead of minimums, we should use a max-heap instead of a min-heap.

You should have a go at coding up this approach by yourself before reading any further; it's a good exercise to ensure you understood approach 1.

Algorithm
In addition to replacing the min-heap with a max-heap, we also need to keep track of how many bricks we've used so far (as we can't simply check the
current size of the heap like we did in Approach 1). The simplest way of doing this is to subtract from the bricks input parameter and check for when 
it goes to zero. Here is the pseudocode for this approach.

define function furthestBuilding(heights, bricks, ladders):
    bricks_allocations = a new max heap
    for each i from 0 to heights.length - 2 (including the end point):
        current_height = heights[i]
        next_height = heights[i + 1]
        difference = next_height - current_height
        if difference is 0 or difference is negative:
            continue (this is a jump)
        rename difference to climb
        add climb to brick_allocations
        subtract climb from bricks
        if bricks is not negative:
            continue (this climb is fine for now)
        if ladders is zero:
            return i (we can't get to i + 1)
        largest_brick_allocation = remove maximum from brick_allocations
        add largest_brick_allocation onto bricks
        subtract one from ladders
    return heights.length - 1 (we must have covered all of the climbs)
Like how we first tried to use a ladder in approach 1, in this approach, we first try to allocate bricks; this requires adding the climb to the heap and
subtracting the climb from bricks. If bricks is still non-negative after doing this, then there is nothing else we need to do for this climb right now, 
so we continue onto the next one.

If, however, bricks has become negative, then we'll need to make bricks positive again by reclaiming some bricks; we do this by removing the largest
brick allocation from the heap and subtracting 1 from ladders to cover the removed brick allocation. This works because one of two cases is true; either 
there's a previous climb with more bricks to reclaim, or we've just added the largest climb onto the max-heap. So when we remove the maximum from the
max-heap, we'll definitely get at least as many bricks as we just subtracted to make bricks non-negative again.

The only case we can't simply replace the largest brick allocation with a ladder happens when ladders is already zero. In this case, we know we can't 
jump to the next building index and so should return the current building index. In the pseudocode, we've firstly checked for this case and then gone 
onto the process described above for making bricks non-negative.
*/
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
/*
Intuition

In Approach 1, we made the following observation.

Given an input, determining whether or not it is possible to go all the way across is an easy problem; you could simply make a list of all the climbs, 
min-sort it, and then cover as many climbs as you can with bricks, and then cover the rest with ladders. If this covered the entire climb list, then 
you’d know that it was possible to get all the way across.

More generally, this implies that given a specific building, we can easily determine whether or not we have enough bricks and ladders to reach it 
(starting from the first building). For example, if you have 9 bricks and 4 ladders, can you reach building 8 on this example?
How about reaching building 15? (Again, with 9 bricks and 4 ladders)
If you solved these examples correctly, you would have found that the answer was "yes" for reaching building 8 but "no" for reaching building 15.

Now, remembering that our goal is to find the furthest building reachable with a given quantity of bricks and ladders, a possible (but inefficient) 
algorithm would be to iterate backward through the list of buildings, checking whether or not each is reachable. The first one we find that is reachable 
has to be the furthest building we can reach and so should be returned.

Unfortunately, though, this algorithm will be too slow. Remember that determining reachability for a given building requires sorting. Doing N−1 sorts in 
the worst case gives an overall time complexity of O(N² \log N)O(N²logN).

Looking at this algorithm from another angle, it’s actually performing a linear search. We are, after all, sequentially going through a list and checking
each item until we find the one we’re looking for (the highest-index one that is reachable). Whenever you have an algorithm based on linear search that 
is too slow, your brain should be jumping straight to thinking about binary search. Binary search can be used whenever the list is sorted on the property
we’re searching for.

In this case, that property is simply a boolean "yes" or "no" for each building that specifies whether or not that building is reachable. For the list 
to be sorted on this property, either all of the buildings would have to have the same value, or all of one value would have to come after the other. 
Let's go back to the example that we played around with above. Each building has its corresponding "yes" or "no" written on it (remember, we had 9 bricks 
and 4 ladders).
This looks promising; all of the "yes" tags are before all of the "no" tags. And in fact, we can easily see that this will always be true. For a given 
case, either all of the buildings are reachable (so it's trivially sorted), or there is some building before the last building that is the 
final-reachable-building. All of the buildings after it have to have a "no" (otherwise, it couldn't be the final reachable building), and all of the 
buildings before it have to have a "yes", as we reached them in the process of getting to the final-reachable-building.

In conclusion, we can confidently say that the list is sorted on this property. All of the "yes" tags come before any "no" tags. And this means that we
can use binary search!

In binary search, we repeatedly halve the search space by checking the building in the middle. If that building is not reachable, then we know that none
of the buildings after it are (and therefore they couldn't possibly be the final-reachable-building). If the building is reachable, then we know that it 
is the final-reachable-building, or some building after it is.

While we won't look too much at the time complexity yet, it's always worth having a quick think about whether or not an approach is viable before you go 
too far with it. In this case, recall that binary search is O(\log N)O(logN). Sorting is O(N \log N)O(NlogN). We're going to do a sort for each binary 
search, so this is looking to be O(N \log² N)O(Nlog²N). This isn't too bad, and so it is worth continuing on with. While it is slightly worse than 
approach 1, it's not by much, and it's still worth looking at as an alternate approach (and in approach 4, we'll look at how we can optimize it a bit more).

Algorithm

There are two main parts to this algorithm: checking whether or not a given building is reachable and using a binary search to decide which buildings to 
check. The first is easier, so we’ll start with that.

Recall that given a building index, we want to determine whether or not we have enough bricks and ladders to get from the first building to the building
with that given index. We do this by firstly making a min-sorted list of all the climbs we'd need to make, and then by allocating bricks to the smallest 
climbs and ladders once we run out of bricks. If we could cover all the climbs, then we know it is possible to reach the given building.
heights, bricks, and ladders are as specified in the problem

define function isReachable(building_index):
    climbs = a new list
    for each i between 0 and building_index - 1 (inclusive):
        climb_distance = heights[i + 1] - heights[i]
        if climb_distance is positive:
            add climb_distance to climbs
    min-sort climbs
    bricks_remaining = bricks
    ladders_remaining = ladders
    for each climb in climbs:
        if there are enough bricks_remaining for climb:
            subtract climb from bricks_remaining
        else if there is at least one ladder left:
            subtract 1 from ladders_available
        else:
            return false
     return true
Now that we have that out of the way, we need to figure out the details for the Binary search.

Binary search's details easily confuse many programmers, so many become tempted to resort to poor practices such as Programming by Permutation. With that
in mind, I'm going to walk you through a methodical way of approaching this problem that also applies to other binary search problems. If you’re already 
confident with binary search and don't want to walk through it in this level of detail, then you can skip straight to the pseudocode, or ideally, go away
and try and code it up on your own. Otherwise, I recommend following really carefully, as this process really does help.

The importance of being able to methodically design and implement a binary search cannot be understated. You'll almost certainly encounter it in multiple 
interviews. Focus firstly on your accuracy and then work on your coding speed. While coding fast and correctly is the ideal, keep in mind that accuracy
is still worth more than speed. Buggy code is bad, no matter how quickly you wrote it.

These are the questions we'll be answering and then putting into code.

What is the target item, value, and type?
What bounds must the target item be within?
What can we conclude when isReachable(mid) returns true ("yes")?
What can we conclude when isReachable(mid) returns false ("no") ?
How do we know when we've found the target item?
Which calculation for mid should we use?
Step 1: What is the target item, value, and type?

We want to know which building in the heights array (i.e., not a climb distance) is the final-reachable-building. Buildings are identified by indexes in
the array; therefore, we're looking for the index in heights that corresponds to the final-reachable-building. It might sound obvious in this case, but 
it's always important to pinpoint whether we're looking for an index, between two indexes, or something else entirely.

Step 2: What bounds must the target item be within?

The highest possible index for the final-reachable-building is heights.length - 1 (i.e. the last building in heights). In the worst case, the only
building we’ll be able to reach is the building at index 0 (which we can always reach). Therefore, the answer could be anywhere from 0 to 
heights.length - 1, inclusive of both of these endpoints. In the binary search code, this will be:

lo = 0
hi = heights.length - 1
Notice that we haven't used hi = heights.length or lo = -1, or anything else like that. People are sometimes tempted to put that stuff in to "cover edge 
cases", handle inclusive vs. exclusive endpoints, etc., without fully understanding what they were trying to achieve with it. It's generally easiest to 
set lo to represent the lowest possible position of the target and hi to be the highest possible position.

Step 3: How do we know when we found the target?

Often in a binary search, we immediately know when we've found the answer. For example, if the problem was to search for 12 in a sorted array, then as 
soon as the value at mid is 12, we can return true.

For this problem, though, we can't do that. If the value of isReachable(mid) is true, then it is possible that it is indeed the final-reachable-building, 
but it is also very possible that the final-reachable-building is further to the right. Because of this, we should instead shrink the search space down 
to length-one (i.e., to where lo = hi) and then determine whether or not that one item is the one we want. In this case, we know that the target 
definitely exists, so as soon as the search space is of length-one, the index in it is for the final-reachable-building.

With that, this is what we have so far.

lo = 0
hi = heights.length - 1
while lo is less than hi:
    Details here are still to be decided
return lo
Don't attempt to keep track of the highest true so far using an additional variable. It's unnecessary, as we can simply shrink the search space down so 
that in the end, it only contains the final reachable value.

Step 4: What can we conclude when isReachable(mid) returns true?

If isReachable(mid) returns true, then we know that the building at mid is reachable. We're no longer interested in any of the buildings before mid, as
they can’t possibly be the final-reachable-building (as mid is further than them).

We don't know whether or not the building at mid + 1 is reachable, though, and nor should we check right now.

Remembering that lo and hi represent the boundaries of where the final-reachable-building could be, we should set lo = mid. This means that the building 
at mid is now the lowest building in the search range.

Setting lo = mid + 1 would be incorrect here because it’s possible that all buildings from mid + 1 onwards are not reachable, and that we’ve just chopped
the final-reachable-building out of the search space (remember, we want to shrink the search space down to a single index; the index of the 
final-reachable-building).

Step 5: What can we conclude when isReachable(mid) returns false?

If isReachable(mid) returns false, then we know that the building at mid is not reachable. This means that none of the buildings after mid could possibly
be reachable either. Therefore, the final reachable building must be before mid; in other words, the highest possible candidate now is mid - 1. So 
we should set hi = mid - 1.

A common mistake here is to simply use hi = mid so as to avoid having to reason about whether or not to put the - 1. This is very problematic, though, as 
whenever you’re doing a binary search with the while lo is less than hi condition (i.e., reducing the search space to length-one), you must have at least
one of hi = mid - 1 and lo = mid + 1. If you have neither of these, then your algorithm might infinitely loop once the search space is of length-two.

Updating our code with the latest details we've determined, here is what we have so far.

lo = 0
hi = heights.length - 1
while lo is less than hi:
    mid = [formula to be decided]
    if building at mid is reachable:
        lo = mid
    else:
        hi = mid - 1
return lo
Step 6: Which calculation for mid should we use?

On an odd-lengthed search space, identifying the midpoint is straightforward. On even-lengthed search spaces, though, we have two possible midpoints. 
The final step of the binary search algorithm design process is to decide whether it is the lower or higher midpoint that should be used.

Your decision should be based on how you are updating hi and lo (i.e., lo = mid and hi = mid - 1 for the algorithm we've designed here). Think about 
what happens once the search space is of length-two. You must ensure that the search space is guaranteed to be shrunk down to length-one, regardless of 
which condition is executed. If you take the lower middle, it will sometimes infinitely loop. And if you take the upper middle, it will be guaranteed to 
shrink the search space down to one.

So, it is the upper-middle that we want.

The short rule to remember is: if you used hi = mid - 1, then use the higher midpoint. If you used lo = mid + 1, then use the lower midpoint. If you 
used both of these, then you can use either midpoint. If you didn’t use either (i.e., you have lo = mid and hi = mid), then, unfortunately, your code is 
buggy, and you won’t be able to guarantee convergence.

Whenever we want the upper middle, we use either mid = (lo + hi + 1) / 2 or mid = lo + (hi - lo + 1) / 2. These formulas ensure that on even-lengthed 
search spaces, the upper middle is chosen and on odd-lengthed search spaces, the actual middle is chosen.

This completes our binary search algorithm.
lo = 0
hi = heights.length - 1
while lo is less than hi:
    mid = lo + (hi - lo + 1) / 2
    if building at mid is reachable:
        lo = mid
    else:
        hi = mid - 1
return lo
*/
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
/*
Intuition

The previous approach had some redundant sorting. We're now going to look very briefly at a neat trick we can do to remove this and reduce the time 
complexity down to O(N \log N)O(NlogN).

If we were to simply extract all the climbs, put them in a list, and sort them, then isReachable(...) would run into the problem of not knowing which 
climbs are within the relevant range and which are not. The solution to this is surprisingly simple, though: attach an index to each climb in the climbs
list. This way, isReachable(...) can iterate over the sorted list in the same fashion as before, but with an extra conditional telling it to skip any 
climbs with an index higher than the index of the building we're checking the reachability of.

Algorithm

At the start, the algorithm needs to make a sorted list of all the climbs. Each climb must have the index it reaches attached to it.

heights, bricks, and ladders are as specified in the problem

sorted_climbs = a new list
for each i between 0 and heights.length - 2 (inclusive):
    difference = heights[i + 1] - heights[i]
    if difference is positive:
        add pair(difference, i + 1) to sorted_climbs
min-sort sorted_climbs using the first value (climb distances) of each pair
The code above only runs once for each test case. sorted_climbs will be reused by all calls to isReachable(...).

While the binary search algorithm stays the same, the implementation of isReachable(...) needs to be changed slightly. We no longer need to build and 
sort a list of climbs within it. We should, instead, use sorted_climbs and add an additional check to skip any climbs that are beyond the index we're 
checking the reachability of.

heights, bricks, and ladders are as specified in the problem
sorted_climbs is the list we defined just above

define function isReachable(building_index):
    bricks_remaining = bricks
    ladders_remaining = ladders
    for each value in sorted_climbs:
        climb, index = split value into its two parts
        if index is greater than building_index:
            continue to the next iteration
        if bricks_remaining is at least climb:
            subtract climb from bricks_remaining
        else if ladders_remaining greater than 0:
            subtract 1 from ladders_available
        else:
            return false
     return true
You may have observed that once we've allocated a ladder, we'll never again allocate bricks (remember, the climbs are sorted. If we don't have enough 
bricks for the current climb, we definitely won't have enough for the climbs after it!). An alternate algorithm design could be to use two loops; one that
allocates bricks and then one that allocates ladders.
*/
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
/*
Note: This approach is almost certainly beyond what would be expected from you in an interview; the other approaches we’ve looked at should be 
sufficient. It is essentially a "hard" solution to a "medium" problem. In particular, to understand it, you'll need to be comfortable with mathematical
reasoning. Even though you don't need this approach for interviews, working through it will be beneficial to your general problem-solving skills. It is
a very cool approach, as it needs no auxiliary memory yet has a good time complexity.

Intuition and Algorithm

In this final approach, we're going to come up with a way of solving the problem with O(1)O(1) space and with a comparable time complexity. It involves 
binary searching for a threshold value representing the shortest climb we should allocate a ladder to (which is also the longest climb we should allocate
bricks to) and then examines the solution found to deduce whether it is optimal or whether the threshold was too high or too low. To really understand 
how and why this approach works, we need to explore and prove various mathematical properties of solutions. I've tried to keep the math as friendly as 
possible, but keep in mind that this is an advanced approach beyond the scope of interviews.

Characterizing an optimal solution

All of our approaches so far have used a strategy of allocating bricks to the shortest climbs and ladders to the longest climbs until no ladders or 
"usable" bricks remained. This guaranteed that at least one of the following conditions was true at the end.

We reached the final building.
We were not able to go any further, and there was no way of rearranging bricks or ladders so that we could go any further.
Let's quickly define and prove what we mean by "we were not able to go any further".

More precisely, each of the previous approaches guaranteed that all of the following conditions were true of an optimal solution (in cases where it was 
not possible to reach the final building).

There were no ladders remaining, and there were not enough bricks for the next climb.
The number of bricks remaining at the end was less than the length of the shortest climb to use a ladder.
The longest climb to use bricks was shorter than, or equal to, the shortest climb to use a ladder.
Intuitively, these all make sense. Condition 1 ensures that we don't have enough bricks or ladders left to simply do the next climb, condition 2 ensures 
that we can't use our remaining bricks to reclaim an earlier ladder and then use that to go further, and condition 3 ensures that we can't reclaim bricks
or a ladder by rearranging our earlier allocations. If any of these conditions weren't true, then there could be a way of doing an additional climb, thus 
contradicting the assertion that this solution is optimal.

Interestingly, the third condition isn't strictly necessary for a solution to be optimal. In some cases, there is more than one way of allocating bricks 
and ladders to get to the final-reachable-index. But there is always at least one optimal solution that does satisfy condition 3, and in fact, all of our 
approaches found the solution that satisfied it. Therefore, we're able to treat it as "strictly necessary" for our purposes, and as we'll see, this 
assumption is important for making sense of approach 5.

What we've convinced ourselves of so far is that all of these conditions are necessary for a solution to be optimal. What we haven't yet proven, though, 
is that they are sufficient for a solution to be optimal. In other words, if a solution meets these three conditions, can we safely conclude that it must
be an optimal solution?

When we're trying to prove (or at least informally convince ourselves) of the correctness of an algorithm, it is very important to not muddle necessity 
and sufficiency. A condition or group of conditions might be necessary for a solution to be optimal, but this doesn't always mean that those conditions 
are enough to conclude that the solution has to be optimal!

The easiest way to prove that it must be optimal is to use contradiction. In other words, we need to show that the claim of having a solution that is not 
optimal, yet meets all three of the conditions, would lead to a contradiction.

So, assume that you have a solution that satisfies all three of the above criteria, but it is not optimal.

Because our solution is not optimal, this means that there must exist a more optimal solution that can cover an additional climb. For starters, we know 
we don't have enough ladders or bricks remaining from our "not optimal" solution to cover that additional climb; otherwise, our "not optimal" solution 
would be violating condition 1.

This leaves only one possibility: we'd have to somehow cover the current climbs of our "non-optimal" solution using less ladders and bricks than we have.
After all, this is the only way we could possibly reclaim enough climbing implements to do that additional climb! Is it possible for us to rearrange the 
previous allocations to make this happen? We're going to look at two options: remove 1 ladder, and remove at least 1 brick.

We can't simply take a brick; this would mean a climb was no longer fully covered. In order to reclaim some bricks, we'd have to move a ladder to where 
they were. This would then require putting some of our reclaimed bricks where the ladder was. But if we were able to do that and have any bricks left 
over, then condition 3 would be violated: all of the ladder climbs are at least the length of all the brick climbs. Again, we get a contradiction.

Simply taking out a ladder for the next climb would leave an uncovered climb that we'd now have to cover with bricks. But we don't have enough bricks 
left over to do this; otherwise, our "not optimal" solution would violate condition 2, resulting in another contradiction. And like we determined just 
above, we can't reclaim any additional bricks with rearranging.

In conclusion, if we have a solution that meets the above three conditions, then it is not possible for there to exist a solution that does an additional
climb. This proves that a climb meeting the above 3 conditions is optimal.

Defining K: the threshold between ladder and brick climbs

Condition 3 from above is particularly interesting: the longest climb to use bricks is not greater than the shortest climb to use a ladder. The
implication of this is that for all optimal solutions identified by our previous approaches, there exists some threshold value, K, where the shortest
climb to use a ladder is equal to this K, and there is no climb greater than K that used bricks.

If we somehow knew K beforehand (we don't, but let's just follow this thought through), then coming up with the optimal solution could be done in linear 
time and constant space! Let's write an algorithm to do exactly this.

The main challenge in designing the algorithm is handling climbs of length K itself; some will be with bricks, and others with ladders. We'll start by
pretending that all climb lengths are unique, and as such, there will be at most one climb of length K, and this would be covered with a ladder.

heights, bricks, and ladders are as specified in the problem

define function solveWithGivenThreshold(K):
    
    ladders_remaining = ladders
    bricks_remaining = bricks
    
    for each i between 0 and building_index - 1 (inclusive):
    
        climb = heights[i + 1] - heights[i]
        if climb is not positive:
            continue (this is not a climb)
    
        if climb is greater than or equal to K:
            subtract 1 from ladders_remaining
        else:
            subtract climb from bricks_remaining
    
        if bricks_remaining or ladders_remaining is now negative:
            return i
    
    return heights.length - 1
The way that we can make it work with non-unique climb lengths is to allocate ladders to climbs of length K, but keep track of how many ladders were
allocated in this way. If we then come across a climb of length K when we only have bricks left, we should simply cover it with bricks. And if we come 
across a climb longer than K and are out of ladders, we should check if we used any ladders on K length climbs, and if so, attempt to replace that ladder
with bricks to reclaim it. Essentially, we are optimizing the use of ladders and bricks on this edge case.

heights, bricks, and ladders are as specified in the problem

define function solveWithGivenThreshold(K):
    
    ladders_remaining = ladders
    bricks_remaining = bricks
    ladders_assigned_on_threshold = 0
    
    for each i between 0 and building_index - 1 (inclusive):
        
        climb = heights[i + 1] - heights[i]
        if climb is not positive:
            continue (this is not a climb)
        
        if climb is greater than or equal to K:
            subtract 1 from ladders_remaining
            if climb is equal to K:
                ladders_assigned_on_threshold += 1
        else:
            subtract climb from bricks_remaining
        
        if ladders_remaining is now negative:
            if ladders_assigned_on_threshold is positive:
                subtract 1 from ladders_assigned_on_threshold
                add 1 to ladders_remaining
                subtract K from bricks_remaining
                [Note: if this made bricks_remaining negative, the next condition will catch it]
            else:
                return i
        
        if bricks_remaining is now negative:
            return i

    return heights.length - 1
Laying the foundations for a binary search on K

What is the range of values that this K could be?

Well, the shortest climb to use a ladder must be the shortest climb that can be extracted from the heights array. If K was this value, then potentially
all climbs would be using a ladder, other than possibly a few of length K itself that were using bricks.

The other extreme would be that we're not using ladders at all. In this case, the shortest climb to use a ladder would essentially be 1 more than the 
longest climb from the height array (in code, we can get away with just setting it to be the length of the longest climb; the optimization around K 
itself will cover the possibility of no ladders being used on K).

With this, we could do a linear search until we find a solution that is optimal, based on our definition above of an optimal solution. This linear search 
wouldn't work very well; the possible range in climb length is huge! In the worst case, it will be equal to the maximum height in heights, and according 
to the problem description, that is a very big number!

As I said earlier in this article, when linear search is too slow, consider using binary search!

A binary search would work by identifying the maximum possible value for K, the lowest possible value, and then cutting out half of the possible values
on each iteration.

We've already figured out a way of knowing if a solution is optimal, but in the case that it's not, how do we know whether the value we tried was too
high or too low? It turns out there is a way, and that's what we're going to look at next.

Identifying when mid is greater than K

If mid > K, then it turns out that either we'd reach the final building regardless, or we'd have at least one ladder left over. But why?

Well, the optimal solution, whatever it is, makes CC climbs. Additionally, we know that LL of those climbs have to be with ladders (remember, optimal 
solutions that don't reach the end have to use all of the ladders!). These ladders were allocated to all climbs greater than K, and at least one climb 
equal to K. If mid was not optimal, then it has to have made a subset of those CC climbs (i.e., it can't have covered any climbs that were beyond the
optimal solution's final-reachable-building). Of that subset, it only put ladders on the climbs greater than K, which is strictly a smaller number of 
climbs than the optimal solution put ladders on. Therefore, there has to be at least one ladder left over at the end.

So, if there is at least one ladder left over, then we know that mid is too high.

Identifying when mid is less than K

If mid < K, then either we'd reach the final building regardless, or we'd have a non-optimal solution that used all of the ladders.

By setting mid below K, we're essentially going to be wasting a lot of ladders on climbs that the optimal solution would have used bricks for. Remember 
that our not-optimal solution could only make a subset of the CC climbs that the optimal solution must make. There are enough bricks to get to the optimal
solution's final-reachable-index using a higher threshold, and so our lower threshold definitely won't run out of bricks. So, it will be running out of 
ladders that causes it to fail to go all of the way.

Putting everything together

You now have all the ingredients you need to assemble the code for this algorithm.

Firstly, we should modify the solveWithGivenThreshold function so that it returns a 2D array with 3 values: the index reached, ladders remaining, and
bricks remaining. Secondly, we need to write the binary search. It needs to interpret the return value from solveWithGivenThreshold to know how to proceed 
with the search. Keep in mind: this algorithm is not allowed to use auxiliary memory!

define function furthestBuilding(heights, bricks, ladders):

    lo = find the minimum climb 
    hi = find the maximum climb
    if lo and hi are undefined (there was no climbs):
        return heights.length - 1

    while lo is not greater than hi:
        mid = lo + (hi - lo) / 2
        result = solveWithGivenThreshold(mid)
        index_reached, ladders_remaining, bricks_remaining = unpack result
        if index_reached is heights.length - 1:
            return heights.length - 1
        if ladders_remaining is not zero:
            hi = mid - 1
        else if the climb from index_reached to index_reached + 1 could be covered
                with the bricks_remaining, or bricks_remaining was at least K:
            lo = mid + 1
        else:
            return index_reached

To help you visualize what this algorithm is doing, here's an animation. To keep the focus on the core algorithm details, we've made the assumption that 
the climbs are all unique (and therefore, we don't need to do the optimizing around K itself).

Note that the bars represent climbs, not heights. Also, the actual algorithm won't explicitly make a climb list; we refrain from doing this as that would
require auxiliary memory.
*/
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
