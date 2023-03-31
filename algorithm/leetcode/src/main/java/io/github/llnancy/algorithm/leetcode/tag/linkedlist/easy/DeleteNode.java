package io.github.llnancy.algorithm.leetcode.tag.linkedlist.easy;

import io.github.llnancy.algorithm.common.SinglyLinkedListNode;
import io.github.llnancy.algorithm.common.util.LinkedListUtils;

/**
 * 面试题 02.03. 删除中间节点
 * <p>
 * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
 * <p>
 * 示例：
 * <p>
 * 输入：单向链表a->b->c->d->e->f中的节点c
 * 结果：不返回任何数据，但该链表变为a->b->d->e->f
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/delete-middle-node-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/27
 */
public class DeleteNode {
    public static void main(String[] args) {
        SinglyLinkedListNode head = LinkedListUtils.generateSinglyLinkedList();
        LinkedListUtils.printLink(head); // ->1->2->3->4->5
        SinglyLinkedListNode node = head.next.next;
        deleteNode(node);
        LinkedListUtils.printLink(head); // ->1->2->4->5
    }

    public static void deleteNode(SinglyLinkedListNode node) {
        SinglyLinkedListNode next = node.next;
        node.val = next.val;
        node.next = next.next;
        next = null;
    }
}
