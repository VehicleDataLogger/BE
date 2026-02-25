package com.mobs1.autoE.domain;

import com.mobs1.autoE.global.Enum.SlotCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "VEHICLE")
public class Vehicle {

    @Id
    @Column(name = "vehicle_num", nullable = false)
    private String vehicleNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type_code")
    private SlotCategory vehicleTypeCode;
}
