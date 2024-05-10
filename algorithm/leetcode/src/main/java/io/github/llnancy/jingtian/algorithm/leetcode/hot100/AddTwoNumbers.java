package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 两数相加
 * <a href="https://leetcode.cn/problems/add-two-numbers/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/add-two-numbers/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/12/17
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 虚拟哨兵节点（hair：头发。）
        ListNode hair = new ListNode();
        // cur 指针连接新链表
        ListNode cur = hair;
        // 每次相加的进位
        int jin = 0;
        // 开始执行加法，两条链表走完且没有进位时才能结束循环。
        while (l1 != null || l2 != null || jin != 0) {
            // 加上进位
            int sum = jin;
            // 加上 l1 的值
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            // 加上 l2 的值
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            // 更新进位
            jin = sum / 10;
            // 当前结果
            sum = sum % 10;
            // 连接新节点
            cur.next = new ListNode(sum);
            // cur右移
            cur = cur.next;
        }
        // 返回新的头节点（虚拟哨兵节点的下一个就是新的头节点）
        return hair.next;
    }
}
