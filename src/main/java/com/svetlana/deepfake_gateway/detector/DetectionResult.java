package com.svetlana.deepfake_gateway.detector;

public record DetectionResult(boolean isDeepfake, double confidenceScore) {}