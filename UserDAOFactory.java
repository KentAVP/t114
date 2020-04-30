package t113;

public class UserDAOFactory {
    public static UserDAO getUserDAO(String type){
        if (type.equalsIgnoreCase("hibernate")){
            return new UserHibernateDAO();
        }else{
            return new UserJdbcDAO();
        }
    }
}
