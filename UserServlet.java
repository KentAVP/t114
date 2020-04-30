package t113;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;
    Properties properties = new Properties();
    FileInputStream fis;
    String type;

    {
        try {
            fis = new FileInputStream("C:\\Users\\alexe\\IdeaProjects\\serv\\src\\main\\java\\t113\\daoconfig.properties");
            properties.load(fis);
            type = properties.getProperty("daotype");
        } catch (Exception e) {
           System.out.println("отсутствует файл конфигурации");
        }
    }

    public void init() {
        userDao = UserDAOFactory.getUserDAO(type);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    insertUser(req, resp);
                    break;
                case "/delete":
                    deleteUser(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateUser(req, resp);
                    break;
                default:
                    listUser(req, resp);
                    break;
            }
        } catch (SQLException | IOException ex) {
            throw new ServletException(ex);
        }

    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDao.getAll();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        String ageS = request.getParameter("age");
        int age = Integer.parseInt(ageS);
        User user = new User(name,age);
        userDao.add(user);
        response.sendRedirect("list");
    }
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        String ageS = request.getParameter("age");
        int age = Integer.parseInt(ageS);
        User us = new User(name,age);
        userDao.delete(us);
        response.sendRedirect("list");
    }
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id =Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String ageS = request.getParameter("age");
        int age = Integer.parseInt(ageS);
        User user = new User(id,name,age);
        userDao.update(user);
        response.sendRedirect("list");
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDao.getbyID(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

}
