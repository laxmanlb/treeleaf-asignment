package com.treeleaf.assistment.service;

import com.treeleaf.assistment.dto.GeneralResponseDto;
import com.treeleaf.assistment.dto.StudentMarksDetailsDto;
import com.treeleaf.assistment.dto.StudentMarksDto;
import com.treeleaf.assistment.dto.UserDetailsDto;
import com.treeleaf.assistment.entity.StudentsMarks;
import com.treeleaf.assistment.entity.User;
import com.treeleaf.assistment.enums.SubjectsEnum;
import com.treeleaf.assistment.enums.UserRoleEnum;
import com.treeleaf.assistment.repository.StudentsMarksRepository;
import com.treeleaf.assistment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentsMarksRepository studentsMarksRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    public GeneralResponseDto checkAndAddUser(UserDetailsDto userDetails) {
        GeneralResponseDto responseDto = new GeneralResponseDto();
        responseDto.setResponseMsg("Error while adding user. Please try again.");
        try {
            this.validateUserDetails(userDetails);
            User user = userRepository.findByUserName(userDetails.getUserName());
            if (user != null) {
                throw new Exception("User already exist.");
            }

            String password = userDetails.getPassword();
            user = new User(userDetails.getUserName(), bCryptPasswordEncoder.encode(password.subSequence(0, password.length())), userDetails.getUserRole());
            userRepository.save(user);

            responseDto.setResponseStatus(true);
            responseDto.setResponseMsg("Successfully created user with username: " + userDetails.getUserName());
        } catch (Exception e) {
            responseDto.setResponseMsg("Error: " + e.getMessage());
            logger.debug("Error while adding user: " + e.toString());
        }
        return responseDto;
    }

    private void validateUserDetails(UserDetailsDto userDetails) throws Exception {

        if (userDetails.getUserName() == null || userDetails.getUserName().isEmpty()) {
            throw new Exception("Username cannot be empty.");
        }

        if (userDetails.getPassword() == null || userDetails.getPassword().isEmpty()) {
            throw new Exception("Password cannot be empty.");
        }

        if (userDetails.getUserRole() == null || userDetails.getUserRole().isEmpty()) {
            throw new Exception("User role cannot be empty.");
        }

        //check for roles
        if (Stream.of(UserRoleEnum.values()).noneMatch(role -> userDetails.getUserRole().equals(role.toString()))) {
            throw new Exception("Invalid role. Please select 'STUDENT' or 'TEACHER' only.");
        }

    }

    public GeneralResponseDto addUpdateStudentMarks(StudentMarksDto studentMarksDto) {
        GeneralResponseDto responseDto = new GeneralResponseDto();
        responseDto.setResponseMsg("Error while adding marks.");
        try {
            this.checkUser(studentMarksDto.getUserName());
            this.checkMarksDetails(studentMarksDto);
            StudentsMarks marks = studentsMarksRepository.findBySubject(studentMarksDto.getSubject(), studentMarksDto.getUserName());
            if (marks == null) {//adding new detail
                marks = new StudentsMarks(studentMarksDto.getUserName(), studentMarksDto.getUpdatedBy(), studentMarksDto.getSubject(), studentMarksDto.getMarks());
                responseDto.setResponseMsg("Successfully added details.");

            } else {//updating details
                marks.setTeachersUsername(studentMarksDto.getUpdatedBy());
                marks.setMarks(studentMarksDto.getMarks());
                responseDto.setResponseMsg("Successfully updated details.");

            }
            studentsMarksRepository.save(marks);
            responseDto.setResponseStatus(true);
        } catch (Exception e) {
            logger.debug("Error while adding details of marks.");
            responseDto.setResponseMsg(e.getLocalizedMessage());
        }
        return responseDto;
    }

    /**
     * @param @return list of students marks with percentage where each subjects
     * marks are considered with 100 as total marks
     */
    public List<StudentMarksDto> getAllStudentsPercentage() {
        List<StudentMarksDto> responseDto = new ArrayList<>();
        try {
            List<StudentsMarks> studentsMarks = studentsMarksRepository.findAll();
            HashMap<String, Double> studentDetails = new HashMap<>();
            final int totalMarks = SubjectsEnum.values().length * 100;
            logger.debug("Total marks of subjects are: " + totalMarks);
            for (StudentsMarks st : studentsMarks) {
                if (studentDetails.containsKey(st.getStudentUserName())) {
                    studentDetails.put(st.getStudentUserName(), (studentDetails.get(st.getStudentUserName()) + st.getMarks()));
                } else {
                    studentDetails.put(st.getStudentUserName(), st.getMarks());
                }
            }
            //calculating percentage
            studentDetails.forEach((key, value) -> {
                StudentMarksDto stDetails = new StudentMarksDto();
                stDetails.setPercentage((value / totalMarks) * 100);
                stDetails.setUserName(key);
                responseDto.add(stDetails);
            });
        } catch (Exception e) {
            logger.debug("Error while adding details of marks.");
        }
        return responseDto;
    }

    public GeneralResponseDto removeStudentsMarks(StudentMarksDto studentMarksDto) {
        GeneralResponseDto responseDto = new GeneralResponseDto();
        responseDto.setResponseMsg("Error while adding marks.");
        try {
            int status = studentsMarksRepository.deleteMarksByUserName(studentMarksDto.getSubject(), studentMarksDto.getUserName());
            if (status == 1) {
                responseDto.setResponseStatus(true);
            }else{
                responseDto.setResponseStatus(false);
                responseDto.setResponseMsg("Cannot delete detail.");
            }
        } catch (Exception e) {
            logger.debug("Error while removing details.");
            responseDto.setResponseMsg("Cannot remove detail." + e.toString());
        }
        return responseDto;
    }

    public StudentMarksDetailsDto getStudentDetails(StudentMarksDto studentMarksDto) {
        StudentMarksDetailsDto responseDto = new StudentMarksDetailsDto();
        responseDto.setResponseMsg("Cannot get details.");
        try {
            List<StudentsMarks> studentsMarks = studentsMarksRepository.findByStudent(studentMarksDto.getUserName());
            StudentMarksDto marksDto;
            List<StudentMarksDto> marksDtos = new ArrayList<>();
            final int totalMarks = SubjectsEnum.values().length * 100;
            double marksObtained = 0;
            for (StudentsMarks st : studentsMarks) {
                marksDto = new StudentMarksDto();
                marksDto.setMarks(st.getMarks());
                marksDto.setSubject(st.getSubject());
                marksDtos.add(marksDto);
                //for percentage
                marksObtained += st.getMarks();
            }
            responseDto.setMarksDetails(marksDtos);
            responseDto.setPercentage((marksObtained / totalMarks) * 100);
            responseDto.setResponseStaus(true);
            responseDto.setResponseMsg("Successfully fetched details.");
        } catch (Exception e) {
            logger.debug("Error while adding details of marks.");
        }
        return responseDto;
    }

    private void checkUser(String userName) throws Exception {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new Exception("Invalid user. Please register user first.");
        }
    }

    private void checkMarksDetails(StudentMarksDto studentMarksDto) throws Exception {
        if (studentMarksDto.getUserName() == null || studentMarksDto.getUserName().isEmpty()) {
            throw new Exception("Username cannot be empty.");
        }

        if (studentMarksDto.getSubject() == null) {
            throw new Exception("Subject cannot be empty.");
        }

        //check for subjects
        if (Stream.of(SubjectsEnum.values()).noneMatch(sub -> studentMarksDto.getSubject().equals(sub.getValue()))) {
            throw new Exception("Invalid subject. Please select subject correctly.");
        }
    }
}
