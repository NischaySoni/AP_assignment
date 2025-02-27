import java.util.*;

class Students extends UserRoles{
    private String name;
    private int semester;
    private String academicStanding;
    private long contact;
    private int totalCredits = 0;
    private List<Courses> registeredCourses = new ArrayList<>();
    private List<Complaint> complaints = new ArrayList<>();
    private Grade trackAcademic = new Grade();
    private List<Courses> giveFeedBack;
    private List<FeedBack<?>> feedbackList = new ArrayList<>();


    public Students(String name, String email, String password, long contact){
        super(email, password);
        this.name = name;
        this.semester = 1;
        this.academicStanding = "";
        this.contact = contact;
        this.giveFeedBack = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    public long getContact(){
        return contact;
    }

    public String getAcademicStanding() {
        return academicStanding;
    }

    public void setAcademicStanding(String academicStanding) {
        this.academicStanding = academicStanding;
    }

    public List<Courses> getRegisteredCourses() {
        return registeredCourses;
    }

    public List<Courses> getGiveFeedback() {
        return giveFeedBack;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    TimeManager timeManager = new TimeManager();

    @Override
    public void log(List<Courses> courseCatalog, List<Students> studentsList, Map<String, Students> studentsMap, Map<String, Professors> professorsMap, Map<String, TeachingAssistant> taMap){
        Scanner scanner = new Scanner(System.in);
        int functionality;
        do {
            System.out.println("\nWelcome " + name);
            System.out.println("Student Functionalities");
            System.out.println("1-View Available Courses");
            System.out.println("2-Register for courses");
            System.out.println("3-View Schedule");
            System.out.println("4-Track Academic Progress");
            System.out.println("5-Drop Courses");
            System.out.println("6-Submit Complaints");
            System.out.println("7-View Complaints");
            System.out.println("8-Add FeedBack");
            System.out.println("9-Submit Assignment");
            System.out.println("10-Logout");
            System.out.println("Enter your option: ");
            functionality = scanner.nextInt();
            scanner.nextLine();
        switch (functionality){
            case 1:
            viewAvailableCourses(courseCatalog);
            break;
            case 2:
            try {
                registerForCourses(courseCatalog);
            }
            catch (CourseFullException e){
                System.out.println((e.getMessage()));
            }
            break;
            case 3:
            viewSchedule();
            break;
            case 4:
            trackAcademicProgress();
            break;
            case 5:
            dropCourses();
            break;
            case 6:
            submitComplaints();
            System.out.println("Do you want to view the status of your complaint? (yes/no): ");
            String viewoption = scanner.nextLine();
            if (viewoption.equalsIgnoreCase("yes")) {
                viewComplaints();
            }
            break;
            case 7:
            viewComplaints();
            case 8:
            addFeedback(registeredCourses);
            break;
            case 9:
            try {
                long currentDay = timeManager.getCurrentDay();
                submitAssignment(currentDay, registeredCourses);
            }
            catch (DropDeadlinePassedException e){
                System.out.println((e.getMessage()));
            }
            case 10:
            logout();
            break;
            default:
            System.out.println("Invalid option");
            }
        }
        while (functionality != 10);
    }

    public void viewAvailableCourses(List<Courses> courseCatalog) {
        System.out.println("Available Courses:");
        for (Courses c : courseCatalog) {
            System.out.println(c);
        }
    }

    public void registerForCourses(List<Courses> courseCatalog) throws CourseFullException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available courses for semester " + semester + ":");
        List<Courses> Course = new ArrayList<>();
        for (Courses c : courseCatalog) {
            if (c.semester == semester) {
                Course.add(c);
                System.out.println(c.title + " " + c.code + " (" + c.credits + " credits)");
            }
        }
        System.out.println("Enter the course code to register:");
        String courseCode = scanner.nextLine();
        Courses regCourse = null;
        for (Courses c : Course) {
            if (c.code.equals(courseCode)) {
                regCourse = c;
                break;
            }
        }
        if (regCourse == null) {
            System.out.println("No courses registered.");
            return;
        }
        if (registeredCourses.contains(regCourse)) {
            System.out.println("You registered for this course already.");
            return;
        }
        if (this.totalCredits > 20) {
            System.out.println("Credit limit exceeded !!!");
            return;
        }
        try {
            regCourse.enrollStudents(this);
            registeredCourses.add(regCourse);
            totalCredits += regCourse.credits;
            System.out.println("You have successfully registered for " + regCourse.title);
        } 
        catch (CourseFullException e) {
            System.out.println(e.getMessage());
        }
    }


    public void viewSchedule() {
        System.out.println("Your current schedule:");
        for (Courses c : registeredCourses) {
            System.out.println(c.title + " - " + c.timings);
            System.out.println(c.location);
        }
    }

    public void trackAcademicProgress() {
        trackAcademic.trackAcademicProgress(registeredCourses, semester);
    }

    public void dropCourses() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the course code to drop:");
        String courseCode = scanner.nextLine();
        registeredCourses.removeIf(course -> course.code.equals(courseCode));
        System.out.println("Dropped course " + courseCode);
    }

    public void submitComplaints() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the description of your complaint:");
        String description = scanner.nextLine();
        Complaint c = new Complaint(description);
        complaints.add(c);
        System.out.println("Your complaint has been submitted successfully. It will be resolved asap");
    }

    public void viewComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("You have no complaints.");
        }
        else {
            System.out.println("Your complaints:");
            for (Complaint c : complaints) {
                System.out.println(c);
            }
        }
    }

    public void addFeedback(String studentName, int rating) {
        FeedBack<Integer> feedback = new FeedBack<>(studentName, rating);
        feedbackList.add(feedback);
    }
    
    public void addFeedback(String studentName, String textFeedback) {
        FeedBack<String> feedback = new FeedBack<>(studentName, textFeedback);
        feedbackList.add(feedback);
    }
    
    public void addFeedback(List<Courses> registeredCourses) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select a course to provide feedback:");
        for (int i = 0; i < registeredCourses.size(); i++) {
            System.out.println((i + 1) + ". " + registeredCourses.get(i).getTitle());
        }
        int courseOption = scanner.nextInt();
        scanner.nextLine();
        if (courseOption > 0 && courseOption <= registeredCourses.size()) {
            Courses selectedCourse = registeredCourses.get(courseOption - 1);
            System.out.println("Would you like to give a numeric rating (1-5) or textual feedback?");
            System.out.println("Enter '1' for numeric, '2' for textual: ");
            int feedbackType = scanner.nextInt();
            scanner.nextLine();
    
            if (feedbackType == 1) {
                System.out.print("Enter your numeric rating (1-5): ");
                int rating = scanner.nextInt();
                scanner.nextLine();
                FeedBack<Integer> feedback = new FeedBack<>(this.name, rating);
                selectedCourse.addFeedback(this.name, feedback);
                System.out.println("Numeric feedback submitted.");
            }
    
            if (feedbackType == 2) {
                System.out.print("Enter your textual feedback: ");
                String textFeedback = scanner.nextLine();
                FeedBack<String> feedback = new FeedBack<>(this.name, textFeedback);
                selectedCourse.addFeedback(this.name, feedback);
                System.out.println("Textual feedback submitted.");
            }
        } 
        else {
            System.out.println("Invalid course selection.");
        }
    }
    

    public void submitAssignment(long currentDay, List<Courses> registeredCourses) throws DropDeadlinePassedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the course you want to submit the assignment:");
        for (int i = 0; i < registeredCourses.size(); i++) {
            System.out.println((i + 1) + ". " + registeredCourses.get(i).getTitle());
        }
        int course = scanner.nextInt() - 1; 
        if (course < 0 || course >= registeredCourses.size()) {
            System.out.println("Invalid course selection.");
            return;
        }
        Courses selectedCourse = registeredCourses.get(course);
        if (!selectedCourse.isDeadlineMet(currentDay)) {
            throw new DropDeadlinePassedException("You cannot submit the assignment for course " + selectedCourse.getTitle() + " after the deadline.");
        }
        System.out.println(name + " submitted the assignment for course " + selectedCourse.getTitle());
    }
    
    public void logout(){
        System.out.println("Student Functionality: Logging out...");
    }
}
