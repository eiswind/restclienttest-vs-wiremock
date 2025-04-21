package de.eiswind.restclienttest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// tag::snip[]
@RestClientTest(SomeWildApiClient.class)
@TestPropertySource(
        properties = {"some-wild-api.url=http://localhost"}
)
public class MockRestServiceTest {

    @Autowired
    MockRestServiceServer mockServer;

    @Autowired
    SomeWildApiClient apiClient;

    @Test
    void apiTest() {

        var body = "This API is very wild. It's not safe to use it.";

        mockServer
                .expect(requestTo("http://localhost/some-wild-api"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(body, MediaType.TEXT_PLAIN));

        var result = apiClient.invokeApi();

        assertThat(result).isEqualTo(body);

        mockServer.verify(); // <1>
    }
}
// end::snip[]
