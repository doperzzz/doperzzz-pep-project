package Service;
import Model.Account;
import DAO.AccountDAO;



public class AccountService extends Account {

    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }


    public Account addAccount(Account account) {
        if(account.username != ""  && account.password.length() >= 4){
            return accountDAO.insertAccount(account);
        }
        else {
            return null;
        }
    }

    public Account loginCheck(Account account){
        
     return accountDAO.checkLogin(account);
      
    }
    
    
}
