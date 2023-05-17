package org.fluentjava.volundr.springbootexample;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClockApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void clockShouldReturnDefaultMessage() throws Exception {
        long now = Clock.systemUTC().millis();
        String encoded = Base64.getEncoder().encodeToString("Aladdin:open sesame".getBytes(StandardCharsets.UTF_8));
        this.mockMvc.perform(get("/clock").header("Authorization", "Basic " + encoded).param("from", "home"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(new Matcher<String>() {
                    @Override
                    public void describeTo(Description description) {
                    }

                    @Override
                    public boolean matches(Object actual) {
                        return Long.parseLong((String) actual) > now;
                    }

                    @Override
                    public void describeMismatch(Object actual, Description mismatchDescription) {
                    }

                    @Override
                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
                    }
                })).andExpect(jsonPath("$.from").value("home"));
    }

}
