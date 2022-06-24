// Approach 0: Sort
/*
The naive solution would be to sort an array first and then return kth element from the end, something like sorted(nums)[-k] on Python. That would be an 
algorithm of O(NlogN) time complexity and O(1) space complexity. This time complexity is not really exciting so let's check how to improve it by using 
some additional space.
*/

// Approach 1: Heap
class Solution {
  // T(O): O(N log k)
  // S(O): O(k), to store the heap elements.
    public int findKthLargest(int[] nums, int k) {
        if(nums.length ==0) return 0;
        Queue<Integer> pq = new PriorityQueue<>((a,b)-> a-b);
        
        for(int x: nums) {
            pq.add(x);
            if(pq.size() > k) pq.poll();
        }
        return pq.poll();
    }
}

// Approach 2: Quickselect
/*
EVERYBODY IS GONNA DO IN O(N log k) times, BETTER STAND OUT AND KNOW QUICKSELECT
This textbook algorthm has O(N) average time complexity. Like quicksort, it was developed by Tony Hoare, and is also known as Hoare's 
selection algorithm.

The approach is basically the same as for quicksort. For simplicity let's notice that kth largest element is the same as N - kth smallest element, hence 
one could implement kth smallest algorithm for this problem.
First one chooses a pivot, and defines its position in a sorted array in a linear time. This could be done with the help of partition algorithm.
To implement partition one moves along an array, compares each element with a pivot, and moves all elements smaller than pivot to the left of the pivot.

As an output we have an array where pivot is on its perfect position in the ascending sorted array, all elements on the left of the pivot are smaller than 
pivot, and all elements on the right of the pivot are larger or equal to pivot.
Hence the array is now split into two parts. If that would be a quicksort algorithm, one would proceed recursively to use quicksort for the both parts 
that would result in O(NlogN) time complexity. Here there is no need to deal with both parts since now one knows in which part to search for N - kth 
smallest element, and that reduces average time complexity to O(N).

Finally the overall algorithm is quite straightforward :

1. Choose a random pivot.
2. Use a partition algorithm to place the pivot into its perfect position pos in the sorted array, move smaller elements to the left of pivot, and larger or 
equal ones - to the right.
3. Compare pos and N - k to choose the side of array to proceed recursively.
! Please notice that this algorithm works well even for arrays with duplicates.
*/
class Solution {
  // T(O): O(N), in the average case, O(N^2) in the worst case
  // S(O): O(1)
    public void swap(int[] nums, int i, int j) {
        int temp= nums[i];
        nums[i]= nums[j];
        nums[j]= temp;
    }
    public int partition(int[] nums, int l, int r) {
        if(l==r) return l;
        int pivot= nums[r];
        int j=l-1;
        
        for(int i=l; i<r; i++) {
            if(nums[i] < pivot) {
                j++;
                swap(nums, i, j);
            }
        }
        j++;
        swap(nums, r, j);
        return j;
    }
    public int quickselect(int[] nums, int i, int j, int k) {
        if(i == j) return nums[i];
        
        int partition = partition(nums, i,j);
        if(partition == k) return nums[partition];
        else if(partition > k) return quickselect(nums, i, partition-1, k);
        return quickselect(nums, partition+1, j, k);
    }
    public int findKthLargest(int[] nums, int k) {
        if(nums.length ==0) return 0;
        return quickselect(nums, 0, nums.length-1, nums.length-k);
    }
}
