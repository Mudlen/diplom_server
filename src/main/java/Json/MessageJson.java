package Json;

import Entity.MessageEntity;
import Service.MessageService;
import Service.UserService;
import io.netty.channel.Channel;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageJson {
    private JSONObject root;
    private UserService service;

    public MessageJson(){
        root = new JSONObject();
    }
    public MessageJson(UserService service){
        root = new JSONObject();
        this.service = service;
    }
    public String getMessageJson(String fromUserId, String toRoomId, String text, String date){
        root.put("from_user_id",fromUserId);
        root.put("to_room_id",toRoomId);
        root.put("text",text);
        root.put("date",date);
        return root.toJSONString();
    }

    public String getJsonMessageList(List<MessageEntity> list,String name){
        root.put("length",list.size());
        root.put("nameChat",name);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date;
        String d;
        int i = 0;
        for(MessageEntity msg: list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",msg.getId());
            String nameUser = (service.findUserEntity(msg.getFromUserId())).getName();
            jsonObject.put("from_user_id",nameUser);
            jsonObject.put("to_room_id",msg.getToRoomId());
            jsonObject.put("text",msg.getText());
            date = msg.getDate();
            d = df.format(date);
            jsonObject.put("date",d);
            root.put(i+"",jsonObject);
            i++;
        }
        return root.toJSONString();
    }

}
