package com.GuideAPP_AKS.QR;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonIdQRCodeRepo extends JpaRepository<CommonIdQRCode, Long> {

    boolean existsByMalIdAndEngId(String malId, String engId);

    Optional<CommonIdQRCode> findByMalIdAndEngId(String malId, String engId);

    Optional<CommonIdQRCode> findByCommonId(String commonId);
}
