package org.fluentjava.volundr.springbootexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClockControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void unauthorizedGetClockRequiresSignIn() {
        ResponseEntity<String> response = template.getForEntity("/clock", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Please sign in");
    }
}
