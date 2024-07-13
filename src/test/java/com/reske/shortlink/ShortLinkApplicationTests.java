package com.reske.shortlink;

import com.reske.shortlink.model.RequestDto;
import com.reske.shortlink.model.ShortLinkInfo;
import com.reske.shortlink.service.ShortLinkService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;


class ShortLinkApplicationTests extends BaseIntegrationTest {

    @Test
    void checkLinkGenerator() {
        //given
        var request = new RequestDto("https://www.google.com");

        //when
        var response = webTestClient
                .post()
                .uri("/shl")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        //then
        assertThat(response).isNotNull();
    }

    @Test
    void checkShortLink() {
        //given
        var shortLink = "http://localhost:8080/qweasd";
        ShortLinkService.SHORT_LINK_MAP.put("https://www.google.com", new ShortLinkInfo(shortLink, LocalDateTime.now()));

        //when
        var response = webTestClient
                .get()
                .uri(shortLink)
                .exchange();

        //then
        response.expectStatus().isOk();
    }

}
