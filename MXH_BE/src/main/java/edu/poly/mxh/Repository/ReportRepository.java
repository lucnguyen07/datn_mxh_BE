package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query(value = "select * from report", nativeQuery = true)
    List<Report> findAll();
}
