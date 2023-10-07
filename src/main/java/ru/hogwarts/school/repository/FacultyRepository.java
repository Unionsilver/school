package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color);
    Optional<Faculty> findByName(String name);
    Optional<Faculty> findByColor(String color );
}
