package keel.apierrorhandling;

import com.fasterxml.jackson.annotation.JsonInclude;

// api-error-model-start
public class ApiErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String field;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String message;

    ApiErrorResponse(String field, String message) {
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
