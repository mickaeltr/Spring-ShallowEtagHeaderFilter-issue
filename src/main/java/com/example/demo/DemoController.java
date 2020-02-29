package com.example.demo;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/text")
    public ResponseEntity<String> text() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body("demo");
    }

    @GetMapping("/stream")
    public ResponseEntity<InputStreamResource> stream(HttpServletRequest request) {
        // Let's say I have a big input stream for which I want to:
        // - provide the Content-Length and ETag (to avoid a heavy calculation by ShallowEtagHeaderFilter)
        // - allow caching through If-None-Match
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .eTag("demo")
                .contentLength(123)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(new InputStreamResource(new ByteArrayInputStream("demo".getBytes())));
    }

}
