package com.mobs1.autoE.service;

import com.mobs1.autoE.domain.zone.entity.ZoneAvailability;
import com.mobs1.autoE.global.enums.SlotCategory;
import com.mobs1.autoE.repository.ZoneAvailabilityRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class ZoneAvailabilityService {

    private final ZoneAvailabilityRepository availabilityRepository;

    public ZoneAvailabilityService(ZoneAvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public List<ZoneAvailabilityView> getAllZonesAvailability() {
        throw new UnsupportedOperationException("미구현");
    }

    public ZoneAvailabilityView getZoneAvailability(Integer zoneId) {
        throw new UnsupportedOperationException("미구현");
    }

    public TypeAvailabilityView getZoneTypeAvailability(Integer zoneId, SlotCategory category) {
        throw new UnsupportedOperationException("미구현");
    }

    /**
     * 특정 존의 전체 여석 수만 반환.
     */
    public int getZoneAvailableCount(Integer zoneId) {
        throw new UnsupportedOperationException("미구현");
    }

    /**
     * 특정 존의 타입별 여석 수만 반환.
     */
    public int getZoneTypeAvailableCount(Integer zoneId, SlotCategory category) {
        throw new UnsupportedOperationException("미구현");
    }
}
