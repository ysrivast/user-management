package com.usermanagement.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	 @NonNull
	    private static MessageSource messageSource;

	    @Autowired
	    public void setMessageSource(MessageSource messageSource) {
//	        MessageProvider.messageSource = messageSource;
	    }

	@ExceptionHandler({ UserException.class })
//	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorDetails> handleUserException(UserException e) {
		log.error("error in operation {} ", e.getMessage());
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorDetails errorDetails = ErrorDetails.of(new Date(), e.getMessage(), details);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("error in operation {} ", ex.getMessage());
		BindingResult result = ex.getBindingResult();
		List<FieldViolation> fieldViolations = 
				result.getFieldErrors()
					.stream()
					.map(f -> FieldViolation.of(f.getField(), f.getCode()))
							.collect(Collectors.toList());
		
		List<String> details = null;
		ErrorDetails errorDetails = ErrorDetails.of(new Date(), "Validation failed!", details);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	 public static String getMessageForFieldError(final FieldError fieldError) {
	        String message = null;
	        fieldError.getCode();
//	        for (String code : fieldError.getCodes()) {
//	            message = messageSource.getMessage(code, fieldError.getArguments(), null, locale);
//	            if (message != null) {
//	                break;
//	            }
//	        }
//	        if (message == null) {
//	            message = messageSource.getMessage(fieldError.getDefaultMessage(), fieldError.getArguments(),
//	                    fieldError.getDefaultMessage(), locale);
//	        }
	        fieldError.getDefaultMessage();
	        return message;
	    }

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("error in operation {} ", ex.getLocalizedMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorDetails errorDetails = ErrorDetails.of(new Date(), "Unsupported mediatype!", details);
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorDetails);
	}

//	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//	public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
//		log.error("error in operation {} ", ex.getMessage());
//		List<String> details = new ArrayList<>();
//		details.add(ex.getLocalizedMessage());
//		ErrorDetails errorDetails = ErrorDetails.of(new Date(), ex.getMessage(), details);
//		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported mediatype");
//	}

}
