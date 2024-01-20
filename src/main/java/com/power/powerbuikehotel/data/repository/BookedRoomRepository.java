package com.power.powerbuikehotel.data.repository;

import com.power.powerbuikehotel.data.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);

}
