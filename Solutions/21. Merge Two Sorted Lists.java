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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(-1), current = dummyHead;
        while(l1!=null && l2!=null){
            if(l1.val <= l2.val) {
                current.next = l1;
                l1= l1.next;
            } else {
                current.next = l2;
                l2= l2.next;
            }
            current= current.next;
        }
        
        if(l1!=null) current.next = l1;
        if(l2!=null) current.next = l2;

        
        return dummyHead.next;
    }
}

class Solution {
    public void mergeTwoListsUtil(ListNode l1, ListNode l2, ListNode c){
        if(l1==null && l2==null)
            return;
        else if(l1!=null && l2==null) {
            c.next=l1;
            return;
        }else if(l1==null && l2!=null) {
            c.next=l2;
            return;
        } else if(l1.val<l2.val) {
            c.next=l1;
            mergeTwoListsUtil(l1.next,l2,c.next);
        } else {
            c.next=l2;
            mergeTwoListsUtil(l1,l2.next,c.next);
        }
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(-1), c = dummyHead;
        
        mergeTwoListsUtil(l1,l2,c);
        return dummyHead.next;
    }
}