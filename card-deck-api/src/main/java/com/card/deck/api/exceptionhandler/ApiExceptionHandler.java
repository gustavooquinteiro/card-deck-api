package com.card.deck.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.card.deck.api.exceptionhandler.Problem.Field;
import com.card.deck.domain.exception.DifferentCardQuantityException;
import com.card.deck.domain.exception.DifferentPlayersQuantityException;
import com.card.deck.domain.exception.GameNotFoundException;
import com.card.deck.domain.exception.InsufficientCardsException;
import com.card.deck.domain.exception.InsufficientPlayersException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static String RESOURCE_NOT_FOUND = "The %s resource, which you tried to access, is not valid";
	private static String METHOD_NOT_ALLOWED = "The requested HTTP method is not supported for this URL." ;
	private static String METHOD_NOT_ALLOWED_DETAIL = "Only [%s] are supported in this URL";
	private static String INVALID_FIELDS = "One or more fields are invalid. Please fill in the correct information and try again.";

	@Autowired
	private MessageSource messageSource;
	
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

		ProblemType problemType = ProblemType.INSUFFICIENT_VALUES;
		List<Problem.Field> fields = getErrorFields(ex.getBindingResult());
		String detail = INVALID_FIELDS;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.fields(fields)
				.build();	
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private List<Field> getErrorFields(BindingResult bindingResult) {
		return bindingResult.getAllErrors().stream()
				.map(objectError ->{
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					String name = objectError.getObjectName();
					if (objectError instanceof FieldError)
						name = ((FieldError)objectError).getField();
					return Problem.Field.builder()
							.name(name)
							.userMessage(message)
							.build();	
				})
				.toList();
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = String.format(RESOURCE_NOT_FOUND, ex.getRequestURL());
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.METHOD_NOT_ALLOWED;
		String detail = String.format(METHOD_NOT_ALLOWED_DETAIL,String.join(",", ex.getSupportedMethods()));
		Problem problem = createProblemBuilder(status, problemType, detail).uiMessage(METHOD_NOT_ALLOWED).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
		
	@ExceptionHandler(DifferentCardQuantityException.class)
	public ResponseEntity<Object> handleDifferentCardQuantity(DifferentCardQuantityException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.DIFFERENT_CARD_AMOUNT;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(DifferentPlayersQuantityException.class)
	public ResponseEntity<Object> handleDifferentCardQuantity(DifferentPlayersQuantityException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.DIFFERENT_PLAYER_AMOUNT;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
}
