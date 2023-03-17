import React, { useEffect, useState } from 'react';
import ProjectService from '../Service/ProjectService';

const ListProjectsComponent = () =>  {

    const [projects, setProjects] = useState([]);

    useEffect(() => {

        /*ProjectService.findAll().then((response) => {
            setProjects(response.data)
        },
        (error) => {
            console.log(error)
        }
        )*/

        setProjects(ProjectService.findAll);



    }, [])






    return (
        <div>
        <h2 className='text-center'>Projektek</h2>
        <div className='row' >
            <table className='table table-striped table-bordered' >
                <thead>
                    <tr>
                        <th>Project id</th>
                        <th>Helyszín</th>
                        <th>Leírás</th>
                        <th>Megrendelő</th>
                        <th>Időtartam</th>
                        <th>Költség</th>
                        <th>Státusz</th>
                        <th>Státusz változás</th>
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
                                    <td>{project.projectStatus.projectCurrentStatus}</td>
                                    <td>{project.projectStatus.statusChanged}</td>
                                    <td>
                                        <button className='btn btn-info m-1'>Szerkesztés</button>
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