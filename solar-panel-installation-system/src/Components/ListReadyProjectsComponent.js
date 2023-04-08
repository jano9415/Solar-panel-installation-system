import React, { useEffect, useState } from 'react';
import ProjectService from '../Service/ProjectService';
import { Link } from 'react-router-dom';

const ListReadyProjectsComponent = () => {

    const [projects, setProjects] = useState([]);

    useEffect(() => {

        ProjectService.listProjectsWithoutPreReservation().then((response) => {
            setProjects(response.data)
            console.log(response.data)
        },
            (error) => {
                console.log(error)
            }
        )

    }, [])

    return (
        <div>
            <h2 className='text-center'>Projektek kivételezésre</h2>
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
                                            <Link to={`/showpartsofproject/${project.id}`} className='btn btn-info m-1'>Alkatrészek</Link>
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

export default ListReadyProjectsComponent;
