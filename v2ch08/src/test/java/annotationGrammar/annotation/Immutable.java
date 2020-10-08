package annotationGrammar.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by shucheng on 2020/10/1 14:04
 */
@Target(ElementType.TYPE_PARAMETER)
public @interface Immutable {
    String value() default "";
}
