package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.model.ProjectPart;
import com.jwt_authentication_springboot.model.ProjectStatus;
import com.jwt_authentication_springboot.payload.response.PartDTO;
import com.jwt_authentication_springboot.repository.ProjectRepository;
import com.jwt_authentication_springboot.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;


    //Projekt mentése
    //Projekt státusz létrehozása. A projekt státusza: "new"
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

    //Projekt keresése id szerint
    @Override
    public ResponseEntity<Project> findById(long id) {
        return ResponseEntity.ok(projectRepository.findById(id).get());
    }

    //Összes projekt listázása
    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    //Projekt valamely adatának frissítése
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
        boolean insertNewProjectStatus = true;

        ProjectPart projectPart = projectPartService.findByProjectIdAndPartId(projectId, partId);

        //Ha nincs még a projekt az alkatrésszel összerendelve akkor összerendelem és megadom a foglalt darabszámot
        if(projectPart == null){

            projectPart = new ProjectPart();
            projectPart.setProject(project);
            projectPart.setPart(part);
            projectPart.setNumberOfParts(reservedNumber);
            projectPartService.save(projectPart);


        }
        //Ha a projekt és az alkatrész össze van már rendelve, akkor csak frissítem a foglalat darabszámot
        else{
            projectPart.setNumberOfParts(projectPart.getNumberOfParts() + reservedNumber);
            projectPartService.save(projectPart);
        }

        //Alkatrész összes foglalt darabszámának frissítése
        part.setAllReservedNumber(part.getAllReservedNumber() + reservedNumber);
        partService.save(part);


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
        boolean insertNewProjectStatus = true;

        ProjectPart projectPart = projectPartService.findByProjectIdAndPartId(projectId, partId);

        //Ha nincs még a projekt az alkatrésszel összerendelve akkor összerendelem és megadom az előfoglalt darabszámot
        if(projectPart == null){

            projectPart = new ProjectPart();
            projectPart.setProject(project);
            projectPart.setPart(part);
            projectPart.setPreReservedNumber(preReservedNumber);
            projectPartService.save(projectPart);


        }
        //Ha a projekt és az alkatrész össze van már rendelve, akkor csak frissítem az előfoglalat darabszámot
        else{
            projectPart.setPreReservedNumber(projectPart.getPreReservedNumber() + preReservedNumber);
            projectPartService.save(projectPart);
        }

        //Alkatrész összes előfoglalt darabszámának frissítése
        part.setPreReservedNumber(part.getPreReservedNumber() + preReservedNumber);
        partService.save(part);


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

    //Árkalkuláció elkészítése. Ha minden alkatrész elérhető a raktárban, visszatér a költséggel és a project "scheduled" fázisba kerül.
    //Ha nem érhető el minden alkatrész, akkor a költség 0 és a project "wait" fázisba kerül
    @Override
    public int showFullCost(Long projectId) {
        Project project = findById(projectId).getBody();
        boolean isPreReservation = false;
        boolean isScheduledStatus = false;
        boolean isWaitStatus = false;
        int cost = 0;

        //Megnézem, hogy az adott projekthez tartozik-e előfoglalt alkatrész
        for(ProjectPart projectPart : project.getProjectParts()){
            //Nem lehet árkalkulációt készíteni
            if(projectPart.getPreReservedNumber() > 0){
                isPreReservation = true;
            }
            //Lehet árkalkulációt készíteni
            else{
                cost += projectPart.getPart().getPrice() * projectPart.getNumberOfParts();
            }
        }

        //Megnézem, létezik-e már "scheduled" vagy "wait" státusz
        for(ProjectStatus projectStatus : project.getProjectStatuses()){
            if(projectStatus.getProjectCurrentStatus().equals("scheduled")){
                isScheduledStatus = true;
            }
            if(projectStatus.getProjectCurrentStatus().equals("wait")){
                isWaitStatus = true;
            }
        }

        //"scheduled" státusz létrehozása és projekthez rendelése
        if(project.getProjectStatuses() != null && isPreReservation == false && isScheduledStatus == false){
            ProjectStatus projectStatus = new ProjectStatus();

            DateTimeFormatter todayDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String todayDate = todayDateFormat.format(now);

            projectStatus.setProjectCurrentStatus("scheduled");
            projectStatus.setStatusChanged(todayDate);
            projectStatusService.save(projectStatus);

            project.getProjectStatuses().add(projectStatusService.findByStatusChanged(todayDate));
            projectStatusService.findByStatusChanged(todayDate).setProject(project);

            projectRepository.save(project);
        }

        //"wait" státusz létrehozása és projekthez rendelése
        if(project.getProjectStatuses() != null && isPreReservation && isWaitStatus == false){
            ProjectStatus projectStatus = new ProjectStatus();

            DateTimeFormatter todayDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String todayDate = todayDateFormat.format(now);

            projectStatus.setProjectCurrentStatus("wait");
            projectStatus.setStatusChanged(todayDate);
            projectStatusService.save(projectStatus);

            project.getProjectStatuses().add(projectStatusService.findByStatusChanged(todayDate));
            projectStatusService.findByStatusChanged(todayDate).setProject(project);


            projectRepository.save(project);
        }

        //Árkalkulációt nem lehet elkészíteni
        if(isPreReservation){
            return 0;
        }

        //Az árkalkuláció elkészült
        return cost;

    }

    //Projekt lezárása
    @Override
    public void finishProject(Long projectId) {

    }

    //Projektek listázása kivételezésre
    //Csak olyan projekteket adok vissza, amelyeknek a kivételezését meg lehet kezdeni
    //Tehát az adott projekthez nem tartozik előfoglalt alkatrész
    @Override
    public List<Project> listProjectsWithoutPreReservation() {
        List<Project> projects = new ArrayList<Project>();

        for(Project p : findAll()){
            for(ProjectPart projectPart : p.getProjectParts()){
                if(projectPart.getPreReservedNumber() == 0 && !projects.contains(p)){
                    System.out.println("Projekt hozzáadva");
                    projects.add(p);
                }
            }
        }

        for(Project p : findAll()){
            for(ProjectPart projectPart : p.getProjectParts()){
                if(projectPart.getPreReservedNumber() != 0 && projects.contains(p)){
                    System.out.println("Projekt eltávolítva");
                    projects.remove(p);
                }
            }
        }

        System.out.println("A hossz: " + projects.size());
        return projects;
    }

    //A kiválasztott projekthez tartozó lefoglalt alkatrészek listázása
    //Ezeket az alkatrészeket már ki lehet venni a raktárból
    @Override
    public List<PartDTO> showPartsOfProject(Long projectId) {
        List<Part> parts = new ArrayList<Part>();
        List<PartDTO> partsDTOs = new ArrayList<PartDTO>();


        for(ProjectPart projectPart : findById(projectId).getBody().getProjectParts()){
            //parts.add(projectPart.getPart());
            PartDTO partDTO = new PartDTO();

            partDTO.setId(projectPart.getPart().getId());
            partDTO.setPartName(projectPart.getPart().getPartName());
            partDTO.setPrice(projectPart.getPart().getPrice());
            partDTO.setMaxPieceInBox(projectPart.getPart().getMaxPieceInBox());
            partDTO.setAllAvailableNumber(projectPart.getPart().getAllAvailableNumber());
            partDTO.setAllReservedNumber(projectPart.getPart().getAllReservedNumber());
            partDTO.setPreReservedNumber(projectPart.getPart().getPreReservedNumber());
            partDTO.setNumberOfParts(projectPart.getNumberOfParts());

            partsDTOs.add(partDTO);

        }


        return partsDTOs;


        //Alkatrész lista konvertálása alkatrész DTO listába
        /*return parts.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());*/



    }

    //Alkatrész entity objektum konvertálása alkatrész DTO objektummá
    private PartDTO convertEntityToDto(Part part){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PartDTO partDTO = new PartDTO();
        partDTO = modelMapper.map(part, PartDTO.class);
        return partDTO;
    }
}
