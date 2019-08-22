《剑指offer》面试题22：代码中倒数第k个节点

题目：输入一个单链表，输出该链表中倒数第k个结点。

思路：定义2个指针，第一个指针先从链表头部开始走k-1步，第二个再从链表头部和第一个指针以相同速度走，当第一个指针走到链表尾部时，第二个指针的位置就是倒数第k个节点。

代码如下：
```
// 节点类
public class ListNode {
    int val;
    ListNode next;
    public ListNode(int val) {
        this.val = val;
    }
}

// 实现方法
public ListNode FindKthToTail(ListNode head,int k) {
    if (head == null || k <= 0) {
        return null;
    }
    ListNode node1 = head;
    for (int i = 0;i < k - 1;i++) {
        // 链表中节点个数小于k
        if (node1.next == null) {
            return null;
        }
        node1 = node1.next;
    }
    ListNode node2 = head;
    while (node1.next != null) {
        node1 = node1.next;
        node2 = node2.next;
    }
    return node2;
}
```
相关题目：求链表的中间节点。如果链表中的节点总数为奇数，则返回中间节点；如果节点总数是偶数，则返回中间两个节点的任意一个。

思路：定义2个指针，同时从链表头节点出发，一个指针一次走一步，另一个指针一次走两步，当走一步的指针走到链表的尾部时，走两步的指针正好在链表中间。

代码如下：
```
public ListNode FindMiddleNode(ListNode head) {
    if (head == null) {
        return null;
    }
    ListNode node1 = head;
    ListNode node2 = head;
    while (node2.next != null && node2.next.next != null) {
        node1 = node1.next;
        node2 = node2.next.next;
    }
    return node1;
}
```
