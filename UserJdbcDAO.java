package t113;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAO implements UserDAO {

    private Connection connection = DBHelper.getConnection();
    private Executor executor = new Executor(connection);
/*
    public UserJdbcDAO(Connection connection) {
        this.executor = new Executor(connection);
    }*/

    public UserJdbcDAO() {

    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        return executor.execQuery("select * from uers", result -> {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                User user = new User(id, name, age);
                list.add(user);
            }
            return list;
        });
    }

    @Override
    public void delete(User user) throws SQLException {
        executor.execUpdate("delete from uers where id = "+user.getId());

    }

    @Override
    public void add(User user) throws SQLException {
        executor.execUpdate("insert into uers (name, age) values ('" + user.getName()
                +"', '"+user.getAge()+"')");

    }

    @Override
    public void update(User user) throws SQLException {
        executor.execUpdate("update uers set "+
                "name = '"+user.getName()+"', "+
                "age = '"+user.getAge()+"' "+
                "WHERE id = "+user.getId());

    }

    @Override
    public User getbyID(int id) throws SQLException {
        return executor.execQuery("select * from uers where id = "+id,result->{
            result.next();
            return new User(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getInt("age")
            );
        });
    }
}
