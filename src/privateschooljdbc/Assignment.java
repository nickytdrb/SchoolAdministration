/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateschooljdbc;

/**
 *
 * @author Nicky
 */
public class Assignment {
private int id;
private String title;
private String description;
private double totalMark;
private int course_id;


    public Assignment(int id, String title, String description, double totalMark, int course_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.totalMark = totalMark;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    @Override
    public String toString() {
        return "Assignments{" + "title=" + title + ", description=" + description + ", totalMark=" + totalMark + '}';
    }
 
}