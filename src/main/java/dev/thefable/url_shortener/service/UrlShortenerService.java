package dev.thefable.url_shortener.service;

import dev.thefable.url_shortener.model.Url;
import dev.thefable.url_shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String url) {
        Url urlEntity = new Url(url);
        urlRepository.saveAndFlush(urlEntity);

        long id = urlEntity.getId();
        String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        long base = 62;
        int MIN_LENGTH = 4;
        StringBuilder shortUrlPart = new StringBuilder();

        if (id == 0) {
            shortUrlPart.append(charSet.charAt(0));
        } else {
            while (id > 0) {
                shortUrlPart.append(charSet.charAt((int)(id % base)));
                id /= base;
            }
        }

        shortUrlPart.reverse();

        while(shortUrlPart.length() < MIN_LENGTH) {
            shortUrlPart.insert(0, 'a');
        }

        String shortCode = shortUrlPart.toString();



        urlEntity.setShortUrl(shortCode);
        urlRepository.save(urlEntity);

        return shortCode;
    }

    public String findByShortUrl(String shortUrl) {

        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(()->new RuntimeException("Short URL not found"));

        return url.getUrl();
    }
}
