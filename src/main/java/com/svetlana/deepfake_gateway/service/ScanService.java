package com.svetlana.deepfake_gateway.service;

import java.util.List;
import com.svetlana.deepfake_gateway.detector.DeepfakeDetector;
import com.svetlana.deepfake_gateway.detector.DetectionResult;
import com.svetlana.deepfake_gateway.model.ScanRecord;
import com.svetlana.deepfake_gateway.repository.ScanRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ScanService {

    private final ScanRepository scanRepository;
    private final DeepfakeDetector deepfakeDetector;

    public ScanService(ScanRepository scanRepository, DeepfakeDetector deepfakeDetector) {
        this.scanRepository = scanRepository;
        this.deepfakeDetector = deepfakeDetector;
    }

    public ScanRecord processScan(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file cannot be empty.");
        }

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.startsWith("video/"))) {
            throw new IllegalArgumentException("Only image and video files are supported.");
        }

        DetectionResult result = deepfakeDetector.analyze(file);

        ScanRecord record = new ScanRecord();
        record.setFileName(file.getOriginalFilename());
        record.setDeepfake(result.isDeepfake());
        record.setConfidenceScore(result.confidenceScore());

        return scanRepository.save(record);
    }

    public ScanRecord getScanById(Long id) {
        return scanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scan record not found with ID: " + id));
    }

    public List<ScanRecord> getAllScans() {
        return scanRepository.findAll();
    }

    public List<ScanRecord> getScans(Boolean deepfake) {
        if (deepfake != null) {
            return scanRepository.findByDeepfake(deepfake);
        }
        return scanRepository.findAll();
    }
}