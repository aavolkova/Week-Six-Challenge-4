package me.anna.demo.models;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min=1, max=50, message = "Must enter your first name.")
    private String firstName;

    @NotEmpty
    @Size(min=1, max=50, message = "Must enter your last name.")
    private String lastName;

    @NotEmpty
    @Size(min=1, max=50, message = "Must enter your email address.")
    @Email
    private String emailAddress;


//No need anymore:
//    private ArrayList<Education> educationalAchievements;
//    private ArrayList<Employment> workExperience;
//    private ArrayList<Skills> skillsWithRating;


    //=== Add Relationship - Person and Education ===
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Education> educationalAchievements;

    //=== Add Relationship - Person and Employment ===
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Employment> workExperience;

    //=== Add Relationship - Person and Skills ===
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skills> skillsWithRating;





    // ====== Constructor (we can also do it in the controller)
    public Person(){
        setEducationalAchievements(new HashSet<>());
        setWorkExperience(new HashSet<>());
        setSkillsWithRating(new HashSet<>());
    }






    // ========== Custom methods: ==========

    // 1.1) ====== Delete Skill from Set
    public void removeSkill(Skills s) {
        skillsWithRating.remove(s);
    }

    // 1.2) ====== Add a Skill: =========
    public void addSkill(Skills s){
        s.setPerson(this);  //set person with this object (set Person)
        skillsWithRating.add(s);
    } // ==============================



    // 2.1) ====== Delete Education from Set
    public void removeEducation(Education e) {
        educationalAchievements.remove(e);
    }

    // 2.2) ====== Add a Education: =========
    public void addEducation(Education e){
        e.setPerson(this);  //set person with this object (set Person)
        educationalAchievements.add(e);
    } // ==============================



    // 2.1) ====== Delete Employment from Set
    public void removeEmployment(Employment w) {
        workExperience.remove(w);
    }

    // 2.2) ====== Add a Skill: =========
    public void addEmployment(Employment w){
        w.setPerson(this);  //set person with this object (set Person)
        workExperience.add(w);
    } // ==============================





    //==== Setters and Getters ====
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }




//Do not need anymore:
//    public ArrayList<Education> getEducationalAchievements() {
//        return educationalAchievements;
//    }
//    public void setEducationalAchievements(ArrayList<Education> educationalAchievements) {
//        this.educationalAchievements = educationalAchievements;
//    }
//    public ArrayList<Employment> getWorkExperience() {
//        return workExperience;
//    }
//    public void setWorkExperience(ArrayList<Employment> workExperience) {
//        this.workExperience = workExperience;
//    }
//    public ArrayList<Skills> getSkillsWithRating() {
//        return skillsWithRating;
//    }
//    public void setSkillsWithRating(ArrayList<Skills> skillsWithRating) {
//        this.skillsWithRating = skillsWithRating;
//    }



    // ====== Setter and Getter for Relationship - Person and Education
    public Set<Education> getEducationalAchievements() {
        return educationalAchievements;
    }
    public void setEducationalAchievements(Set<Education> educationalAchievements) {
        this.educationalAchievements = educationalAchievements;
    }


    // ====== Setter and Getter for Relationship - Person and Employment
    public Set<Employment> getWorkExperience() {
        return workExperience;
    }
    public void setWorkExperience(Set<Employment> workExperience) {
        this.workExperience = workExperience;
    }


    // ====== Setter and Getter for Relationship - Person and Skills
    public Set<Skills> getSkillsWithRating() {
        return skillsWithRating;
    }
    public void setSkillsWithRating(Set<Skills> skillsWithRating) {
        this.skillsWithRating = skillsWithRating;
    }

}
