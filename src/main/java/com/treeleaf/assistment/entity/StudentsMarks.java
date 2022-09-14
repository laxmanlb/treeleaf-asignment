package com.treeleaf.assistment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class StudentsMarks implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentMarksId;
    private String studentUserName;
    private String teachersUsername;
    private String subject;
    private double marks;

    private StudentsMarks(){}
    public StudentsMarks(String studentUserName, String teachersUsername, String subject, double marks) {
        this.studentUserName = studentUserName;
        this.teachersUsername = teachersUsername;
        this.subject = subject;
        this.marks = marks;
    }

    public Long getStudentMarksId() {
        return studentMarksId;
    }

    public void setStudentMarksId(Long studentMarksId) {
        this.studentMarksId = studentMarksId;
    }

    public String getStudentUserName() {
        return studentUserName;
    }

    public void setStudentUserName(String studentUserName) {
        this.studentUserName = studentUserName;
    }

    public String getTeachersUsername() {
        return teachersUsername;
    }

    public void setTeachersUsername(String teachersUsername) {
        this.teachersUsername = teachersUsername;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
