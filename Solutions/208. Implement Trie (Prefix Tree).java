class Trie {

    HashMap<String, Integer> m;
    /** Initialize your data structure here. */
    public Trie() {
        m= new HashMap<>();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        m.put(word,1);
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        return m.containsKey(word);
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        for(String x: m.keySet()){
            if(x.startsWith(prefix)) return true;
        }
        
        return false;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */