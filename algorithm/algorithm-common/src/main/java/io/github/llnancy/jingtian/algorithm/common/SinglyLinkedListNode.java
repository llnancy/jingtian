package io.github.llnancy.jingtian.algorithm.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单向链表节点类
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/6/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinglyLinkedListNode {

    /**
     * 值域
     */
    public Integer val;

    /**
     * 指针域
     */
    public SinglyLinkedListNode next;

    public SinglyLinkedListNode(Integer val) {
        this.val = val;
    }
}
