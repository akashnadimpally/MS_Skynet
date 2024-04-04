package org.springframework.skynet;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.AuthenticationException;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationManager authenticationManager;

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
                               BindingResult result,
                               @RequestParam String phone_code,
                               @RequestParam String phone,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        try {
            user.setContactNumber(phone_code, phone);

            if (result.hasErrors()) {
                // Log errors or send them back to the form
                return "signup"; // Return back to signup form with error details
            }

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


    @GetMapping({"/signin", "/Login"})
    public String login_page() {
        return "signin";
    }

    @GetMapping("/Account")
    public String Account() {
        return "Account";
    }

    @PostMapping("/signin")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return "redirect:/Account";
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/signin";
        }
    }



}