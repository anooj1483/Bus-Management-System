/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbms.collaborativeClouds.workers;

import cbms.collaborativeClouds.dbworkers.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CollaborativeClouds Software Solutions
 */
public class SemesterOperation {

    public void increment_semester() throws ClassNotFoundException, SQLException {
        DatabaseConnector mDBConnect = new DatabaseConnector();
        String sql_select = "select * from tbl_student";
        ResultSet mStudents = mDBConnect.select_execute(sql_select);
        while (mStudents.next()) {
            int semester = mStudents.getInt("semester");
            String student_id = mStudents.getString("student_id");
            if (semester < 8) {
                semester++;
                String update_query = "update tbl_student set semester=" + semester + " where student_id='" + student_id + "'";
                int status = mDBConnect.insert_execute(update_query);
            } else {
                semester = 100;
                String update_query = "update tbl_student set semester=" + semester + " where student_id='" + student_id + "'";
                int status = mDBConnect.insert_execute(update_query);
            }
        }
    }

    public void decrement_semester() throws ClassNotFoundException, SQLException {
        DatabaseConnector mDBConnect = new DatabaseConnector();
        String sql_select = "select * from tbl_student";
        ResultSet mStudents = mDBConnect.select_execute(sql_select);
        while (mStudents.next()) {
            int semester = mStudents.getInt("semester");
            String student_id = mStudents.getString("student_id");
            if (semester > 1) {
                semester--;
                String update_query = "update tbl_student set semester=" + semester + " where student_id='" + student_id + "'";
                int status = mDBConnect.insert_execute(update_query);
            }
        }
    }
}
