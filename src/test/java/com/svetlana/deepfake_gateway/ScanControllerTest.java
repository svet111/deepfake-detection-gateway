package com.svetlana.deepfake_gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "ai.detector.mode=heuristic")
class ScanControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldFlagSuspiciousFileAsDeepfake() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "fake_video.mp4",
                "video/mp4",
                "dummy video content".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/scans").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deepfake").value(true))
                .andExpect(jsonPath("$.confidenceScore").value(0.96));
    }

    @Test
    void shouldRejectUnsupportedFileType() throws Exception {
        MockMultipartFile textFile = new MockMultipartFile(
                "file",
                "notes.txt",
                "text/plain",
                "just some text".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/scans").file(textFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Only image and video files are supported."));
    }
}