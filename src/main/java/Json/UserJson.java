package Json;

import Entity.UsersEntity;
import org.json.simple.JSONObject;

public class UserJson {
    private final JSONObject root;
    private final UsersEntity usersEntity;

    public UserJson(UsersEntity usersEntity){
        root = new JSONObject();
        this.usersEntity = usersEntity;
        addJsonFromUser();
    }

    private void addJsonFromUser(){
        root.put("id",usersEntity.getId());
        root.put("name",usersEntity.getName());
        root.put("login",usersEntity.getLogin());
        root.put("password",usersEntity.getPassword());
        root.put("email",usersEntity.getEmail());
    }

    public String getJsonUser(){
        return root.toJSONString();
    }

}
