package com.power.powerbuikehotel.request;

import com.power.powerbuikehotel.data.model.Room;
import lombok.Data;

import java.time.LocalDate;
@Data
public class BookingRequest {
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
