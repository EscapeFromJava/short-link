package com.reske.shortlink.service;

import com.reske.shortlink.model.RequestDto;
import com.reske.shortlink.model.ShortLinkInfo;
import com.reske.shortlink.util.SequenceGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ShortLinkService {

    @Value("${server.port}")
    private String port;

    public static final Map<String, ShortLinkInfo> SHORT_LINK_MAP = new HashMap<>();
    // www.google.com => 8vaWBK

    public String generate(RequestDto request) {
        String url = request.url();
        if (SHORT_LINK_MAP.containsKey(url)) {
            return SHORT_LINK_MAP.get(url).sequence();
        }

        String sequence = SequenceGeneratorUtil.generate();
        SHORT_LINK_MAP.put(url, new ShortLinkInfo(sequence, LocalDateTime.now()));
        return "http://localhost:" + port + "/" + sequence;
    }

    public String getLongLink(String url) {
        for (String key : SHORT_LINK_MAP.keySet()) {
            if (SHORT_LINK_MAP.get(key).sequence().equals(url)) {
                return key;
            }
        }
        throw new RuntimeException("Полная ссылка для = " + url + " не найдена");
    }

    @Scheduled(
            timeUnit = TimeUnit.SECONDS,
            fixedDelay = 30,
            initialDelay = 10)
    public void clearMap() {
        log.info("Запуск шедулера на очистку устаревших ссылок");
        for (String key : SHORT_LINK_MAP.keySet()) {
            ShortLinkInfo info = SHORT_LINK_MAP.get(key);
            if (info.dateTime().plusSeconds(3).isBefore(LocalDateTime.now())) {
                log.info("Ссылка для {} устарела и будет удалена", key);
                SHORT_LINK_MAP.remove(key);
            }
        }
    }

}
