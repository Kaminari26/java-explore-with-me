package ru.practicum.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exeption.DataConflictException;
import ru.practicum.exeption.InvalidStatusException;
import ru.practicum.exeption.UserNotFoundException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        if (userDto.getEmail().length() > 254) {
            throw new InvalidStatusException("Слишком длинная почта");
        }
        Optional<User> optionalUser = userRepository.findByName(userDto.getName());
        if (optionalUser.isPresent()) {
            throw new DataConflictException("Имя уже занято");
        }
        return UserMapper.toDtoUser(userRepository.save(UserMapper.toDtoUser(userDto)));
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        int page = 0;
        if (from != 0) {
            page = from / size;
        }
        Pageable pageable = PageRequest.of(page, size);
        if (ids == null) {
            return userRepository.findAll(pageable).stream().map(UserMapper::toDtoUser).collect(Collectors.toList());
        }
        return userRepository.findAllByIdIn(ids, pageable).stream().map(UserMapper::toDtoUser).collect(Collectors.toList());
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.toDtoUser(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден")));
    }
}
