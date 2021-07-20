package pack;

import Entity.MessageEntity;
import Entity.RoomEntity;
import Entity.UsersEntity;
import Json.MessageJson;
import Json.RoomJson;
import Json.UserJson;
import Service.MessageService;
import Service.RoomService;
import Service.UserService;
import io.netty.channel.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class MainHandler extends SimpleChannelInboundHandler<String> {
    private static final HashMap<String,UserConnection> channels = new HashMap<>();
    private static int newClientIndex = 1;
    private String clientName = "unidentified";
    private static UserService userService = new UserService();
    private static RoomService roomService = new RoomService();
    private static MessageService messageService = new MessageService();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключился: " + ctx);
        clientName = clientName + newClientIndex;
        channels.put(clientName,new UserConnection(ctx));
        newClientIndex++;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//        System.out.println("Получено сообщение: " + s);
        if (s.startsWith("/")) {
            if(s.startsWith("/login")){
                String json = s.split("\\s", 2)[1];
                JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
                String login = (String) jsonObject.get("login");
                String password = (String) jsonObject.get("password");
                String passwordHash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
                UsersEntity usersEntity
                        = userService.findByLoginAndPassword(login,passwordHash);
                if(usersEntity != null){
                    UserJson userJson = new UserJson(usersEntity);
                    channels.get(clientName).getChannel()
                            .writeAndFlush("/login "+userJson.getJsonUser()+"\n");
                }else {
                    channels.get(clientName).getChannel().writeAndFlush("/login error"+"\n");
                }
            }
            if(s.startsWith("/register")){
                String json = s.split("\\s", 2)[1];
                JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
                UsersEntity usersEntity = new UsersEntity();
                usersEntity.setEmail((String) jsonObject.get("email"));
                usersEntity.setLogin((String) jsonObject.get("login"));
                usersEntity.setName((String) jsonObject.get("name"));
                usersEntity.setPassword((String) jsonObject.get("password"));
                userService.saveUserEntity(usersEntity);
            }
            if(s.startsWith("/create_room")){
                String json = s.split("\\s", 2)[1];
                JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
                RoomEntity roomEntity = new RoomEntity();
                roomEntity.setName((String) jsonObject.get("name"));
                roomEntity.setAdminId(Math.toIntExact((Long) jsonObject.get("adminId")));
                roomEntity.setIsPublic(Math.toIntExact((Long) jsonObject.get("isPublic")));
                roomService.saveRoomEntity(roomEntity);
            }
            if(s.startsWith("/reload")){
                List<RoomEntity> list = roomService.findPublicRoom();
                RoomJson roomJson = new RoomJson();
                channels.get(clientName).getChannel().writeAndFlush("/reload "+roomJson.getJsonRoomList(list)+"\n");
            }
            if(s.startsWith("/get_private_chat")){
                String name_chat = s.split("\\s", 2)[1];
                RoomEntity roomByName = roomService.findRoomByName(name_chat);
                RoomJson roomJson = new RoomJson();
                if(roomByName != null){
                    channels.get(clientName).getChannel().writeAndFlush("/get_private_chat "+roomJson.getJsonRoom(roomByName)+"\n");
                }else {
                    channels.get(clientName).getChannel().writeAndFlush("/get_private_chat error \n");
                }
            }
            if(s.startsWith("/message")){
                String message = s.split("\\s", 2)[1];
                MessageEntity messageEntity = new MessageEntity();
                JSONObject jsonObject = (JSONObject) JSONValue.parse(message);
                messageEntity.setText((String) jsonObject.get("text"));
                messageEntity.setFromUserId(Math.toIntExact((Long) jsonObject.get("from_user_id")));
                messageEntity.setToRoomId(Math.toIntExact((Long) jsonObject.get("to_room_id")));
                Date date = new Date();
                messageEntity.setDate(date);
                messageService.saveMessageEntity(messageEntity);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String d = df.format(date);
                MessageJson messageJson = new MessageJson();
                broadcastMessage("/message "+
                        messageJson.getMessageJson(
                                userService.findUserEntity(Math.toIntExact((Long) jsonObject.get("from_user_id"))).getName(),
                                roomService.findRoomEntity(Math.toIntExact((Long) jsonObject.get("to_room_id"))).getName(),
                                (String) jsonObject.get("text"),
                                d));
            }
            if(s.startsWith("/load_history")){
                String chat = s.split("\\s", 2)[1];
                if(chat != null && !chat.equals("")){
                    RoomEntity roomEntity = roomService.findRoomByName(chat);
                    List<MessageEntity> messageEntities = messageService.findAllFromRoomId(roomEntity.getId());
                    MessageJson messageJson = new MessageJson(userService);
                    channels.get(clientName).getChannel().writeAndFlush
                            ("/load_history "+messageJson
                                    .getJsonMessageList(messageEntities,chat)+"\n");
                }

            }
        }
    }

    public void broadcastMessage(String clientName, String message) {
        String out = String.format("[%s]: %s\n", clientName, message);
        for (UserConnection uc : channels.values()) {
            uc.getChannel().writeAndFlush(out+"\n");
        }
    }
    public void broadcastMessage(String out){
        for (UserConnection uc : channels.values()) {
            uc.getChannel().writeAndFlush(out+"\n");
        }
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент " + clientName + " вышел из сети");
        channels.remove(clientName);
        broadcastMessage("SERVER", "Клиент " + clientName + " вышел из сети");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Клиент " + clientName + " отвалился");
        channels.remove(clientName);
        broadcastMessage("SERVER", "Клиент " + clientName + " вышел из сети");
        ctx.close();
    }

}