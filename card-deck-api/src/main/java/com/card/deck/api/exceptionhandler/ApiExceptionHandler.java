package com.card.deck.api.exceptionhandler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.card.deck.domain.exception.GameNotFoundException;
import com.card.deck.domain.exception.InsufficientCardsException;
import com.card.deck.domain.exception.InsufficientPlayersException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static String RESOURCE_NOT_FOUND = "The %s resource, which you tried to access, is not valid";

	@ExceptionHandler(GameNotFoundException.class)
	protected ResponseEntity<Object> handleGameNotFoundException(Exception ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(InsufficientPlayersException.class)
	protected ResponseEntity<Object> handleInsufficientPlayersException(InsufficientPlayersException insufficientPlayersException, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.INSUFFICIENT_PLAYERS;
		String detail = insufficientPlayersException.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(insufficientPlayersException, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(InsufficientCardsException.class)
	protected ResponseEntity<Object> handleInsufficientCardsException(InsufficientCardsException insufficientCardsException, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.INSUFFICIENT_CARDS;
		String detail = insufficientCardsException.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(insufficientCardsException, problem, new HttpHeaders(), status, request);
	}

	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

		return Problem.builder()
				.detail(detail)
				.uiMessage(detail)
				.status(status.value())
				.title(problemType.getTitle())
				.type(problemType.getUri())
				.timestamp(OffsetDateTime.now());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String detail = extractMessage(ex.getMessage());
		switch (detail) {
		case InsufficientCardsException.DEFAULT_MESSAGE:
			return handleInsufficientCardsException(new InsufficientCardsException(), request);
		case InsufficientPlayersException.DEFAULT_MESSAGE:
			return handleInsufficientPlayersException(new InsufficientPlayersException(), request);
		default:
			ProblemType problemType = ProblemType.INSUFFICIENT_VALUES;
			Problem problem = createProblemBuilder(status, problemType, detail).build();	
			return handleExceptionInternal(ex, problem, headers, status, request);
		}
	}
	
	private String extractMessage(String message) {
        String[] messageParts = message.split(";");
        String finalPart = messageParts[messageParts.length -1];
        return finalPart.trim().replaceAll("default message \\[|]]","");
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = String.format(RESOURCE_NOT_FOUND, ex.getRequestURL());
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
}
