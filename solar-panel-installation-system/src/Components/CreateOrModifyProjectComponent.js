import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import ProjectService from '../Service/ProjectService';

const CreateOrModifyProjectComponent = () => {

    const [projectLocation, setProjectLocation] = useState("");
    const [projectDescription, setProjectDescription] = useState("");
    const [customerData, setCustomerData] = useState("");
    const [workDuration, setWorkDuration] = useState();
    const [workCost, setWorkCost] = useState();
    const [projectCurrentStatus, setProjectCurrentStatus] = useState("");
    const [statusChanged, setStatusChanged] = useState();

    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            /*ProjectService.findById(id).then((response) => {
                setProjectLocation(response.data.setProjectLocation)
                setProjectDescription(response.data.projectDescription)
                setCustomerData(response.data.customerData)
                setWorkDuration(response.data.workDuration)
                setWorkCost(response.data.workCost)
                setProjectCurrentStatus(response.data.projectStatus.projectCurrentStatus)
                setStatusChanged(response.data.projectStatus.statusChanged)

            },
            (error) => {

            }
            )*/

            //Teszt
            /*
            console.log(id)
            let project = ProjectService.findById(id)
            setProjectLocation(project.projectLocation)
            setProjectDescription(project.projectDescription)
            setCustomerData(project.customerData)
            setWorkDuration(project.workDuration)
            setWorkCost(project.workCost)
            setProjectCurrentStatus(project.projectStatus.projectCurrentStatus)
            setStatusChanged(project.projectStatus.statusChanged)
            */
        }
    }, []);

    //Új projekt létrehozása vagy meglévő módosítása
    const createOrModifyProject = (e) => {
        e.preventDefault();
        const projectStatus = { projectCurrentStatus, statusChanged }
        const project = { projectLocation, projectDescription, customerData, workDuration, workCost, projectStatus }

        //Projekt módosítása
        if (id) {
            ProjectService.modifyProject(id, project).then((response) => {

            },
                (error) => {

                }
            )

        }
        //Új projekt létrehozása
        else {
            ProjectService.createProject(project).then((response) => {

            },
                (error) => {
                    console.log(error)
                }
            )
        }
    }

    //Cím bállítása
    const getTitle = () => {
        if (id) {
            return <h3 className='text-center'>Projekt módosítása</h3>
        }
        else {
            return <h3 className='text-center'>Új projekt hozzáadása</h3>
        }
    }




    return (
        <div>
            <div className='container m-3'>
                <div className='container'>
                    <div className='card col-md-6 offset-md-3 offset-md-3'>
                        {getTitle()}
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