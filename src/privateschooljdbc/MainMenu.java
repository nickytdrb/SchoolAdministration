/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateschooljdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Nicky
 */
public class MainMenu {

    public static void mainMenu(Connection con) throws SQLException {
        ArrayList<Student> allStudents = new ArrayList<>();
        ArrayList<Trainer> allTrainers = new ArrayList<>();
        ArrayList<Assignment> allAssignments = new ArrayList<>();
        ArrayList<Course> allCourses = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String option;
        String answer;
        String courseAnswer;
        int lastTrainerId;
        int courseAnswerInt = 0;
        int lastStudentId;
        int lastCoursesId;
        int courseId;

        loop:
        while (true) {
            System.out.println("-----------------------------Main Menu--------------------------");
            System.out.println("Please pick an option (1-16) or type 'exit' to close application:");
            System.out.println("1.To print all the students.");
            System.out.println("2.To print all the trainers.");
            System.out.println("3.To print all the assignments.");
            System.out.println("4.To print all the courses");
            System.out.println("5.To print all the students per course.");
            System.out.println("6.To print all the trainers per course.");
            System.out.println("7.To print all the assignments per course.");
            System.out.println("8.To print all the assignments per course per student.");
            System.out.println("9.To print all the students that belong to more than one courses.");
            System.out.println("10.To insert a student.");
            System.out.println("11.To insert a trainer.");
            System.out.println("12.To insert an assignment.");
            System.out.println("13.To insert a course.");
            System.out.println("14.To insert students per course.");
            System.out.println("15.To insert trainers per course.");
            System.out.println("16.To insert assignments per course per student.");
            System.out.println("----------------------------------------------------------------");

            option = sc.nextLine();

            switch (option) {
                case "1":
                    allStudents = DBUtils.getStudents(con);
                    System.out.println("All students:");
                    for (Student st : allStudents) {
                        System.out.println(st);//isws na thelei kai ena.toString meta to allStudents, opws kai se oles tis parakatw listes
                    }
                    break;
                case "2":
                    allTrainers = DBUtils.getTrainers(con);
                    System.out.println("All trainers:");
                    for (Trainer tr : allTrainers) {
                        System.out.println(tr);
                    }
                    break;
                case "3":
                    allAssignments = DBUtils.getAssignments(con);
                    System.out.println("All assignments:");
                    for (Assignment assi : allAssignments) {
                        System.out.println(assi);
                    }
                    break;
                case "4":
                    allCourses = DBUtils.getCourses(con);
                    System.out.println("All courses:");
                    for (Course cs : allCourses) {
                        System.out.println(cs);
                    }
                    break;
                case "5":
                    DBUtils.studentsPerCourse(con);
                    break;
                case "6":
                    DBUtils.trainersPerCourse(con);
                    break;
                case "7":
                    DBUtils.assignmentsPerCourse(con);
                    break;
                case "8":
                    DBUtils.assignmentsPerCoursePerStudent(con);
                    break;
                case "9":
                    DBUtils.studentsThatBelongToMoreThanOneCourses(con);
                    break;
                case "10":
                    DBUtils.insertStudent(con);
                    break;
                case "11":
                    DBUtils.insertTrainer(con);
                    break;
                case "12":
                    DBUtils.insertAssignment(con);
                    break;
                case "13":
                    DBUtils.insertCourse(con);
                    break;
                case "14":
                    DBUtils.insertStudent(con);
                    System.out.println("Student created! Type 1 to insert him to an already existing course, type 2 to insert him to a new one..");
                    answer = sc.nextLine();
                    allCourses = DBUtils.getCourses(con);
                    allStudents = DBUtils.getStudents(con);
                    if (answer.equals("1")) {
                        for (Course cs : allCourses) {
                            System.out.println("ID:" + cs.getId() + " | :" + cs);
                        }
                        System.out.println("Please type an Id:");
                        courseAnswer = sc.nextLine();
                        courseAnswerInt = Integer.parseInt(courseAnswer);
                        for (Course cs : allCourses) {
                            if (cs.getId() == courseAnswerInt) {
                                courseAnswerInt = cs.getId();
                            }
                        }
                        System.out.println("Please give a numeric id corresponding to one of the above classes.");
                        System.out.println("Inserting Student and Course on Students per Course:");
                        lastStudentId = (allStudents.get(allStudents.size() - 1)).getId();
                        DBUtils.insertStudentPerCourse(lastStudentId, courseAnswerInt, con);
                    } else {
                        DBUtils.insertCourse(con);
                        lastStudentId = (allStudents.get(allStudents.size() - 1)).getId();
                        lastCoursesId = (allCourses.get(allCourses.size() - 1)).getId();
                        System.out.println("Inserting Student and Course on Students per Course:");
                        DBUtils.insertStudentPerCourse(lastStudentId, lastCoursesId, con);
                    }
                    break;
                case "15":
                    DBUtils.insertTrainer(con);
                    System.out.println("Trainer created! Type 1 to insert him to an already existing course, type 2 to insert him to a new one.");

                    answer = sc.nextLine();
                    allCourses = DBUtils.getCourses(con);
                    allTrainers = DBUtils.getTrainers(con);
                    if (answer.equals("1")) {
                        for (Course cs : allCourses) {
                            System.out.println("Id:" + cs.getId() + " | :" + cs);
                        }
                        System.out.println("Pleasy Type an id:");
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
                        System.out.println("Inserting Trainer and Course on table Trainers per Course:");
                        lastTrainerId = (allTrainers.get(allTrainers.size() - 1)).getId();
                        DBUtils.insertTrainerPerCourse(lastTrainerId, courseAnswerInt, con);
                    } else {
                        DBUtils.insertCourse(con);
                        lastTrainerId = (allTrainers.get(allTrainers.size() - 1)).getId();
                        lastCoursesId = (allCourses.get(allCourses.size() - 1)).getId();
                        System.out.println("Inserting Trainer and Course on table Trainers per Course:");
                        DBUtils.insertTrainerPerCourse(lastTrainerId, lastCoursesId, con);
                    }
                    break;
                case "16":
                    DBUtils.insertAssignment(con);
                    System.out.println("Now that the Assignment per Course is created. Please Insert a Student");
                    DBUtils.insertStudent(con);
                    System.out.println("Inserting Student and Course of Assignmnet on Students per Course:");
                    allStudents = DBUtils.getStudents(con);
                    allAssignments = DBUtils.getAssignments(con);
                    lastStudentId = (allStudents.get(allStudents.size() - 1)).getId();
                    courseId = (allAssignments.get(allAssignments.size() - 1).getCourse_id());
                    DBUtils.insertStudentPerCourse(lastStudentId, courseId, con);
                    break;
                case "exit":
                    System.out.println("End of application.");
                    break loop;
            }
        }
    }
}
