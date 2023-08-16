package ru.lopatuxin.scola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lopatuxin.scola.models.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
