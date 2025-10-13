package com.huntercodexs.handler.exception.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huntercodexs.handler.exception.HttpCustomExceptionHandler;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.huntercodexs.handler.exception.DataBuilder.convertToJSON;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({HttpCustomExceptionHandler.class, HttpCustomExceptionHandlerIntegrationTest.SecurityConfig.class})
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(controllers = HttpCustomExceptionHandlerIntegrationTest.TestController.class)
class HttpCustomExceptionHandlerIntegrationTest {

    private static final String BASE_PATH_V1 = "/huntercodexs/api/v1";
    private static final String BAD_GATEWAY_URI = BASE_PATH_V1 + "/error-502";
    private static final String BAD_REQUEST_URI = BASE_PATH_V1 + "/error-400";
    private static final String CONFLICT_URI = BASE_PATH_V1 + "/error-409";
    private static final String FORBIDDEN_URI = BASE_PATH_V1 + "/error-403";
    private static final String GATEWAY_TIMEOUT_URI = BASE_PATH_V1 + "/error-504";
    private static final String INTERNAL_SERVER_URI = BASE_PATH_V1 + "/error-500";
    private static final String METHOD_NOT_ALLOWED_URI = BASE_PATH_V1 + "/error-405";
    private static final String NOT_ACCEPTABLE_URI = BASE_PATH_V1 + "/error-406";
    private static final String NOT_FOUND_URI = BASE_PATH_V1 + "/error-404";
    private static final String NOT_IMPLEMENTED_URI = BASE_PATH_V1 + "/error-501";
    private static final String PAYMENT_REQUIRED_URI = BASE_PATH_V1 + "/error-402";
    private static final String PRE_CONDITION_FAILED_URI = BASE_PATH_V1 + "/error-412";
    private static final String SERVICE_UNAVAILABLE_URI = BASE_PATH_V1 + "/error-503";
    private static final String TOO_MANY_REQUESTS_URI = BASE_PATH_V1 + "/error-429";
    private static final String UNAUTHORIZED_URI = BASE_PATH_V1 + "/error-401";
    private static final String UNPROCESSABLE_ENTITY_URI = BASE_PATH_V1 + "/error-422";
    private static final String UNSUPPORTED_MEDIA_URI = BASE_PATH_V1 + "/error-415";
    private static final String URI_TOO_LONG_URI = BASE_PATH_V1 + "/error-414";
    private static final String GENERIC_URI = BASE_PATH_V1 + "/error-generic";

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class SecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
    }

    @SpringBootApplication
    @RestController
    static class TestController {
        @GetMapping(BAD_GATEWAY_URI)
        public String error502() {
            throw new BadGatewayException("Bad Gateway from test");
        }

        @GetMapping(BAD_REQUEST_URI)
        public String error400() {
            throw new BadRequestException("Bad Request from test");
        }

        @GetMapping(CONFLICT_URI)
        public String error409() {
            throw new ConflictException("Conflict from test");
        }

        @GetMapping(FORBIDDEN_URI)
        public String error403() {
            throw new ForbiddenException("Forbidden from test");
        }

        @GetMapping(GATEWAY_TIMEOUT_URI)
        public String error504() {
            throw new GatewayTimeoutException("Gateway Timeout from test");
        }

        @GetMapping(INTERNAL_SERVER_URI)
        public String error500() {
            throw new InternalServerException("Internal Server Exception from test");
        }

        @GetMapping(METHOD_NOT_ALLOWED_URI)
        public String error405() {
            throw new MethodNotAllowedException("Method Not Allowed from test");
        }

        @GetMapping(NOT_ACCEPTABLE_URI)
        public String error406() {
            throw new NotAcceptableException("Not Acceptable from test");
        }

        @GetMapping(NOT_FOUND_URI)
        public String error404() {
            throw new NotFoundException("Not Found from test");
        }

        @GetMapping(NOT_IMPLEMENTED_URI)
        public String error501() {
            throw new NotImplementedException("Not Implemented from test");
        }

        @GetMapping(PAYMENT_REQUIRED_URI)
        public String error402() {
            throw new PaymentRequiredException("Payment Required from test");
        }

        @GetMapping(PRE_CONDITION_FAILED_URI)
        public String error412() {
            throw new PreConditionFailedException("Pre Condition Failed from test");
        }

        @GetMapping(SERVICE_UNAVAILABLE_URI)
        public String error503() {
            throw new ServiceUnavailableException("Service Unavailable from test");
        }

        @GetMapping(TOO_MANY_REQUESTS_URI)
        public String error429() {
            throw new TooManyRequestsException("Too Many Requests from test");
        }

        @GetMapping(UNAUTHORIZED_URI)
        public String error401() {
            throw new UnauthorizedException("Unauthorized from test");
        }

        @GetMapping(UNPROCESSABLE_ENTITY_URI)
        public String error422() {
            throw new UnprocessableEntityException("Unprocessable Entity from test");
        }

        @GetMapping(UNSUPPORTED_MEDIA_URI)
        public String error415() {
            throw new UnSupportedMediaTypeException("Unsupported Media Type from test");
        }

        @GetMapping(URI_TOO_LONG_URI)
        public String error414() {
            throw new UriTooLongException("Uri Too Long from test");
        }

        @GetMapping(GENERIC_URI)
        public String errorGeneric() {
            throw new RuntimeException("Unexpected failure");
        }

        @GetMapping("/method-argument-not-valid")
        public String methodArgumentNotValidException() throws MethodArgumentNotValidException {
            throw new org.springframework.web.bind.MethodArgumentNotValidException(null, null);
        }

        @PostMapping("/detach/{userId}")
        public ResponseEntity<Void> detach(
                @NotNull @PathVariable("userId") String userId,
                @Valid @RequestBody DetachBusinessAccountsOwnRequest detachBusinessAccountsOwnRequest
        ) {
            //Test jakarta validation works
            return new ResponseEntity<>(NO_CONTENT);
        }

        @Getter
        @Setter
        public static class BusinessAccountRequest {
            @NotNull @Size(min = 1)
            @JsonProperty("businessId")
            private String businessId;

            @NotNull @Size(min = 1)
            @JsonProperty("employId")
            private String employId;
            public BusinessAccountRequest() {
                super();
            }
        }

        @Getter
        @Setter
        public static class DetachBusinessAccountsOwnRequest {
            @NotNull @Valid @Size(min = 1)
            @JsonProperty("businessAccounts")
            private List<@Valid BusinessAccountRequest> businessAccounts = new ArrayList<>();
            public DetachBusinessAccountsOwnRequest() {
                super();
            }
        }
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForBadGatewayException() throws Exception {
        mockMvc.perform(get(BAD_GATEWAY_URI))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.message").value("Bad Gateway from test"))
                .andExpect(jsonPath("$.code").value(502));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForBadRequestException() throws Exception {
        mockMvc.perform(get(BAD_REQUEST_URI))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bad Request from test"))
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForConflictException() throws Exception {
        mockMvc.perform(get(CONFLICT_URI))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Conflict from test"))
                .andExpect(jsonPath("$.code").value(409));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForForbiddenException() throws Exception {
        mockMvc.perform(get(FORBIDDEN_URI))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Forbidden from test"))
                .andExpect(jsonPath("$.code").value(403));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForGatewayTimeoutException() throws Exception {
        mockMvc.perform(get(GATEWAY_TIMEOUT_URI))
                .andExpect(status().isGatewayTimeout())
                .andExpect(jsonPath("$.message").value("Gateway Timeout from test"))
                .andExpect(jsonPath("$.code").value(504));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForInternalServerException() throws Exception {
        mockMvc.perform(get(INTERNAL_SERVER_URI))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal Server Exception from test"))
                .andExpect(jsonPath("$.code").value(500));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForMethodNotAllowedException() throws Exception {
        mockMvc.perform(get(METHOD_NOT_ALLOWED_URI))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Method Not Allowed from test"))
                .andExpect(jsonPath("$.code").value(405));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForNotAcceptableException() throws Exception {
        mockMvc.perform(get(NOT_ACCEPTABLE_URI))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value("Not Acceptable from test"))
                .andExpect(jsonPath("$.code").value(406));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForNotFoundException() throws Exception {
        mockMvc.perform(get(NOT_FOUND_URI))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found from test"))
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForNotImplementedException() throws Exception {
        mockMvc.perform(get(NOT_IMPLEMENTED_URI))
                .andExpect(status().isNotImplemented())
                .andExpect(jsonPath("$.message").value("Not Implemented from test"))
                .andExpect(jsonPath("$.code").value(501));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForPaymentRequiredException() throws Exception {
        mockMvc.perform(get(PAYMENT_REQUIRED_URI))
                .andExpect(status().isPaymentRequired())
                .andExpect(jsonPath("$.message").value("Payment Required from test"))
                .andExpect(jsonPath("$.code").value(402));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForPreConditionFailedException() throws Exception {
        mockMvc.perform(get(PRE_CONDITION_FAILED_URI))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.message").value("Pre Condition Failed from test"))
                .andExpect(jsonPath("$.code").value(412));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForServiceUnavailableException() throws Exception {
        mockMvc.perform(get(SERVICE_UNAVAILABLE_URI))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Service Unavailable from test"))
                .andExpect(jsonPath("$.code").value(503));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForTooManyRequestsException() throws Exception {
        mockMvc.perform(get(TOO_MANY_REQUESTS_URI))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.message").value("Too Many Requests from test"))
                .andExpect(jsonPath("$.code").value(429));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForUnauthorizedException() throws Exception {
        mockMvc.perform(get(UNAUTHORIZED_URI))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Unauthorized from test"))
                .andExpect(jsonPath("$.code").value(401));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForUnprocessableEntityException() throws Exception {
        mockMvc.perform(get(UNPROCESSABLE_ENTITY_URI))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Unprocessable Entity from test"))
                .andExpect(jsonPath("$.code").value(422));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForUnSupportedMediaTypeException() throws Exception {
        mockMvc.perform(get(UNSUPPORTED_MEDIA_URI))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.message").value("Unsupported Media Type from test"))
                .andExpect(jsonPath("$.code").value(415));
    }

    @Test
    @WithMockUser
    void shouldReturnCustomResponseForUriTooLongException() throws Exception {
        mockMvc.perform(get(URI_TOO_LONG_URI))
                .andExpect(status().isUriTooLong())
                .andExpect(jsonPath("$.message").value("Uri Too Long from test"))
                .andExpect(jsonPath("$.code").value(414));
    }

    @Test
    @WithMockUser
    void shouldReturn500ForGenericException() throws Exception {
        mockMvc.perform(get(GENERIC_URI))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Unexpected failure")));
    }

    @Test
    @WithMockUser
    void shouldReturnMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(get("/method-argument-not-valid"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void shouldReturnMethodArgumentNotValidExceptionByJakarta() throws Exception {
        var bodyRequest = new TestController.DetachBusinessAccountsOwnRequest();
        TestController.BusinessAccountRequest businessAccountRequest = new TestController.BusinessAccountRequest();
        businessAccountRequest.setBusinessId("123");
        businessAccountRequest.setEmployId("123");
        bodyRequest.getBusinessAccounts().add(businessAccountRequest);

        //invalidating request
        bodyRequest.getBusinessAccounts().getFirst().setBusinessId(null);
        bodyRequest.getBusinessAccounts().getFirst().setEmployId(null);

        var request = convertToJSON(bodyRequest);

        mockMvc.perform(post("/detach/123")
                        .with(csrf()) //required for spring-security
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Field validation failed"))
                .andExpect(jsonPath("$.errors[0]").value("Field 'businessAccounts[0].businessId' must not be null"))
                .andExpect(jsonPath("$.errors[1]").value("Field 'businessAccounts[0].employId' must not be null"))
                .andExpect(jsonPath("$.code").value(400));
    }
}

