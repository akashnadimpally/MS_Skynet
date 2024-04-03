package org.springframework.skynet;

import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.skynet.Users;
import org.springframework.skynet.UsersService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private UsersService usersService;

    @GetMapping({"/home", "/"})
    protected String home() {
        return "Home";
    }

    @GetMapping("/signup")
    protected String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute Users user,
                               @RequestParam String phone_code,
                               @RequestParam String phone,
                               RedirectAttributes redirectAttributes) {
        try {
            user.setContactNumber(phone_code, phone);

            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Password cannot be empty.");
                return "redirect:/signup";
            }

            // Encrypt and set the password
            user.setPassword(user.getPassword());

            usersService.saveUser(user);
            return "redirect:/home";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Duplicate entry detected. Please try again with different credentials.");
            return "redirect:/signup";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. Please try again.");
            return "redirect:/signup";
        }
    }

}
