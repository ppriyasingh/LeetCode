// Solution 1: Recursive
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
    public ListNode reverseList(ListNode start, ListNode end) {
        ListNode prev = null, curr = start, next = curr.next;
        while(curr != end) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        curr.next = prev;
        return end;
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null) return null;
        
        ListNode curr = head;
        ListNode dummy = new ListNode(-1);
        ListNode nextGroupHead;
        int c = 1;
        while(c != k && curr != null) {
            curr = curr.next;
            c++;
        }
        
        if(curr == null) return head;
        
        nextGroupHead = curr.next;
        dummy.next = reverseList(head, curr);
        head.next = reverseKGroup(nextGroupHead, k);
        return dummy.next;
    }
}

// Solution 1: Iterative
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        
        int count=0;
        ListNode track= head;
        while (track != null) {
            count++;
            track= track.next;
        }
        
        if (count < k) return head;
        
        ListNode dummy= new ListNode(Integer.MIN_VALUE);
        dummy.next= head;
        
        ListNode prev= dummy, cur= head;
        ListNode predecessor= prev, reversedTail= predecessor.next;
        
        for (int i=0; i<(count/k); i++) {
            prev= cur;
            cur= cur.next;

            ListNode tmp;
            for (int indx=1; indx<k; indx++) {
                tmp= cur.next;
                cur.next= prev;
                prev= cur;
                cur= tmp;
            }

            ListNode reversedHead= prev, successor= cur;

            predecessor.next= reversedHead;
            reversedTail.next= successor;

            predecessor= reversedTail; reversedTail= predecessor.next;
        }
        
        return dummy.next;
    
    }
}

// Solution 3:
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy= new ListNode(0);
        dummy.next= head;
        ListNode prev= dummy, curr= dummy, next= dummy;
        int c=0;
        while(head!=null) {
            head=head.next;
            c++;
        }
        while(c>=k) {
            curr= prev.next;
            next= curr.next;
            for(int i=1; i<k; i++) {
                curr.next= next.next;
                next.next= prev.next;
                prev.next = next;
                next= curr.next;
            }
            prev = curr;
            c -= k;
        }
        return dummy.next;
    }
}
