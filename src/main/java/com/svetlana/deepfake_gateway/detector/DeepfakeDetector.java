package com.svetlana.deepfake_gateway.detector;

import org.springframework.web.multipart.MultipartFile;

public interface DeepfakeDetector {
    DetectionResult analyze(MultipartFile file);
}