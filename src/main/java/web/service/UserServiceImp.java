package web.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import web.repository.UserRepository;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public User getUserById(Long id) {
        User user = repository.findById(id).get();
        return user;
    }

    @Override
    public boolean saveUser(User user) {
        try {
            repository.save(user);
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to save user: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean deleteUserById(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

}
