package com.github.pacey.passwordgen.api;

import com.github.pacey.passwordgen.app.provider.DateTimeProvider;
import com.github.pacey.passwordgen.app.provider.IdentifierProvider;
import com.github.pacey.passwordgen.app.logging.LoggingContext;
import com.github.pacey.passwordgen.validation.ValidationException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Slf4j
@Singleton
public class ApiErrorHandler implements ExceptionHandler<Throwable, HttpResponse<ApiErrorResponse>> {

    private static final Map<Class<? extends Throwable>, HttpStatus> throwableStatusMap = Map.of(
        ValidationException.class, HttpStatus.BAD_REQUEST
    );

    private final DateTimeProvider dateTimeProvider;
    private final IdentifierProvider identifierProvider;

    @Inject
    public ApiErrorHandler(DateTimeProvider dateTimeProvider, IdentifierProvider identifierProvider) {
        this.dateTimeProvider = dateTimeProvider;
        this.identifierProvider = identifierProvider;
    }

    @Override
    public HttpResponse<ApiErrorResponse> handle(HttpRequest request, Throwable exception) {

        var httpStatus = throwableStatusMap.getOrDefault(exception.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        var apiError = getApiError(request, exception, httpStatus);
        logError(exception, apiError);
        return HttpResponse.status(httpStatus)
            .body(apiError);
    }

    private ApiErrorResponse getApiError(HttpRequest<?> request, Throwable exception, HttpStatus httpStatus) {
        return new ApiErrorResponse(
            identifierProvider.identifier(),
            dateTimeProvider.now(),
            httpStatus,
            exception.getMessage() != null ? exception.getMessage() : "No message provided.",
            request.getPath(),
            request.getMethodName()
        );
    }

    private void logError(Throwable exception, ApiErrorResponse apiErrorResponse) {

        LoggingContext.setErrorId(apiErrorResponse.getId());

        if (apiErrorResponse.getStatus() >= 500) {
            log.error("Unhandled exception.", exception);
        } else {
            log.warn("Unhandled exception.", exception);
        }
    }
}
