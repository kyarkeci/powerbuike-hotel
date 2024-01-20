package com.power.powerbuikehotel.service;

import com.power.powerbuikehotel.data.model.BookedRoom;
import com.power.powerbuikehotel.data.model.Room;
import com.power.powerbuikehotel.request.BookingRequest;

import java.time.LocalDate;
import java.util.List;

public interface IBookedRoomService {
    void cancelBooking(Long bookingId);

    BookedRoom bookRoom(BookingRequest bookingRequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();

    List<BookedRoom> getBookingsByUserEmail(String email);
    List<Room> availableRooms(LocalDate checkInDate, LocalDate checkOutDate);

    String saveBooking(Long roomId, BookedRoom bookingRequest);
}
