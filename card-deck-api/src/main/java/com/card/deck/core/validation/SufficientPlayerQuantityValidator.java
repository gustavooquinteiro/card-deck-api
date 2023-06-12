package com.card.deck.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SufficientPlayerQuantityValidator implements ConstraintValidator<SufficientPlayerQuantity, Number>{

	private static final BigDecimal minimunNumberOfPlayers = BigDecimal.valueOf(2);
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context){
		boolean valid = false;	
		if (value != null) {
				var number = BigDecimal.valueOf(value.doubleValue());
				if (number.compareTo(minimunNumberOfPlayers) >= 0) 
					valid = true;
			}
		return valid;
	}
	
}
