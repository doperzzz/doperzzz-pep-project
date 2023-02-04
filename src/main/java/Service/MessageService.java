package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;




public class MessageService extends Message{
    
    public MessageDAO messageDAO;


    public MessageService(){
       
     messageDAO = new MessageDAO();

    }

    public MessageService(MessageDAO messageDAO){
       
    this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() {
        
        return messageDAO.getAllMessages();
    }

    
    public Message addMessage(Message message){
        
        if(message.message_text != "" && message.message_text.length() <= 255  ){
            return messageDAO.insertMessage(message);
        }
        else {
            return null;
        }
    }

    
    public Message getMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }
    
    public Message deleteMessage(int message_id){
        if(messageDAO.getMessageByID(message_id) != null){
           
        Message message = messageDAO.getMessageByID(message_id);
         
        messageDAO.deleteMessage(message_id);
        return message;
         }
         return null;
    }

    
    public Message updateMessage(Message message, int message_id){
         if(message.message_id != 0 && message.message_text != "" && message.message_text.length() <= 255){
            System.out.println(message);
            System.out.println(message_id);
            
            return messageDAO.updateMessage(message, message_id);
         }
         return null;
    }
    
    public List<Message> messageByUser (int account){
        
        return messageDAO.allMessagesByAccount(account);
    }
    
   
    
}
        
    

    

