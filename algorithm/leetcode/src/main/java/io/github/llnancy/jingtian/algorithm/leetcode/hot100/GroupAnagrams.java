package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 字母异位词分组
 * <a href="https://leetcode.cn/problems/group-anagrams/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/group-anagrams/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/1
 */
public class GroupAnagrams {

    /*
    利用每个字符的出现次数进行编码，编码相同的字符串即为异位词，使用哈希表进行存储。
     */

    public List<List<String>> groupAnagrams(String[] strs) {
        // 编码到分组的映射
        Map<String, List<String>> codeToGroup = new HashMap<>();
        for (String str : strs) {
            // 对字符串进行编码
            String code = encode(str);
            // 编码相同的字符串放在一起
            codeToGroup.computeIfAbsent(code, v -> new LinkedList<>()).add(str);
        }
        return new ArrayList<>(codeToGroup.values());
    }

    private String encode(String str) {
        // 利用每个字符的出现次数进行编码
        char[] count = new char[26];
        for (char c : str.toCharArray()) {
            int delta = c - 'a';
            // 字符出现次数加一
            count[delta]++;
        }
        return new String(count);
    }
}
