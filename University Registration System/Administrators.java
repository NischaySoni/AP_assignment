import java.util.*;

class Administrators extends UserRoles{
    
    private final String PASSWORD = "IIITD2027";

    public String getEmail() {
        return email;
    }

    public boolean authenticate(String password) {
        return this.password.equals(PASSWORD);
    }

    public Administrators(String email, String password){
        super(email, password);
        if (!password.equals(PASSWORD)){
            System.out.println("Invalid password");
            throw new SecurityException("Unauthorized access.");
        }
    }

    public void log(List<Courses> courseCatalog, List<Students> studentsList, Map<String, Students> studentsMap, Map<String, Professors> professorsMap, Map<String, TeachingAssistant> taMap){
        Scanner scanner = new Scanner(System.in);
        int functionality;
        do {
            System.out.println("\nWelcome Admin");
            System.out.println("Administrators Functionalities");
            System.out.println("1-Manage Course Catalog");
            System.out.println("2-Manage Student Records");
            System.out.println("3-Assign Professors to courses");
            System.out.println("4-Handle Complaints");
            System.out.println("5-Logout");
            System.out.println("Enter your option: ");
            functionality = scanner.nextInt();
            scanner.nextLine();
        switch (functionality){
            case 1:
            manageCourseCatalog(courseCatalog);
            break;
            case 2:
            manageStudentRecords(studentsMap);
            break;
            case 3:
            assignProfToCourses(courseCatalog, professorsMap);
            break;
            case 4:
            handleComplaints(studentsMap);
            break;
            case 5:
            logout();
            break;
            default:
            System.out.println("Invalid option");
        }
    }
        while (functionality != 5);
    }

   public void manageCourseCatalog(List<Courses> courseCatalog){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nManage Courses:");
        System.out.println("1-View Course Catalog");
        System.out.println("2-Add a Course");
        System.out.println("3-Remove a Course");
        System.out.print("Enter your option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println("\nCourse Catalog:");
                for (Courses c : courseCatalog) {
                    System.out.println(c);
                }
                break;
            case 2:
                System.out.println("Enter the course details to add:");
                System.out.print("Course Code: ");
                String courseCode = scanner.nextLine();
                System.out.print("Course Name: ");
                String courseName = scanner.nextLine();
                System.out.print("Semester: ");
                int semester = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Credits: ");
                int credits = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Timings: ");
                String timings = scanner.nextLine();
                System.out.print("Location: ");
                String location = scanner.nextLine();
                Courses newCourse = new Courses(courseCode, courseName, semester, credits, timings, "NA", location);
                courseCatalog.add(newCourse);
                System.out.println("New Course added successfully!");
                break;
            case 3:
                System.out.print("Enter the course Code to remove: ");
                String c = scanner.nextLine();
                courseCatalog.removeIf(course -> course.getCode().equals(c));
                System.out.println("Course removed successfully!");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void manageStudentRecords(Map<String, Students> studentsMap){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nManage Student Records:");
        System.out.println("1-View Student Records");
        System.out.println("2-Update Student Records");
        System.out.print("Enter your option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println("Student Records:");
                for (Students student : studentsMap.values()) {
                    System.out.println("Name: " + student.getName());
                    System.out.println("Email: " + student.getEmail());
                    System.out.println("Semester: " + student.getSemester());
                    System.out.println("Academic Standing: " + student.getAcademicStanding());
                    System.out.println("Contact: " + student.getContact());
                }
                break;
                case 2:
                System.out.print("Enter the student email to update the information: ");
                String email = scanner.nextLine();
                if (studentsMap.containsKey(email)) {
                    Students s = studentsMap.get(email);
                    System.out.println("Updating records for: " + s.getName());
                    System.out.print("Enter the current standing of " + s.getName() + " : ");
                    String standing = scanner.nextLine();
                    s.setAcademicStanding(standing);
                    System.out.println("Student records updated successfully!!.");
                }
                else{
                    System.out.println("Student not found.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void assignProfToCourses(List<Courses> courseCatalog, Map<String, Professors> professorsMap) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nAssign Professor to Courses:");
        System.out.println("Available Courses: ");
        for (int i = 0; i < courseCatalog.size(); i++) {
            System.out.println((i + 1) + "=> " + courseCatalog.get(i).getTitle() + " (" + courseCatalog.get(i).getCode() + ")");
        }
        System.out.print("Enter the index of the course given above to assign a professor: ");
        int c_idx = scanner.nextInt() - 1;
        scanner.nextLine(); 
        if (c_idx < 0 || c_idx >= courseCatalog.size()) {
            System.out.println("Invalid course selection.");
            return;
        }
        Courses regCourse = courseCatalog.get(c_idx);
        System.out.println("Available Professors:");
        List<Professors> prof = new ArrayList<>(professorsMap.values());
        for (int i = 0; i < prof.size(); i++) {
            System.out.println((i + 1) + ". " + prof.get(i).getEmail());
        }
        System.out.print("Enter the professor number to assign to " + regCourse.getTitle() + ": ");
        int p_idx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (p_idx < 0 || p_idx >= prof.size()) {
            System.out.println("Invalid professor number.");
            return;
        }
        Professors p = prof.get(p_idx);
        p.getAssignedCourses().add(regCourse);
        regCourse.assignProfessor(p);
        System.out.println("Professor " + p.getEmail() + " is now assigned to course " + regCourse.getTitle());
    }

    public void handleComplaints(Map<String, Students> studentsMap) {
        Scanner scanner = new Scanner(System.in);
        List<Complaint> comp = new ArrayList<>();
        for (Students s : studentsMap.values()) {
            comp.addAll(s.getComplaints());
        }
        if (comp.isEmpty()) {
            System.out.println("No complaints available for now.");
            return;
        }
        System.out.println("1->Filter complaints by status");
        System.out.println("2->Filter complaints by date");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        List<Complaint> fil = new ArrayList<>(comp);
        switch (option) {
            case 1:
                System.out.print("Enter status (Pending/Resolved): ");
                String status = scanner.nextLine();
                fil.removeIf(c -> !c.getStatus().equalsIgnoreCase(status));
                break;
            case 2:
                System.out.print("Enter filter date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                fil.removeIf(c -> !c.getComplaintDate().toString().startsWith(date));
                break;
            default:
                break;
        }

        if (fil.isEmpty()) {
            System.out.println("No complaints to resolve.");
            return;
        }
        for (int i = 0; i < fil.size(); i++) {
            System.out.println((i + 1) + ": " + fil.get(i));
        }
        System.out.print("Enter the complaint number to resolve: ");
        int comp_idx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (comp_idx < 0 || comp_idx >= fil.size()) {
            System.out.println("Invalid complaint index.");
            return;
        }
        Complaint selected = fil.get(comp_idx);
        System.out.print("Enter resolution details: ");
        selected.setStatus("Resolved");
        System.out.println("Complaint resolved successfully!");
    }

    public void logout(){
        System.out.println("Administrator functionality: Logging out ...");
    }
}
