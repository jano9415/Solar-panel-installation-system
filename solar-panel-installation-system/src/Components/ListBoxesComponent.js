import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import BoxService from '../Service/BoxService';
import PartService from '../Service/PartService';

const ListBoxesComponent = () => {


    const [boxes, setBoxes] = useState([]);
    const {id} = useParams();
    const [part, setPart] = useState();
    const [placedAmount, setPlacedAmount] = useState();

    useEffect(() => {

        /*PartService.findById(id).then((response) => {
            setPart(response.data);
        },
        (error) => {
            console.log(error)
        }
        )*/

        /*BoxService.findAll().then((response) => {
            setBoxes(response.data);
        },
        (error) => {
            console.log(error)
        })

        BoxService.findByPartId().then((response) => {
            setBoxes(response.data);
        },
        (error) => {
            console.log(error)
        })*/

        setPart(PartService.findById(id));
        setBoxes(BoxService.findByPartId);



    }, [])

    const placePart = (e, box) => {
        e.preventDefault();
        if(placedAmount <= (box.part.maxPieceInBox - box.numberOfProducts)){
            if(box.part == null){
                BoxService.placePartInBox(box.id, placedAmount).then((response) => {
                    window.location.reload();

                },
                (error) => {
                    console.log(error)
                }
                )
            }
            BoxService.placePartInEmptyBox(box.id, placedAmount, part).then((response) => {
                window.location.reload();

            },
            (error) => {
                console.log(error)
            }
            )
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
                                        <td>{box.part.partName}</td>
                                        <td>{box.wareHouse.row}</td>
                                        <td>{box.wareHouse.col}</td>
                                        <td>{box.wareHouse.cell}</td>
                                        <td>{box.numberOfProducts}</td>
                                        <td>
                                            <input type="number" placeholder='Darabszám' onChange={(e) => setPlacedAmount(e.target.value)} />
                                            <button className='btn btn-info m-1' onClick={(e) => {placePart(e, box)}}>Elhelyez</button>
                                            {
                                                box.numberOfProducts === 0 ? (

                                                    <p>Még {part.maxPieceInBox} darab alkatrész fér el.</p>

                                                ) :
                                                    (
                                                        <p>Még {box.part.maxPieceInBox - box.numberOfProducts} darab alkatrész fér el.</p>
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