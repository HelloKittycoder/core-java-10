package optional;

import java.io.IOException;
import java.util.*;

import static common.StreamUtil.readFileContent;

/**
 * 1.7 Optional类型
 * Created by shucheng on 2020/9/10 22:22
 */
public class OptionalTest {

    public static void main(String[] args) throws IOException {
        String contents = readFileContent("gutenberg/alice30.txt");
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));

        // 情形一：在值不存在的情况下会产生一个可替代物
        // 可能是空字符串
        Optional<String> optionalValue = wordList.stream()
                .filter(s -> s.contains("fred"))
                .findFirst();
        System.out.println(optionalValue.orElse("No word"));

        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("optionalString.orElse: " + result);
        // 可以调用代码来计算默认值
        result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("optionalString.orElseGet: " + result);
        // 可以在没有任何值时抛出异常
        try {
            result = optionalString.orElseThrow(IllegalStateException::new);
            System.out.println("optionalString.orElseThrow: " + result);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        // 情形二：只有在值存在的情况下才会使用这个值
        optionalValue = wordList.stream()
                .filter(s -> s.contains("red"))
                .findFirst();
        // optionalValue1中有值时，则进行打印操作
        optionalValue.ifPresent(s -> System.out.println(s + " contains red"));

        // optionalValue1中有值时，把它加到results中
        Set<String> results = new HashSet<>();
        optionalValue.ifPresent(results::add);

        // -------------------------------------------------------------------------------
        // 使用Optional获取add后的返回值
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println("added==" + added);

        // 计算倒数
        System.out.println("inverse(2.0)==" + inverse(2.0));

        // 计算倒数的平方根
        System.out.println("4.0的倒数的平方根==" + inverse(4.0).flatMap(OptionalTest::squareRoot));
        System.out.println("-1.0的倒数的平方根==" + inverse(-1.0).flatMap(OptionalTest::squareRoot));
        System.out.println("0.0的倒数的平方根==" + inverse(0.0).flatMap(OptionalTest::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0)
                .flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
        System.out.println("-4.0的倒数的平方根==" + result2);
    }

    // 计算倒数
    // 1.7.3 创建Optional值
    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    // 计算平方根
    // 1.7.4 用flatMap来构建Optional值的函数
    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
