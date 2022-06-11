// Approach 1:
class Trie {
    Trie[] children;
    boolean we;
    int charNumber = 26;
    
    Trie() {
        children = new Trie[charNumber];
        we = false;
    }
    
    public Trie get(char ch) { return children[ch-'a']; }
    public boolean isPresent(char ch) { return children[ch-'a'] != null; }
    public void put(char ch, Trie tr) { children[ch-'a'] = tr; }
    public boolean isEnd() { return this.we; }
    public void setEnd() { this.we = true; }
}
class WordDictionary {

    Trie root;
    public WordDictionary() {
        root = new Trie();
    }
    
    public void addWord(String word) {
        // T(O): M, where M is the length of the word
        // S(O): M, where M is the length of the word
        Trie node = root;
        
        for(char c: word.toCharArray()) {
            if(!node.isPresent(c)) node.put( c, new Trie());
            node = node.get(c);
        }
        node.setEnd();
    }
    
    public boolean search(String word, Trie r) {
        // T(O): N*26^M, where M is the length of the word and N is number of words searched
        // S(O): 1
        if(r == null) return false;
        if(word.length() == 0) return r.isEnd();
        
        Trie node = r;
        
        for(int j=0; j<word.length(); j++) {
            char c= word.charAt(j);
            
            if(c == '.') {
                boolean subRes= false;
                for(int i=0; i<26; i++) {
                    if( node.children[i] != null) subRes = search(word.substring(j+1), node.children[i]);
                    if(subRes) return true;
                }
                return false;
            }
            else if(node.isPresent(c)) node = node.get(c);
            else return false;
        }
        return node.isEnd();
    }
    
    public boolean search(String word) {
        return search(word, root);
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
 
