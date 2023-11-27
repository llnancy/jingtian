package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * 最小栈
 * <a href="https://leetcode.cn/problems/min-stack/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/min-stack/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/7
 */
public class MinStack {

    /**
     * 记录栈中所有元素
     */
    private final Deque<Integer> stack;

    /**
     * 阶段性记录栈中的最小元素
     */
    private final Deque<Integer> minStack;

    public MinStack() {
        stack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        stack.push(val);
        // 维护 minStack 栈顶为全栈最小元素
        if (minStack.isEmpty() || val <= minStack.peek()) {
            // 新插入的元素就是最小元素
            minStack.push(val);
        }
    }

    public void pop() {
        if (top() == getMin()) {
            minStack.pop();
        }
        stack.pop();
    }

    public int top() {
        Integer top = stack.peek();
        if (top == null) {
            throw new NoSuchElementException();
        }
        return top;
    }

    public int getMin() {
        Integer min = minStack.peek();
        if (min == null) {
            throw new NoSuchElementException();
        }
        return min;
    }
}
