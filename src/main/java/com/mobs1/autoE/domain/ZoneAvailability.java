package com.mobs1.autoE.domain;

import com.mobs1.autoE.domain.Zone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ZONE_AVAILABILITY")
public class ZoneAvailability {

    @Id
    @Column(name = "zone_id")
    private Integer id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(name = "total_slots", nullable = false)
    private int totalSlots;

    @Column(name = "occupied_slots", nullable = false)
    private int occupiedSlots;

    @Column(name = "available_slots", nullable = false)
    private int availableSlots;

    @Column(name = "general_total", nullable = false)
    private int generalTotal;

    @Column(name = "general_occupied", nullable = false)
    private int generalOccupied;

    @Column(name = "general_available", nullable = false)
    private int generalAvailable;

    @Column(name = "ev_total", nullable = false)
    private int evTotal;

    @Column(name = "ev_occupied", nullable = false)
    private int evOccupied;

    @Column(name = "ev_available", nullable = false)
    private int evAvailable;

    @Column(name = "disabled_total", nullable = false)
    private int disabledTotal;

    @Column(name = "disabled_occupied", nullable = false)
    private int disabledOccupied;

    @Column(name = "disabled_available", nullable = false)
    private int disabledAvailable;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
