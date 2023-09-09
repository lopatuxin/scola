package ru.lopatuxin.scola.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.repositories.StudentRepository;
import ru.lopatuxin.scola.security.StudentDetails;

import java.util.Optional;

/**
 * Класс {@code StudentDetailService} реализует интерфейс {@link UserDetailsService},
 * чтобы предоставлять детали пользователя для аутентификации Spring Security.
 * Он загружает детали пользователя на основе предоставленного имени пользователя (email).
 */
@Service
@RequiredArgsConstructor
public class StudentDetailService implements UserDetailsService {

    private final StudentRepository studentRepository;

    /**
     * Загружает детали пользователя по заданному имени пользователя (email).
     *
     * @param username Имя пользователя (email), для которого нужно загрузить детали пользователя.
     * @return Экземпляр {@link UserDetails}, содержащий детали пользователя.
     * @throws UsernameNotFoundException Если пользователь с предоставленным именем пользователя не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> user = studentRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw  new UsernameNotFoundException("Пользователь с таким именем не найден");
        }
        return new StudentDetails(user.get());
    }
}
