package com.reske.shortlink.model;

import java.time.LocalDateTime;

public record ShortLinkInfo(
        String sequence,
        LocalDateTime dateTime
) {
}
