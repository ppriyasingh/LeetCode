class Solution {
    public int[] prisonAfterNDays(int[] cells, int N) {
        
        Set<String> m = new HashSet<>();
        int cycle=0, len = cells.length;
        boolean hasCycle = false;
        
        for(int j= 1; j<= N; j++) {
            int[] next = nextDay(cells);           
            
            if(m.contains(Arrays.toString(next))) {
                hasCycle = true;                
                break;
            }
            
            m.add(Arrays.toString(next));
            cycle++;            
            cells = next.clone();
        }
        
        if(hasCycle) {            
            N %= cycle;
            for(int j= 1; j<= N; j++) {
                cells = nextDay(cells);                 
            }
        }
        return cells;
    }
    public int[] nextDay(int[] cells) {
        
        int[] temp = new int[cells.length];        
        for(int i=1; i< cells.length-1; i++ ) {
            temp[i] = (cells[i-1] == cells[i+1])? 1: 0;
            temp[0]= temp[cells.length-1]= 0;
        }
        return temp;
    }
}