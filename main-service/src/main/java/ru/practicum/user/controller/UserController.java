package ru.practicum.user.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.service.IUserService;
import ru.practicum.user.dto.UserDto;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/users")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }



    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createNewUser(@RequestBody UserDto userDto) {
        log.info("Пришел запрос Post /users  UserDto: {}", userDto);
        UserDto userDto1 = userService.addNewUser(userDto);
        log.info("Пользователь добавлен! {}", userDto1);
        return userDto1;

    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable Long userId) {
        log.info("Пришел запрос Delete /users/{} ", userId);
        userService.removeUser(userId);
        log.info("Пользователь удален!");
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long> ids, @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                  @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        log.info("Пришел запрос Get /users ids: {}, from: {}, size: {}", ids, from, size);
        if (size <= 0 || from < 0) {
            throw new IllegalReceiveException("Неверно указан параметр");
        }
        List<UserDto> userDtos = userService.getUsers(ids, from, size);
        log.info("Отправлен ответ: {}", userDtos);
        return userDtos;

    }
}
