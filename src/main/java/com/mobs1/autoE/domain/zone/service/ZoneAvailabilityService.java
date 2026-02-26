package com.mobs1.autoE.domain.zone.service;

import com.mobs1.autoE.domain.zone.dto.TypeAvailabilityResponse;
import com.mobs1.autoE.domain.zone.dto.ZoneAvailabilityResponse;
import com.mobs1.autoE.domain.zone.entity.ZoneAvailability;
import com.mobs1.autoE.global.apiResponse.code.ErrorCode;
import com.mobs1.autoE.global.apiResponse.exception.BusinessException;
import com.mobs1.autoE.global.enums.SlotCategory;
import com.mobs1.autoE.domain.zone.repository.ZoneAvailabilityRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ZoneAvailabilityService {

    private final ZoneAvailabilityRepository availabilityRepository;

    public ZoneAvailabilityService(ZoneAvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    // 전체 여석 조회 메서드
    public List<ZoneAvailabilityResponse> getAllZonesAvailability() {
        return availabilityRepository.findAll()
                .stream()
                .map(ZoneAvailabilityResponse::from)
                .collect(Collectors.toList());
    }


    public ZoneAvailabilityResponse getZoneAvailability(Integer zoneId) {
        ZoneAvailability availability = availabilityRepository.findByZoneId(zoneId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ZONE_NOT_FOUND));
        return ZoneAvailabilityResponse.from(availability);
    }

    public TypeAvailabilityResponse getZoneTypeAvailability(Integer zoneId, SlotCategory category) {
        ZoneAvailability availability = availabilityRepository.findByZoneId(zoneId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ZONE_NOT_FOUND));
        return toTypeAvailabilityResponse(availability, category);
    }

    // A 존의 전체 여석 수 반환
    public int getZoneAvailableCount(Integer zoneId) {
        return availabilityRepository.findAvailableSlotsByZoneId(zoneId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ZONE_NOT_FOUND));
    }

    // A 존의 타입 별 여석 수 반환
    public int getZoneTypeAvailableCount(Integer zoneId, SlotCategory category) {
        return (switch (category) {
            case GENERAL -> availabilityRepository.findGeneralAvailableByZoneId(zoneId);
            case EV -> availabilityRepository.findEvAvailableByZoneId(zoneId);
            case DISABLED -> availabilityRepository.findDisabledAvailableByZoneId(zoneId);
            default -> throw new BusinessException(ErrorCode.SLOT_CATEGORY_NOT_SUPPORTED);
        }).orElseThrow(() -> new BusinessException(ErrorCode.ZONE_NOT_FOUND));
    }

    // 타입 별 정보조회 메서드
    private TypeAvailabilityResponse toTypeAvailabilityResponse(ZoneAvailability availability, SlotCategory category) {
        if (category == null) {
            throw new BusinessException(ErrorCode.SLOT_CATEGORY_NOT_SUPPORTED);
        }

        return switch (category) {
            case GENERAL -> new TypeAvailabilityResponse(
                    availability.getId(),
                    category,
                    availability.getGeneralTotal(),
                    availability.getGeneralOccupied(),
                    availability.getGeneralAvailable());
            case EV -> new TypeAvailabilityResponse(
                    availability.getId(),
                    category,
                    availability.getEvTotal(),
                    availability.getEvOccupied(),
                    availability.getEvAvailable());
            case DISABLED -> new TypeAvailabilityResponse(
                    availability.getId(),
                    category,
                    availability.getDisabledTotal(),
                    availability.getDisabledOccupied(),
                    availability.getDisabledAvailable());
        };
    }
}
