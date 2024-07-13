package com.reske.shortlink.controller;

import com.reske.shortlink.model.RequestDto;
import com.reske.shortlink.model.ShortLinkInfo;
import com.reske.shortlink.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @GetMapping("/{url}")
    public RedirectView getLongLink(@PathVariable String url) {
        String longLink = shortLinkService.getLongLink(url);
        return new RedirectView(longLink);
    }

    @PostMapping(value = "/shl")
    public String getShortLink(@RequestBody RequestDto request) {
        return shortLinkService.generate(request);
    }

    @GetMapping("/map")
    public Map<String, ShortLinkInfo> getShortLinkMap() {
        return ShortLinkService.SHORT_LINK_MAP;
    }

}
