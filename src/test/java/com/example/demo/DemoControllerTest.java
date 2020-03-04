package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DemoController.class)
class DemoControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void text() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/text"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("demo"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.header().longValue(HttpHeaders.CONTENT_LENGTH, 4))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"0fe01ce2a7fbac8fafaed7c982a04e229\""))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CACHE_CONTROL, "max-age=3600"));
    }

    @Test
    void text_not_modified() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/text").header(HttpHeaders.IF_NONE_MATCH, "\"0fe01ce2a7fbac8fafaed7c982a04e229\""))
                .andExpect(MockMvcResultMatchers.status().isNotModified())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.header().longValue(HttpHeaders.CONTENT_LENGTH, 4))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"0fe01ce2a7fbac8fafaed7c982a04e229\""))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CACHE_CONTROL, "max-age=3600"));
    }

    @Test
    void stream() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/stream"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("demo"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.header().longValue(HttpHeaders.CONTENT_LENGTH, 123))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"demo\""))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CACHE_CONTROL, "max-age=3600"));
    }

    @Test
    void stream_not_modified() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/demo/stream").header(HttpHeaders.IF_NONE_MATCH, "\"demo\""))
                .andExpect(MockMvcResultMatchers.status().isNotModified())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.header().longValue(HttpHeaders.CONTENT_LENGTH, 123))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"demo\""))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CACHE_CONTROL, "max-age=3600"));
    }
}