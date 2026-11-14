package com.svetlana.deepfake_gateway.detector;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Component
@ConditionalOnProperty(name = "ai.detector.mode", havingValue = "heuristic", matchIfMissing = true)
public class HeuristicDeepfakeDetector implements DeepfakeDetector {

    @Override
    public DetectionResult analyze(MultipartFile file) {
        String name = file.getOriginalFilename() != null ? file.getOriginalFilename().toLowerCase() : "";

        if (name.contains("fake") || name.contains("edited")) {
            return new DetectionResult(true, 0.96);
        }
        if (file.getSize() < 10_000) {
            return new DetectionResult(true, 0.88);
        }
        return new DetectionResult(false, 0.94);
    }
}