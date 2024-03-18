package employees;

import java.util.List;

public class Employee {

    private String name;

    private int yearOfBirth;

    private List<String> skills;

    public Employee(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public Employee(String name, int yearOfBirth, List<String> skills) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.skills = skills;
    }

    public int getAgeIn(int year) {
        if (year <= yearOfBirth) {
            throw new IllegalArgumentException("Year is too early.");
        }
        return year - yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public List<String> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", skills=" + skills +
                '}';
    }
}
