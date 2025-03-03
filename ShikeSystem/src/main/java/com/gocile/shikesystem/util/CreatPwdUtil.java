package com.gocile.shikesystem.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class CreatPwdUtil {
    public static String getInitialUppercase(String name) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不带声调

        StringBuilder initials = new StringBuilder();
        char[] chars = name.toCharArray();

        for (char c : chars) {
            try {
                // 获取汉字拼音
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyinArray != null) {
                    // 提取首字母并转为大写
                    initials.append(pinyinArray[0].substring(0, 1).toUpperCase());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return initials.toString();
    }
}