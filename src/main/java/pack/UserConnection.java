package pack;

import Entity.RoomEntity;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;

public class UserConnection {
    private Channel channel;
    private ArrayList<RoomEntity> rooms = new ArrayList<>();

    public UserConnection(ChannelHandlerContext ctx){
        this.channel = ctx.channel();
    }

    public Channel getChannel() {
        return channel;
    }

    public void addRoomToChannel(RoomEntity roomEntity){
        rooms.add(roomEntity);
    }

    public ArrayList<RoomEntity> getRooms(){
        return rooms;
    }
}
