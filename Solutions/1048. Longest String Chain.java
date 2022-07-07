// Solution 1:
class Solution {
    public int longestStrChain(String[] words) {
        int ans = 0;
        Set<String> set = new HashSet<>();
        Map<String, Integer> map = new HashMap<>();
        for(String word : words) set.add(word);
        for(String word : words) ans = Math.max(ans, helper(map, set, word));
        return ans;
    }
    
    private int helper(Map<String, Integer> map, Set<String> set, String word){
        if(map.containsKey(word)) return map.get(word);
        int cnt = 0;
        for(int i = 0; i < word.length(); i++){
            String next = word.substring(0, i) + word.substring(i+1);
            if(set.contains(next)){
                cnt = Math.max(cnt, helper(map, set, next));
            }
        }
        map.put(word, 1 + cnt);
        return 1 + cnt;
    }
}

// Solution 2:
class Solution {
    // T(O): N*LogN + N*L*L
    // S(O): words.length
    public int longestStrChain(String[] words) {
        if(words.length==0) return 0;
        // n(logn)
        Arrays.sort(words, (a,b) -> a.length()- b.length());
        Map<String, Integer> freq= new HashMap<>();
        int ans=1;
        
        // n
        for(String w: words) {
            int best=1;
            // L
            for(int i=0; i<w.length(); i++) {
                //L
                String temp= w.substring(0,i)+w.substring(i+1);
                if(freq.containsKey(temp)) {
                    best= Math.max(best, freq.get(temp)+1);
                }
            }
            freq.put(w,best);
            ans= Math.max(ans, best);
        }
        return ans;
    }
}

// Solution 3:
class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a,b)->a.length()-b.length());
        Map<String, Integer> m= new HashMap<>();
        int level=0;
        
        for(String word:words) {
            
            int max=0;
            for(int i=0; i<word.length(); i++) {
                String prev= word.substring(0,i)+word.substring(i+1);
                max= Math.max(max, m.getOrDefault(prev,0)+1);
            }
            m.put(word, max);
            level= Math.max(level, max);
        }
        
        return level;
    }
}
