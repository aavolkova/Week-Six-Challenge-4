package me.anna.demo.controllers;

import me.anna.demo.repositories.EducationRepository;
import me.anna.demo.repositories.EmploymentRepository;
import me.anna.demo.repositories.PersonRepository;
import me.anna.demo.repositories.SkillsRepository;
import me.anna.demo.models.Education;
import me.anna.demo.models.Employment;
import me.anna.demo.models.Person;
import me.anna.demo.models.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class MainController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    EmploymentRepository employmentRepository;

    @Autowired
    SkillsRepository skillsRepository;




    // ============ Display the Login page ============
    @RequestMapping("/login")
    public String login(){
        return "login";
    }




    // ============ Display the Home page ============
    @GetMapping("/")
    public String showIndex(Model model)
    {
        String myMessage = "Welcome to the Robo Resume Application";
        model.addAttribute("message", myMessage);
        return "index";
    }




    //=========== List ALL Persons/Users ==============
    @GetMapping("/allPersons")
    public String showAllPersons(Model model)
    {
        Iterable <Person> personslist = personRepository.findAll();
        model.addAttribute("allpersons", personslist);
        return "allPersons";
    }




    // ============= Add Personal Info: =============
    // Allow user to enter Person's information
    @GetMapping("/enterPerson")
    public String addPerson(Model model)
    {
        model.addAttribute("newPerson", new Person());
        return "enterPerson";
    }

    // Validate entered information and if it is valid display the result
    // Person must have first name, last name, and email address
    @PostMapping("/enterPerson")
    public String postPerson(@Valid @ModelAttribute("newPerson") Person person, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){return "enterPerson";}

        personRepository.save(person);

        long personId = person.getId();
        Person p = personRepository.findOne(personId);
        model.addAttribute("personObject", p);

        return "resultPerson";
    }





    // ============== Add Education ===============
    // Allow user to enter Educational Achievements
    @GetMapping("/enterEducation/{id}")
    public String addEducation(@PathVariable("id") long id, Model model)
    {
        Person p = personRepository.findOne(id);
        model.addAttribute("newPerson", p);  /////////??????

        Education e = new Education();

//        ????????????????????
//        e.setPerson(p);
        p.addEducation(e);

        model.addAttribute("newEducation", e);
        return "enterEducation";
    }

    // Validate entered Educational Achievement and if it is valid display the result
    @PostMapping("/enterEducation")
    public String postEducation(@Valid @ModelAttribute("newEducation") Education education, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){return "enterEducation";}

        model.addAttribute("newEducation", education);

        long personId = education.getPerson().getId();
        Person p = personRepository.findOne(personId);
        model.addAttribute("personObject", p);

        educationRepository.save(education);
        return "resultEducation";
    }





    // ============== Add Employment ==============
    // Allow user to enter Employment
    @GetMapping("/enterEmployment/{id}")
    public String addEmployment(@PathVariable("id") long id, Model model)
    {
        Person p = personRepository.findOne(id);
        model.addAttribute("newPerson", p);  /////////??????

        Employment w = new Employment();
//        ????????????????????
//        w.setPerson(p);
        p.addEmployment(w);

        model.addAttribute("newEmployment", w);
        return "enterEmployment";
    }

    // Validate entered work experience and if it is valid display the result
    @PostMapping("/enterEmployment")
    public String postEmployment(@Valid @ModelAttribute("newEmployment") Employment employment, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){return "enterEmployment";}

        // === Allow user to leave the end date empty (assume he/she is still employed)
        if(employment.getEndDate() == null){
            employment.setEndDate(LocalDate.now());
        }
        //=== If dates entered, do not accept the end date before the start date
        else if(employment.getEndDate().compareTo(employment.getStartDate())< 0){
            return "enterEmployment";
        }

        model.addAttribute("newEmployment", employment);

        long personId = employment.getPerson().getId();
        Person p = personRepository.findOne(personId);
        model.addAttribute("personObject", p);

        employmentRepository.save(employment);
        return "resultEmployment";
    }




    //================== SKILLS =====================
    //============ Input form: Skills ===============
    // Add new Skill (allow user to enter new Skill)
    @GetMapping("/enterSkills/{id}")    // This is PERSON'S ID
    public String addSkills(@PathVariable("id") long id, Model model)
    {
        Person p = personRepository.findOne(id);

//        model.addAttribute("newPerson", p);  /////////??????
        Skills s = new Skills();

//        ????????????????????
//        s.setPerson(p);
        p.addSkill(s);

        model.addAttribute("newSkills", s);
        return "enterSkills";
    }

    // Validate entered Skill and if it is valid display the result
    @PostMapping("/enterSkills")
    public String postSkills(@Valid @ModelAttribute("newSkills") Skills skills, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){return "enterSkills";}

        skillsRepository.save(skills);
        model.addAttribute("newSkills", skills);

        long personId = skills.getPerson().getId();
        Person p = personRepository.findOne(personId);
//        model.addAttribute("personfirstname", p.getFirstName());
//        model.addAttribute("personlastname", p.getLastName());
        model.addAttribute("personObject", p);

//        model.addAttribute("personID", personId);
        return "resultSkills";
    }







    ///////////////////////////////////////////////////////////////////////
    // ================== DISPLAY FINAL RESUME ================
    // ===== Display Person's info saved to the database ======
    @GetMapping("/displayPersonAllInfo/{id}")
    public String showAllPersonsInfo(@PathVariable("id") long id, Model model)
    {
        //Afua's help: Person myPerson = personRepository.findOne(Long.valueOf(1));
        Person myPerson = personRepository.findOne(id);

        model.addAttribute("person", myPerson );

        return "displayPersonAllInfo";
    }
    ///////////////////////////////////////////////////////////////////////




    //================== Update Personal Info ===================
    @GetMapping("/updatePerson/{id}")
    public String updatePerson(@PathVariable("id") long id, Model model)
    {
        Person p = personRepository.findOne(id);
        model.addAttribute("newPerson", p);
        return "enterPerson";
    }

    //========= DELETE ENTIRE Person and his/her Resume  ===========
    @GetMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") long id)
    {
        personRepository.delete(id);
        return "redirect:/allPersons";
    }






    //=========== Update, Delete Education =============
    //=========== Update Education =============
    @GetMapping("/updateEducation/{id}")
    public String updateEducation(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newEducation", educationRepository.findOne(id));
        return "enterEducation";
    }

    //=========== Delete Education =============
    @RequestMapping("/deleteEducation/{id}")
    public String delEducation(@PathVariable("id") long id)
    {
        Education oneEducation= educationRepository.findOne(id);
        long personToGoTo = oneEducation.getPerson().getId();

        // you MUST first remove the Education from the Set of educationalAchievements for their person, then you can delete the education
        // Remove Education from person
        educationRepository.findOne(id).getPerson().removeEducation(oneEducation);

        // delete Education from the education table
        educationRepository.delete(id);
        return "redirect:/displayPersonAllInfo/" + personToGoTo;
    }




    //=========== Update, Delete Employment ============
    //=========== Update Employment ============
    @GetMapping("/updateEmployment/{id}")
    public String updateEmployment(@PathVariable("id") long id, Model model)
    {
        Employment e = employmentRepository.findOne(id);
        model.addAttribute("newEmployment", e);
        // The same in one line:
        // model.addAttribute("newEmployment", employmentRepository.findOne(id));
        return "enterEmployment";
    }

    //=========== Delete Employment ============
    @RequestMapping("/deleteEmployment/{id}")
    public String delEmployment(@PathVariable("id") long id)
    {
        Employment oneEmployment= employmentRepository.findOne(id);
        long personToGoTo = oneEmployment.getPerson().getId();

        // you MUST first remove the Employment from the Set of workExperience for their person, then you can delete the skill
        // Remove Employment from person
        employmentRepository.findOne(id).getPerson().removeEmployment(oneEmployment);

        // delete Employment from the employment table
        employmentRepository.delete(id);
        return "redirect:/displayPersonAllInfo/" + personToGoTo;
    }




    //============= Update, Delete Skills ==============
    //============= Update Skills ==============
    @GetMapping("/updateSkills/{id}")
    public String updateSkills(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newSkills", skillsRepository.findOne(id));
        return "enterSkills";
    }

    //============= Delete Skills ==============
    @RequestMapping("/deleteSkills/{id}")
    public String delSkills(@PathVariable("id") long id)
    {
        Skills oneSkill = skillsRepository.findOne(id);
        long personToGoTo = oneSkill.getPerson().getId();

        // you MUST first remove the Skill from the Set of skillsWithRating for their person, then you can delete the skill
        skillsRepository.findOne(id).getPerson().removeSkill(oneSkill);

        skillsRepository.delete(id);
        return "redirect:/displayPersonAllInfo/" + personToGoTo;
    }
}
