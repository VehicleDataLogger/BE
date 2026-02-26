package com.mobs1.autoE.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.mobs1.autoE.domain.zone.dto.TypeAvailabilityResponse;
import com.mobs1.autoE.domain.zone.service.ZoneAvailabilityService;
import com.mobs1.autoE.domain.zone.dto.ZoneAvailabilityResponse;
import com.mobs1.autoE.domain.zone.entity.Zone;
import com.mobs1.autoE.domain.zone.entity.ZoneAvailability;
import com.mobs1.autoE.global.apiResponse.exception.BusinessException;
import com.mobs1.autoE.global.enums.SlotCategory;
import com.mobs1.autoE.repository.ZoneAvailabilityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ZoneAvailabilityServiceTest {

    private ZoneAvailabilityRepository repository;
    private ZoneAvailabilityService service;

    private ZoneAvailability availability;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ZoneAvailabilityRepository.class);
        service = new ZoneAvailabilityService(repository);

        Zone zone = new Zone("A");
        LocalDateTime now = LocalDateTime.now();
        availability = new ZoneAvailability(zone,
                100, 52, 48,
                80, 68, 12,
                10, 7, 3,
                10, 7, 3, now);
    }

    @Test
    @DisplayName("전체 존의 여석 수를 목록으로 반환")
    void getAllZonesAvailability() {
        when(repository.findAll()).thenReturn(List.of(availability));

        List<ZoneAvailabilityResponse> result = service.getAllZonesAvailability();

        assertThat(result).hasSize(1);
        ZoneAvailabilityResponse view = result.getFirst();
        assertThat(view.zoneName()).isEqualTo("A");
        assertThat(view.availableSlots()).isEqualTo(48);
        assertThat(view.generalAvailable()).isEqualTo(12);
        assertThat(view.evAvailable()).isEqualTo(3);
        assertThat(view.disabledAvailable()).isEqualTo(3);
    }

    @Test
    @DisplayName("존 ID로 여석 수를 조회")
    void getZoneAvailability() {
        when(repository.findByZoneId(null)).thenReturn(Optional.of(availability));

        ZoneAvailabilityResponse view = service.getZoneAvailability(null);

        assertThat(view.availableSlots()).isEqualTo(48);
    }

    @Test
    @DisplayName("존이 존재하지 않으면 404 예외처리")
    void getZoneAvailabilityNotFound() {
        when(repository.findByZoneId(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getZoneAvailability(99))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("타입별 여석 수를 GENERAL/EV/DISABLED로 조회")
    void getZoneTypeAvailability() {
        when(repository.findByZoneId(1)).thenReturn(Optional.of(availability));

        TypeAvailabilityResponse general = service.getZoneTypeAvailability(1, SlotCategory.GENERAL);
        TypeAvailabilityResponse ev = service.getZoneTypeAvailability(1, SlotCategory.EV);
        TypeAvailabilityResponse disabled = service.getZoneTypeAvailability(1, SlotCategory.DISABLED);

        assertThat(general.available()).isEqualTo(12);
        assertThat(ev.available()).isEqualTo(3);
        assertThat(disabled.available()).isEqualTo(3);
    }
}
