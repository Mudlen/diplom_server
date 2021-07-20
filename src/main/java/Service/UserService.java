package Service;

import DAO.UsersDAO;
import Entity.UsersEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.List;

public class UserService {
    private final UsersDAO usersDAO = new UsersDAO();
    public UserService(){}

    public UsersEntity findUserEntity(int id){
        return usersDAO.findById(id);
    }

    public void saveUserEntity(UsersEntity usersEntity){
        usersDAO.save(usersEntity);
    }

    public void updateUserEntity(UsersEntity usersEntity){
        usersDAO.update(usersEntity);
    }

    public void deleteUserEntity(UsersEntity usersEntity){
        usersDAO.delete(usersEntity);
    }

    public List<UsersEntity> findAllUsers(){
        return usersDAO.findAll();
    }

    public UsersEntity findByLoginAndPassword(String login, String password){
        return usersDAO.findByLoginAndPassword(login,password);
    }
}
