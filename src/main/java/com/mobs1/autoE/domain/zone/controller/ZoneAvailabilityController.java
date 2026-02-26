package com.mobs1.autoE.domain.zone.controller;

import com.mobs1.autoE.domain.zone.dto.TypeAvailabilityResponse;
import com.mobs1.autoE.domain.zone.dto.ZoneAvailabilityResponse;
import com.mobs1.autoE.domain.zone.service.ZoneAvailabilityService;
import com.mobs1.autoE.global.apiResponse.code.SuccessCode;
import com.mobs1.autoE.global.apiResponse.response.ApiResponse;
import com.mobs1.autoE.global.enums.SlotCategory;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zones")
public class ZoneAvailabilityController {

    private final ZoneAvailabilityService zoneAvailabilityService;

    public ZoneAvailabilityController(ZoneAvailabilityService zoneAvailabilityService) {
        this.zoneAvailabilityService = zoneAvailabilityService;
    }

    @GetMapping("/availability")
    public ResponseEntity<ApiResponse<List<ZoneAvailabilityResponse>>> getAllZonesAvailability() {
        return ok(zoneAvailabilityService.getAllZonesAvailability());
    }

    @GetMapping("/{zoneId}/availability")
    public ResponseEntity<ApiResponse<ZoneAvailabilityResponse>> getZoneAvailability(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneAvailability(zoneId));
    }

    @GetMapping("/{zoneId}/availability/count")
    public ResponseEntity<ApiResponse<Integer>> getZoneAvailableCount(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneAvailableCount(zoneId));
    }

    @GetMapping("/{zoneId}/availability/general")
    public ResponseEntity<ApiResponse<TypeAvailabilityResponse>> getGeneralAvailability(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneTypeAvailability(zoneId, SlotCategory.GENERAL));
    }

    @GetMapping("/{zoneId}/availability/general/count")
    public ResponseEntity<ApiResponse<Integer>> getGeneralAvailableCount(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneTypeAvailableCount(zoneId, SlotCategory.GENERAL));
    }

    @GetMapping("/{zoneId}/availability/ev")
    public ResponseEntity<ApiResponse<TypeAvailabilityResponse>> getEvAvailability(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneTypeAvailability(zoneId, SlotCategory.EV));
    }

    @GetMapping("/{zoneId}/availability/ev/count")
    public ResponseEntity<ApiResponse<Integer>> getEvAvailableCount(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneTypeAvailableCount(zoneId, SlotCategory.EV));
    }

    @GetMapping("/{zoneId}/availability/disabled")
    public ResponseEntity<ApiResponse<TypeAvailabilityResponse>> getDisabledAvailability(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneTypeAvailability(zoneId, SlotCategory.DISABLED));
    }

    @GetMapping("/{zoneId}/availability/disabled/count")
    public ResponseEntity<ApiResponse<Integer>> getDisabledAvailableCount(@PathVariable Integer zoneId) {
        return ok(zoneAvailabilityService.getZoneTypeAvailableCount(zoneId, SlotCategory.DISABLED));
    }

    private <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.SUCCESS_READ, data));
    }
}
