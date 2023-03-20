import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import AuthService from '../Service/AuthService';
import PartService from '../Service/PartService';
import ProjectService from '../Service/ProjectService';

const PartToProjectComponent = () => {

    const [parts, setParts] = useState([]);
    const [project, setProject] = useState();
    const [reservedNumber, setReservedNumber] = useState();

    const { id } = useParams();

    useEffect(() => {
        /*PartService.getParts().then((response) => {
            setParts(response.data);
        },
        (error) => {
            console.log(error)
        })*/

        /*ProjectService.findById(id).then((response) => {
            setProject(response.data)
        },
        (error) => {
            console.log(error)
        })*/

        setParts(PartService.getParts)
        setProject(ProjectService.findById(id))
        console.log(project)

    }, [])

    //Alkatrész lefoglalása
    const reservePart = (e) => {
        e.preventDefault();

    }

    //Előfoglalás leadása az alkatrészre
    const preReservePart = (e) => {
        e.preventDefault();

    }




    return (
        <div>
            <h2 className='text-center'>Alkatrészek</h2>
            <div className='row' >
                <table className='table table-striped table-bordered' >
                    <thead>
                        <tr>
                            <th>Név</th>
                            <th>Ár</th>
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
                                        <td>{part.allAvailableNumber}</td>
                                        <td>{part.allReservedNumber}</td>
                                        <td>{part.preReservedNumber}</td>
                                        <td>
                                            {part.allAvailableNumber - part.allReservedNumber}
                                        </td>
                                        <td>
                                            <input type="number" placeholder='Darabszám'
                                                onChange={(e) => setReservedNumber(e.target.value)}
                                            />
                                            <br />
                                            {
                                                part.allAvailableNumber - part.allReservedNumber > 0 && (
                                                    <button to={`/createpart/${part.id}`} className='btn btn-info m-1'
                                                        onClick={(e) => { reservePart(e) }}
                                                    >
                                                        Lefoglal
                                                    </button>
                                                )
                                            }
                                            {
                                                part.allAvailableNumber - part.allReservedNumber <= 0 && (
                                                    <button to={`/listboxes/${part.id}`} className='btn btn-info m-1'
                                                        onClick={(e) => { preReservePart(e) }}
                                                    >
                                                        Előfoglalás
                                                    </button>
                                                )
                                            }
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

export default PartToProjectComponent;