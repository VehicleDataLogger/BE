package com.mobs1.autoE.repository;

import com.mobs1.autoE.domain.zone.entity.ZoneAvailability;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneAvailabilityRepository extends JpaRepository<ZoneAvailability, Integer> {
    Optional<ZoneAvailability> findByZoneId(Integer zoneId);
}
