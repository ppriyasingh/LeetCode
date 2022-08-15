// Solution 1: BFS
class Solution {
    
    // Using Pair Java Class
    // T(O): The BFS runs at O(target * log(target)) in the worst case, with O(target * log(target)) space. The reasoning is as follows: in the worst case,
    // all positions in the range [-target, target] will be visited and for each position there can be as many as 2 * log(target) different speeds.
    // S(O): 2^N
    public int racecar(int target) {
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        int minimumSeqOfInstruction = 0;
        Pair<Integer, Integer> pair = new Pair(0, 1);
        q.add(pair);
        visited.add(pair.toString());
        
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                Pair<Integer, Integer> car = q.poll();
                
                if(car.getValue() + car.getKey() == target) return minimumSeqOfInstruction+1;
                
                // "A"
                int nextPosition = car.getKey() + car.getValue();
                int nextSpeed = 2 * car.getValue();
                Pair<Integer, Integer> nextPair = new Pair(nextPosition, nextSpeed);
                if(!visited.contains(nextPair.toString()) && Math.abs(target-nextPosition) < target) {
                    visited.add(nextPair.toString());
                    q.add(nextPair);
                }
                
                // "R"
                nextPosition = car.getKey();
                nextSpeed = car.getValue() > 0 ? -1 : 1;
                nextPair = new Pair(nextPosition, nextSpeed);
                if(!visited.contains(nextPair.toString()) && Math.abs(target-nextPosition) < target) {
                    visited.add(nextPair.toString());
                    q.add(nextPair);
                }
            }
            minimumSeqOfInstruction++;
        }
        
        return -1;
    }
}

// Solution 2: BFS
public class CarAttributes {
    public int position;
    public int speed;

    public CarAttributes(int position, int speed) {
        this.position = position;
        this.speed = speed;
    }
}
class Solution {
    
    // Using Custom Java Class
    // T(O): The BFS runs at O(target * log(target)) in the worst case, with O(target * log(target)) space. The reasoning is as follows: in the worst case,
    // all positions in the range [-target, target] will be visited and for each position there can be as many as 2 * log(target) different speeds.
    // S(O): 2^N
    public int racecar(int target) {
        
        Queue<CarAttributes> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        int minimumSeqOfInstruction = 0;
        q.add(new CarAttributes(0, 1));
        visited.add("0"+":"+"1");
        
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                CarAttributes car = q.poll();
                
                if(car.position == target) return minimumSeqOfInstruction;
                
                // "A"
                int nextPosition = car.position + car.speed;
                int nextSpeed = 2*car.speed;
                if(!visited.contains(nextPosition + ":" + nextSpeed) && Math.abs(target-nextPosition) < target) {
                    visited.add(nextPosition + ":" + nextSpeed);
                    q.add(new CarAttributes(nextPosition, nextSpeed));
                }
              
                // "R"
                nextPosition = car.position;
                nextSpeed = car.speed > 0 ? -1 : 1;
                if(!visited.contains(nextPosition + ":" + nextSpeed) && Math.abs(target-nextPosition) < target) {
                    visited.add(nextPosition + ":" + nextSpeed);
                    q.add(new CarAttributes(nextPosition, nextSpeed));
                }
            }
            minimumSeqOfInstruction++;
        }
        
        return -1;
    }
}

// Solution 3: BFS
/*
Minor modifications leading to enhanced complexity:
1. use Array instead of custom Class, Pair or List as it is fastest
2. use <<1 instead of *2 as it is on bit level
*/
class Solution {
   // T(O): The BFS runs at O(target * log(target)) in the worst case, with O(target * log(target)) space. The reasoning is as follows: in the worst case,
    // all positions in the range [-target, target] will be visited and for each position there can be as many as 2 * log(target) different speeds.
    // S(O): 2^N
  
    public int racecar(int target) {
        Queue<int[]> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        int minimumSeqOfInstruction = 0;
        q.add(new int[]{0,1});
        visited.add("0"+":"+"1");
        
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                int[] car = q.poll();
                
                if(car[0] + car[1] == target) return minimumSeqOfInstruction+1;
                
                // "A"
                int nextPosition = car[0] + car[1];
                int nextSpeed = car[1] << 1;
                String key = nextPosition + ":" + nextSpeed;
                if(!visited.contains(key) && nextPosition>0 && nextPosition < (target<<1)) {
                    visited.add(key);
                    q.add(new int[]{nextPosition, nextSpeed});
                }
                
                // "R"
                nextPosition = car[0];
                nextSpeed = car[1] > 0 ? -1 : 1;
                key = nextPosition + ":" + nextSpeed;
                if(!visited.contains(key) && nextPosition>0 && nextPosition < (target<<1)) {
                    visited.add(key);
                    q.add(new int[]{nextPosition, nextSpeed});
                }
            }
            minimumSeqOfInstruction++;
        }
        return -1;
    }
}

// Solution 4: Modified Solution to extended question to print the 'sequence Of Instructions' as well
public class CarAttributes {
    public int position;
    public int speed;
    public String seqOfInstruction;

    public CarAttributes(int position, int speed) {
        this.position = position;
        this.speed = speed;
        seqOfInstruction = new String();
    }
    
    public CarAttributes(int position, int speed, String seqOfInstruction) {
        this.position = position;
        this.speed = speed;
        this.seqOfInstruction = seqOfInstruction;
    }
}
class Solution {
    
    public int racecar(int target) {
        
        Queue<CarAttributes> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        int minimumSeqOfInstruction = 0;
        q.add(new CarAttributes(0, 1));
        visited.add("0"+":"+"1");
        
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                CarAttributes car = q.poll();
                
                if(car.position == target) {
                    System.out.println(car.seqOfInstruction);
                    return minimumSeqOfInstruction;
                }
                
                // "A"
                int nextPosition = car.position + car.speed;
                int nextSpeed = 2*car.speed;
                if(!visited.contains(nextPosition + ":" + nextSpeed) && Math.abs(target-nextPosition) < target) {
                    visited.add(nextPosition + ":" + nextSpeed);
                    String nextSeqOfInstruction = car.seqOfInstruction +"A";
                    q.add(new CarAttributes(nextPosition, nextSpeed, nextSeqOfInstruction));
                }
                // "R"
                nextPosition = car.position;
                nextSpeed = car.speed > 0 ? -1 : 1;
                if(!visited.contains(nextPosition + ":" + nextSpeed) && Math.abs(target-nextPosition) < target) {
                    visited.add(nextPosition + ":" + nextSpeed);
                    String nextSeqOfInstruction = car.seqOfInstruction +"R";
                    q.add(new CarAttributes(nextPosition, nextSpeed, nextSeqOfInstruction));
                }
            }
            minimumSeqOfInstruction++;
        }
        
        return -1;
    }
}

// Solution 5: Recursion
class Solution {
    // Throws StackOverFlow or TLE
    // NOT RECOMMENDED
    public int racecarUtil(int pos, int speed, int target, int steps) {
        if(speed > target<<1) return -1;
        if(pos > target<<1) return -1;
        if(pos == target) return steps;

        // "A"
        int choosenA = racecarUtil(pos+speed, speed<<1, target, steps+1);
        
        //"R"
        int choosenR = -1;
        if((pos+speed > target && speed > 0) || (pos+speed < target && speed< 0))
            choosenR = racecarUtil(pos, (speed>0? -1: 1), target, steps+1);
        
        if(choosenA == -1 && choosenR == -1) return -1;
        else if(choosenA == -1) return choosenR;
        else if(choosenR == -1) return choosenA;
        
        return Math.min(choosenA, choosenR);
    }
    public int racecar(int target) {
        return racecarUtil(0, 1, target, 0);
    }
}
