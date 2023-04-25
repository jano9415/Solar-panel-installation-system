import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import BoxService from '../Service/BoxService';
import PartService from '../Service/PartService';

const ListBoxesComponent = () => {


    const [boxes, setBoxes] = useState([]);
    const { id } = useParams();
    const [part, setPart] = useState({
        id: 0, partName: "", price: 0, maxPieceInBox: 0, allAvailableNumber: 0,
        allReservedNumber: 0, preReservedNumber: 0
    });
    const [placedAmount, setPlacedAmount] = useState();

    useEffect(() => {


        PartService.findById(id).then((response) => {

            part.id = response.data.id
            part.partName = response.data.partName
            part.price = response.data.price
            part.maxPieceInBox = response.data.maxPieceInBox
            part.allAvailableNumber = response.data.allAvailableNumber
            part.allReservedNumber = response.data.allReservedNumber
            part.preReservedNumber = response.data.preReservedNumber
        },
            (error) => {
                console.log(error)
            }
        )

        BoxService.findByPartId(id).then((response) => {
            setBoxes(response.data);
        },
            (error) => {
                console.log(error)
            })

    }, [])

    const placePart = (e, box) => {
        e.preventDefault();
        if (placedAmount <= (part.maxPieceInBox - box.numberOfProducts)) {
            if (box.part != null) {
                BoxService.placePartInBox(box.id, placedAmount).then((response) => {
                    window.location.reload();

                },
                    (error) => {
                        console.log(error)
                    }
                )
            }
            BoxService.placePartInEmptyBox(box.id, placedAmount, part.id).then((response) => {
                window.location.reload();

            },
                (error) => {
                    console.log(error)
                }
            )
        }
        else {
            alert("Ennyi alkatrész nem fér el a rekeszben!")
        }


    }



    return (
        <div>
            <h2 className='text-center'>Rekeszek</h2>
            <div className='row' >
                <table className='table table-striped table-bordered' >
                    <thead>
                        <tr>
                            <th>Rekesz id</th>
                            <th>Alkatrész a rekeszben</th>
                            <th>Sor</th>
                            <th>Oszlop</th>
                            <th>Pozíció</th>
                            <th>Darabszám a rekeszben</th>
                            <th>Bevételezés</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            boxes.map(
                                box =>
                                    <tr key={box.id}>
                                        <td>{box.id}</td>
                                        {
                                            box.part == null ? (
                                                <td>Üres</td>
                                            ) :
                                                (
                                                    <td>{box.part.partName}</td>
                                                )
                                        }

                                        <td>{box.location.row}</td>
                                        <td>{box.location.col}</td>
                                        <td>{box.location.cell}</td>
                                        <td>{box.numberOfProducts}</td>
                                        <td>
                                            <input type="number" placeholder='Darabszám' onChange={(e) => setPlacedAmount(e.target.value)}
                                                className='form-control'
                                            />
                                            <button className='btn btn-info m-1' onClick={(e) => { placePart(e, box) }}>Elhelyez</button>
                                            {
                                                box.numberOfProducts === 0 ? (

                                                    <p>Még {part.maxPieceInBox} darab alkatrész fér el.</p>

                                                ) :
                                                    (
                                                        <p>Még {part.maxPieceInBox - box.numberOfProducts} darab alkatrész fér el.</p>
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

export default ListBoxesComponent;