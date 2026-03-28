package pl.pg.kyrczak.jakarta.authorization.interceptor.binding;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AllowRoles {
    @Nonbinding
    String[] value() default {};
}
