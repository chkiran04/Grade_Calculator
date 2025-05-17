

import java.util.*;

class Student {
    private String id;
    private String name;
    private List<Grade> grades;
    
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.grades = new ArrayList<>();
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public List<Grade> getGrades() { return grades; }
    
    public void addGrade(Grade grade) {
        grades.add(grade);
    }
    
    public double calculateOverallGrade() {
        if (grades.isEmpty()) return 0.0;
        
        double totalWeight = 0.0;
        double weightedSum = 0.0;
        
        for (Grade grade : grades) {
            double percentage = grade.getScore() / grade.getMaxScore();
            weightedSum += percentage * grade.getWeight();
            totalWeight += grade.getWeight();
        }
        
        return totalWeight > 0 ? (weightedSum / totalWeight) * 100 : 0;
    }
    
    public String getLetterGrade() {
        double grade = calculateOverallGrade();
        if (grade >= 90) return "A";
        if (grade >= 80) return "B";
        if (grade >= 70) return "C";
        if (grade >= 60) return "D";
        return "F";
    }
}

class Grade {
    private String assignmentName;
    private double score;
    private double maxScore;
    private double weight;
    
    public Grade(String assignmentName, double score, double maxScore, double weight) {
        this.assignmentName = assignmentName;
        this.score = score;
        this.maxScore = maxScore;
        this.weight = weight;
    }
    
    public String getAssignmentName() { return assignmentName; }
    public double getScore() { return score; }
    public double getMaxScore() { return maxScore; }
    public double getWeight() { return weight; }
}

public class SimpleGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        
        boolean running = true;
        while (running) {
            System.out.println("\n===== Student Grade Calculator =====");
            System.out.println("1. Add student");
            System.out.println("2. Add grade");
            System.out.println("3. View students");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Student name: ");
                    String name = scanner.nextLine();
                    students.add(new Student(id, name));
                    System.out.println("Student added!");
                    break;
                    
                case 2:
                    System.out.print("Student ID: ");
                    String studentId = scanner.nextLine();
                    
                    // Find student
                    Student targetStudent = null;
                    for (Student s : students) {
                        if (s.getId().equals(studentId)) {
                            targetStudent = s;
                            break;
                        }
                    }
                    
                    if (targetStudent == null) {
                        System.out.println("Student not found!");
                        break;
                    }
                    
                    System.out.print("Assignment name: ");
                    String assignment = scanner.nextLine();
                    System.out.print("Score: ");
                    double score = scanner.nextDouble();
                    System.out.print("Max score: ");
                    double maxScore = scanner.nextDouble();
                    System.out.print("Weight: ");
                    double weight = scanner.nextDouble();
                    
                    Grade grade = new Grade(assignment, score, maxScore, weight);
                    targetStudent.addGrade(grade);
                    System.out.println("Grade added!");
                    break;
                    
                case 3:
                    System.out.println("\n===== Students =====");
                    for (Student s : students) {
                        double overall = s.calculateOverallGrade();
                        String letter = s.getLetterGrade();
                        System.out.printf("%s - %s: %.2f%% (%s)%n", 
                                s.getId(), s.getName(), overall, letter);
                        
                        if (!s.getGrades().isEmpty()) {
                            System.out.println("  Assignments:");
                            for (Grade g : s.getGrades()) {
                                System.out.printf("  - %s: %.1f/%.1f (%.1f%%)%n", 
                                        g.getAssignmentName(), g.getScore(), 
                                        g.getMaxScore(), g.getWeight());
                            }
                        }
                    }
                    break;
                    
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
        
        scanner.close();
    }
}
