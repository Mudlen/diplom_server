package Json;

import Entity.RoomEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class RoomJson {
    private JSONObject root;

    public RoomJson(){
        root = new JSONObject();
    }

    public String getJsonRoom(RoomEntity roomEntity){
        root.put("id",roomEntity.getId());
        root.put("name",roomEntity.getName());
        root.put("adminId",roomEntity.getAdminId());
        root.put("isPublic",roomEntity.getIsPublic());
        return root.toJSONString();
    }

    public String getJsonRoom(String name, int adminId, int isPublic){
        root.put("name",name);
        root.put("adminId",adminId);
        root.put("isPublic",isPublic);
        return root.toJSONString();
    }

    public String getJsonRoomList(List<RoomEntity> rooms){
        JSONArray array = new JSONArray();
        int i = 0;
        for(RoomEntity roomEntity: rooms){
            JSONObject data = new JSONObject();
            data.put("id",roomEntity.getId());
            data.put("name",roomEntity.getName());
            data.put("adminId",roomEntity.getAdminId());
            data.put("isPublic",roomEntity.getIsPublic());
            array.add(data);
            i++;
        }
        root.put("array",array);
        return root.toJSONString();
    }
}
