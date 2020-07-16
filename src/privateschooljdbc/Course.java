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
public class Course {
private int id;    
private String title;
private String stream;
private String type;

    public Course(int id,String title, String stream, String type) {
        this.id = id;
        this.title = title;
        this.stream = stream;
        this.type = type;
    }

    public Course(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Course " + "title=" + title + ", stream=" + stream + ", type=" + type + "\n";
    }
    
    
}
