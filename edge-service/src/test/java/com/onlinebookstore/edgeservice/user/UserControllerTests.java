package com.onlinebookstore.edgeservice.user;

import com.onlinebookstore.edgeservice.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTests {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;

    @Test
    void whenNotAuthenticatedThen401() {
        webTestClient
                .get()
                .uri("/user")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void whenAuthenticatedThenReturnUser() {
        User expectedUser = new User("jon.snow", "Jon", "Snow", List.of("employee", "customer"));

        webTestClient
                .mutateWith(ConfigureMockOidcLogin(expectedUser))
                .get()
                .uri("/user")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(User.class)
                .value(user -> assertThat(user).isEqualTo(expectedUser));
    }

    private SecurityMockServerConfigurers.OidcLoginMutator ConfigureMockOidcLogin(User expectedUser) {
        return SecurityMockServerConfigurers.mockOidcLogin().idToken(builder -> {
           builder.claim(StandardClaimNames.PREFERRED_USERNAME, expectedUser.username());
           builder.claim(StandardClaimNames.GIVEN_NAME, expectedUser.firstname());
           builder.claim(StandardClaimNames.FAMILY_NAME, expectedUser.lastname());
           builder.claim("roles", expectedUser.roles());
        });
    }
}
