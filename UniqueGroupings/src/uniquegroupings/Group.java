package uniquegroupings;

import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class Group {
    private int id;
    private int limit;
    private double average;
    private ArrayList<Student> students;
    
    Group(int id, int limit){
        students = new ArrayList<Student>();
        this.id = id;
        this.limit = limit;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
    
    private boolean isFull(){
        if (students.size() >= limit){
            return true;
        } 
        return false;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
          
    public boolean addStudent(Student student){
        if (isFull()){
            return false;
        }
        if (students.isEmpty()) {
            students.add(student);
            return true;
        }
        
        boolean okayToAdd = true;
        int pastGroup = student.getPastGroup();
        for (Student s : students) {
            if (s.getPastGroup() == pastGroup || student.getName() == s.getName()) {
                return false;
            }
        }
        students.add(student);
        return true;
    }
    
}
