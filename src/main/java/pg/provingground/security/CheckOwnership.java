package pg.provingground.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메소드를 대상으로 적용.
@Retention(RetentionPolicy.RUNTIME) // 해당 어노테이션이 런타임에도 적용되게끔 한다
public @interface CheckOwnership {
    String serviceName();
}
