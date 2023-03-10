package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto get(long id) {
        if (userRepository.findById(id).isEmpty())
            throw new NullPointerException("User " + id + "is not found.");
        return UserMapper.toUserDto(userRepository.findById(id).get());
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto add(UserDto userDto) {
        if (userDto.getEmail() == null)
            throw new ValidationException("Email is null");
        try {
            return UserMapper.toUserDto(userRepository.save(UserMapper.toUserWithoutId(userDto)));
        } catch (Exception e) {
            throw new DuplicateException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserDto update(long id, UserDto user) {
        if (userRepository.findById(id).isEmpty())
            throw new NullPointerException("User " + id + " is not found.");
        try {
            User newUser = userRepository.findById(id).get();
            if (user.getEmail() != null) {
                if (userRepository.findAllByEmail(user.getEmail()).size() > 0 &&
                        !Objects.equals(userRepository.findAllByEmail(user.getEmail()).get(0).getId(), user.getId())) {
                    throw new DuplicateException("Email already exist.");
                }
                newUser.setEmail(user.getEmail());
            }
            if (user.getName() != null)
                newUser.setName(user.getName());
            return UserMapper.toUserDto(userRepository.save(newUser));
        } catch (Exception e) {
        throw new DuplicateException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (userRepository.findById(id).isEmpty())
            throw new NullPointerException("User " + id + "is not found.");
        userRepository.deleteById(id);
    }
}
