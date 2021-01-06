class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null || head.next==null) {
            return head;
        }
        
        if(head.val == head.next.val) {
            ListNode h = head, c = head;
            while(c!=null && h.val == c.val)
                c=c.next;
            
            return deleteDuplicates(c);
        } else {
            head.next = deleteDuplicates(head.next);
        }
        return head;
    }
}