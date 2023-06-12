package com.card.deck.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SufficientCardQuantityValidator implements ConstraintValidator<SufficientCardQuantity, Number>{

	private static final BigDecimal minimunNumberOfCard = BigDecimal.ZERO;
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context){
		if (value != null) {
			var number = BigDecimal.valueOf(value.doubleValue());
			return (number.compareTo(minimunNumberOfCard) > 0);
		}
		return false;
	}
	
}
