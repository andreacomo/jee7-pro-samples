package it.cosenonjaviste.utils;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.annotation.Annotation;

public class BeanManagerHelper {

    public static <T> T getBean(Class<T> clazz, Annotation... annotations) throws NamingException {
        Context context = new InitialContext();
        BeanManager beanManager = (BeanManager) context.lookup("java:comp/BeanManager");

        Bean<T> bean = (Bean<T>) beanManager.getBeans(clazz, annotations).iterator().next();
        return (T) beanManager.getReference(bean, clazz, beanManager.createCreationalContext(bean));
    }
}
