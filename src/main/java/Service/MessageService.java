package Service;

import DAO.MessageDAO;
import Entity.MessageEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO = new MessageDAO();
    private final String secretKey = "49e081d87b7f050a";
    private final TextEncryptor encryptor = Encryptors.text(secretKey,secretKey);
    public MessageService(){}

    public MessageEntity findMessageEntity(int id){
        return messageDAO.findById(id);
    }

    public void saveMessageEntity(MessageEntity messageEntity){
        messageDAO.save(messageEntity);
    }

    public void updateMessageEntity(MessageEntity messageEntity){
        messageDAO.update(messageEntity);
    }

    public void deleteMessageEntity(MessageEntity messageEntity){
        messageDAO.delete(messageEntity);
    }

    public List<MessageEntity> findALL(){
        return messageDAO.findAll();
    }

    public List<MessageEntity> findAllFromRoomId(int id){
        return messageDAO.findAllFromRoomId(id);
    }

}
