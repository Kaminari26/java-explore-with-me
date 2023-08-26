package ru.practicum.user.mapper;

import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

public class UserMapper {
    public static User toDtoUser(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail());
    }

    public static UserDto toDtoUser(User user) {
        return new UserDto(user.getName(), user.getId(), user.getEmail());
    }

    public static UserShortDto toDtoShortUser(User user) {
        return UserShortDto.builder().id(user.getId()).name(user.getName()).build();
    }
}
