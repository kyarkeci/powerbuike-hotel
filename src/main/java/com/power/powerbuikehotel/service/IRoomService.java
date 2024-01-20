package com.power.powerbuikehotel.service;

import com.power.powerbuikehotel.data.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;
    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, String photoUrl);

    Optional<Room> getRoomById(Long roomId);

    List<Room> getAvailableRooms();

    void saveRoom(Room room);

    void bookRoom(Room room);
}
