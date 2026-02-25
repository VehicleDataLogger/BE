package com.mobs1.autoE.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SlotOccupancyTest {

    private final Zone zone = new Zone("B");
    private final SlotType slotType = new SlotType("GENERAL");
    private final ParkingSlot slot = new ParkingSlot(zone, slotType, "B1");
    private final Vehicle vehicle = new Vehicle("12가3456", null);

    @Test
    @DisplayName("빈 슬롯을 점유하면 차량, 세션, 시간 정보가 설정된다")
    void occupyEmptySlot() {
        SlotOccupancy occupancy = new SlotOccupancy();
        LocalDateTime now = LocalDateTime.now();
        ParkingHistory session = ParkingHistory.start(zone, slot, vehicle, now);

        occupancy.occupy(vehicle, session, now);

        assertThat(occupancy.isOccupied()).isTrue();
        assertThat(occupancy.getVehicle()).isEqualTo(vehicle);
        assertThat(occupancy.getCurrentSession()).isEqualTo(session);
        assertThat(occupancy.getOccupiedSince()).isEqualTo(now);
        assertThat(occupancy.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("이미 점유된 슬롯을 다시 점유하려 하면 예외를 던진다")
    void occupyAlreadyOccupiedThrows() {
        SlotOccupancy occupancy = new SlotOccupancy();
        LocalDateTime now = LocalDateTime.now();
        ParkingHistory session = ParkingHistory.start(zone, slot, vehicle, now);
        occupancy.occupy(vehicle, session, now);

        assertThatThrownBy(() -> occupancy.occupy(vehicle, session, now.plusMinutes(1)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 점유된 슬롯입니다.");
    }

    @Test
    @DisplayName("점유된 슬롯을 해제하면 상태와 관련 참조를 초기화한다")
    void releaseOccupiedSlot() {
        SlotOccupancy occupancy = new SlotOccupancy();
        LocalDateTime now = LocalDateTime.now();
        ParkingHistory session = ParkingHistory.start(zone, slot, vehicle, now);
        occupancy.occupy(vehicle, session, now);

        LocalDateTime releaseTime = now.plusMinutes(5);
        occupancy.release(releaseTime);

        assertThat(occupancy.isOccupied()).isFalse();
        assertThat(occupancy.getVehicle()).isNull();
        assertThat(occupancy.getCurrentSession()).isNull();
        assertThat(occupancy.getOccupiedSince()).isNull();
        assertThat(occupancy.getUpdatedAt()).isEqualTo(releaseTime);
    }
}
