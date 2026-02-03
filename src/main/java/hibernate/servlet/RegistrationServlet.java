package hibernate.servlet;

import hibernate.dto.UserDto;
import hibernate.entity.Gender;
import hibernate.entity.Role;
import hibernate.exception.ValidationException;
import hibernate.service.UserService;
import hibernate.util.JspHelper;
import hibernate.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;

import static hibernate.util.UrlPath.REGISTRATION;

@Slf4j
@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private static final UserService userService = UserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Opening registration page");

        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());

        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing registration request for email: {}", req.getParameter("email"));

        var userDto = UserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("pwd"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            log.debug("Creating user with DTO: {}", userDto);
            userService.create(userDto);
            log.info("User {} successfully registered", userDto.getEmail());
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            log.warn("Validation failed for user {}: {}", userDto.getEmail(), e.getErrors());
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }catch (Exception e) {
            log.error("Unexpected error during registration", e);  // ✅
            throw e;
        }

    }
}