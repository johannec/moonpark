package progging.johannes.studenttemplate.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long studentId) {
        Optional<Student> studentById = studentRepository.findById(studentId);
        if (studentById.isEmpty()) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        return studentById.get();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean isExistingStudent = studentRepository.existsById(studentId);
        if (!isExistingStudent) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, Student student) {
        boolean isExistingStudent = studentRepository.existsById(studentId);
        if (!isExistingStudent) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        Optional<Student> studentByEmail = studentRepository.findByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists");
        }
        studentRepository.save(student);
    }
}
