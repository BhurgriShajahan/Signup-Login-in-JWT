package jwt.spring.security.exceptions;
public class CustomJwtException extends RuntimeException {
    public CustomJwtException(String message) {
        super(message);
    }
    public CustomJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}

