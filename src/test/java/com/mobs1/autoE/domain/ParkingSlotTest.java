package com.mobs1.autoE.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingSlotTest {

    private final Zone zone = new Zone("A");
    private final SlotType slotType = new SlotType("EV");

    @Test
    @DisplayName("신규 슬롯은 기본적으로 활성 상태다")
    void defaultActiveIsTrue() {
        ParkingSlot slot = new ParkingSlot(zone, slotType, "A1");

        assertThat(slot.isActive()).isTrue();
    }

    @Test
    @DisplayName("슬롯 활성/비활성 상태를 의도된 메서드로 전환한다")
    void activateAndDeactivate() {
        ParkingSlot slot = new ParkingSlot(zone, slotType, "A2");

        slot.deactivate();
        assertThat(slot.isActive()).isFalse();

        slot.activate();
        assertThat(slot.isActive()).isTrue();
    }
}
