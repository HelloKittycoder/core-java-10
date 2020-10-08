package annotationGrammar.po;

import annotationGrammar.annotation.Entity;
import annotationGrammar.annotation.Immutable;

/**
 * 8.4.3 注解各类声明
 * Created by shucheng on 2020/10/1 13:57
 */
public class AnnotationDeclaration {

    public static void main(String[] args) {
        Cache<Immutable> cache = new Cache<>();
    }
}

@Entity
class User {
}

class Cache<@Immutable V> {}
