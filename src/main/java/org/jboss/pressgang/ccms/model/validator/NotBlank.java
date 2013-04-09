package org.jboss.pressgang.ccms.model.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

@ValidatorClass(NotBlankValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface NotBlank {
    String message() default "{validator.notBlank}";
}
