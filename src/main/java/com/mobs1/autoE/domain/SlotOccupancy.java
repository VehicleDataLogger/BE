package com.mobs1.autoE.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
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
@Table(name = "SLOT_OCCUPANCY", indexes = {
        @Index(name = "idx_vehicle_plate_lookup", columnList = "vehicle_plate")
})
public class SlotOccupancy {

    @Id
    @Column(name = "slot_id")
    private Integer id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "slot_id")
    private ParkingSlot slot;

    @Builder.Default
    @Column(name = "occupied", nullable = false)
    private boolean occupied = false;

    @OneToOne
    @JoinColumn(name = "current_session_id")
    private ParkingHistory currentSession;

    @ManyToOne
    @JoinColumn(name = "vehicle_plate", referencedColumnName = "vehicle_num")
    private Vehicle vehicle;

    @Column(name = "occupied_since")
    private LocalDateTime occupiedSince;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
