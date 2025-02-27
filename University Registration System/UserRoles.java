import java.util.List;
import java.util.Map;

abstract class UserRoles {
    
    protected String email;
    protected String password;

    public UserRoles(String email, String password){
        this.email = email;
        this.password = password;
    }

    public abstract void log(List<Courses> courseCatalog, List<Students> studentsList, Map<String, Students> studentsMap, Map<String, Professors> professorsMap, Map<String, TeachingAssistant> taMap);
}
