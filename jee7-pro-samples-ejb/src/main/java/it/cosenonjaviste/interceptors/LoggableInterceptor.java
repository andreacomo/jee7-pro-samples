package it.cosenonjaviste.interceptors;

import it.cosenonjaviste.interceptors.binding.Loggable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor @Loggable
public class LoggableInterceptor {

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Logger logger = Logger.getLogger(context.getMethod().getDeclaringClass().getName());
        logger.info("Eseguo il metodo " + context.getMethod());
        return context.proceed();
    }

}
