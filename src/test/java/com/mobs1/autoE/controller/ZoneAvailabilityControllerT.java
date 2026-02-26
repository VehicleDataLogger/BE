package com.mobs1.autoE.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mobs1.autoE.domain.zone.controller.ZoneAvailabilityController;
import com.mobs1.autoE.domain.zone.dto.TypeAvailabilityResponse;
import com.mobs1.autoE.domain.zone.dto.ZoneAvailabilityResponse;
import com.mobs1.autoE.domain.zone.service.ZoneAvailabilityService;
import com.mobs1.autoE.global.apiResponse.handler.GlobalExceptionHandler;
import com.mobs1.autoE.global.enums.SlotCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ZoneAvailabilityControllerT {

    private MockMvc mockMvc;
    private ZoneAvailabilityService service;

    private ZoneAvailabilityResponse zoneA;
    private TypeAvailabilityResponse general;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(ZoneAvailabilityService.class);
        ZoneAvailabilityController controller = new ZoneAvailabilityController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        zoneA = new ZoneAvailabilityResponse(1, "A", 100, 52, 48, 12, 3, 3);
        general = new TypeAvailabilityResponse(1, SlotCategory.GENERAL, 80, 68, 12);
    }


    @Test
    @DisplayName("A 존 전체 여석 정보와 num 반환")
    void getZoneAvailability() throws Exception {
        when(service.getZoneAvailability(1)).thenReturn(zoneA);
        when(service.getZoneAvailableCount(1)).thenReturn(48);

        mockMvc.perform(get("/zones/1/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.availableSlots").value(48));

        mockMvc.perform(get("/zones/1/availability/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(48));
    }

    @Test
    @DisplayName("타입별 여석 정보/num 반환")
    void getTypeAvailability() throws Exception {
        when(service.getZoneTypeAvailability(1, SlotCategory.GENERAL)).thenReturn(general);
        when(service.getZoneTypeAvailableCount(1, SlotCategory.GENERAL)).thenReturn(12);

        mockMvc.perform(get("/zones/1/availability/general"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.available").value(12))
                .andExpect(jsonPath("$.data.category").value("GENERAL"));

        mockMvc.perform(get("/zones/1/availability/general/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(12));
    }

    @Test
    @DisplayName("존이 없으면 404 에러 반환")
    void zoneNotFound() throws Exception {
        when(service.getZoneAvailability(99)).thenThrow(new com.mobs1.autoE.global.apiResponse.exception.BusinessException(
                com.mobs1.autoE.global.apiResponse.code.ErrorCode.ZONE_NOT_FOUND));

        mockMvc.perform(get("/zones/99/availability"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("E100"));
    }

    @Test
    @DisplayName("지원하지 않는 타입이면 400 에러 반환")
    void unsupportedCategory() throws Exception {
        when(service.getZoneTypeAvailability(1, SlotCategory.GENERAL))
                .thenThrow(new com.mobs1.autoE.global.apiResponse.exception.BusinessException(
                        com.mobs1.autoE.global.apiResponse.code.ErrorCode.SLOT_CATEGORY_NOT_SUPPORTED));

        mockMvc.perform(get("/zones/1/availability/general"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("E101"));
    }
}
