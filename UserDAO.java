package t113;


import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    public List<User> getAll() throws SQLException;
    public void delete(User user) throws SQLException;
    public void add(User user) throws SQLException;
    public void update(User user) throws SQLException;
    public User getbyID(int id) throws SQLException;
}
