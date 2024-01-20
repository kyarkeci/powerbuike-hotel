package com.power.powerbuikehotel.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    @Column(name = "photo_url")
    @JsonProperty("photo_url")
    private String photoUrl;
    public Room(){
        this.isBooked = false;
    }
}
