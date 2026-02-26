package com.mobs1.autoE.service;

import com.mobs1.autoE.domain.zone.entity.ZoneAvailability;

public record ZoneAvailabilityView(
        Integer zoneId,
        String zoneName,
        int totalSlots,
        int occupiedSlots,
        int availableSlots,
        int generalAvailable,
        int evAvailable,
        int disabledAvailable
) {
    public static ZoneAvailabilityView from(ZoneAvailability availability) {
        return new ZoneAvailabilityView(
                availability.getId(),
                availability.getZone().getName(),
                availability.getTotalSlots(),
                availability.getOccupiedSlots(),
                availability.getAvailableSlots(),
                availability.getGeneralAvailable(),
                availability.getEvAvailable(),
                availability.getDisabledAvailable()
        );
    }
}
