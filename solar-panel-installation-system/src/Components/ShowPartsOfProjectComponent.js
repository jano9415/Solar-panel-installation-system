import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import ProjectService from '../Service/ProjectService';
import BoxService from '../Service/BoxService';

const ShowPartsOfProjectComponent = () => {

    const [parts, setParts] = useState([]);
    const [project, setProject] = useState();
    const [boxes, setBoxes] = useState([]);
    const [numberOfPart, setNumberOfPart] = useState()
    const [selectedNumberOfPart, setSelectedNumberOfPart] = useState()

    //Projekt id
    const { id } = useParams();

    

    useEffect(() => {
        //Projektek lekérése, amik készen állnak a kivételezésre
        ProjectService.showPartsOfProject(id).then((response) => {
            setParts(response.data)

        },
            (error) => {
                console.log(error)
            }
        )


    }, [])

    //Kiválasztott alkatrész keresése a rekeszekben
    const showBox = (e, partId, numberOfParts) => {

        e.preventDefault()
        setNumberOfPart(numberOfParts)

        BoxService.findBoxesByPartId(partId).then((response) => {
            setBoxes(response.data)

        },
            (error) => {
                console.log(error)
            }
        )
    }

    //Alkatrész kivétele a rekeszből
    const takePart = (e, boxId, numberOfProducts, location) => {
        

        if (selectedNumberOfPart > numberOfProducts || selectedNumberOfPart > numberOfPart) {
            alert("Ennyi alkatrészt nem tudsz kivenni!")
        }
        else {
            BoxService.takePart(boxId, numberOfPart, selectedNumberOfPart, id).then((response) => {

                window.location.reload();

            },
                (error) => {
                    console.log(error)
                }
            )

        }
    }

    //Legjobb útvonal listázása
    const bestPath = () => {
        ProjectService.bestPath(id).then((response) => {
            console.log(response.data)


        },
            (error) => {
                console.log(error)
            }
        )

    }


    return (
        <div>
            <h2 className='text-center'>Alkatrészek a projekthez</h2>
            <Link onClick={bestPath} className='btn btn-info m-1'>Legjobb útvonal</Link>
            <div className='row' >
                <table className='table table-striped table-bordered' >
                    <thead>
                        <tr>
                            <th>Név</th>
                            <th>Ár</th>
                            <th>Szükséges darabszám</th>
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
                                        <td>{part.numberOfParts}</td>
                                        <td>
                                            <Link onClick={(e) => showBox(e, part.id, part.numberOfParts)} className='btn btn-info m-1'>Keresés a rekeszekben</Link>
                                        </td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
            <h2 className='text-center'>Rekeszek</h2>
            <div className='row' >
                <table className='table table-striped table-bordered' >
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Sor</th>
                            <th>Oszlop</th>
                            <th>Rekesz</th>
                            <th>Darabszám a rekeszben</th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            boxes.map(
                                box =>
                                    <tr key={box.id}>
                                        <td>{box.id}</td>
                                        <td>{box.location.row}</td>
                                        <td>{box.location.col}</td>
                                        <td>{box.location.cell}</td>
                                        <td>{box.numberOfProducts}</td>
                                        <td>
                                            <input type="number" placeholder='Darabszám' onChange={(e) => setSelectedNumberOfPart(e.target.value)}
                                                className='form-control' />
                                            <Link onClick={(e) => takePart(e, box.id, box.numberOfProducts, box.location)} className='btn btn-info m-1'>Kivételezés</Link>
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

export default ShowPartsOfProjectComponent;