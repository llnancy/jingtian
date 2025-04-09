package io.github.llnancy.jingtian.algorithm.base.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 滑动窗口
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/11
 */
public class SlidingWindow {
    /**
     * 滑动窗口模板
     * <p>
     * 现在开始套模板，只需要思考以下四个问题：
     * <p>
     * 1、当移动 right 扩大窗口，即加入字符时，应该更新哪些数据？
     * <p>
     * 2、什么条件下，窗口应该暂停扩大，开始移动 left 缩小窗口？
     * <p>
     * 3、当移动 left 缩小窗口，即移出字符时，应该更新哪些数据？
     * <p>
     * 4、我们要的结果应该在扩大窗口时还是缩小窗口时进行更新？
     *
     * @param s
     * @param t
     */
    public static void slidingWindow(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : tArr) {
            need.putIfAbsent(c, 1);
            Integer count = need.get(c);
            need.put(c, count + 1);
        }
        int left = 0;
        int right = 0;
        int valid = 0;
        while (right < sArr.length) {
            char c = sArr[right];// c 是要移入窗口的字符
            right++;// 窗口右移
            // 进行窗口内数据的更新

            // 判断左侧窗口是否需要收缩
            while (true/*window needs shrink*/) {
                char d = sArr[left];// d 是要移出窗口的字符
                left++;// 左移窗口
                // 进行窗口内数据更新
            }
        }
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] sArr = s.toCharArray();
        Map<Character, Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
        // 结果
        int res = 0;
        while (right < sArr.length) {
            char c = sArr[right];// c 是要移入窗口的字符
            right++;// 窗口右移

            //进行窗口内数据的更新
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 判断左侧窗口是否需要收缩
            while (window.get(c) > 1) {// 窗口内存在重复字符，需要移动 left 缩小窗口
                char d = sArr[left];// d 是要移出窗口的字符
                left++;// 左移窗口

                // 进行窗口内数据的更新
                window.put(d, window.getOrDefault(d, 1) - 1);
            }

            // 这里更新答案
            res = Math.max(res, right - left);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }
}
