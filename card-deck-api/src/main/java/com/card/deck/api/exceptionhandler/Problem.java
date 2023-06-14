package com.card.deck.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {

	private Integer status;
	private String title;
	private String type;
	private String detail;
	private String uiMessage;
	private List<Field> fields;
	private OffsetDateTime timestamp;
	
	@Getter
	@Builder
	public static class Field {
		private String name;
		private String userMessage;
	}
}
