package common.util;

import common.support.MatchDetail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shucheng on 2022/12/17 16:09
 */
public class RegexUtil {

    /**
     * 验证输入的字符串能否匹配给定的正则表达式
     * @param regex
     * @param input
     * @return
     */
    public static boolean matches(String regex, CharSequence input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 批量验证输入的字符串能否匹配给定的正则表达式
     * @param regex
     * @param input
     * @return
     */
    public static MatchDetail matches(String regex, CharSequence... input) {
        MatchDetail matchDetail = MatchDetail.build(regex);

        Pattern pattern = Pattern.compile(regex);
        for (CharSequence str : input) {
            Matcher matcher = pattern.matcher(str);
            matchDetail.addMatchResult(str, matcher.matches());
        }
        return matchDetail;
    }
}
