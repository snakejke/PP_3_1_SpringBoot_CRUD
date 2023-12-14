package web.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController() {
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = {"/", "/home"})
    public String hello() {
        return "home";
    }


    @PostMapping("/allUsers")
    public String displayAllUsers(Model model) {
        System.out.println("User Page Requested: All Users");
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "allUsers";
    }

    @GetMapping("/addUser")
    public String displayNewUserForm(Model model) {
        model.addAttribute("headerMessage", "Add User Details");
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String saveNewUser(@ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }

        boolean isAdded = userService.saveUser(user);
        if (isAdded) {
            model.addAttribute("message", "New user successfully added");
        } else {
            return "error";
        }
        return "redirect:/home";
    }

    @GetMapping("/editUser")
    public String displayEditUserForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("headerMessage", "Edit User Details");
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String saveEditedUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result);
            return "error";
        }
        boolean isSaved = userService.saveUser(user);
        if (!isSaved) {
            return "error";
        }
        return "redirect:/home";
    }

    @GetMapping("/deleteUser")
    public String deleteUserById(@RequestParam("id") Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        System.out.println("User deletion response: " + isDeleted);
        return "redirect:/home";
    }

}

