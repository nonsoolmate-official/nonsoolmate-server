package com.nonsoolmate.global.handler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.nonsoolmate.exception.common.BusinessException;
import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.CommonErrorType;
import com.nonsoolmate.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler({ClientException.class})
	protected ResponseEntity<ErrorResponse> handleCustomException(ClientException ex) {
		return ResponseEntity.status(ex.getExceptionType().status()).body(ErrorResponse.of(ex.getExceptionType()));
	}

	@ExceptionHandler({BusinessException.class})
	protected ResponseEntity<ErrorResponse> handleServerException(BusinessException ex) {
		log.error("🚨BusinessException occurred: {} 🚨\n{}",
			ex.getMessage(),
			getStackTraceAsString(ex));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ErrorResponse.of(CommonErrorType.INTERNAL_SERVER_ERROR));
	}

	@ExceptionHandler({Exception.class})
	protected ResponseEntity<ErrorResponse> handleServerException(Exception ex) {
		log.error("🚨InternalException occurred: {} 🚨\n{}",
			ex.getMessage(),
			getStackTraceAsString(ex));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ErrorResponse.of(CommonErrorType.INTERNAL_SERVER_ERROR));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ErrorResponse> handleNotFoundException(final NoHandlerFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ErrorResponse.of(CommonErrorType.NOT_FOUND_PATH, ex.getRequestURL()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append("[");
			builder.append(fieldError.getField());
			builder.append("](은)는 ");
			builder.append(fieldError.getDefaultMessage());
			builder.append(" 입력된 값: [");
			builder.append(fieldError.getRejectedValue());
			builder.append("]");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.of(CommonErrorType.INVALID_INPUT_VALUE, builder.toString()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.of(CommonErrorType.INVALID_INPUT_VALUE, ex.getMessage()));
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpMediaTypeNotSupportedException ex) {
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
			.body(ErrorResponse.of(CommonErrorType.INVALID_JSON_TYPE, ex.getMessage()));
	}

	private String getStackTraceAsString(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
}
