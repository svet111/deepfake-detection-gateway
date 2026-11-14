package com.svetlana.deepfake_gateway.repository;
import java.util.List;
import com.svetlana.deepfake_gateway.model.ScanRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<ScanRecord, Long> {
    List<ScanRecord> findByDeepfake(boolean deepfake);
}