package uniquegroupings;

/**
 *
 * @author Sam
 */
public class Student {
    private String name;
    private int code; //1,2,3
    private int pastGroup;
    
    Student(String name, int code, int pastGroup){
        this.name = name;
        this.code = code;
        this.pastGroup = pastGroup;
    }

    public int getCode() {
        return code;
    }

    public int getPastGroup() {
        return pastGroup;
    }

    public String getName() {
        return name;
    }
    
    
}
