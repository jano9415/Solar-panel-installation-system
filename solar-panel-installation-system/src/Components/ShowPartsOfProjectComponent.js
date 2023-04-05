import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ProjectService from '../Service/ProjectService';

const ShowPartsOfProjectComponent = () => {

    const [parts, setParts] = useState([]);
    const [project, setProject] = useState();


    const { id } = useParams();

    useEffect(() => {

        ProjectService.showPartsOfProject(id).then((response) => {
            console.log(response.data)

        },
        (error) => {
            console.log(error)
        }
        )


    }, [])


    return (
        <div>
            
        </div>
    );
}

export default ShowPartsOfProjectComponent;