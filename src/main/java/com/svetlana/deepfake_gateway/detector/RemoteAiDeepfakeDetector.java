package com.svetlana.deepfake_gateway.detector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Component
@ConditionalOnProperty(name = "ai.detector.mode", havingValue = "remote")
public class RemoteAiDeepfakeDetector implements DeepfakeDetector {

    private final RestClient restClient;

    public RemoteAiDeepfakeDetector(@Value("${ai.detector.url}") String aiServiceUrl) {
        this.restClient = RestClient.builder().baseUrl(aiServiceUrl).build();
    }

    @Override
    public DetectionResult analyze(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        return restClient.post()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(DetectionResult.class);
    }
}