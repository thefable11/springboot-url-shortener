package dev.thefable.url_shortener.controller;

import dev.thefable.url_shortener.dto.UrlRequest;
import dev.thefable.url_shortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @PostMapping
    public String shortenUrl(@RequestBody UrlRequest urlRequest) {
        return urlShortenerService.shortenUrl(urlRequest.getUrl());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String url = urlShortenerService.findByShortUrl(shortCode);

        return ResponseEntity.status(302)
                .header("Location", url)
                .build();
    }
}
