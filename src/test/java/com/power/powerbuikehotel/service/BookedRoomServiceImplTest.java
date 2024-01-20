package com.power.powerbuikehotel.service;

import com.power.powerbuikehotel.data.model.BookedRoom;
import com.power.powerbuikehotel.data.model.Room;
import com.power.powerbuikehotel.data.repository.BookedRoomRepository;
import com.power.powerbuikehotel.data.repository.RoomRepository;
import com.power.powerbuikehotel.request.BookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookedRoomServiceImplTest {

    @Autowired
    private BookedRoomServiceImpl bookedRoomService;

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    @Autowired
    private RoomRepository roomRepository;


    @BeforeEach
    public void deleteBeforeEveryTest() {
        bookedRoomRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    public void testBookRoom() {
        BookingRequest bookingRequest = new BookingRequest();
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.of(2024,1,20);
        bookingRequest.setCheckInDate(checkInDate);
        bookingRequest.setCheckOutDate(checkOutDate);
        Room room = new Room();
        room.setRoomType("deluxe");
        room.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room);
        bookingRequest.setRoom(room);
        BookedRoom bookedRoom = bookedRoomService.bookRoom(bookingRequest);

        assertThat(bookedRoomRepository.count(), is(1L));
        assertThat(roomRepository.findById(room.getId()).get(), is(notNullValue()));
        System.out.println(bookedRoom);


        BookingRequest bookingRequest1 = new BookingRequest();
        LocalDate checkInDate1 = LocalDate.now();
        LocalDate checkOutDate1 = LocalDate.of(2024,1,20);
        bookingRequest1.setCheckInDate(checkInDate1);
        bookingRequest1.setCheckOutDate(checkOutDate1);
        Room room1 = new Room();
        room1.setRoomType("deluxe");
        room1.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room1);
        bookingRequest1.setRoom(room1);
        BookedRoom bookedRoom1 = bookedRoomService.bookRoom(bookingRequest1);

        assertThat(bookedRoomRepository.count(), is(1L));
        assertThat(roomRepository.findById(room1.getId()).get(), is(notNullValue()));
        System.out.println(bookedRoom1);

    }

    @Test
    public void testThatWhenCheckedInAnotherUserCannotCheckin() {

        BookingRequest bookingRequest = new BookingRequest();
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.of(2024,1,20);
        bookingRequest.setCheckInDate(checkInDate);
        bookingRequest.setCheckOutDate(checkOutDate);
        Room room = new Room();
        room.setRoomType("deluxe");
        room.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room);
        bookingRequest.setRoom(room);
        BookedRoom bookedRoom = bookedRoomService.bookRoom(bookingRequest);

        assertThat(bookedRoomRepository.count(), is(1L));
        assertThat(roomRepository.findById(room.getId()).get(), is(notNullValue()));
        System.out.println(bookedRoom);


        BookingRequest bookingRequest1 = new BookingRequest();
        LocalDate checkInDate1 = LocalDate.now();
        LocalDate checkOutDate1 = LocalDate.of(2024,1,20);
        bookingRequest1.setCheckInDate(checkInDate1);
        bookingRequest1.setCheckOutDate(checkOutDate1);
        //Room room1 = new Room();
        //room1.setRoomType("deluxe");
        //room1.setRoomPrice(BigDecimal.valueOf(100.00));
        //roomRepository.save(room1);
        bookingRequest1.setRoom(room);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            bookedRoomService.bookRoom(bookingRequest);
        });


        assertThat(exception.getStatusCode(), is(HttpStatus.CONFLICT));
        assertThat(exception.getReason(), is("Room not available"));
    }

    @Test
    public void testGetAllBookingsByRoomId() {
        BookingRequest bookingRequest1 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 22));
        BookingRequest bookingRequest2 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 25));
        BookedRoom bookedRoom1 = bookedRoomService.bookRoom(bookingRequest1);
        BookedRoom bookedRoom2 = bookedRoomService.bookRoom(bookingRequest2);

        List<BookedRoom> bookings = bookedRoomService.getAllBookings();


        assertThat(bookings.size(), is(2));
        assertThat(bookings, contains(bookedRoom1, bookedRoom2));
        assertThat(bookedRoomRepository.count(), is(2L));
    }

    private BookingRequest createBookingRequest(LocalDate checkInDate, LocalDate checkOutDate) {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setCheckInDate(checkInDate);
        bookingRequest.setCheckOutDate(checkOutDate);
        Room room = new Room();
        room.setRoomType("deluxe");
        room.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room);
        bookingRequest.setRoom(room);
        return bookingRequest;
    }
    @Test
    public void testThatICanGetBookedRoomsAndRooms(){
        BookingRequest bookingRequest1 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 22));
        BookingRequest bookingRequest2 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 23));
        BookingRequest bookingRequest3 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 24));
        BookingRequest bookingRequest4 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 25));
        BookingRequest bookingRequest5 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 26));
        BookingRequest bookingRequest6 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 27));
        BookedRoom bookedRoom1 = bookedRoomService.bookRoom(bookingRequest1);
        BookedRoom bookedRoom2 = bookedRoomService.bookRoom(bookingRequest2);
        BookedRoom bookedRoom3 = bookedRoomService.bookRoom(bookingRequest3);
        BookedRoom bookedRoom4 = bookedRoomService.bookRoom(bookingRequest4);
        BookedRoom bookedRoom5 = bookedRoomService.bookRoom(bookingRequest5);
        BookedRoom bookedRoom6 = bookedRoomService.bookRoom(bookingRequest6);

        Room room = new Room();
        room.setRoomType("deluxe");
        room.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room);

        Room room1 = new Room();
        room1.setRoomType("deluxe");
        room1.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room1);

        Room room2 = new Room();
        room2.setRoomType("deluxe");
        room2.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room2);

        Room room3 = new Room();
        room3.setRoomType("deluxe");
        room3.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room3);

        assertThat(bookedRoomRepository.count(), is(6L));
        assertThat(roomRepository.count(), is(10L));

    }
    @Test
    public void testThatICanGetAvailableRooms(){
        BookingRequest bookingRequest1 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 22));
        BookingRequest bookingRequest2 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 23));
        BookingRequest bookingRequest3 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 24));
        BookingRequest bookingRequest4 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 25));
        BookingRequest bookingRequest5 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 26));
        BookingRequest bookingRequest6 = createBookingRequest(LocalDate.now(), LocalDate.of(2024, 1, 27));
        BookedRoom bookedRoom1 = bookedRoomService.bookRoom(bookingRequest1);
        BookedRoom bookedRoom2 = bookedRoomService.bookRoom(bookingRequest2);
        BookedRoom bookedRoom3 = bookedRoomService.bookRoom(bookingRequest3);
        BookedRoom bookedRoom4 = bookedRoomService.bookRoom(bookingRequest4);
        BookedRoom bookedRoom5 = bookedRoomService.bookRoom(bookingRequest5);
        BookedRoom bookedRoom6 = bookedRoomService.bookRoom(bookingRequest6);

        Room room = new Room();
        room.setRoomType("deluxe");
        room.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room);

        Room room1 = new Room();
        room1.setRoomType("deluxe");
        room1.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room1);

        Room room2 = new Room();
        room2.setRoomType("deluxe");
        room2.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room2);

        Room room3 = new Room();
        room3.setRoomType("deluxe");
        room3.setRoomPrice(BigDecimal.valueOf(100.00));
        roomRepository.save(room3);

        LocalDate checkinDate = LocalDate.of(2024,1,21);
        LocalDate checkOutDate = LocalDate.of(2024,1,29);

        assertThat(bookedRoomService.availableRooms(checkinDate,checkOutDate).size(), is(4));
    }
    @Test
    public void deleteAll(){
        assertThat(bookedRoomRepository.count(), is(0L));
        assertThat(roomRepository.count(), is(0L));
    }


}
