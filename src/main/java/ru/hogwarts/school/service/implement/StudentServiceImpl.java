package ru.hogwarts.school.service.implement;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.excepcion.StudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private  long id = 0;
    @Override
    public Student createStudent(Student student) {
        if (students.containsValue(student))
            throw new StudentException("студент в базе");
        student.setId(++id);
        students.put(student.getId(),student);
        return student;

    }

    @Override
    public Student readStudent(long id) {
        if (!students.containsKey(id)) {
            throw new StudentException("студент не найден");
        }
        return students.get(id);

    }

    @Override
    public Student updateStudent(Student student) {
        if(students.containsKey(student.getId())) {
            throw new StudentException("студент не найден");
        }
        students.put(student.getId(),student);
        return student;
    }

    @Override
    public Student deleteStudent(long id) {
        Student student = students.remove(id);
        if (student == null) {
            throw new StudentException("студент не найден");
        }
        return student;
    }
    @Override
    public List<Student> readAll(int age) {
      return students.values().stream()
                .filter(st -> st.getAge() == age)
                .collect(Collectors.toUnmodifiableList());
    }
}
