package com.power.powerbuikehotel.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String bookingConfirmationCode;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_id")
    private Room room;

    public BookedRoom(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = generateBookingConfirmationCode();
    }
    private String generateBookingConfirmationCode() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BookedRoom otherBooking = (BookedRoom) obj;
        return Objects.equals(bookingConfirmationCode, otherBooking.bookingConfirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingConfirmationCode);
    }






}
