
package uniquegroupings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sam
 */
public class GroupGenerator {

    private static ArrayList<Student> students;
    private int numGroups;
    private int groupSize;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        students = new ArrayList<Student>();
        students.add(new Student("Michelle", 1, 1));
        students.add(new Student("Mo", 1, 1));
        students.add(new Student("Jovana", 2, 1));
        students.add(new Student("Dejan", 3, 1));
        students.add(new Student("Sonya", 2, 2));
        students.add(new Student("Anh", 1, 2));
        students.add(new Student("Stella", 2, 2));
        students.add(new Student("Zaid", 2, 2));
        students.add(new Student("Lorianna", 1, 3));
        students.add(new Student("Gabrielle", 1, 3));
        students.add(new Student("Nilay", 3, 3));
        students.add(new Student("Rundan", 3, 3));
        students.add(new Student("Pujan", 1, 4));
        students.add(new Student("Selinay", 2, 4));
        students.add(new Student("Diane", 2, 4));
        students.add(new Student("Jeffrey", 3, 4));
        students.add(new Student("Gor", 1, 5));
        students.add(new Student("Louise", 2, 5));
        students.add(new Student("Michael", 2, 5));
        students.add(new Student("Kai Xi", 2, 5));
        students.add(new Student("Shannon", 1, 6));
        students.add(new Student("Zu Zie", 2, 6));
        students.add(new Student("Bharath", 2, 6));
        students.add(new Student("Tony", 2, 6));
        
        GroupGenerator generator = new GroupGenerator(6, 4);
        
        //will contain all the group sets to analyze
        Map<ArrayList<Group>, Double> allGroups = new HashMap<>();
        
        //run a whole bunch of times, shuffling the students after each one
        int completed = 0;
        int numRuns = 100000;
        for (int x = 0; x < numRuns; x++){
            System.out.print("Run " + x);
            ArrayList<Group> groupSet = generator.generateGroups();
            if (groupSet != null){
                allGroups.put(groupSet, generator.getGroupScore(groupSet));
                System.out.println();
                completed++;
            } else {
                System.out.println("...failed");
            }
            Collections.shuffle(students);
        }
        //printGroups(groupList);

        ArrayList<Group> bestGroupSet = null;
        double bestScore = 100000;
        for (Map.Entry<ArrayList<Group>, Double> entry : allGroups.entrySet()) {
            ArrayList<Group> key = entry.getKey();
            double value = entry.getValue();
            if (value < bestScore) {
                bestGroupSet = key;
                bestScore = value;
            }
        }
        System.out.println("Best score: " + bestScore);
        if (bestGroupSet != null){
            printGroups(bestGroupSet);
        }
        System.out.println("Fail rate of algorithm: " + (double)completed/numRuns);
    }
    
    GroupGenerator(int numGroups, int groupSize){
        this.numGroups = numGroups;
        this.groupSize = groupSize;
    }
    
    private ArrayList generateGroups(){
        ArrayList<Group> groupList = new ArrayList<>();
        ArrayList<Student> listClone = (ArrayList<Student>) students.clone();
        for (int x = 0; x < numGroups; x++){
            groupList.add(new Group(x, groupSize));
        }
        
        int lastGroupAdded = -1;
        Iterator iter = listClone.iterator();
        while (iter.hasNext()) {
            boolean studentAdded = false;
            Student student = (Student) iter.next();
            int numTries = 0;
            while (!studentAdded && numTries < numGroups){
                lastGroupAdded = getNextGroupId(lastGroupAdded);
                studentAdded = groupList.get(lastGroupAdded).addStudent(student);
                numTries++;
            }
            
            if (!studentAdded) {
                //System.err.println("Couldn't find a group for student");
                return null;
            }
        }
        return groupList;
    }
    
    public int getNextGroupId(int id){
        if (id++ >= numGroups-1){
            return 0;
        } 
        return id++;
    }
    
    private static void printGroups(List<Group> list){
        for (Group group : list){
            System.out.println("Group " + group.getId() + " average score: " + group.getAverage());
            for (Student student : group.getStudents()){
                System.out.println(student.getName());
            }
            System.out.println("");
        }
        System.out.println("---------------");
    }
    
    private double getGroupScore(ArrayList<Group> groupList){
        double[] averages = new double[groupList.size()];
        for (int x =0; x < groupList.size(); x++){
            Group g = groupList.get(x);
            int groupScore = 0;
            for (Student s : g.getStudents()){
                groupScore += s.getCode();
            }
            averages[x] = (double) groupScore/g.getStudents().size();
            g.setAverage(averages[x]);
        }
        double averagesSum = 0;
        for (int x = 0; x < averages.length; x++){
            averagesSum += averages[x];
        }
        double averagesMean = averagesSum/averages.length;
        
        //find the standard deviation
        double sdNumerator = 0;
        for (int x = 0; x < averages.length; x++){
            double distToMean = averages[x] - averagesMean;
            sdNumerator += distToMean*distToMean;
        }
        return sdNumerator/averages.length;
        
    }
    
}