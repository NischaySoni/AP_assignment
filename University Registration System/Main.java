import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the University Course Registration System");

        Students student1 = new Students("Student1", "student1@iiitd", "A12", 982317463);
        Students student2 = new Students("Student2", "student2@iiitd", "B34", 999988732);
        Students student3 = new Students("Student3", "student3@iiitd", "C56", 934999886);

        Professors prof1 = new Professors("prof1@iiitd", "profcse");
        Professors prof2 = new Professors("prof2@iiitd", "profmth");

        Administrators admin = new Administrators("admin@iiitd", "IIITD2027");

        TeachingAssistant ta1 = new TeachingAssistant("TA1", "ta1@iiitd", "ta1", 10234, null);
        TeachingAssistant ta2 = new TeachingAssistant("TA2", "ta2@iiitd", "ta2", 10254, null);
        TeachingAssistant ta3 = new TeachingAssistant("TA3", "ta3@iiitd", "ta3", 10264, null);
        TeachingAssistant ta4 = new TeachingAssistant("TA4", "ta4@iiitd", "ta4", 10344, null);
        TeachingAssistant ta5 = new TeachingAssistant("TA5", "ta5@iiitd", "ta5", 12334, null);

        Courses course1 = new Courses("CSE101", "Introduction to Programming", 1, 4, "9-10", "NA", "B003");
        Courses course2 = new Courses("MTH100", "Linear Algebra", 1, 4, "10-11", "NA", "C102");
        Courses course3 = new Courses("ECE111", "Digital Circuits", 1, 4, "11-12", "NA", "B003");
        Courses course4 = new Courses("DES102", "Introduction to HCI", 1, 4, "1-2", "NA", "C201");
        Courses course5 = new Courses("COM101", "Communication", 1, 4, "2-3", "NA", "C102");

        List<Courses> courseCatalog = new ArrayList<>(Arrays.asList(course1, course2, course3, course4, course5));

        Map<String, Students> studentsMap = new HashMap<>();
        studentsMap.put(student1.getEmail(), student1);
        studentsMap.put(student2.getEmail(), student2);
        studentsMap.put(student3.getEmail(), student3);

        Map<String, TeachingAssistant> tasMap = new HashMap<>();
        tasMap.put(ta1.getEmail(), ta1);
        tasMap.put(ta2.getEmail(), ta2);
        tasMap.put(ta3.getEmail(), ta3);
        tasMap.put(ta4.getEmail(), ta4);
        tasMap.put(ta5.getEmail(), ta5);

        Map<String, Professors> professorsMap = new HashMap<>();
        professorsMap.put(prof1.getEmail(), prof1);
        professorsMap.put(prof2.getEmail(), prof2);

        Map<String, Administrators> adminMap = new HashMap<>();
        adminMap.put(admin.getEmail(), admin);

        while (true) {
            System.out.println("\n1 - Login as Student");
            System.out.println("2 - Login as Professor");
            System.out.println("3 - Login as Administrator");
            System.out.println("4 - Login as Teaching Assistant");
            System.out.println("5 - EXIT");
            int personType = scanner.nextInt();
            scanner.nextLine(); 

            if (personType == 5) {
                System.out.println("Exiting system.");
                break;
            }

            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            switch (personType) {
                case 1:
                    try {
                        loginStudent(email, password, studentsMap, courseCatalog, tasMap, professorsMap, adminMap);
                    }
                    catch (InvalidLoginException e){
                        System.out.println((e.getMessage()));
                    }
                    break;
                case 2:
                    try {
                        loginProfessor(email, password, professorsMap, courseCatalog, tasMap, studentsMap, adminMap);
                    }
                    catch (InvalidLoginException e){
                        System.out.println((e.getMessage()));
                    }
                    break;
                case 3:
                    try {
                        loginAdmin(email, password, adminMap, courseCatalog, tasMap, professorsMap, studentsMap);
                    }
                    catch (InvalidLoginException e){
                        System.out.println((e.getMessage()));
                    }
                    break;
                case 4:
                    try {
                        loginTA(email, password, tasMap, courseCatalog, professorsMap, studentsMap, adminMap);
                    }
                    catch (InvalidLoginException e){
                        System.out.println((e.getMessage()));
                    }
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
        scanner.close();
    }

    public static void loginStudent(String email, String password, Map<String, Students> studentsMap, List<Courses> courseCatalog, Map<String, TeachingAssistant> tasMap, Map<String, Professors> professorsMap, Map<String, Administrators> adminMap) throws InvalidLoginException{
        if (studentsMap.containsKey(email) && studentsMap.get(email).authenticate(password)) {
            Students student = studentsMap.get(email);
            System.out.println("Login successful! Welcome, " + student.getName());
            student.log(courseCatalog, new ArrayList<>(studentsMap.values()), studentsMap, professorsMap, tasMap);
        } 
        else {
            System.out.println("Invalid email or password for student.");
        }
    }

    public static void loginProfessor(String email, String password, Map<String, Professors> professorsMap, List<Courses> courseCatalog, Map<String, TeachingAssistant> tasMap, Map<String, Students> studentsMap, Map<String, Administrators> adminMap) throws InvalidLoginException{
        if (professorsMap.containsKey(email) && professorsMap.get(email).authenticate(password)) {
            Professors professor = professorsMap.get(email);
            System.out.println("Login successful! Welcome, Professor " + professor.getEmail());
            professor.log(courseCatalog, new ArrayList<>(studentsMap.values()), studentsMap, professorsMap, tasMap);
        } 
        else {
            System.out.println("Invalid email or password for professor.");
        }
    }

    public static void loginAdmin(String email, String password, Map<String, Administrators> adminMap, List<Courses> courseCatalog, Map<String, TeachingAssistant> tasMap, Map<String, Professors> professorsMap, Map<String, Students> studentsMap) throws InvalidLoginException{
        if (adminMap.containsKey(email) && adminMap.get(email).authenticate(password)) {
            Administrators administrator = adminMap.get(email);
            System.out.println("Login successful! Welcome, Admin.");
            administrator.log(courseCatalog, new ArrayList<>(studentsMap.values()), studentsMap, professorsMap, tasMap);
        } 
        else {
            System.out.println("Invalid email or password for administrator.");
        }
    }

    public static void loginTA(String email, String password, Map<String, TeachingAssistant> tasMap, List<Courses> courseCatalog, Map<String, Professors> professorsMap, Map<String, Students> studentsMap, Map<String, Administrators> adminMap) throws InvalidLoginException{
        if (tasMap.containsKey(email) && tasMap.get(email).authenticate(password)) {
            TeachingAssistant ta = tasMap.get(email);
            System.out.println("Login successful! Welcome, TA " + ta.getName());
            ta.log(courseCatalog, new ArrayList<>(studentsMap.values()), studentsMap, professorsMap, tasMap);
        } 
        else {
            System.out.println("Invalid email or password for Teaching Assistant.");
        }
    }
}
