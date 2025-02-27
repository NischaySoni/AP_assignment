import java.util.List;
import java.util.Objects;

public class Grade {

    public int gradeToPoints(String grade) {
        if (Objects.equals(grade, "A") || Objects.equals(grade, "A+")){
            return 10;
        }
        else if (Objects.equals(grade, "A-")){
            return 9;
        }
        else if (Objects.equals(grade, "B")){
            return 8;
        }
        else if (Objects.equals(grade, "B-")){
            return 7;
        }
        else if (Objects.equals(grade, "C")){
            return 6;
        }
        else if (Objects.equals(grade, "C-")){
            return 5;
        }
        else if (Objects.equals(grade, "D")){
            return 4;
        }
        else{
            return 0;
        }
    }

    public void trackAcademicProgress(List<Courses> regCourses, int semester) {
        if (regCourses.isEmpty()) {
            System.out.println("You are not registered to any courses yet.");
            return;
        }
        double cummulativeCredits = 0;
        double cummulativePoints = 0;
        double semesterCredits = 0;
        double semesterPoints = 0;

        System.out.println("Grade: ");
        for (Courses c : regCourses) {
            for (Students s : c.getEnrolledStudents()) {
                String grade = c.getGrade(s);
                if (!grade.equals("No grade assigned")) {
                    int gradePoints = gradeToPoints(grade);
                    cummulativeCredits += c.getCredits();
                    cummulativePoints += gradePoints * c.getCredits();
                    if (c.getSemester() == semester) {
                        semesterCredits += c.getCredits();
                        semesterPoints += gradePoints * c.getCredits();
                    }
                    System.out.println(c.getTitle() + " " + c.getCode() + " -> " + s.getName() + ": " + grade);
                }
                double sgpa = semesterPoints/semesterCredits;
                double cgpa = cummulativePoints/cummulativeCredits;
                System.out.printf("SGPA for semester %d: %.2f\n", semester, sgpa);
                System.out.printf("CGPA: %.2f\n", cgpa);
                if (cgpa < 4){
                    System.out.println("You got a back!!\nStudy much harder next time.");
                }
            }
        }
    }
}
