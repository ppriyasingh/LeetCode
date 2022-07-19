package com.atlassian;

import java.util.*;
import java.util.Map.Entry;

public class VoteManagement {

    /**
     * For a list of votes, return an ordered set of candidate in descending order of their votes.
     * Sample1:
     * ABC
     * 321
     * O/P: ABC
     *
     * Sample 2:
     * ABC  CAD
     * 321  321
     * O/P: ACBD (5421)
     *
     *
     * A - 3+2=5
     * B - (maxLen-index) - 2
     * C - 1+3 = 4
     * D - 1
     *
     *      1   2   3
     * A    1   0   4
     * B    0   1   0
     * C    1   1   0
     * D    0   0   1
     */
    private Map<Character, Integer> sortByComparator(Map<Character, Integer> unsortMap, final boolean order)
    {

        List<Entry<Character, Integer>> list = new LinkedList<Entry<Character, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Character, Integer>>()
        {
            public int compare(Entry<Character, Integer> o1,
                               Entry<Character, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
        for (Entry<Character, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    List<Character> findWinner(List<String> votes) {
        List<Character> result = new LinkedList<>();
        Map<Character, Integer> countScore = new HashMap<>();
        int MAX_VOTE = 3;

        for(int i=0; i<votes.size(); i++) {
            String candidates = votes.get(i);

            for(int index =0; index < candidates.length(); index++) {
                char candidate = candidates.charAt(index);
                countScore.put(candidate, countScore.getOrDefault(candidate, 0 ) + (MAX_VOTE - index));
            }
        }

        Map<Character, Integer> sortedCountScore = sortByComparator(countScore, false);

        for(char candidate: sortedCountScore.keySet()) {
            result.add(candidate);
        }
        return result;
    }
    public static void main(String[] args) {
        VoteManagement mainObj = new VoteManagement();
        List<String> input = new LinkedList<>();
        input.add("ACB");
        input.add("CAD");

        /**
         *  A - 5
         *  C - 5
         *  B - 1
         *  D - 1
         *
         *
         *
         *  1   - C -> A -> B -> D
         *  2   -
         *  3   -
         *  4
         *  5   -
         *
         *  n-len of all candidates
         */

        List<Character> output = mainObj.findWinner(input);
        System.out.println(output);
    }
}
