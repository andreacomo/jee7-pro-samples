package it.cosenonjaviste.processors.qualifiers;

import it.cosenonjaviste.model.DeliveryNoteType;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Processing {

    DeliveryNoteType value();

    @Nonbinding String comment() default "";
}
