package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.LinkedList;
import java.util.List;

/**
 * 课程表
 * <a href="https://leetcode.cn/problems/course-schedule/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/course-schedule/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/12/4
 */
public class CourseSchedule {

    /*
    当存在循环依赖时不可能修完所有课程。问题可转化为有向图，只要图中有环，说明存在循环依赖。
     */

    /**
     * 记录一次递归栈中的节点
     */
    private boolean[] onPath;

    /**
     * 记录遍历过的节点
     */
    private boolean[] visited;

    /**
     * 记录图中是否有环
     */
    private boolean hasCycle;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 构建图
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        onPath = new boolean[numCourses];
        visited = new boolean[numCourses];
        // 遍历图中所有节点
        for (int i = 0; i < numCourses; i++) {
            traverse(graph, i);
        }
        // 图中无环才能完成所有课程学习
        return !hasCycle;
    }

    private void traverse(List<Integer>[] graph, int i) {
        if (onPath[i]) {
            // 出现环
            hasCycle = true;
        }
        if (visited[i] || hasCycle) {
            return;
        }
        visited[i] = true;
        onPath[i] = true;
        for (Integer t : graph[i]) {
            traverse(graph, t);
        }
        onPath[i] = false;
    }

    private List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] edge : prerequisites){
            int from = edge[1];
            int to = edge[0];
            // 添加一条从 from 指向 to 的有向边：表示修完课程 from 才能修课程 to
            graph[from].add(to);
        }
        return graph;
    }
}
