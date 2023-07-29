package ru.lopatuxin.scola.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lopatuxin.scola.models.Student;
import ru.lopatuxin.scola.repositories.StudentRepository;
import ru.lopatuxin.scola.security.StudentDetails;

import java.util.Optional;

@Service
public class StudentDetailService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentDetailService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> user = studentRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw  new UsernameNotFoundException("Пользователь с таким именем не найден");
        }
        return new StudentDetails(user.get());
    }

    /**
     * Метод отдает StudentDetails для того чтобы получить имя юзера*/
    public StudentDetails getUser(HttpServletRequest request) {
        var auth = (Authentication) request.getUserPrincipal();
        return (StudentDetails) auth.getPrincipal();
    }
}
