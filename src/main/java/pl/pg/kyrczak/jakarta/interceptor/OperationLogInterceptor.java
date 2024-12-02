package pl.pg.kyrczak.jakarta.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.interceptor.binding.OperationLog;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@Interceptor
@OperationLog
@Priority(100)
@Log
public class OperationLogInterceptor {
    private final SecurityContext securityContext;

    @Inject
    public OperationLogInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String className = context.getTarget().getClass().getName();
        Object[] parameters = context.getParameters();
        String user = securityContext.getCallerPrincipal().getName();
        String uuid = Arrays.stream(parameters).filter(param -> param instanceof UUID).findFirst().map(Object::toString).orElse(null);
        log.warning(() -> String.format(
                "User: %s, Operation: %s, Resource UUID: %s, Class: %s",
                user, methodName, uuid != null ? uuid : "N/A", className));

        return context.proceed();
    }
}
