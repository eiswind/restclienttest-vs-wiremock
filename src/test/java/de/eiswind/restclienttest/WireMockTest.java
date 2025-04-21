package de.eiswind.restclienttest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

// tag::snip[]
@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(
                name = "wild-api-servicemock",
                property = "some-wild-api.url"
        )
})
class WireMockTest {

    @InjectWireMock("wild-api-servicemock")
    WireMockServer wiremock;

    @Autowired
    SomeWildApiClient apiClient;

    @Test
    void apiTest() {

        var body = "This API is very wild. It's not safe to use it.";


        wiremock.stubFor(get("/some-wild-api")  // <1>
                .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody(body)
        ));

        var response = apiClient.invokeApi();

        assertThat(response)
                .isEqualTo(body);

        wiremock.verify(getRequestedFor(urlEqualTo("/some-wild-api")));

    }
}
// end::snip[]
