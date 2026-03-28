package pl.pg.kyrczak.jakarta.authorization.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import pl.pg.kyrczak.jakarta.authorization.exception.NoPrincipalException;
import pl.pg.kyrczak.jakarta.authorization.exception.NoRolesException;
import pl.pg.kyrczak.jakarta.authorization.interceptor.binding.AllowRoles;

@Interceptor
@AllowRoles
@Priority(10)
public class AllowRolesInterceptor {
    private final SecurityContext securityContext;

    @Inject
    public AllowRolesInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NoPrincipalException();
        }
        String[] roles = context.getMethod().getAnnotation(AllowRoles.class).value();
        for (String role : roles) {
            if (securityContext.isCallerInRole(role)) {
                return context.proceed();
            }
        }
        throw new NoRolesException();
    }

}
