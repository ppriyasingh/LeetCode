class Solution {
    public int numberOfSteps (int num) {
        
		/*
			Time complexity: O(n)
			Space complexity: O(n)
		*/
		
        if(num == 0)
            return 0;
        if(num%2 == 0)
            return 1 + numberOfSteps(num/2);
        else return 1 + numberOfSteps(num-1);
    }
}