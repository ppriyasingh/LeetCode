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
 
 /*  Solution l  */
 
 
 class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode((l1.val+l2.val)%10);
        ListNode head = l3;
        int carry = (l1.val+l2.val)/10;
        l1 = l1.next;
        l2 = l2.next;
        
        while(l1!=null || l2!=null){
            
            if(l1!=null && l2!=null) {
                int sum = carry+l1.val+l2.val;
                l3.next = new ListNode(sum%10);
                carry = sum/10;
                l1 = l1.next;
                l2 = l2.next;
                
            } else if(l1!=null) {
                
                int sum = carry+l1.val;
                l3.next = new ListNode(sum%10);
                carry = sum/10;
                l1 = l1.next;
            } else {
                
                int sum = carry+l2.val;
                l3.next = new ListNode(sum%10);
                carry = sum/10;
                l2 = l2.next;
            }
            
            l3= l3.next;
        } 
        
        if(carry>0) {
            l3.next = new ListNode(carry);
        }
        
        return head;
    }
}
 
 
 
 
/*
Solution ll
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode((l1.val+l2.val)%10);
        ListNode head = l3;
        int carry = (l1.val+l2.val)/10;
        l1 = l1.next;
        l2 = l2.next;
        
        while(l1!=null || l2!=null){
            int sum =0, a, b;
            a = l1!=null?  l1.val:0;
            b = l2!=null? l2.val:0;
            
            sum = a+b+carry;
            l3.next = new ListNode(sum%10);
            carry= sum/10;
            if(l1!=null) l1= l1.next;
            if(l2!=null) l2= l2.next;
            
            l3= l3.next;
        } 
        
        if(carry>0) {
            l3.next = new ListNode(carry);
        }
        
        return head;
    }
}
*/