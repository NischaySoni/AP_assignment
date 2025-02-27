import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class TeachingAssistant extends Students {
    private Professors ta;
    private List<Courses> assignedCourses;

    public TeachingAssistant(String name, String email, String password, int contact, Professors ta) {
        super(name, email, password, contact);
        this.ta = ta;
        this.assignedCourses = null;
    }

    public Professors getTa() {
        return ta;
    }

    public String getName() {
        return super.getName();
    }

    public void setTa(Professors ta) {
        this.ta = ta;
    }

    public void setAssignedCourses(List<Courses> courses) {
        this.assignedCourses = courses;
    }

    public List<Courses> getAssignedCourses() {
        return assignedCourses;
    }

    public void viewStudentsGrade(Courses course) {
        if (assignedCourses.contains(course)) {
            System.out.println("Grades for " + course.getTitle() + ":");
            course.getGrade();
        } else {
            System.out.println("You cannot view the grades for this course.");
        }
    }

    public void manageStudentGrades(Courses course, Students student, String grade) {
        if (assignedCourses.contains(course)) {
            System.out.println("Managing grade for student: " + student.getName() + " in " + course.getTitle());
            course.assignGrade(student, grade);
        } else {
            System.out.println("You cannot manage the grades for this course.");
        }
    }

    public Courses getCourseByCode(List<Courses> courseCatalog, String courseCode) {
        for (Courses course : courseCatalog) {
            if (course.getCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public void log(List<Courses> courseCatalog, List<Students> studentsList, Map<String, Students> studentsMap, Map<String, Professors> professorsMap, Map<String, TeachingAssistant> taMap) {
        Scanner scanner = new Scanner(System.in);
        int functionality;
        do {
            System.out.println("\nWelcome, Teaching Assistant " + this.getName());
            System.out.println("1-View Available Courses");
            System.out.println("2-Register for Courses");
            System.out.println("3-View Schedule");
            System.out.println("4-Track Academic Progress");
            System.out.println("5-View Student Grades");
            System.out.println("6-Manage Student Grades");
            System.out.println("7-View Enrolled Students");
            System.out.println("8-Logout");
            System.out.print("Enter your option: ");
            functionality = scanner.nextInt();
            scanner.nextLine();

            switch (functionality) {
                case 1:
                    viewAvailableCourses(courseCatalog);
                    break;
                case 2:
                    try {
                        registerForCourses(courseCatalog);
                    } catch (CourseFullException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    trackAcademicProgress();
                    break;
                case 5:
                    System.out.print("Enter the course code to view grades: ");
                    String courseCode = scanner.nextLine();
                    Courses course = getCourseByCode(courseCatalog, courseCode);
                    if (course != null) {
                        viewStudentsGrade(course);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 6:
                    assignGrades(studentsList);
                    break;
                case 7:
                    viewEnrolledStudents(studentsList);
                    break;
                case 8:
                    logout();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (functionality != 8);
    }

    public void assignGrades(List<Students> studentsList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the course to assign the grades:");
        for (Courses course : assignedCourses) {
            System.out.println(course.getTitle() + " " + course.getCode());
        }
        System.out.println("Enter course code: ");
        String code = scanner.nextLine();
        Courses regCourse = getCourseByCode(assignedCourses, code);
        
        if (regCourse != null) {
            System.out.println("Assign grade for " + regCourse.getTitle());
            for (Students student : studentsList) {
                if (student.getRegisteredCourses().contains(regCourse)) {
                    System.out.println("Student: " + student.getName());
                    System.out.print("Enter grade for " + student.getName() + ": ");
                    String grade = scanner.nextLine();
                    if (isValidGrade(grade)) {
                        regCourse.assignGrade(student, grade);
                    } else {
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

    public void viewEnrolledStudents(List<Students> studentsList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a course code to view enrolled students:");
        for (Courses course : assignedCourses) {
            System.out.println(course.getTitle() + " " + course.getCode());
        }
        String code = scanner.nextLine();
        Courses regCourse = getCourseByCode(assignedCourses, code);
        
        if (regCourse != null) {
            System.out.println("Students enrolled in " + regCourse.getTitle() + ":");
            boolean studentRegistered = false;
            for (Students student : studentsList) {
                if (student.getRegisteredCourses().contains(regCourse)) {
                    System.out.println("Name: " + student.getName());
                    System.out.println("Academic Standing: " + student.getAcademicStanding());
                    System.out.println("Contact Details: " + student.getContact());
                    studentRegistered = true;
                }
            }
            if (!studentRegistered) {
                System.out.println("No students registered.");
            }
        } 
        else {
            System.out.println("Course not found.");
        }
    }

    @Override
    public String toString() {
        return "Teaching Assistant " + this.getName() + ", email: " + this.getEmail() + " is assigned under " + ta.getEmail();
    }

    public void logout() {
        System.out.println("Logging out...");
    }
}
