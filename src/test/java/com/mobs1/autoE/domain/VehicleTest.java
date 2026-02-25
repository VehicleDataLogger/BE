package com.mobs1.autoE.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mobs1.autoE.global.Enum.SlotCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VehicleTest {

    @Test
    @DisplayName("차량 타입 코드를 변경할 수 있다")
    void changeVehicleType() {
        Vehicle vehicle = new Vehicle("56다7890", SlotCategory.GENERAL);

        vehicle.changeType(SlotCategory.EV);

        assertThat(vehicle.getVehicleTypeCode()).isEqualTo(SlotCategory.EV);
    }
}
