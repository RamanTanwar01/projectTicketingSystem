package com.cs.controllers;

import com.cs.entities.Employee;
import com.cs.entities.Project;
import com.cs.entities.Task;
import com.cs.helper.Message;
import com.cs.repositories.EmployeeRepository;
import com.cs.repositories.ProjectRepository;
import com.cs.repositories.TaskRepository;
import com.cs.services.EmployeeServiceImpl;
import com.cs.services.ProjectServiceImpl;
import com.cs.services.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static java.lang.System.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private ProjectServiceImpl projectService;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private TaskRepository taskRepository;

    //Method to add common data
    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String userName = principal.getName();
        //get employee using userName
        Employee employee = employeeRepository.getEmployeeByEmail(userName);
        out.println(employee);
        model.addAttribute("employee",employee);
    }

    // admin dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model)
    {
        model.addAttribute("title","Admin Dashboard");
        return "admin/admin_dashboard";
    }

    //open add project form handler
    @GetMapping("/addProject")
    public String openAddProjectForm(Model model){
        model.addAttribute("title","Add Project");
        model.addAttribute("project", new Project());
        return "admin/add_project";
    }

    //processing add project form
    @PostMapping("/processProject")
    public String processProject(@ModelAttribute Project project,
                                 @RequestParam("specificationFile") MultipartFile file,
                                 Principal principal,
                                 HttpSession session){
        try {
            String name = principal.getName();
            Employee emp = this.employeeRepository.getEmployeeByEmail(name);
            //processing and uploading file
            if(file.isEmpty())
            {
                out.println("File is empty");
            }
            else{
                project.setFile(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
            }
            emp.getProjectList().add(project);
            int i = projectRepository.countByprojectName(project.getProjectName());
            if(i!=0){
                session.setAttribute("message", new Message("Project already exists","alert-danger"));
                return "admin/add_Project";
            }
            this.employeeRepository.save(emp);

            //success message
            session.setAttribute("message",new Message("Project is added Successfully","alert-success"));
        }catch (Exception e){
            e.printStackTrace();
            //error message
            session.setAttribute("message",new Message( "Something went Wrong...!","alert-danger"));
        }
        return "admin/add_Project";
    }

    //view all Projects
    @RequestMapping("/viewProject")
    public String viewProjects(Model model){
        List<Project> viewList = projectService.viewProjects();
        model.addAttribute("title","View Projects");
        model.addAttribute("viewList",viewList);
        return "admin/view_Project";
    }

    //view all Developers
    @RequestMapping("/viewDeveloper")
    public String viewDevelopers(Model model){
        try {
            List<Employee> developerList = employeeService.viewDevelopers();
            List<Employee> devList = new ArrayList<>();
            for (Employee emp : developerList) {
                if (emp.getRole().contains("ROLE_DEVELOPER")) {
                    devList.add(emp);
                }
            }
            model.addAttribute("title", "View Developers");
            model.addAttribute("developerList", devList);
            return "admin/view_Developer";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/view_Developer";
    }

    //open add task form handler
    @GetMapping("/addTask/{id}")
    public String openAddTaskForm(@PathVariable("id") int id,Model model){
        model.addAttribute("title","Add Task");
        model.addAttribute("task", new Task());
        model.addAttribute("id",id);
        return "admin/add_Task";
    }

    //create Task
    @PostMapping("/processTask")
    public String createTask(@ModelAttribute Task task,@RequestParam("id")int projectId,HttpSession session){
        try {
            Project project = projectRepository.findById(projectId);
            List<Task> taskList = project.getTaskList();
            LocalDateTime date = LocalDateTime.now();
            task.setCreationDate(date);
            if (task.getPriority().contains("Critical")) {
                task.setDeadline(date.plus(24, ChronoUnit.HOURS));
            } else if (task.getPriority().contains("Major")) {
                task.setDeadline(date.plus(48, ChronoUnit.HOURS));
            } else if (task.getPriority().contains("Moderate")) {
                task.setDeadline(date.plus(96, ChronoUnit.HOURS));
            } else if (task.getPriority().contains("Minor")) {
                task.setDeadline(date.plus(120, ChronoUnit.HOURS));
            } else if (task.getPriority().contains("Cosmetic")) {
                task.setDeadline(date.plus(240, ChronoUnit.HOURS));
            }
            int i = taskRepository.countBytitle(task.getTitle());
            if(i!=0){
                session.setAttribute("message", new Message("Task already exists","alert-danger"));
                return "admin/add_Task";
            }
            taskList.add(task);
            project.setTaskList(taskList);
            taskRepository.save(task);
            //success message
            session.setAttribute("message",new Message("Task is created Successfully","alert-success"));
            return "admin/add_Task";
        }catch (Exception e){
            e.printStackTrace();
            //error message
            session.setAttribute("message",new Message( "Something went Wrong...!","alert-danger"));
        }
        return "admin/add_Task";
    }

    //view all Task
    @RequestMapping("/viewTask/{id}")
    public String viewTasks(@PathVariable("id")int id, Model model){
       try{
           List<Task> viewTask = taskService.viewTasks(id);
           model.addAttribute("title","View Tasks");
           model.addAttribute("viewTask",viewTask);
           return "admin/view_Task";
       }catch (Exception e){
           e.printStackTrace();
           return "admin/view_Task";
       }
    }

    //open assign Developer form handler
    @GetMapping("/assignDeveloper/{id}")
    public String openAssignForm(@PathVariable("id") int id, Model model){
        List<Employee> developerList = employeeService.viewDevelopers();
        List<Employee> devList = new ArrayList<>();
        for (Employee emp : developerList) {
            if (emp.getRole().contains("ROLE_DEVELOPER")) {
                devList.add(emp);
            }
        }
        model.addAttribute("developerList", devList);
        model.addAttribute("title","Assign Developer");
        model.addAttribute("id", id);
        return "admin/assign_Developers";
    }

    // assign developer to project
    @PostMapping("/processAssign")
    public String assignDeveloper(@RequestBody List<Employee> employeeList,@PathVariable("id")int projectId){
        Project project = projectRepository.findById(projectId);
        project.setEmployeeList(employeeList);
        projectRepository.save(project);
        return "admin/assign_Developers";
    }

//    // assign developer to project
//    @PostMapping("/processAssign")
//    public String assignDeveloper(@ModelAttribute Employee employee,@RequestParam("id")int projectId){
//        List<Employee> employeeList = new ArrayList<>();
//        employeeList.add(employee);
//        Project project = projectRepository.findById(projectId);
//        project.setEmployeeList(employeeList);
//        projectRepository.save(project);
//        return "admin/assign_Developer";
//    }
}
