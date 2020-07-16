/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateschooljdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Nicky
 */
public class DBUtils {

    public static ArrayList<Student> getStudents(Connection con) throws SQLException {
        int id;
        String fullName;
        double tuitionFees;
        String dateOfBirth;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from students");
        ArrayList<Student> studentsList = new ArrayList<>();
        System.out.println(result);

        while (result.next()) {
            //System.out.println(result.getString("st_fullname")); 

            id = result.getInt("st_id");
            fullName = result.getString("st_fullname");
            tuitionFees = result.getDouble("st_tuitionfees");
            dateOfBirth = result.getString("st_dateofbirth");

            Student st01 = new Student(id, fullName, dateOfBirth, tuitionFees);
            studentsList.add(st01);

        }
        return studentsList;
    }

    public static ArrayList<Trainer> getTrainers(Connection con) throws SQLException {
        int id;
        String fullName;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from trainers");
        ArrayList<Trainer> trainersList = new ArrayList<>();
        System.out.println(result);

        while (result.next()) {
            //System.out.println(result.getString("tr_fullname")); 

            id = result.getInt("tr_id");
            fullName = result.getString("tr_fullname");

            Trainer tr01 = new Trainer(id, fullName);
            trainersList.add(tr01);

        }
        return trainersList;
    }

    public static ArrayList<Assignment> getAssignments(Connection con) throws SQLException {
        int id;
        String title;
        String description;
        double totalMark;
        int course_id;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from assignments");
        ArrayList<Assignment> assignmentsList = new ArrayList<>();
        System.out.println(result);

        while (result.next()) {
            id = result.getInt("as_id");
            title = result.getString("as_title");
            description = result.getString("as_description");
            totalMark = result.getDouble("as_totalmark");
            course_id = result.getInt("course_id");

            Assignment assi01 = new Assignment(id, title, description, totalMark, course_id);
            assignmentsList.add(assi01);
        }
        return assignmentsList;
    }

    public static ArrayList<Course> getCourses(Connection con) throws SQLException {
        int id;
        String title;
        String stream;
        String type;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from courses");
        ArrayList<Course> coursesList = new ArrayList<>();
        System.out.println(result);

        while (result.next()) {
            id = result.getInt("cs_id");
            title = result.getString("cs_title");
            stream = result.getString("cs_stream");
            type = result.getString("cs_type");

            Course cs01 = new Course(id, title, stream, type);
            coursesList.add(cs01);
        }
        return coursesList;
    }

    public static void studentsPerCourse(Connection con) throws SQLException {
        String fullname;
        String title;
        String stream;
        String type;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT students.st_fullname, courses.cs_title, courses.cs_stream, courses.cs_type  \n"
                + "FROM students\n"
                + "INNER JOIN students_per_course\n"
                + "ON students.st_id = students_per_course.ST_ID\n"
                + "INNER JOIN courses \n"
                + "ON courses.cs_id = students_per_course.CS_ID\n"
                + "ORDER BY courses.cs_stream, courses.cs_type;");

        while (result.next()) {
            fullname = result.getString("st_fullname");
            title = result.getString("cs_title");
            stream = result.getString("cs_stream");
            type = result.getString("cs_type");
            System.out.println("Student's full name: " + fullname + " Course title: " + title + " Course stream: " + stream + " Course type: " + type);
        }
    }

    public static void trainersPerCourse(Connection con) throws SQLException {
        String fullname;
        String title;
        String stream;
        String type;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT trainers.tr_fullname, courses.cs_title, courses.cs_stream, courses.cs_type\n"
                + "                 FROM trainers\n"
                + "                 INNER JOIN trainers_per_course\n"
                + "                 ON trainers.tr_id = trainers_per_course.tr_id\n"
                + "                 INNER JOIN courses \n"
                + "                 ON courses.cs_id = trainers_per_course.cs_id\n"
                + "                 ORDER BY courses.cs_stream, courses.cs_type;");

        while (result.next()) {
            fullname = result.getString("tr_fullname");
            title = result.getString("cs_title");
            stream = result.getString("cs_stream");
            type = result.getString("cs_type");
            System.out.println("Trainer's full name: " + fullname + " Course title: " + title + " Course stream: " + stream + " Course type: " + type);
        }
    }

    public static void assignmentsPerCourse(Connection con) throws SQLException {
        String title;
        String stream;
        String type;
        String assignmentsTitles;
        String assignmentsTotalMark;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT courses.*, GROUP_CONCAT(assignments.as_title) AS Assignments, GROUP_CONCAT(assignments.as_totalmark) AS Total_marks\n"
                + "FROM assignments\n"
                + "LEFT JOIN courses\n"
                + "ON courses.cs_id = assignments.course_id\n"
                + "GROUP BY assignments.course_id;");

        while (result.next()) {
            title = result.getString("cs_title");
            stream = result.getString("cs_stream");
            type = result.getString("cs_type");
            assignmentsTitles = result.getString("Assignments");
            assignmentsTotalMark = result.getString("Total_marks");
            System.out.println("Course Title: " + title + " Course Stream: " + stream + " Course Type: " + type);
            System.out.println("Assignments titles: " + assignmentsTitles + " Assignments total mark: " + assignmentsTotalMark);
        }
    }

    public static void assignmentsPerCoursePerStudent(Connection con) throws SQLException {
        String fullname;
        String title;
        String stream;
        String type;
        String assignmentsTitles;
        String assignmentsDescriptions;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT st.st_fullname,c.cs_title,c.cs_stream,c.cs_type,GROUP_CONCAT(assi.as_title) AS Assignment_Titles, GROUP_CONCAT(assi.as_description) AS Assignment_Descriptions\n"
                + "FROM students_per_course as stpc\n"
                + "LEFT JOIN assignments as assi\n"
                + "ON stpc.cs_id = assi.course_id\n"
                + "LEFT JOIN students as st\n"
                + "ON stpc.st_id = st.st_id\n"
                + "LEFT JOIN courses as c\n"
                + "ON stpc.cs_id = c.cs_id\n"
                + "GROUP BY st.st_fullname;");

        while (result.next()) {
            fullname = result.getString("st_fullname");
            title = result.getString("cs_title");
            stream = result.getString("cs_stream");
            type = result.getString("cs_type");
            assignmentsTitles = result.getString("Assignment_Titles");
            assignmentsDescriptions = result.getString("Assignment_Descriptions");
            System.out.println("Student's full name: " + fullname + " Course title: " + title + " Course stream: " + stream + " Course type: " + type);
            System.out.println("Assignments titles: " + assignmentsTitles + " Assignments Descriptions: " + assignmentsDescriptions);
        }
    }

    public static void studentsThatBelongToMoreThanOneCourses(Connection con) throws SQLException {
        String fullname;
        int courses;
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("SELECT s.st_fullname,COUNT(spc.st_id) AS Courses\n"
                + "FROM students_per_course as spc\n"
                + "LEFT JOIN students as s\n"
                + "On spc.st_id = s.st_id\n"
                + "GROUP BY spc.st_id HAVING COUNT(spc.st_id)>1 ;");

        while (result.next()) {
            fullname = result.getString("st_fullname");
            courses = result.getInt("Courses");
            System.out.println("Student's full name: " + fullname + " Number of courses he/she is attending: " + courses);
        }
    }

    public static boolean insertStudent(Connection con) throws SQLException {
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);

        String studentFullName;
        String date_of_birth;
        String tuition_fees;

        System.out.println("Please type the student's full name: (Please don't use numeric type or special characters). ");
        studentFullName = sc.nextLine();

        System.out.println("Please type the student's date of birth: (Please insert the date in a format similar to: '1995-02-24'). ");
        date_of_birth = sc.nextLine();

        System.out.println("Please type the student's tuition fees: (Please use numbers.)");
        tuition_fees = sc.nextLine();

        int rowsAffected = st.executeUpdate("INSERT INTO students VALUES (NULL,'" + studentFullName + "','" + date_of_birth + "','" + tuition_fees + "')");
        if (rowsAffected == 0) {
            System.out.println("Zero rows affected.");
            return false;
        } else {
            System.out.println("Rows affected: " + rowsAffected + ".");
            return true;
        }
    }

    public static boolean insertTrainer(Connection con) throws SQLException {
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        String trainerFullName;

        System.out.println("Please type the trainer's full name: (Please don't use numeric type or special characters). ");
        trainerFullName = sc.nextLine();

        int rowsAffected = st.executeUpdate("INSERT INTO trainers VALUES (NULL,'" + trainerFullName + "')");
        if (rowsAffected == 0) {
            System.out.println("Zero rows affected.");
            return false;
        } else {
            System.out.println("Rows affected: " + rowsAffected + ".");
            return true;
        }
    }

    public static boolean insertAssignment(Connection con) throws SQLException {
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        ArrayList<Course> allCourses = new ArrayList<>();
        String assignmentTitle;
        String assignmentDescription;
        String answer;
        double assignmentTotalMark;
        int assignmentCourseID;
        String courseAnswer;
        int courseAnswerInt;

        System.out.println("Please type the assignment's title: (Please don't use any special characters). ");
        assignmentTitle = sc.nextLine();

        System.out.println("Please type the assignment's description: ");
        assignmentDescription = sc.nextLine();

        System.out.println("Please type the assignment's total mark: ");
        assignmentTotalMark = sc.nextDouble();
        System.out.println("Now that a new Assignment is created please select if you want to insert the Assignment"
                + " to an already existing Course(1) or create a new Course(2).");
        answer = sc.nextLine();
        allCourses = DBUtils.getCourses(con);
        if (answer.equals("1")) {
            for (Course cs : allCourses) {
                System.out.println("Id:" + cs.getId() + " | :" + cs);
            }
            System.out.println("Please choose a number id from the above courses:");
            loop1:
            while (true) {
                courseAnswer = sc.nextLine();

                courseAnswerInt = Integer.parseInt(courseAnswer);
                for (Course cs : allCourses) {
                    if (cs.getId() == courseAnswerInt) {
                        courseAnswerInt = cs.getId();
                        break loop1;
                    }
                }
                System.out.println("Please give a numeric id corresponding to one of the above classes.");
            }
            System.out.println("Inserting Assignment and its corresponding Course:");
            int rowsAffected = st.executeUpdate("INSERT INTO assignments VALUES (NULL,'" + assignmentTitle + "','" + assignmentDescription + "','" + assignmentTotalMark + "','" + courseAnswerInt + "')");
            if (rowsAffected == 0) {
                System.out.println("Zero rows affected.");
                return false;
            } else {
                System.out.println("Rows affected: " + rowsAffected + ".");
                return true;
            }
        } else {
            DBUtils.insertCourse(con);
            int lastCoursesId = (allCourses.get(allCourses.size() - 1)).getId();
            System.out.println("Inserting Student and Course on Students per Course:");
            int rowsAffected = st.executeUpdate("INSERT INTO assignments VALUES (NULL,'" + assignmentTitle + "','" + assignmentDescription + "','" + assignmentTotalMark + "','" + lastCoursesId + "')");
            if (rowsAffected == 0) {
                System.out.println("Nothing affected.");
                return false;
            } else {
                System.out.println("Number of rows affected:" + rowsAffected + ".");
                return true;
            }
        }
    }

    public static boolean insertCourse(Connection con) throws SQLException {
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        String courseTitle;
        String courseStream;
        String courseType;

        System.out.println("Please type the course's title: (Course title must be of the format eg CB9). ");
        courseTitle = sc.nextLine();

        System.out.println("Please type the course's stream: (Course stream must be of the format eg Java or C#). ");
        courseStream = sc.nextLine();

        System.out.println("Please type the course's type: (Course type must be of the format eg Full-time or Part-time). ");
        courseType = sc.nextLine();

        int rowsAffected = st.executeUpdate("INSERT INTO courses VALUES (NULL,'" + courseTitle + "','" + courseStream + "','" + courseType + "')");

        if (rowsAffected == 0) {
            System.out.println("Zero rows affected.");
            return false;
        } else {
            System.out.println("Rows affected: " + rowsAffected + ".");
            return true;
        }
    }

    public static boolean insertStudentPerCourse(int studentId, int courseId, Connection con) throws SQLException {
        Statement st = con.createStatement();
        int rowsAffected = st.executeUpdate("INSERT INTO students_per_course VALUES ('" + studentId + "','" + courseId + "')");
        if (rowsAffected == 0) {
            System.out.println("Zero rows affected.");
            return false;
        } else {
            System.out.println("Rows affected:" + rowsAffected + ".");
            return true;
        }
    }

    public static boolean insertTrainerPerCourse(int trainerId, int courseId, Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        int rowsAffected = st.executeUpdate("INSERT INTO trainers_per_course VALUES ('" + trainerId + "','" + courseId + "')");
        if (rowsAffected == 0) {
            System.out.println("Nothing affected.");
            return false;
        } else {
            System.out.println("Number of rows affected:" + rowsAffected + ".");
            return true;
        }
    }

}
