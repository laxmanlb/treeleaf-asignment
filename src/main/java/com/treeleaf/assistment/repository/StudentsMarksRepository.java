package com.treeleaf.assistment.repository;

import com.treeleaf.assistment.entity.StudentsMarks;
import com.treeleaf.assistment.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentsMarksRepository extends JpaRepository<StudentsMarks, Long> {

    @Query("Select st from StudentsMarks st where st.studentUserName=?1")
    public List<StudentsMarks> findByStudent(String studentUserName);

    @Query("Select st from StudentsMarks st where st.subject=?1 and st.studentUserName=?2")
    StudentsMarks findBySubject(String subject, String userName);

    @Modifying
    @Query("delete from StudentsMarks st where st.subject = ?1 and st.studentUserName=?2")
    int deleteMarksByUserName(String subject, String userName);
}
