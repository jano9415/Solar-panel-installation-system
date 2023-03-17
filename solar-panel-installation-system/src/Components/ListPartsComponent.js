import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import PartService from '../Service/PartService';

const ListPartsComponent = () => {

    const [parts, setParts] = useState([]);

    useEffect(() => {
        /*PartService.getParts().then((response) => {
            setParts(response.data);
        },
        (error) => {
            console.log(error)
        })*/

        setParts(PartService.getParts)

    },[])





    return (
        <div>
        <h2 className='text-center'>Alkatrészek</h2>
        <Link to="/createpart" className='btn btn-primary mb-2'>Új alkatrész hozzáadása</Link>
        <div className='row' >
            <table className='table table-striped table-bordered' >
                <thead>
                    <tr>
                        <th>Név</th>
                        <th>Ár</th>
                        <th>Maximum darabszám egy dobozban</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                    {
                        parts.map(
                            part =>
                                <tr key={part.id}>
                                    <td>{part.partName}</td>
                                    <td>{part.price}</td>
                                    <td>{part.maxPieceInBox}</td>
                                    <td>
                                        <Link to={`/createpart/${part.id}`} className='btn btn-info m-1'>Módosítás</Link>
                                        <Link to={`/listboxes/${part.id}`} className='btn btn-info m-1'>Bevételezés</Link>
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

export default ListPartsComponent;