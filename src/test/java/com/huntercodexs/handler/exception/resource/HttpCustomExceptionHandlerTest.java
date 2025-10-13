package com.huntercodexs.handler.exception.resource;

import com.huntercodexs.handler.exception.CustomResponseException;
import com.huntercodexs.handler.exception.HttpCustomExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HttpCustomExceptionHandlerTest {

    private HttpCustomExceptionHandler httpCustomExceptionHandler;

    @BeforeEach
    void setUp() {
        httpCustomExceptionHandler = new HttpCustomExceptionHandler();
    }

    @Test
    void shouldHandleBadGatewayExceptionCorrectly() {
        BadGatewayException ex = new BadGatewayException("Message 502, Bad Gateway", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(502, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 502, Bad Gateway", response.getBody().getMessage());
        assertEquals("502", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleBadRequestExceptionCorrectly() {
        BadRequestException ex = new BadRequestException("Message 400, Bad Request", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 400, Bad Request", response.getBody().getMessage());
        assertEquals("400", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleConflictExceptionCorrectly() {
        ConflictException ex = new ConflictException("Message 409, Conflict", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(409, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 409, Conflict", response.getBody().getMessage());
        assertEquals("409", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleCustomExceptionCorrectly() {
        CustomException ex = new CustomException("Message 400, Custom", HttpStatus.BAD_REQUEST, "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 400, Custom", response.getBody().getMessage());
        assertEquals("400", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleForbiddenExceptionCorrectly() {
        ForbiddenException ex = new ForbiddenException("Message 403, Forbidden", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(403, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 403, Forbidden", response.getBody().getMessage());
        assertEquals("403", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleGatewayTimeoutExceptionCorrectly() {
        GatewayTimeoutException ex = new GatewayTimeoutException("Message 504, GatewayTimeout", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(504, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 504, GatewayTimeout", response.getBody().getMessage());
        assertEquals("504", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleInternalServerExceptionCorrectly() {
        InternalServerException ex = new InternalServerException("Message 500, InternalServerException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 500, InternalServerException", response.getBody().getMessage());
        assertEquals("500", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleMethodNotAllowedExceptionCorrectly() {
        MethodNotAllowedException ex = new MethodNotAllowedException("Message 405, MethodNotAllowedException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(405, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 405, MethodNotAllowedException", response.getBody().getMessage());
        assertEquals("405", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleNotAcceptableExceptionCorrectly() {
        NotAcceptableException ex = new NotAcceptableException("Message 406, NotAcceptableException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(406, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 406, NotAcceptableException", response.getBody().getMessage());
        assertEquals("406", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleNotFoundExceptionCorrectly() {
        NotFoundException ex = new NotFoundException("Message 404, NotFoundException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 404, NotFoundException", response.getBody().getMessage());
        assertEquals("404", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleNotImplementedExceptionCorrectly() {
        NotImplementedException ex = new NotImplementedException("Message 501, NotImplementedException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(501, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 501, NotImplementedException", response.getBody().getMessage());
        assertEquals("501", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandlePaymentRequiredExceptionCorrectly() {
        PaymentRequiredException ex = new PaymentRequiredException("Message 402, PaymentRequiredException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(402, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 402, PaymentRequiredException", response.getBody().getMessage());
        assertEquals("402", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandlePreConditionFailedExceptionCorrectly() {
        PreConditionFailedException ex = new PreConditionFailedException("Message 412, PreConditionFailedException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(412, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 412, PreConditionFailedException", response.getBody().getMessage());
        assertEquals("412", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleServiceUnavailableExceptionCorrectly() {
        ServiceUnavailableException ex = new ServiceUnavailableException("Message 503, ServiceUnavailableException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(503, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 503, ServiceUnavailableException", response.getBody().getMessage());
        assertEquals("503", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleTooManyRequestsExceptionCorrectly() {
        TooManyRequestsException ex = new TooManyRequestsException("Message 429, TooManyRequestsException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(429, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 429, TooManyRequestsException", response.getBody().getMessage());
        assertEquals("429", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleUnauthorizedExceptionCorrectly() {
        UnauthorizedException ex = new UnauthorizedException("Message 401, UnauthorizedException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(401, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 401, UnauthorizedException", response.getBody().getMessage());
        assertEquals("401", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleUnprocessableEntityExceptionCorrectly() {
        UnprocessableEntityException ex = new UnprocessableEntityException("Message 422, UnprocessableEntityException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(422, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 422, UnprocessableEntityException", response.getBody().getMessage());
        assertEquals("422", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleUnSupportedMediaTypeExceptionCorrectly() {
        UnSupportedMediaTypeException ex = new UnSupportedMediaTypeException("Message 415, UnSupportedMediaTypeException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(415, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 415, UnSupportedMediaTypeException", response.getBody().getMessage());
        assertEquals("415", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleUriTooLongExceptionCorrectly() {
        UriTooLongException ex = new UriTooLongException("Message 414, UriTooLongException", "tracker-fake-generated-by-test-123456");
        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleHttpCustomException(ex);

        assertEquals(414, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Message 414, UriTooLongException", response.getBody().getMessage());
        assertEquals("414", response.getBody().getCode());
        assertEquals("tracker-fake-generated-by-test-123456", response.getBody().getTracker());
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected error");

        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler.handleGenericException(ex);

        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("An unexpected error occurred"));
    }

    @Test
    void shouldHandleValidationMethodArgumentNotValidExceptionCorrectly() {
        FieldError fieldError1 = new FieldError("objectNameTest", "name", "must not be null or empty");
        FieldError fieldError2 = new FieldError("objectNameTest", "email", "has an invalid format");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<CustomResponseException> responseEntity =
                httpCustomExceptionHandler.handleValidationMethodArgumentNotValidException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        CustomResponseException body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Field argument failed", body.getMessage());
        assertEquals("400", body.getCode());
        assertEquals(List.of("Field 'name' must not be null or empty", "Field 'email' has an invalid format"), body.getErrors());
    }

    @Test
    void shouldHandleValidationHttpMessageNotReadableExceptionCorrectly() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Malformed JSON request");

        ResponseEntity<CustomResponseException> responseEntity =
                httpCustomExceptionHandler.handleHttpMessageNotReadableException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        CustomResponseException body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("400", body.getCode());
        assertTrue(body.getMessage().contains("Malformed JSON request"));
    }

    @Test
    void shouldHandleValidationMissingServletRequestParameterExceptionCorrectly() {
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException(null, null);

        ResponseEntity<CustomResponseException> responseEntity =
                httpCustomExceptionHandler.handleMissingServletRequestParameterException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        CustomResponseException body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("400", body.getCode());
        assertTrue(body.getMessage().contains("Missing required parameter"));
    }

    @Test
    void shouldHandleValidationConstraintViolationExceptionCorrectly() {
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);

        Path path1 = mock(Path.class);
        Path path2 = mock(Path.class);

        when(violation1.getPropertyPath()).thenReturn(path1);
        when(violation1.getMessage()).thenReturn("must not be null");
        when(path1.toString()).thenReturn("name");

        when(violation2.getPropertyPath()).thenReturn(path2);
        when(violation2.getMessage()).thenReturn("must have at least 5 characters");
        when(path2.toString()).thenReturn("password");

        Set<ConstraintViolation<?>> violations = Set.of(violation1, violation2);

        ConstraintViolationException exception = new ConstraintViolationException(violations);

        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler
                .handleConstraintViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        CustomResponseException body = response.getBody();
        assertNotNull(body);
        assertEquals("400", body.getCode());
        assertEquals("Constraint violations", body.getMessage());
        assertEquals(List.of("name must not be null", "password must have at least 5 characters"), body.getErrors());
    }

    @Test
    void shouldHandleValidationHandlerMethodValidationExceptionCorrectly() throws NoSuchMethodException {
        FieldError error1 = new FieldError("param", "name", "must not be null");
        FieldError error2 = new FieldError("param", "email", "has an invalid format");

        MethodParameter methodParameter = mock(MethodParameter.class);

        ParameterValidationResult result1 = new ParameterValidationResult(
                methodParameter, null, List.of(error1), null, 0, null
        );
        ParameterValidationResult result2 = new ParameterValidationResult(
                methodParameter, null, List.of(error2), null, 1, null
        );

        Method handlerMethod = this.httpCustomExceptionHandler.getClass()
                .getDeclaredMethod("handleHandlerMethodValidationException", HandlerMethodValidationException.class);

        MethodValidationResult methodValidationResult = MethodValidationResult.create(
                this.httpCustomExceptionHandler,
                handlerMethod,
                List.of(result1, result2)
        );

        HandlerMethodValidationException exception = new HandlerMethodValidationException(methodValidationResult);

        ResponseEntity<CustomResponseException> response = httpCustomExceptionHandler
                .handleHandlerMethodValidationException(exception);

        CustomResponseException body = response.getBody();

        assertNotNull(body);
        assertEquals("Field validation failed", body.getMessage());
        assertEquals("400", body.getCode());

        List<String> errors = body.getErrors();
        assertNotNull(errors);
        assertEquals(2, errors.size());

        assertEquals(List.of("Field 'email' has an invalid format", "Field 'name' must not be null"), body.getErrors());
    }

    @Test
    void handleAuthorizationDeniedException_ShouldReturn401_ForAnonymous() {
        AuthorizationDeniedException ex =
                new AuthorizationDeniedException("Access denied", () -> false);

        SecurityContextHolder.clearContext();

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleAuthorizationDeniedException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Authentication required");
    }

    @Test
    void handleAuthorizationDeniedException_ShouldReturn401_ForAnonymousToken() {
        Authentication anonymous = new AnonymousAuthenticationToken(
                "key",
                "anonymousUser",
                List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
        );
        SecurityContextHolder.getContext().setAuthentication(anonymous);

        AuthorizationDeniedException ex =
                new AuthorizationDeniedException("Access denied", () -> false);

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleAuthorizationDeniedException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Authentication required");
    }

    @Test
    void handleAuthorizationDeniedException_ShouldReturn403_ForAuthenticatedUser() {
        Authentication auth = new TestingAuthenticationToken("user", "password", "ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(auth);

        AuthorizationDeniedException ex =
                new AuthorizationDeniedException("Access denied", () -> false);

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleAuthorizationDeniedException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Access denied");
    }

    @Test
    void shouldHandleAuthorizationDeniedExceptionCorrectly() {
        AuthorizationResult deniedResult = mock(AuthorizationResult.class);
        when(deniedResult.isGranted()).thenReturn(false);

        AuthorizationDeniedException exception =
                new AuthorizationDeniedException("User does not have the required role", deniedResult);

        var authentication = new TestingAuthenticationToken("user", "password", "ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleAuthorizationDeniedException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        CustomResponseException body = response.getBody();

        assertNotNull(body);
        assertEquals("403", body.getCode());
        assertEquals("Access denied", body.getMessage());
        assertEquals(List.of("User does not have the required role"), body.getErrors());
    }

    @Test
    void shouldHandleHttpRequestMethodNotSupportedExceptionCorrectly() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleHttpRequestMethodNotSupportedException(ex);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

        CustomResponseException body = response.getBody();
        assertNotNull(body);
        assertEquals(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()), body.getCode());
        assertEquals("HTTP method 'POST' not supported for this endpoint", body.getMessage());
        assertNotNull(body.getTracker());
        assertNull(body.getErrors());
    }

    @Test
    void shouldHandleHttpMediaTypeNotSupportedExceptionCorrectly() {
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException("application/xml");

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleHttpMediaTypeNotSupportedException(ex);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());

        CustomResponseException body = response.getBody();
        assertNotNull(body);
        assertEquals(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()), body.getCode());
        assertEquals("Media type 'application/xml' is not supported", body.getMessage());
        assertNotNull(body.getTracker());
        assertNull(body.getErrors());
    }

    @Test
    void shouldHandleRestClientExceptionCorrectly() {
        RestClientException ex = new RestClientException("Connection refused");

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleRestClientException(ex);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());

        CustomResponseException body = response.getBody();
        assertNotNull(body);
        assertEquals(String.valueOf(HttpStatus.BAD_GATEWAY.value()), body.getCode());
        assertEquals("External service communication failed", body.getMessage());
        assertNotNull(body.getTracker());
        assertEquals(List.of("Connection refused"), body.getErrors());
    }

    @Test
    void shouldHandleRuntimeExceptionCorrectly() {
        RuntimeException ex = new RuntimeException("Something went wrong");

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        CustomResponseException body = response.getBody();
        assertNotNull(body);
        assertEquals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), body.getCode());
        assertEquals("Internal server error", body.getMessage());
        assertNotNull(body.getTracker());
        assertEquals(List.of("RuntimeException: Something went wrong"), body.getErrors());
    }

    @Test
    void shouldHandleAuthenticationCredentialsNotFoundExceptionCorrectly() {
        AuthenticationCredentialsNotFoundException exception =
                new AuthenticationCredentialsNotFoundException("No authentication found");

        ResponseEntity<CustomResponseException> response =
                httpCustomExceptionHandler.handleAuthenticationCredentialsNotFoundException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        CustomResponseException body = response.getBody();
        assertNotNull(body);
        assertEquals("Authentication required", body.getMessage());
        assertEquals("401", body.getCode());
        assertEquals(List.of("No authentication found"), body.getErrors());
    }

}
