import React, { useEffect, useState } from 'react';
import AuthService from '../Service/AuthService';
import { Link } from 'react-router-dom';
import PartService from '../Service/PartService';

const ListLackOfPartsComponent = () => {

    const [parts, setParts] = useState([]);

    useEffect(() => {


    }, [])

    const listLackOfParts = () => {

        PartService.findLackOfParts().then((response) => {
            setParts(response.data);
            console.log(response.data)
        },
            (error) => {
                console.log(error)
            })

    }

    const listLackOfPartsWithPreReservation = () => {
        PartService.findLackOfPartsWithPreReservation().then((response) => {
            setParts(response.data);
            console.log(response.data)
        },
            (error) => {
                console.log(error)
            })


    }


    return (
        <div>
            <h2 className='text-center'>Hiányzó alkatrészek</h2>
            <Link className='btn btn-primary m-1' onClick={listLackOfParts}>Hiányzó alkatrészek</Link>
            <Link className='btn btn-primary m-1' onClick={listLackOfPartsWithPreReservation}>Hiányzó és előfoglalt alkatrészek</Link>
            <div className='row' >
                <table className='table table-striped table-bordered text-center' >
                    <thead>
                        <tr>
                            <th>Név</th>
                            <th>Ár</th>
                            <th>Maximum darabszám egy dobozban</th>
                            <th>Raktárkészlet</th>
                            <th>Lefoglalt mennyiség</th>
                            <th className='bg-danger'>Előfoglalt mennyiség</th>
                            <th className='bg-warning'>Elérhető darabszám</th>
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
                                        <td className="table-danger">{part.preReservedNumber}</td>
                                        <td className="table-warning">
                                            {part.allAvailableNumber - part.allReservedNumber}
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

export default ListLackOfPartsComponent;