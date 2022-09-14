package com.treeleaf.assistment.controller;

import com.treeleaf.assistment.dto.GeneralResponseDto;
import com.treeleaf.assistment.dto.StudentMarksDetailsDto;
import com.treeleaf.assistment.dto.StudentMarksDto;
import com.treeleaf.assistment.enums.SubjectsEnum;
import com.treeleaf.assistment.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @GetMapping(value = "getSubjects")
    public List<String> getSubjectList() {
        List<String> shiftList = new ArrayList<>();
        for (SubjectsEnum value : SubjectsEnum.values()) {
            shiftList.add(value.getValue());
        }
        return shiftList;
    }

    /**
     *
     * @param studentMarksDto where all variables are compulsory
     * @return
     */
    @PostMapping(value = "addMarks", consumes = "application/json", produces = "application/json")
//    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<GeneralResponseDto> addMarks(@RequestBody StudentMarksDto studentMarksDto) {

        GeneralResponseDto resp = userService.addUpdateStudentMarks(studentMarksDto);
        if (resp.isResponseStatus()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resp);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp);
        }
    }

    @PutMapping(value = "updateMarks", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GeneralResponseDto> updateMarks(@RequestBody StudentMarksDto studentMarksDto) {

        GeneralResponseDto resp = userService.addUpdateStudentMarks(studentMarksDto);
        if (resp.isResponseStatus()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resp);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp);
        }
    }

    /**
     *
     * @param studentMarksDto where subject name and student name are required
     * @return
     */
    @DeleteMapping(value = "removeMarks", consumes = "application/json", produces = "application/json")
//    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<GeneralResponseDto> removeMarks(@RequestBody StudentMarksDto studentMarksDto) {

        GeneralResponseDto resp = userService.removeStudentsMarks(studentMarksDto);
        if (resp.isResponseStatus()) {
            resp.setResponseMsg("Successfully removed detail.");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resp);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp);
        }
    }

    /**
     *
     * @param @return list of students with percentage
     */
    @GetMapping(value = "getPercentageOfStudent", consumes = "application/json", produces = "application/json")
//    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<StudentMarksDto>> getPercentageOfStudent() {

        List<StudentMarksDto> resp = userService.getAllStudentsPercentage();
        if (!resp.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resp);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp);
        }
    }

    /**
     *
     * @param @return students marks with percentage
     */
    @PostMapping(value = "getStudentDetails", consumes = "application/json", produces = "application/json")
//    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentMarksDetailsDto> getStudentDetails(@RequestBody StudentMarksDto studentMarksDto) {

        StudentMarksDetailsDto resp = userService.getStudentDetails(studentMarksDto);
        if (resp.isResponseStaus()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resp);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp);
        }
    }
}
