package com.hontian.util;

import org.springframework.util.StringUtils;

/**
 * @author weed
 * @date 2020/11/16 0016 17:13
 * @description
 */
public class StringUtil {

    public static String firstWord2Up(String word) {
        if(StringUtils.isEmpty(word)) {
            return word;
        }
        String first = word.substring(0,1);
        return word.replaceFirst(first,first.toUpperCase() );
    }
}
