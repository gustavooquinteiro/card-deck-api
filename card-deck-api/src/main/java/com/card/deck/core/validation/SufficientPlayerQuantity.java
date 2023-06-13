package com.card.deck.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.card.deck.domain.exception.InsufficientPlayersException;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SufficientPlayerQuantityValidator.class)
public @interface SufficientPlayerQuantity {
	String message() default InsufficientPlayersException.DEFAULT_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
