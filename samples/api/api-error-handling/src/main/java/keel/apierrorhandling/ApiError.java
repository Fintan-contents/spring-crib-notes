package keel.apierrorhandling;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

// api-error-model-start
public class ApiError implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String field;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String message;

    ApiError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
// api-error-model-end
