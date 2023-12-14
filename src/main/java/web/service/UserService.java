package web.service;


import java.util.List;
import web.model.User;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean saveUser(User user);

    boolean deleteUserById(Long id);

}
