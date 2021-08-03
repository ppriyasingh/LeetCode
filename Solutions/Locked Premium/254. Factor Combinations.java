/**
Numbers can be regarded as the product of their factors.

For example, 8 = 2 x 2 x 2 = 2 x 4.
Given an integer n, return all possible combinations of its factors. You may return the answer in any order.

Note that the factors should be in the range [2, n - 1].

Input: n = 1
Output: []

Input: n = 12
Output: [[2,6],[3,4],[2,2,3]]

Input: n = 37
Output: []

Input: n = 32
Output: [[2,16],[4,8],[2,2,8],[2,4,4],[2,2,2,4],[2,2,2,2,2]]
*/

// better explained: https://medium.com/@hazemu/finding-the-median-of-2-sorted-arrays-in-logarithmic-time-1d3f2ecbeb46

//Solution 1: 
class Solution {
    public List<List<Integer>> getFactors(int n) {
         List<List<Integer>> res= new ArrayList<>();
        if (n <= 3)
            return res;    

        return dfs(res, new ArrayList<>(), n, 2);
    }

    private void dfs(List<List<Integer>> res, List<Integer> list,  int n, int index) {

        if(n==1) {
                    if(list.size()>1) res.add(list);
                    return;
                }
                for(int i=index; i<=n; i++){
                    if(n%i==0) {
                        list.add(i);
                        dfs(res, list, n/i, i);
                        list.remove(list.size()-1);
                    }
                }
    }
}

// Solution 2:
public class Solution {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    public List<List<Integer>> getFactors(int n) {
        getFactors(n, new ArrayList<Integer>(), 2);
        return result;
    }
    public void getFactors(int n, List<Integer> iList, int st){
        for(int i = st; i * i <= n; ++i){
            if(n % i == 0){
                iList.add(i);
                iList.add(n/i);
                result.add(new ArrayList<Integer>(iList));
                iList.remove(iList.size()-1);
                getFactors(n/i, iList, i);
                iList.remove(iList.size()-1);
            }
        }
    }
}