package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     AccountService accountService;
     MessageService messageService;

     public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
     }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.post("/register" , this::insertAccountHandler);
        app.post("/login", this::checkLoginHandler);
        app.post("/messages", this::insertMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        
        //parth parameter for {message_id}
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::messageByAccountHandler);
        
        return app;
    }

        private void insertAccountHandler(Context ctx) throws JsonProcessingException{
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account addedAccount = accountService.addAccount(account);
            if(addedAccount != null){
                ctx.json(mapper.writeValueAsString(addedAccount));
                ctx.status(200);
            }
            else {
                ctx.status(400);
            }
        }

        private void checkLoginHandler(Context ctx) throws JsonProcessingException{
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account loginCheck = accountService.loginCheck(account);
            if(loginCheck != null){
                ctx.json(mapper.writeValueAsString(loginCheck));
                ctx.status(200);
            }
            else {
                ctx.status(401);
            }
        }

        private void insertMessageHandler(Context ctx) throws JsonProcessingException{
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message addMessage = messageService.addMessage(message);
            if(addMessage != null){
                ctx.json(mapper.writeValueAsString(addMessage));
                ctx.status(200);
            }
            else {
                ctx.status(400);
            }
        }

        private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
            List<Message> messages = messageService.getAllMessages();
            ctx.json(messages);
            ctx.status(200);
        }
        
        private void getMessageByIDHandler(Context ctx) throws JsonProcessingException{
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message gotmessage = messageService.getMessageByID(message_id);
            
            if(gotmessage != null){
                ctx.json(gotmessage);
                ctx.status(200);
            }
            else {
                ctx.status(404);
            }
            
        }

        private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException{
            ObjectMapper mapper = new ObjectMapper();
            int messageID = Integer.parseInt(ctx.pathParam("message_id"));
            Message deletedMessage = messageService.deleteMessage(messageID);
            mapper.writeValueAsString(deletedMessage);
        
            if(deletedMessage != null){
                ctx.json(mapper.writeValueAsString(deletedMessage));
                ctx.status(200);
            }
            
        }

        public void updateMessageHandler(Context ctx) throws JsonProcessingException{
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(ctx.body(), Message.class);
            int updatedMessage = Integer.parseInt(ctx.pathParam("message_id"));
            Message updateMessage = messageService.updateMessage(message, updatedMessage);
            if(updateMessage != null){
                ctx.json(mapper.writeValueAsString(updateMessage));
                ctx.status(200);
            }
            else {
                ctx.status(400);
            }
        }

        public void messageByAccountHandler(Context ctx) throws JsonProcessingException{
            String messageByUser = ctx.pathParam("account_id");
            List<Message>userMessage = messageService.messageByUser(Integer.parseInt(messageByUser));
            ctx.json(userMessage);


        }


}