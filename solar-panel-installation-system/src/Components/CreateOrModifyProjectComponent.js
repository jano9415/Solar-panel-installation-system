import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import ProjectService from '../Service/ProjectService';

const CreateOrModifyProjectComponent = () =>  {

    const [projectLocation, setProjectLocation] = useState("");
    const [projectDescription, setProjectDescription] = useState("");
    const [customerData, setCustomerData] = useState("");
    const [workDuration, setWorkDuration] = useState();
    const [workCost, setWorkCost] = useState();
    const [projectCurrentStatus, setProjectCurrentStatus] = useState("");
    const [statusChanged, setStatusChanged] = useState();

    useEffect(() => {
        setProjectCurrentStatus("new")


    }, []);

    const createOrModifyProject = (e) => {
        e.preventDefault();

        

        const projectStatus = {projectCurrentStatus, statusChanged}
        const project = {projectLocation, projectDescription, customerData, workDuration, workCost, projectStatus}
        

        console.log(project)

        
        ProjectService.createProject(project).then((response) => {

        },
        (error) => {
            console.log(error)
        }
        )


    }




    return (
        <div>
            <div className='container m-3'>
                <div className='container'>
                    <div className='card col-md-6 offset-md-3 offset-md-3'>
                        <h2>Új projekt hozzáadása</h2>
                        <div className='card-body'>
                            <form>
                                <div className='form-group'>
                                    <label className='form-label'>Helyszín</label>
                                    <input
                                        type="text"
                                        placeholder='Helyszín'
                                        name='projectLocation'
                                        className='form-control'
                                        value={projectLocation}
                                        onChange={(e) => setProjectLocation(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Leírás</label>
                                    <input
                                        type="text"
                                        placeholder='Leírás'
                                        name='projectDescription'
                                        className='form-control'
                                        value={projectDescription}
                                        onChange={(e) => setProjectDescription(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Megrendelő neve</label>
                                    <input
                                        type="text"
                                        placeholder='Megrendelő neve'
                                        name='customerData'
                                        className='form-control'
                                        value={customerData}
                                        onChange={(e) => setCustomerData(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Időtartam</label>
                                    <input
                                        type="number"
                                        placeholder='Időtartam'
                                        name='workDuration'
                                        className='form-control'
                                        value={workDuration}
                                        onChange={(e) => setWorkDuration(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Költség</label>
                                    <input
                                        type="number"
                                        placeholder='Költség'
                                        name='workDuration'
                                        className='form-control'
                                        value={workCost}
                                        onChange={(e) => setWorkCost(e.target.value)} />
                                </div>
                                <button className='btn btn-success m-2' onClick={(e) => createOrModifyProject(e)}>Mentés</button>
                                <Link to="/">
                                    <button className='btn btn-danger'>Mégse</button>
                                </Link>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    );
}

export default CreateOrModifyProjectComponent;