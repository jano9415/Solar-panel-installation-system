package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.*;
import com.jwt_authentication_springboot.payload.response.BestPathDTO;
import com.jwt_authentication_springboot.payload.response.PartDTO;
import com.jwt_authentication_springboot.repository.ProjectRepository;
import com.jwt_authentication_springboot.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    @Lazy
    private BoxServiceImpl boxService;

    @Autowired
    private ModelMapper modelMapper;


    //Projekt mentése
    //Projekt státusz létrehozása. A projekt státusza: "new"
    //Projekt és projekt státusz egymáshoz rendelése
    @Override
    public void save(Project project) {

        projectRepository.save(project);
        addNewProjectStatus(project.getId(), "new");

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

        //Új státusz hozzáadása
        addNewProjectStatus(projectId, "draft");

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

        //Új státusz hozzáadása
        addNewProjectStatus(projectId, "draft");

    }

    //Árkalkuláció elkészítése. Ha minden alkatrész elérhető a raktárban, visszatér a költséggel és a project "scheduled" fázisba kerül.
    //Ha nem érhető el minden alkatrész, tehát van előfoglalt alkatrész,
    // akkor a költség 0 és a project "wait" fázisba kerül
    @Override
    public int showFullCost(Long projectId) {
        Project project = findById(projectId).getBody();
        boolean isPreReservation = false;
        boolean addScheduledStatus = true;
        boolean addWaitStatus = true;
        int cost = 0;

        //Megnézem, hogy az adott projekthez tartozik-e előfoglalt alkatrész
        for(ProjectPart projectPart : project.getProjectParts()){
            //Nem lehet árkalkulációt készíteni
            if(projectPart.getPreReservedNumber() > 0){
                //Új státusz hozzáadása
                //addNewProjectStatus(projectId, "wait");
                isPreReservation = true;
            }
            //Lehet árkalkulációt készíteni
            else{
                //Új státusz hozzáadása
                //addNewProjectStatus(projectId, "scheduled");
                cost += projectPart.getPart().getPrice() * projectPart.getNumberOfParts();
            }
        }

        //Árkalkulációt nem lehet elkészíteni
        if(isPreReservation){
            addNewProjectStatus(projectId, "wait");
            return 0;
        }

        //Az árkalkuláció elkészült
        addNewProjectStatus(projectId, "scheduled");
        return cost;

    }

    //Projekt lezárása
    //A bejövő paraméter "completed" vagy "failed"
    //Ha sikeres akkor "completed" fázsiba kerül, ha nem, akkor "failed" fázisba.
    //Ha a státusz "failed", akkor projekt és alkatrész összerendelések törlése
    //Adott alkatrészre vonatkozó foglalások és előfoglalások frissítése
    @Override
    public void finishProject(Long projectId, String status) {

        Project project = findById(projectId).getBody();

        //Új státusz hozzáadása
        addNewProjectStatus(projectId, status);

        //Projekt és alkatrész összerendelések törlése
        //Adott alkatrészre vonatkozó foglalások és előfoglalások frissítése
        if(status.equals("failed") && project.getProjectParts() != null){
            for(ProjectPart projectPart : project.getProjectParts()){
                //Adott alkatrész összesen lefoglalt mennyiségének csökkentése
                projectPart.getPart().setAllReservedNumber(projectPart.getPart().getAllReservedNumber() - projectPart.getNumberOfParts());
                //Adott alkatrész összesen előfoglalt mennyiségének csökkentése
                projectPart.getPart().setPreReservedNumber(projectPart.getPart().getPreReservedNumber() - projectPart.getPreReservedNumber());
                System.out.println("Az id: " + projectPart.getId());
                //Összerendelt objektum törlése
                projectPartService.deleteProjectPart(projectPart.getId());
            }
        }

    }

    //Projektek listázása kivételezésre
    //Csak olyan projekteket adok vissza, amelyeknek a kivételezését meg lehet kezdeni
    //Tehát az adott projekthez nem tartozik előfoglalt alkatrész, csak lefoglalt alkatrész
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

    //Legjobb útvonal megkeresése az alkatrészek összegyűjtéséhez
    //1. lépés: A legrövidebb út az, ha minden alkatrészt ki tudok venni egy rekeszből
    //2. lépés: Ha nincs ilyen lehetőség egy adott alkatrészre, akkor törekszek arra, hogy a lehető legkevesebb
    //rekeszből  gyűjtsem össze a szükséges alkatrész mennyiséget
    //3. lépés: A végén pedig sorba rendezem az összegyűjtött pozíciókat
    //Ezekből a lépésekből tevődik össze a legrövidebb útvonal
    @Override
    public List<BestPathDTO> bestPath(Long projectId) {
        List<Part> parts = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<BestPathDTO> bestPathDTOS = new ArrayList<>();

        //Projekthez tartozó alkatérszek és azok elhelyezkedésének keresése
        for(ProjectPart projectPart : findById(projectId).getBody().getProjectParts()){

            //Az adott alkatrész ezekben a rekeszekben található meg
            List<Box> boxes = boxService.findBoxesByPartId(projectPart.getPart().getId());

            //Rekeszek rendezése benne található alkatrész szám szerint csökkenő sorrendbe
            Collections.sort(boxes);

            //Egy rekeszből ki tudom venni a szükséges alkatrész mennyiséget
            //Ez lenne a legrövidebb út. Minden alkatrészt egy rekszből ki tudok venni.
            if(boxes.get(0).getNumberOfProducts() >= projectPart.getNumberOfParts()){
                //BestPathDTO objektum összeállítása
                BestPathDTO bestPathDTO = new BestPathDTO();
                bestPathDTO.setPartName(projectPart.getPart().getPartName());
                bestPathDTO.setPartNumber(projectPart.getNumberOfParts());
                bestPathDTO.setRow(boxes.get(0).getLocation().getRow());
                bestPathDTO.setCol(boxes.get(0).getLocation().getCol());
                bestPathDTO.setCell(boxes.get(0).getLocation().getCell());

                bestPathDTOS.add(bestPathDTO);

            }
            //Az alkatrészt nem tudom kivenni csak egy rekeszből
            //Ezért végig kell nézni a rekeszeket, amik tartalmazzák az adott alaktrészt
            else{
                for(Box b : boxes){
                    if(projectPart.getNumberOfParts() > 0){
                        //Ha a szükséges alkatérsz mennyiség nagyobb, mint a rekeszben található
                        if(projectPart.getNumberOfParts() > b.getNumberOfProducts()){

                            //BestPathDTO objektum összeállítása
                            BestPathDTO bestPathDTO = new BestPathDTO();
                            bestPathDTO.setPartName(projectPart.getPart().getPartName());
                            bestPathDTO.setPartNumber(b.getNumberOfProducts());
                            bestPathDTO.setRow(b.getLocation().getRow());
                            bestPathDTO.setCol(b.getLocation().getCol());
                            bestPathDTO.setCell(b.getLocation().getCell());

                            bestPathDTOS.add(bestPathDTO);
                        }
                        //Ha a szükséges alkatérsz mennyiség kisebb, mint a rekeszben található
                        if(projectPart.getNumberOfParts() < b.getNumberOfProducts()){

                            //BestPathDTO objektum összeállítása
                            BestPathDTO bestPathDTO = new BestPathDTO();
                            bestPathDTO.setPartName(projectPart.getPart().getPartName());
                            bestPathDTO.setPartNumber(projectPart.getNumberOfParts());
                            bestPathDTO.setRow(b.getLocation().getRow());
                            bestPathDTO.setCol(b.getLocation().getCol());
                            bestPathDTO.setCell(b.getLocation().getCell());

                            bestPathDTOS.add(bestPathDTO);

                            //Megvan az összes alkatrész mennyiség
                            projectPart.setNumberOfParts(0);
                        }
                        projectPart.setNumberOfParts(projectPart.getNumberOfParts() - b.getNumberOfProducts());
                    }
                }
            }
        }
        //Rendezés növekvő sorrendbe sor, oszlop majd rekesz szám szerint
        Collections.sort(bestPathDTOS);
        return bestPathDTOS;
    }

    //Projektek lekérése
    //Visszaadom a folyamatban lévő projekteket vagy a lezárt porjekteket
    //Folyamatban lévő projekt - ha nincs olyan státusza, hogy "completed" vagy "failed"
    //Lezárt projekt - ha van olyan státusza, hogy "completed" vagy "failed"
    @Override
    public List<Project> findByProjectStatus(String status) {
        List<Project> projects = projectRepository.findAll();

        List<Project> inProgressProjects = new ArrayList<>();
        List<Project> finishedProject = new ArrayList<>();
        boolean addToInProgressProjects;

        for(Project p : projects){
            addToInProgressProjects = false;
            for(ProjectStatus ps : p.getProjectStatuses()){
                if(ps.getProjectCurrentStatus().equals("completed") || ps.getProjectCurrentStatus().equals("failed")){
                    addToInProgressProjects = true;
                }
            }
            if(addToInProgressProjects){
                finishedProject.add(p);
            }
            else{
                inProgressProjects.add(p);
            }
        }

        if(status.equals("inprogress")){
            return inProgressProjects;
        }
        return finishedProject;
    }

    //Alkatrész entity objektum konvertálása alkatrész DTO objektummá
    private PartDTO convertEntityToDto(Part part){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PartDTO partDTO = new PartDTO();
        partDTO = modelMapper.map(part, PartDTO.class);
        return partDTO;
    }

    //Új státusz hozzáadása a projekthez
    //Ha már tartalmaz ilyen státuszt, akkor nem adom hozzá
    //Ha még nem, akkor létrehozom és hozzáadom
    public void addNewProjectStatus(Long projectId, String status){

        Project project = findById(projectId).getBody();
        boolean statusAlredyExists = false;

        //Megnézem, hogy a projet rendelkezik-e ilyen státusszal
        for(ProjectStatus projectStatus : project.getProjectStatuses()){
            if(projectStatus.getProjectCurrentStatus().equals(status)){
                statusAlredyExists = true;
            }
        }

        //Ha még nem rendelkezik ilyen státusszal, akkor létrehozom azt, majd összerendelem a projekttel
        if(project.getProjectStatuses() != null && statusAlredyExists == false){
            ProjectStatus projectStatus = new ProjectStatus();

            DateTimeFormatter todayDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String todayDate = todayDateFormat.format(now);

            projectStatus.setProjectCurrentStatus(status);
            projectStatus.setStatusChanged(todayDate);
            projectStatusService.save(projectStatus);

            project.getProjectStatuses().add(projectStatusService.findByStatusChanged(todayDate));
            projectStatusService.findByStatusChanged(todayDate).setProject(project);

            projectRepository.save(project);
        }

    }
}
