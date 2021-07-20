package Service;

import DAO.RoomDAO;
import Entity.RoomEntity;

import java.util.List;

public class RoomService {
    private final RoomDAO roomDAO = new RoomDAO();
    public RoomService(){}

    public RoomEntity findRoomEntity(int id){
        return roomDAO.findById(id);
    }

    public void saveRoomEntity(RoomEntity roomEntity){
        roomDAO.save(roomEntity);
    }

    public void updateRoomEntity(RoomEntity roomEntity){
        roomDAO.update(roomEntity);
    }

    public void deleteRoomEntity(RoomEntity roomEntity){
        roomDAO.delete(roomEntity);
    }

    public List<RoomEntity> findAllRooms(){
        return roomDAO.findAll();
    }

    public RoomEntity findRoomByName(String name){
        return roomDAO.findRoomByName(name);
    }

    public List<RoomEntity> findPublicRoom(){
        return roomDAO.findPublicRoom();
    }

}
