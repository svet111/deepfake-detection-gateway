package com.svetlana.deepfake_gateway.controller;

import java.util.List;
import com.svetlana.deepfake_gateway.model.ScanRecord;
import com.svetlana.deepfake_gateway.service.ScanService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1")
public class ScanController {

    private final ScanService scanService;

    public ScanController(ScanService scanService) {
        this.scanService = scanService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Deepfake Gateway is running!";
    }

    @PostMapping("/scans")
    public ScanRecord createScan(@RequestParam("file") MultipartFile file) {
        return scanService.processScan(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScanRecord> getScanById(@PathVariable Long id) {
        return ResponseEntity.ok(scanService.getScanById(id));
    }

    @GetMapping("/scans")
    public List<ScanRecord> getScans() {
        return scanService.getAllScans();
    }

    @GetMapping
    public ResponseEntity<List<ScanRecord>> getScans(
            @RequestParam(required = false) Boolean deepfake) {
        return ResponseEntity.ok(scanService.getScans(deepfake));
    }
}