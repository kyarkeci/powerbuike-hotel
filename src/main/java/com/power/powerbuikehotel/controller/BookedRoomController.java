package com.power.powerbuikehotel.controller;

import com.power.powerbuikehotel.data.model.BookedRoom;
import com.power.powerbuikehotel.data.model.Room;
import com.power.powerbuikehotel.exception.InvalidBookingRequestException;
import com.power.powerbuikehotel.exception.ResourceNotFoundException;
import com.power.powerbuikehotel.request.BookingRequest;
import com.power.powerbuikehotel.service.IBookedRoomService;
import com.power.powerbuikehotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookedRoomController {
    private final IBookedRoomService bookingService;
    private final IRoomService roomService;

    @GetMapping("/all-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookedRoom>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookedRoom> bookingResponses = new ArrayList<>(bookings);
        return ResponseEntity.ok(bookingResponses);
    }

    @PostMapping("/booking")
    public ResponseEntity<?> bookRoom(@RequestBody BookingRequest bookingRequest) {
        try {
            BookedRoom bookedRoom = bookingService.bookRoom(bookingRequest);

            return ResponseEntity.ok(bookedRoom);

        } catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @GetMapping("/available-rooms")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {

        List<Room> availableRooms = bookingService.availableRooms(checkInDate, checkOutDate);

        return ResponseEntity.ok(availableRooms);
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }
}
