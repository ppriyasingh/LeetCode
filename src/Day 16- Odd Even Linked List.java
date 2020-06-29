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
    public ListNode oddEvenList(ListNode head) {
        if(head==null) return head;
        
        ListNode evenp= head.next, oddp=head, eHead= evenp;
        
        
        while(oddp!=null && oddp.next!=null && evenp!=null && evenp.next!=null) {
                oddp.next= oddp.next.next;
                oddp= oddp.next;
                evenp.next= evenp.next.next;
                evenp= evenp.next;
        }
        
        oddp.next= eHead;
            
        return head;
        
    }
}