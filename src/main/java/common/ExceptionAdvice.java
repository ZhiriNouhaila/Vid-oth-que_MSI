package common;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

	/**
	 * message d'erreurs donnees invalides
	 * @param e : exception
	 * @param request : la requete qui est en erreur
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return mapMessage(request, HttpStatus.BAD_REQUEST,"valid exception", errors);
    }

	/**
	 * message d'erreur contrainte d'unicite
	 * @param e : exception 
	 * @param request : la requete qui est en erreur
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, Object> handleDataIntegrityExceptions(DataIntegrityViolationException e, HttpServletRequest request) {
    	return mapMessage(request, HttpStatus.BAD_REQUEST,"data integrity violation exception", "record allready exist");
	}
	
	/**
	 * message d'erreur applicative
	 * @param e : exception
	 * @param request : la requete qui est en erreur
	 * @return
	 */
	@ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AppException.class)
    public Map<String, Object> handleDataIntegrityExceptions(AppException e, HttpServletRequest request) {
    	return mapMessage(request, HttpStatus.FORBIDDEN,e.getCategory(), e.getMessage());
	}

	
	/**
	 * mise en forme du message retourne
	 * @param request : la requete qui est en erreur
	 * @param status : status de retour de la requete
	 * @param error : type d'erreur
	 * @param errors : message d'erreur
	 * @return
	 */
	private Map<String, Object> mapMessage(HttpServletRequest request, HttpStatus status, String error, Object errors) {
		Map<String, Object> response = new LinkedHashMap <>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", errors);
        response.put("path", request.getRequestURI().toString());
        return response;
	}
}

