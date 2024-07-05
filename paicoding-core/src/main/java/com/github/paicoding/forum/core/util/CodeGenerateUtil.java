package com.github.paicoding.forum.core.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 验证码生成工具
 * @author YiHui
 * @date 2022/8/15
 */
public class CodeGenerateUtil {

    public static final Integer CODE_LEN = 3;

    private static final Random random = new Random();

    private static final List<String> specialCodes = Arrays.asList(
            "666", "888", "000", "999", "555", "222", "333", "777",
            "520", "911",
            "234", "345", "456", "567", "678", "789"
    );

    public static String genCode(int cnt) {
        // 如果特殊验证码用完了，就随机生成一个
        if (cnt >= specialCodes.size()) {
            int num = random.nextInt(1000);
            if (num >= 100 && num <= 200) {
                // 100-200之间的数字作为关键词回复，不用于验证码
                return genCode(cnt);
            }
            // 生成一个固定长度的字符串。这个方法的第一个参数是一个格式字符串，它定义了输出字符串的格式。第二个参数是要格式化的值。
            // 这里格式字符串是 "%0" + CODE_LEN + "d" ，表示输出的字符串长度是 CODE_LEN ，左边用 0 填充，d 表示输出的是整数。
            return String.format("%0" + CODE_LEN + "d", num);
        } else {
            return specialCodes.get(cnt);
        }
    }

    public static boolean isVerifyCode(String content) {
        if (!NumberUtils.isDigits(content) || content.length() != CodeGenerateUtil.CODE_LEN) {
            return false;
        }

        int num = Integer.parseInt(content);
        return num < 100 || num > 200;
    }
}