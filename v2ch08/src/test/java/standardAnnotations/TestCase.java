package standardAnnotations;

import java.lang.annotation.Repeatable;

/**
 * 8.5.3 元注解
 *
 * 这里用下@@Repeatable注解
 * 说明：无论何时，只要用户提供了两个或更多个@TestCase注解，那么它们就会自动地被
 * 包装到一个@TestCases注解中
 *
 * Created by shucheng on 2020/10/1 20:26
 */
@Repeatable(TestCases.class)
public @interface TestCase {

    String params();
    String expected();
}
