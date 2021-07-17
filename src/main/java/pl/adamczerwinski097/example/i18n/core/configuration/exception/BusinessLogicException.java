package pl.adamczerwinski097.example.i18n.core.configuration.exception;

import lombok.Getter;
import lombok.Setter;

public class BusinessLogicException extends RuntimeException {
    @Getter
    @Setter
    private transient Object[] args;

    public BusinessLogicException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public BusinessLogicException(String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.args = args;
    }

    public BusinessLogicException(Throwable cause, Object[] args) {
        super(cause);
        this.args = args;
    }

    public BusinessLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] args) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.args = args;
    }
}
