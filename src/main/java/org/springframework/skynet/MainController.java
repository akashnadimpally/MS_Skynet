package org.springframework.skynet;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UsersService usersService;

    @GetMapping({"/home", "/", "/Home"})
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
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        try {
            user.setContactNumber(phone_code, phone);

            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Password cannot be empty.");
                return "redirect:/signup";
            }

            // Temporarily set the password without encryption for testing
            // user.setPassword(user.getPassword());

            usersService.saveUser(user);
            request.getSession().setAttribute("registrationCompleted", true);
            logger.info("User saved successfully");
            return "redirect:/success";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Duplicate entry detected. Please try again with different credentials.");
            return "redirect:/signup";
        } catch (Exception e) {
            logger.error("Error during registration", e);
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. Please try again.");
            return "redirect:/signup";
        }
    }



    @GetMapping("/success")
    public String success(HttpServletRequest request) {
        if (Boolean.TRUE.equals(request.getSession().getAttribute("registrationCompleted"))) {
            request.getSession().removeAttribute("registrationCompleted");
            return "Success";
        } else {
            return "redirect:/signup";
        }
    }


}
