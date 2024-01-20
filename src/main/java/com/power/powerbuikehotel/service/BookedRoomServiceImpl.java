package com.power.powerbuikehotel.service;
import com.power.powerbuikehotel.data.model.BookedRoom;
import com.power.powerbuikehotel.data.model.Room;
import com.power.powerbuikehotel.data.repository.BookedRoomRepository;
import com.power.powerbuikehotel.exception.InvalidBookingRequestException;
import com.power.powerbuikehotel.exception.ResourceNotFoundException;
import com.power.powerbuikehotel.request.BookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl implements IBookedRoomService {
    private final BookedRoomRepository bookingRepository;
    private final IRoomService roomService;


    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }


    @Override
    public List<BookedRoom> getBookingsByUserEmail(String email) {
        return null;
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        return null;
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }


    private void checkOut() {
        for (BookedRoom bookings: bookingRepository.findAll()) {
          if (bookings.getRoom().isBooked()){
              if (bookings.getCheckOutDate().equals(LocalDate.now())) {
                  bookings.getRoom().setBooked(false);
                  roomService.saveRoom(bookings.getRoom());
                  bookingRepository.delete(bookings);
                  System.out.println("Check-out successful for bookings ID: " + bookings.getBookingConfirmationCode());
              }
          }
        }
    }

    @Override
    @Transactional
    public BookedRoom bookRoom(@RequestBody BookingRequest bookingRequest) {
        checkOut();
        Room room = bookingRequest.getRoom();
        Optional<Room> existingRoom = roomService.getRoomById(room.getId());
        if (existingRoom.isEmpty()) {
            throw new ResourceNotFoundException("Room not found with ID: " + room.getId());
        }

        room = existingRoom.get();
        if (isRoomAvailable(room, bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())) {
            roomService.bookRoom(room);
            BookedRoom bookedRoom = new BookedRoom(room,bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());
            bookingRepository.save(bookedRoom);
            return bookedRoom;
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Room not available");
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :"+ confirmationCode));

    }
    @Override
    public List<Room> availableRooms(LocalDate checkInDate, LocalDate checkOutDate){
        List<Room> availableRooms = new ArrayList<>();
        List<Room> rooms = roomService.getAllRooms();
        for (Room room: rooms) {
            if (isRoomAvailable(room,checkInDate,checkOutDate)){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        for (BookedRoom existingBooking : bookingRepository.findAll()) {
            if (existingBooking.getRoom().equals(room)) {
                if (checkInDate.isBefore(existingBooking.getCheckOutDate())
                        && checkOutDate.isAfter(existingBooking.getCheckInDate())) {
                    return false;
                }
            }
        }
        return true;
    }





}
