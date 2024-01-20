package com.power.powerbuikehotel.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.power.powerbuikehotel.data.model.Room;
import com.power.powerbuikehotel.data.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private final Cloudinary cloudinary;


    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(photo.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) result.get("url");
        System.out.println("Image URL: " + imageUrl);

        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        room.setPhotoUrl(imageUrl);

        return roomRepository.save(room);
    }


    @Override
    public List<String> getAllRoomTypes() {
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if(theRoom.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, String photoUrl) {
        Room room = roomRepository.findById(roomId).get();
        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (photoUrl != null) room.setPhotoUrl(photoUrl);
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }

    @Override
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : getAllRooms()) {
            if (isRoomAvailable(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomAvailable(Room room) {
        return room.isBooked();
    }
    @Override
    public void saveRoom(Room room){
        roomRepository.save(room);
    }

    @Override
    public void bookRoom(Room room) {
        room.setBooked(true);
        roomRepository.save(room);
    }
}
