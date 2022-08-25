/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(head == null || head.next == null) return head;
        
        ListNode curr = head, start = head;
        int pos = 1;
        
        while(pos < left) {
            start = curr;
            curr = curr.next;
            pos++;
        }
        ListNode tail = curr, subList = null;
        
        while(pos >= left && pos <= right) {
            ListNode next = curr.next;
            curr.next = subList;
            subList = curr;
            curr = next;
            pos++;
        }
        
        start.next = subList;
        tail.next = curr;
        
        if(left > 1) return head;
        return subList;
    }
}
