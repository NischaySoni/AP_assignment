import java.util.*;

class Professors extends UserRoles {
    private List<Courses> assignedCourses;
    private List<TeachingAssistant> assignedTAs;

    public Professors(String email, String password) {
        super(email, password);
        this.assignedCourses = new ArrayList<>();
        this.assignedTAs = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public List<Courses> getAssignedCourses() {
        return assignedCourses;
    }

    @Override
    public void log(List<Courses> courseCatalog, List<Students> studentsList, Map<String, Students> studentsMap, Map<String, Professors> professorsMap, Map<String, TeachingAssistant> taMap) {
        Scanner scanner = new Scanner(System.in);
        int functionality;
        do {
            System.out.println("\nWelcome " + email);
            System.out.println("Professor Functionalities");
            System.out.println("1-Manage Courses");
            System.out.println("2-View Enrolled Students");
            System.out.println("3-Assign Grades");
            System.out.println("4-View feedBack");
            System.out.println("5-Choose Teaching Assistant");
            System.out.println("6-Set Course Deadline");
            System.out.println("7-Logout");
            System.out.print("Enter your option: ");
            functionality = scanner.nextInt();
            scanner.nextLine();
            switch (functionality) {
                case 1:
                    manageCourses();
                    break;
                case 2:
                    viewEnrolledStudents(studentsList);
                    break;
                case 3:
                    assignGrades(studentsList);
                    break;
                case 4:
                    viewFeedback(courseCatalog);
                    break;
                case 5:
                    chooseTeachingAssistant(taMap, courseCatalog);
                    break;
                case 6:
                    setCourseDeadline(courseCatalog);
                case 7:
                    logout();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (functionality != 7);
    }

    public void manageCourses() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Courses:");
        for (Courses c : assignedCourses) {
            System.out.println(c.getTitle() + " " + c.getCode());
        }
        System.out.print("Enter the course code to update the details: ");
        String code = scanner.nextLine();
        Courses courseToUpdate = null;
        for (Courses c : assignedCourses) {
            if (c.getCode().equalsIgnoreCase(code)) {
                courseToUpdate = c;
                break;
            }
        }
        if (courseToUpdate != null) {
            updateCourseDetails(courseToUpdate);
        } 
        else {
            System.out.println("Course not found.");
        }
    }

    public void updateCourseDetails(Courses c) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Updating course for: " + c.getTitle());
        System.out.print("Enter new syllabus: ");
        String syllabus = scanner.nextLine();
        System.out.print("Enter new class timings: ");
        String timings = scanner.nextLine();
        System.out.print("Enter new credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new prerequisites: ");
        String prerequisites = scanner.nextLine();
        System.out.print("Enter new enrollment limit: ");
        int enrollmentLimit = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new office hours: ");
        String officeHours = scanner.nextLine();

        c.setSyllabus(syllabus);
        c.setTimings(timings);
        c.setCredits(credits);
        c.setPrerequisites(prerequisites);
        c.setEnrollmentLimit(enrollmentLimit);
        c.setOfficeHrs(officeHours);
        System.out.println("Course details updated successfully.");
    }

    public void viewEnrolledStudents(List<Students> studentsList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a course code to view enrolled students:");
        for (Courses c : assignedCourses) {
            System.out.println(c.getTitle() + " " + c.getCode());
        }
        String Code = scanner.nextLine();
        Courses regCourse = null;
        for (Courses course : assignedCourses) {
            if (course.getCode().equals(Code)) {
                regCourse = course;
                break;
            }
        }
        if (regCourse != null) {
            System.out.println("Students enrolled in " + regCourse.getTitle() + ":");
            boolean studentRegistered = false;
            for (Students s : studentsList) {
                if (s.getRegisteredCourses().contains(regCourse)) {
                    System.out.println("Name: " + s.getName());
                    System.out.println("Academic Standing: " + s.getAcademicStanding());
                    System.out.println("Contact Details: " + s.getContact());
                    studentRegistered = true;
                }
            }
            if (!studentRegistered){
                System.out.println("No students registered.");
            }
        } 
        else {
            System.out.println("Course not found.");
        }
    }

    public void assignGrades(List<Students> studentsList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the course to assign the grades:");
        System.out.println("Assigned Courses:");
        for (Courses c : assignedCourses) {
            System.out.println(c.getTitle() + " " + c.getCode());
        }
        System.out.println("Enter course code: ");
        String code = scanner.nextLine();
        Courses regCourse = null;
        for (Courses c : assignedCourses) {
            if (c.getCode().equals(code)) {
                regCourse = c;
                break;
            }
        }
        if (regCourse != null) {
            System.out.println("Assign grade for " + regCourse.getTitle());
            for (Students s : studentsList) {
                if (s.getRegisteredCourses().contains(regCourse)) {
                    System.out.println("Student: " + s.getName());
                    System.out.print("Enter grade for " + s.getName() + ": ");
                    String grade = scanner.nextLine();
                    if (isValidGrade(grade)) {
                        regCourse.assignGrade(s, grade);
                    } 
                    else {
                        System.out.println("Invalid grade. Please enter a valid grade.");
                    }
                }
            }
            System.out.println("Grades assigned successfully.");
        } 
        else {
            System.out.println("Course not found.");
        }
    }

    public boolean isValidGrade(String grade) {
        List<String> validGrades = Arrays.asList("A+", "A", "A-", "B", "B-", "C", "C-", "D", "F");
        return validGrades.contains(grade);
    }

    public void assignCourse(Courses c) {
        assignedCourses.add(c);
    }

    public void viewCourseFeedback(Courses course) {
        if (assignedCourses.contains(course)) {
            System.out.println("Feedback for course: " + course.getTitle());
            course.viewFeedback();
        } 
        else {
            System.out.println("You are not assigned to this course.");
        }
    }

    public void viewFeedback(List<Courses> assignedCourses) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a course to view feedback:");
        for (int i = 0; i < assignedCourses.size(); i++) {
            System.out.println((i + 1) + ". " + assignedCourses.get(i).getTitle());
        }
        System.out.print("Enter course number: ");
        int courseNumber = scanner.nextInt();
        scanner.nextLine();
    
        if (courseNumber > 0 && courseNumber <= assignedCourses.size()) {
            Courses selectedCourse = assignedCourses.get(courseNumber - 1);
            viewCourseFeedback(selectedCourse);
        } 
        else {
            System.out.println("Invalid course selection.");
        }
    }

    public void assignTA(TeachingAssistant ta) {
        assignedTAs.add(ta);
        System.out.println("TA " + ta.getName() + " has been assigned.");
    }

    public void viewAssignedTAs() {
        if (assignedTAs.isEmpty()) {
            System.out.println("No TAs assigned currently.");
        } else {
            System.out.println("Assigned TAs:");
            for (TeachingAssistant ta : assignedTAs) {
                System.out.println(ta.getName() + " - Email: " + ta.getEmail());
            }
        }
    }

    public void chooseTeachingAssistant(Map<String, TeachingAssistant> taMap, List<Courses> courseCatalog) {
        Scanner scanner = new Scanner(System.in);
        if (taMap.isEmpty()) {
            System.out.println("No TAs available to assign.");
            return;
        }
        
        System.out.println("Available TAs:");
        for (Map.Entry<String, TeachingAssistant> entry : taMap.entrySet()) {
            TeachingAssistant ta = entry.getValue();
            System.out.println("TA ID: " + entry.getKey() + ", Name: " + ta.getName());
        }
    
        System.out.print("Enter TA email to assign: ");
        String taMail = scanner.nextLine();
        TeachingAssistant chosenTA = taMap.get(taMail);
    
        if (chosenTA != null) {
            System.out.println("Assign TA to a course.");
            System.out.println("Courses you are managing:");
            for (Courses course : courseCatalog) {
                System.out.println("Course code: " + course.getCode() + ", Title: " + course.getTitle());
            }
            
            System.out.print("Enter the Course code to assign TA to: ");
            String courseCode = scanner.nextLine();
            Courses courseToAssign = null;
            
            for (Courses course : courseCatalog) {
                if (course.getCode().equals(courseCode)) {
                    courseToAssign = course;
                    break;
                }
            }
            if (courseToAssign != null) {
                courseToAssign.assignTA(chosenTA);
                System.out.println("TA " + chosenTA.getName() + " assigned to course " + courseToAssign.getTitle());
            } 
            else {
                System.out.println("Course not found.");
            }
        } 
        else {
            System.out.println("TA not found.");
        }
    }

    public void setCourseDeadline(List<Courses> assignedCourses) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a course:");
        for (int i = 0; i < assignedCourses.size(); i++) {
            System.out.println((i + 1) + " - " + assignedCourses.get(i).getTitle());
        }
        int course = scanner.nextInt() - 1; 
        if (course < 0 || course >= assignedCourses.size()) {
            System.out.println("Invalid course selection. Please try again.");
            return; 
        }
        System.out.println("Enter deadline: ");
        long deadline = scanner.nextLong();
        Courses selectedCourse = assignedCourses.get(course);
        selectedCourse.setDeadline(deadline);
        System.out.println("Deadline for course " + selectedCourse.getTitle() + " has been set to day " + deadline);
    }
    
    public void logout() {
        System.out.println("Professor functionality: Logging out ...");
    }
}
