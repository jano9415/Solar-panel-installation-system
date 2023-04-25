import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import ProjectService from '../Service/ProjectService';


const ListProjectsComponent = () => {

    const [projects, setProjects] = useState([]);

    useEffect(() => {

        ProjectService.findAll().then((response) => {
            setProjects(response.data)
            console.log(response.data)
        },
            (error) => {
                console.log(error)
            }
        )

    }, [])

    //Árkalkuláció
    const showFullCost = (e, projectId) => {
        e.preventDefault();
        ProjectService.showFullCost(projectId).then((response) => {
            if (response.data === 0) {
                alert("Nem lehet árkalkulációt készíteni, mert nem érhető el minden szükséges alkatrész a raktárban.")
            }
            else {
                alert("A projekt költsége: " + response.data + " Ft")
            }


        },
            (error) => {
                console.log(error)
            }
        )

    }

    //Projekt lezárása
    const finishProject = (e, projectId, status) => {
        e.preventDefault();
        ProjectService.finishProject(projectId, status).then((response) => {
            alert("Projekt lezárva.")

        },
            (error) => {
                console.log(error)
            }
        )
    }

    //Státuszok megjelenítése
    const showProjectStatuses = (e, projectStatuses) => {
        e.preventDefault();

        for (let i = 0; i < projectStatuses.length; i++) {
            alert("Id: " + projectStatuses[i].id + "\n" +
                "Státusz: " + projectStatuses[i].projectCurrentStatus + "\n" +
                "Dátum: " + projectStatuses[i].statusChanged

            )
        }

    }




    return (
        <div>
            <h2 className='text-center'>Projektek</h2>
            <label>Keresés projektstátusz alapján</label>
            <select id="cars">
                <option value="volvo">New</option>
                <option value="saab">Draft</option>
                <option value="opel">Wait</option>
                <option value="audi">Scheduled</option>
                <option value="audi">InProgress</option>
                <option value="audi">Completed</option>
                <option value="audi">Failed</option>
            </select>
            <div className='row' >
                <table className='table table-striped table-bordered' >
                    <thead>
                        <tr>
                            <th>Project id</th>
                            <th>Helyszín</th>
                            <th>Leírás</th>
                            <th>Megrendelő</th>
                            <th>Időtartam</th>
                            <th>Munkadíj</th>
                            <th>Státuszok</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            projects.map(
                                project =>
                                    <tr key={project.id}>
                                        <td>{project.id}</td>
                                        <td>{project.projectLocation}</td>
                                        <td>{project.projectDescription}</td>
                                        <td>{project.customerData}</td>
                                        <td>{project.workDuration}</td>
                                        <td>{project.workCost}</td>
                                        <td>
                                            <Link onClick={(e) => showProjectStatuses(e, project.projectStatuses)} className='btn btn-info m-1'>Mutasd</Link>
                                        </td>
                                        <td>
                                            <Link to={`/createproject/${project.id}`} className='btn btn-info m-1'>Módosítás</Link>
                                        </td>
                                        <td>
                                            <Link to={`/parttoproject/${project.id}`} className='btn btn-info m-1'>Alkatrész hozzáadás</Link>
                                        </td>
                                        <td>
                                            <Link onClick={(e) => showFullCost(e, project.id)} className='btn btn-info m-1'>Árkalkuláció</Link>
                                            <br />
                                            <Link onClick={(e) => finishProject(e, project.id, "success")} className='btn btn-info m-1'>Sikeres</Link>
                                            <Link onClick={(e) => finishProject(e, project.id, "unsuccess")} className='btn btn-info m-1'>Sikertelen</Link>
                                        </td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ListProjectsComponent;