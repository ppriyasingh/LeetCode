// Solution 1:
class Solution {
  // T(O): O(n)
  // S(O): O(n)
    public void reorderList(ListNode head) {
        Stack<ListNode> stk = new Stack<>();
        ListNode curr = head;
        
        while(curr != null) {
            stk.push(curr);
            curr = curr.next;
        }
        curr = head;
        
        while(curr.next != stk.peek() && curr != stk.peek()) {
            ListNode next = curr.next;
            curr.next = stk.pop();
            curr = curr.next;
            curr.next = next;
            curr = curr.next;
        }
        if(curr == stk.peek()) curr.next = null;
        else curr.next.next = null;
    }
}

// Solution 2:
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
  // T(O): O(n)
  // S(O): O(1)
    public ListNode getMiddle(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode slow = head, fast = head;
        
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    public ListNode reverse(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode curr = head;
        ListNode prev = null, next = curr.next;
        
        while(curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) return;
        
        ListNode mid = getMiddle(head);
        ListNode second = reverse(mid.next);
        
        mid.next = null;
        ListNode nextCurr, nextSecond;
        
        while(head.next!=null && head!=null && second!=null) {
            nextSecond = second.next;
            nextCurr = head.next;
            
            head.next = second;
            second.next = nextCurr;
            
            head = nextCurr;
            second = nextSecond;
        }
    }
}

// Recursive solution
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
    // T(O): O(n)
    // S(O): O(1)
    ListNode left;
    
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) return;
        left = head;
        
        reorderListUtil(head);
    }
    public void reorderListUtil(ListNode right) {
        if(right == null) return;
        reorderListUtil(right.next);
        
        if(left == null) return;
        
        if(left != right && left.next != right) {
            
            ListNode next = left.next;
            left.next = right;
            right.next = next;
            left = next;
        } else {
            if(left.next == right) {
                right.next = null; 
                left = null;
            } else {
                left.next = null; 
                left = null;
            }
        }
    }
}
