package com.teguh.member.controller;

import com.lowagie.text.DocumentException;
import com.teguh.member.dto.UserDto;
import com.teguh.member.model.User;
import com.teguh.member.repository.UserRepository;
import com.teguh.member.service.UserService;
import com.teguh.member.utils.ExcelGenerator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.teguh.member.utils.PDFGenerator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }
    @GetMapping("/add")
    public String addForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "add";
    }
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "Email user telah terdaftar, silahkan gunakan email yang lain");

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "add";
        }

        userService.saveUser(userDto);
        return "redirect:/user/?success";
    }
    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + id));
        model.addAttribute("user", user);
        return "edit";
    }
    @PostMapping("update/{id}")
    public String updateMember(@PathVariable("id") long id, @Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            userDto.setId(id);
            return "edit";
        }

        userService.updateUser(id,userDto);
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }
    @GetMapping("delete/{id}")
    public String deleteMember(@PathVariable("id") long id, @Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }
    @GetMapping("pdf")
    public void  generatePdf(HttpServletResponse response) throws DocumentException, IOException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);
        List<User> userList = userRepository.findAll();

        PDFGenerator generator = new PDFGenerator();
        generator.setUserList(userList);
        generator.generate(response);
    }
    @GetMapping("xls")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List <User> listUser = userRepository.findAll();
        ExcelGenerator generator = new ExcelGenerator(listUser);
        generator.generateExcelFile(response);
    }
    @GetMapping("/json")
    @ResponseBody
    public Object getObject() {
        Map<String, Object> object = new HashMap<>();
        List<Map<String, Object>> userDataList = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("nama", user.getName());
            userMap.put("email", user.getEmail());
            userDataList.add(userMap);
        }

        object.put("data", userDataList);
        return object;
    }

}
