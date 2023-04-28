import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import ProjectService from '../Service/ProjectService';


const ListProjectsComponent = () => {

    const [projects, setProjects] = useState([]);

    //Option tag változói
    const options = [
        {
            label: "Folyamatban lévő projektek",
            value: "inprogress",
        },
        {
            label: "Lezárt projektek",
            value: "finished",
        }
    ];

    const [selectedStatus, setSelectedStatus] = useState("");

    useEffect(() => {

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
            window.location.reload();

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

    //Projektek lekérése
    //Folyamatban lévő projektek vagy lezárt projektek
    const findByProjectStatus = (e) => {
        e.preventDefault()
        setSelectedStatus(e.target.value)
        ProjectService.findByProjectStatus(e.target.value).then((response) => {
            setProjects(response.data)

        },
            (error) => {
                console.log(error)
            }
        )
        console.log(selectedStatus)

    }




    return (
        <div>
            <h2 className='text-center'>Projektek</h2>
            <label>Keresés projektstátusz alapján</label>
            <div className='row mb-2'>
                <select className="form-select" aria-label="Default select example" onChange={findByProjectStatus}>
                    {options.map((option) => (
                        <option value={option.value}>{option.label}</option>
                    ))}
                </select>
            </div>
            <div className='row' >
                <table className='table table-striped table-bordered text-center' >
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
                                            <Link onClick={(e) => showProjectStatuses(e, project.projectStatuses)} className='btn btn-primary m-1'>Mutasd</Link>
                                        </td>
                                        {
                                            selectedStatus === "inprogress" && (
                                                <td>
                                                    <Link to={`/createproject/${project.id}`} className='btn btn-info m-1'>Módosítás</Link>
                                                </td>
                                            )
                                        }
                                        {
                                            selectedStatus === "inprogress" && (
                                                <td>
                                                    <Link to={`/parttoproject/${project.id}`} className='btn btn-info m-1'>Alkatrész hozzáadás</Link>
                                                </td>
                                            )
                                        }
                                        {
                                            selectedStatus === "inprogress" && (
                                                <td>
                                                    <Link onClick={(e) => showFullCost(e, project.id)} className='btn btn-info m-1'>Árkalkuláció</Link>
                                                </td>

                                            )
                                        }
                                        {
                                            selectedStatus === "inprogress" && (
                                                <td>
                                                    <Link onClick={(e) => finishProject(e, project.id, "completed")} className='btn btn-success m-1'>Sikeres</Link>
                                                    <br />
                                                    <Link onClick={(e) => finishProject(e, project.id, "failed")} className='btn btn-danger m-1'>Sikertelen</Link>
                                                </td>
                                            )
                                        }

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