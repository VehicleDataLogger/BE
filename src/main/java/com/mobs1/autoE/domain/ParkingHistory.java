package com.mobs1.autoE.domain;

import com.mobs1.autoE.global.Enum.ParkingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "PARKING_history", indexes = {
        @Index(name = "idx_zone_status", columnList = "zone_id, status"),
        @Index(name = "idx_slot_status", columnList = "slot_id, status"),
        @Index(name = "idx_vehicle_status", columnList = "vehicle_plate, status")
})
public class ParkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "slot_id", nullable = false)
    private ParkingSlot slot;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_plate", referencedColumnName = "vehicle_num", nullable = false)
    private Vehicle vehicle;

    @Column(name = "entry_at", nullable = false)
    private LocalDateTime entryAt;

    @Column(name = "exit_at")
    private LocalDateTime exitAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParkingStatus status;
}
