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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode newHead = new ListNode(-1, head);
        ListNode prev = newHead, curr = head;
        
        while(n > 0) {
            curr = curr.next;
            n--;
        }
        
        while(curr != null) {
            curr = curr.next;
            prev = prev.next;
        }
        if(prev.next != null) prev.next = prev.next.next;
        
        return newHead.next;
    }
}
