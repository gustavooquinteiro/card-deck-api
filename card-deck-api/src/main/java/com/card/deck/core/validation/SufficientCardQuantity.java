package com.card.deck.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SufficientCardQuantityValidator.class)
public @interface SufficientCardQuantity {

	String message() default "A card game should have a positive non-zero number of cards in a hand";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
