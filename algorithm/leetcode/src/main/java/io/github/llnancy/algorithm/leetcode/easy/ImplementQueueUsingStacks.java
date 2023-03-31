package io.github.llnancy.algorithm.leetcode.easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 232. 用栈实现队列
 * <p>
 * https://leetcode-cn.com/problems/implement-queue-using-stacks/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/19
 */
public class ImplementQueueUsingStacks {
    public static void main(String[] args) {
        MyQueue<Integer> myQueue = new MyQueue<>();
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        myQueue.push(4);
        while (!myQueue.isEmpty()) {
            System.out.println(myQueue.peek());
            System.out.println(myQueue.pop());
        }
    }

    public static class MyQueue<E> {
        private final Deque<E> stack1 = new LinkedList<>();
        private final Deque<E> stack2 = new LinkedList<>();

        public void push(E e) {
            stack1.push(e);
        }

        public E pop() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }

        public E peek() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.peek();
        }

        public boolean isEmpty() {
            return stack1.isEmpty() && stack2.isEmpty();
        }
    }
}
