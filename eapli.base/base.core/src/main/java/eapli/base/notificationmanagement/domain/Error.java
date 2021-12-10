package eapli.base.notificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;

@Embeddable
public class Error implements ValueObject, Comparable<Error> {

    private static final long serialVersionUID = 1L;

    private ErrorType errorType;

    private String description;

    public Error(ErrorType errorType, String description) {
        this.errorType = errorType;
        this.description = description;
    }

    public Error() {

    }

    @Override
    public int compareTo(Error o) {
        return (description.compareTo(o.description));
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getDescription() {
        return description;
    }
}
