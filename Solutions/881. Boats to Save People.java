class Solution {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);        // O(nlogn)
        
        int i=0, j= people.length-1, boatCount=0;
        
        while(i<=j) {
            
            if(people[i]+people[j]<=limit)
                i++;
            
            j--;
            
            boatCount++;
        }
        
        return boatCount;
    }
}