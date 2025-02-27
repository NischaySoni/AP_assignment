import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Courses implements Deadline{
    String code;
    String title;
    int semester;
    Professors professors;
    int credits;
    String timings;
    String prerequisites;
    String location;
    String syllabus;
    int enrollmentLimit;
    String officeHrs;
    String grade;
    List<Professors> assignedProfessors;
    List<Students> enrolledStudents;
    Map<Students, String> studentGrades;
    List<FeedBack<?>> feedBacks;
    TeachingAssistant ta;
    long deadline;

    public Courses(String code, String title,int semester, int credits, String timings, String prerequisites, String location){
        this.code = code;
        this.title = title;
        this.semester = semester;
        this.credits = credits;
        this.timings = timings;
        this.prerequisites = prerequisites;
        this.location = location;
        this.enrolledStudents = new ArrayList<>();
        this.syllabus = "";
        this.enrollmentLimit = 5;
        this.officeHrs = "";
        this.grade = null;
        this.studentGrades = new HashMap<>();
        this.assignedProfessors = new ArrayList<>();
        this.feedBacks = new ArrayList<>();
        this.deadline = Long.MAX_VALUE;
    }

    public void enrollStudents(Students s) throws CourseFullException {
        if (isFull()) {
            throw new CourseFullException("Registration failed: Course " + title + " is already full.");
        }
        if (!enrolledStudents.contains(s)) {
            enrolledStudents.add(s);
            System.out.println(s.getName() + " has been enrolled in " + title);
        } 
        else {
            System.out.println(s.getName() + " is already enrolled in " + title);
        }
    }

    public boolean isFull() {
        return enrolledStudents.size() >= enrollmentLimit;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade(){
        return grade;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setEnrollmentLimit(int enrollmentLimit) {
        this.enrollmentLimit = enrollmentLimit;
    }

    public void setOfficeHrs(String officeHrs) {
        this.officeHrs = officeHrs;
    }

    public void assignProfessor(Professors p) {
        if (!assignedProfessors.contains(p)){
            assignedProfessors.add(p);
        }
    }

    public List<Professors> getAssignedProfessors(){
        return assignedProfessors;
    }

    public List<Students> getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getCode(){
        return code;
    }

    public String getTitle(){
        return title;
    }

    public Professors getProfessor(){
        return professors;
    }

    public int getSemester(){
        return semester;
    }

    public int getCredits(){
        return credits;
    }

    public void assignGrade(Students s, String grade) {
        if (enrolledStudents.contains(s)) {
            studentGrades.put(s, grade);
            System.out.println("Grade " + grade + " assigned to " + s.getName() + " for " + title);
        } 
        else {
            System.out.println(s.getName() + " is not enrolled in this course.");
        }
    }

    public String getGrade(Students student) {
        return studentGrades.getOrDefault(student, "No grade assigned");
    }

    @Override
    public String toString(){
        return "Course: " + title + " (" + code + ")\n" + "Credits: " + credits + "\n" + "Prerequisites: " + prerequisites + "\n" + "Timings: " + timings + "\n" + "Professor: " + professors;
    }

    public TeachingAssistant getTA() {
        return ta;
    }

    public void assignTA(TeachingAssistant ta) {
        this.ta = ta;
        System.out.println("Teaching Assistant " + ta.getName() + " has been assigned to course " + title);
    }
    
    public <T> void addFeedback(String studentName, T feedbackContent) {
        FeedBack<T> feedback = new FeedBack<>(studentName, feedbackContent);
        feedBacks.add(feedback);
    }

    public void viewFeedback() {
        if (feedBacks.isEmpty()) {
            System.out.println("No feedback available for this course.");
        } 
        else {
            System.out.println("Feedback for the course:");
            for (FeedBack<?> fb : feedBacks) {
                System.out.println(fb.toString());
            }
        }
    }

    public boolean isDeadlineMet(long currentDay) {
        return currentDay <= deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
