/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
//     public Node flattenUtil(Node head) {
//         if(head == null) return head;
        
//         if(head.child==null) {
//             if(head.next == null) return head;
//             return flattenUtil(head.next);
//         }
//         else {
//             Node child = head.child;
//             head.child = null;
//             Node next = head.next;
//             Node childtail = flattenUtil(child);
//             head.next = child;
//     		child.prev = head;  
// 			if (next != null) { // CASE 5
// 				childtail.next = next;
// 				next.prev = childtail;
// 				return flattenUtil(next);
// 			}
//             return childtail; // CASE 4
//         }
//     }
    public Node flatten(Node head) {
        if(head == null)  return head;
        
        Node temp = head;
        while(temp != null) {
            
            if( temp.child!= null) {
                Node next = temp.next;
                Node child = flatten(temp.child);
                temp.child=null;
                temp.next = child;
                child.prev = temp;
                while(child.next!=null) {
                    child=child.next;
                }
                child.next = next;
                if(next!=null)
                    next.prev = child;  
                temp = child;
            }
            temp = temp.next;
        }
        return head;
    }
}