package ru.lopatuxin.scola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lopatuxin.scola.models.Role;
import ru.lopatuxin.scola.models.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);

    List<Student> findAllByRole(Role role);

    @Modifying
    @Query(value = "UPDATE student set role = :role where id = :id", nativeQuery = true)
    void updateStudentByRole(@Param("role") String role, @Param("id") int id);
}
