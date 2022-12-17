package common.support;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by shucheng on 2022/12/17 16:33
 */
public class MatchDetail {

    private String regex;
    private Map<CharSequence, Boolean> matchMap = new LinkedHashMap<>();

    private MatchDetail(String regex) {
        this.regex = regex;
    }

    public void addMatchResult(CharSequence input, boolean match) {
        matchMap.put(input, match);
    }

    public static MatchDetail build(String regex) {
        return new MatchDetail(regex);
    }

    @Override
    public String toString() {
        return "MatchDetail{" +
                "regex='" + regex + '\'' +
                ", matchMap=" + matchMap +
                '}';
    }
}
