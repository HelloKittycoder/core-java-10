package annotationGrammar.annotation;

import javax.annotation.Generated;

/**
 * 8.4.1 注解接口（P380）
 *
 * 有关注解的复杂写法，可以看下mybatis的@Result注解
 * Created by shucheng on 2020/10/1 13:41
 */
public @interface BugReport {

    enum Status { UNCONFIRMED, CONFIRMED, FIXED, NOTABUG };
    String assignedTo() default "[none]";
    int severity();
    Class<?> testCase() default Void.class;
    Status status() default Status.UNCONFIRMED;
    Reference ref() default @Reference;
    String[] reportedBy();
}
