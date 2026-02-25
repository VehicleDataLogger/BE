package com.mobs1.autoE.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "PARKING_SLOT",
        uniqueConstraints = @UniqueConstraint(name = "uk_zone_slot_code", columnNames = {"zone_id", "slot_code"}),
        indexes = {
                @Index(name = "idx_zone_slot_type", columnList = "zone_id, slot_type_id")
        })
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "slot_type_id", nullable = false)
    private SlotType slotType;

    @Column(name = "slot_code", nullable = false)
    private String slotCode;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}
