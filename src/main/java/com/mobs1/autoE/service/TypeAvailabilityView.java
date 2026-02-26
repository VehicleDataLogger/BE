package com.mobs1.autoE.service;

import com.mobs1.autoE.global.enums.SlotCategory;

public record TypeAvailabilityView(
        Integer zoneId,
        SlotCategory category,
        int total,
        int occupied,
        int available
) {
}
