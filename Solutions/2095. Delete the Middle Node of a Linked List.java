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
    public ListNode getPrevMiddle(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode slow = head, fast = head, prev = null;
        
        while(fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return prev;
    }
    public ListNode deleteMiddle(ListNode head) {
        if(head == null || head.next == null) return null;
        
        ListNode prevMiddle = getPrevMiddle(head);
        prevMiddle.next = prevMiddle.next.next;
        return head;
    }
}
