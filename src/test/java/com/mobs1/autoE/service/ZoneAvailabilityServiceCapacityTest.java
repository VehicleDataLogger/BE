package com.mobs1.autoE.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.mobs1.autoE.domain.zone.entity.Zone;
import com.mobs1.autoE.domain.zone.entity.ZoneAvailability;
import com.mobs1.autoE.domain.zone.service.ZoneAvailabilityService;
import com.mobs1.autoE.global.enums.SlotCategory;
import com.mobs1.autoE.domain.zone.repository.ZoneAvailabilityRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * 전체 존에서 타입별 가용 공간 합계를 계산하는 서비스 요구사항을 먼저 정의한다.
 */
class ZoneAvailabilityServiceCapacityTest {

    private ZoneAvailabilityRepository repository;
    private ZoneAvailabilityService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ZoneAvailabilityRepository.class);
        service = new ZoneAvailabilityService(repository);
    }

    @Test
    @DisplayName("전체 존에서 타입별 빈자리 를 반환한다")
    void getGlobalAvailableByType() {
        LocalDateTime now = LocalDateTime.now();
        ZoneAvailability a = new ZoneAvailability(new Zone("A"),
                100, 50, 50,
                60, 20, 40,
                20, 10, 10,
                20, 20, 0, now);
        ZoneAvailability b = new ZoneAvailability(new Zone("B"),
                50, 30, 20,
                30, 10, 20,
                10, 7, 3,
                10, 13, -3, now);

        when(repository.findAll()).thenReturn(List.of(a, b));

        assertThat(service.getTotalAvailableByType(SlotCategory.GENERAL)).isEqualTo(40 + 20);
        assertThat(service.getTotalAvailableByType(SlotCategory.EV)).isEqualTo(10 + 3);
        // 데이터 이상 여부 검증용
        assertThat(service.getTotalAvailableByType(SlotCategory.DISABLED)).isEqualTo(0 + (-3));
    }
}
