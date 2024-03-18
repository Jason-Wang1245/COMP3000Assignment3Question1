//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Students";
        String user = "postgres";
        String password = "codeide";

        try { // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
// Connect to the database
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                addStudent("Jason", "Wang", "jasonwang9@cmail.carleton.ca", "2022-09-01", conn);
//                updateStudentEmail(4, "jasonwang1245@gmail.com", conn);
//                deleteStudent(7, conn);
                getAllStudents(conn);
            } else {
                System.out.println("Failed to establish connection.");
            } // Close the connection (in a real scenario, do this in a finally
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllStudents(Connection conn){
        try {
            Statement stmt = conn.createStatement(); // Execute SQL query
            String SQL = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(SQL); // Process the result
            while(rs.next()){
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date date = rs.getDate("enrollment_date");

                System.out.println("Student ID: " + studentId + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Enrollment Date: " + date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(String firstName, String lastName, String email, String date, Connection conn){
        String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(date));
            pstmt.executeUpdate();
            System.out.println("Data inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudentEmail(int studentId, String newEmail, Connection conn){
        String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
            System.out.println("Data updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int studentId, Connection conn){
        String deleteSQL = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
            System.out.println("Data deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}