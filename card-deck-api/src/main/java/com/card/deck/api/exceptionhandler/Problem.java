package com.card.deck.api.exceptionhandler;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem {

	private Integer status;
	private String title;
	private String type;
	private String detail;
	private String uiMessage;
	private OffsetDateTime timestamp;
}
