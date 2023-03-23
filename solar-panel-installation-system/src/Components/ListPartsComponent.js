import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import AuthService from '../Service/AuthService';
import PartService from '../Service/PartService';

const ListPartsComponent = () => {

    const [parts, setParts] = useState([]);

    useEffect(() => {
        PartService.getParts().then((response) => {
            setParts(response.data);
            console.log(response.data)
        },
            (error) => {
                console.log(error)
            })

        //Teszt
        /*
        setParts(PartService.getParts)
        console.log(AuthService.getCurrentUser().roles)
        */

    }, [])





    return (
        <div>
            <h2 className='text-center'>Alkatrészek</h2>
            {AuthService.getCurrentUser().role === 'storeleader' && (
                <Link to="/createpart" className='btn btn-primary mb-2'>Új alkatrész hozzáadása</Link>
            )}
            <div className='row' >
                <table className='table table-striped table-bordered' >
                    <thead>
                        <tr>
                            <th>Név</th>
                            <th>Ár</th>
                            <th>Maximum darabszám egy dobozban</th>
                            <th>Raktárkészlet</th>
                            <th>Lefoglalt mennyiség</th>
                            <th>Előfoglalt mennyiség</th>
                            <th>Elérhető darabszám</th>
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
                                        <td>{part.allAvailableNumber}</td>
                                        <td>{part.allReservedNumber}</td>
                                        <td>{part.preReservedNumber}</td>
                                        <td>
                                            {part.allAvailableNumber - part.allReservedNumber}
                                        </td>
                                        {AuthService.getCurrentUser().roles == "storeleader" && (
                                            <td>
                                                <Link to={`/createpart/${part.id}`} className='btn btn-info m-1'>Módosítás</Link>
                                                <Link to={`/listboxes/${part.id}`} className='btn btn-info m-1'>Bevételezés</Link>
                                            </td>
                                        )}
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