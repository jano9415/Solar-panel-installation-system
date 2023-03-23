package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.model.ProjectPart;
import com.jwt_authentication_springboot.model.ProjectStatus;
import com.jwt_authentication_springboot.repository.ProjectRepository;
import com.jwt_authentication_springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectStatusServiceImpl projectStatusService;

    @Autowired
    PartServiceImpl partService;

    @Autowired
    ProjectPartServiceImpl projectPartService;


    //Projekt mentése
    //Projekt státusz létrehozása. A projekt státusza: new
    //Projekt és projekt státusz egymáshoz rendelése
    @Override
    public void save(Project project) {
        ProjectStatus projectStatus = new ProjectStatus();

        DateTimeFormatter todayDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String todayDate = todayDateFormat.format(now);

        projectStatus.setProjectCurrentStatus("new");
        projectStatus.setStatusChanged(todayDate);
        projectStatusService.save(projectStatus);

        project.getProjectStatuses().add(projectStatusService.findByStatusChanged(todayDate));
        projectStatusService.findByStatusChanged(todayDate).setProject(project);


        projectRepository.save(project);

    }

    @Override
    public ResponseEntity<Project> findById(long id) {
        return ResponseEntity.ok(projectRepository.findById(id).get());
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public void update(Long id, Project project) {
        Project modifedProject = projectRepository.findById(id).get();

        modifedProject.setProjectLocation(project.getProjectLocation());
        modifedProject.setProjectDescription(project.getProjectDescription());
        modifedProject.setCustomerData(project.getCustomerData());
        modifedProject.setWorkCost(project.getWorkCost());
        modifedProject.setWorkDuration(project.getWorkDuration());

        projectRepository.save(modifedProject);
    }

    //Alkatrész lefoglalása
    //Beszúrom a kapcsolótáblába a projektet, az alkatrészt és a lefoglalt mennyiséget
    //Frissítem az adott alkatrész lefoglalt darabszámát
    //Megnézem, hogy az adott projektnek van-e már olyan státusza, hogy "draft"
    //Ha nincs, akkor létrehozok egyet
    @Override
    public void reservePart(Long projectId, Long partId, int reservedNumber) {
        Project project = projectRepository.findById(projectId).get();
        Part part = partService.findById(partId).getBody();

        ProjectPart projectPart = projectPartService.findByProjectIdAndPartId(projectId, partId);

        if(projectPart == null){

            projectPart = new ProjectPart();
            projectPart.setProject(project);
            projectPart.setPart(part);
            projectPart.setNumberOfParts(reservedNumber);
            projectPartService.save(projectPart);


        }
        else{
            projectPart.setNumberOfParts(projectPart.getNumberOfParts() + reservedNumber);
            projectPartService.save(projectPart);
        }

        part.setAllReservedNumber(part.getAllReservedNumber() + reservedNumber);
        partService.save(part);

        /*ProjectPart projectPart = new ProjectPart();
        projectPart.setProject(project);
        projectPart.setPart(part);
        projectPart.setNumberOfParts(reservedNumber);
        projectPartService.save(projectPart);

        part.setAllReservedNumber(part.getAllReservedNumber() + reservedNumber);
        partService.save(part);*/

        boolean insertNewProjectStatus = true;

        //Ha van már olyan státusza, hogy "draft", akkor nem adunk neki új státuszt
        for(ProjectStatus ps : project.getProjectStatuses()){
            if(ps.getProjectCurrentStatus().equals("draft")){
                insertNewProjectStatus = false;
            }
        }

        //Ha nincs még olyan státusza, hogy "draft", akkor adunk neki új státuszt
        if(insertNewProjectStatus){
            ProjectStatus projectStatus = new ProjectStatus();

            DateTimeFormatter todayDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String todayDate = todayDateFormat.format(now);

            projectStatus.setProjectCurrentStatus("draft");
            projectStatus.setStatusChanged(todayDate);
            projectStatusService.save(projectStatus);

            project.getProjectStatuses().add(projectStatusService.findByStatusChanged(todayDate));
            projectStatusService.findByStatusChanged(todayDate).setProject(project);

            projectRepository.save(project);
        }


    }

    //Előfoglalás alkatrészre
    //Beszúrom a kapcsolótáblába a projektet, az alkatrészt és az előfoglalt mennyiséget
    //Frissítem az adott alkatrész előfoglalt darabszámát
    //Megnézem, hogy az adott projektnek van-e már olyan státusza, hogy "draft"
    //Ha nincs, akkor létrehozok egyet
    @Override
    public void preReservePart(Long projectId, Long partId, int preReservedNumber) {
        Project project = projectRepository.findById(projectId).get();
        Part part = partService.findById(partId).getBody();

        ProjectPart projectPart = projectPartService.findByProjectIdAndPartId(projectId, partId);

        if(projectPart == null){

            projectPart = new ProjectPart();
            projectPart.setProject(project);
            projectPart.setPart(part);
            projectPart.setPreReservedNumber(preReservedNumber);
            projectPartService.save(projectPart);


        }
        else{
            projectPart.setPreReservedNumber(projectPart.getPreReservedNumber() + preReservedNumber);
            projectPartService.save(projectPart);
        }

        part.setPreReservedNumber(part.getPreReservedNumber() + preReservedNumber);
        partService.save(part);



        /*ProjectPart projectPart = new ProjectPart();
        projectPart.setProject(project);
        projectPart.setPart(part);
        projectPart.setPreReservedNumber(preReservedNumber);
        projectPartService.save(projectPart);

        part.setPreReservedNumber(part.getPreReservedNumber() + preReservedNumber);
        partService.save(part);*/


    }
}
