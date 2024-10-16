package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Report;

public interface ReportService {

    void save(Report report);
    Iterable<Report> findAll();
}
