import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import PartService from '../Service/PartService';

const CreateOrModifyPartComponent = () => {

    const [partName, setPartName] = useState("");
    const [price, setPrice] = useState(0);
    const [maxPieceInBox, setMaxPieceInBox] = useState(0);
    const [allAvailableNumber, setAllAvailableNumber] = useState(0);
    const [allReservedNumber, setAllReservedNumber] = useState(0);
    const [preReservedNumber, setPreReservedNumber] = useState(0);

    const navigate = useNavigate();
    const { id } = useParams();

    //Új alkatrész hozzáadása vagy meglévő módosítása
    const createOrModifyPart = (e) => {
        e.preventDefault();
        const part = {partName, price, maxPieceInBox, allAvailableNumber, allReservedNumber, preReservedNumber}

        if(id){
            //Alkatrész módosítása
            PartService.modifyPart(id,part)
            navigate("/listparts");
        }
        else{
            //Új alkatrész hozzáadása

            //Teszt
            /*
            PartService.createPart(part)
            navigate("/listparts");
            */
            
            console.log(part)

            PartService.createPart(part).then(() => {
                navigate("/listparts");
            },
            (error) => {
                console.log(error)
            })
        }

    }

    useEffect(() => {
        if(id){
            PartService.findById(id).then((response) => {
                setPartName(response.data.partName)
                setPrice(response.data.price)
                setMaxPieceInBox(response.data.maxPieceInBox)
            },
            (error) => {
                console.log(error)
            })

            //Teszt
            /*
            let part = PartService.findById(id);
            setPartName(part.partName)
            setPrice(part.price)
            setMaxPieceInBox(part.maxPieceInBox)
            */
        }

    }, []);


    //Cím bállítása
    const getTitle = () => {
        if (id) {
            return <h3 className='text-center'>Alkatrész módosítása</h3>
        }
        else {
            return <h3 className='text-center'>Új alkatrész hozzáadása</h3>
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
                                    <label className='form-label'>Alkatrész neve</label>
                                    <input
                                        type="text"
                                        placeholder='Alkatrész neve'
                                        name='partName'
                                        className='form-control'
                                        value={partName}
                                        onChange={(e) => setPartName(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Ár</label>
                                    <input
                                        type="number"
                                        placeholder='Ár'
                                        name='price'
                                        className='form-control'
                                        value={price}
                                        onChange={(e) => setPrice(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Maximum darabszám egy dobozban</label>
                                    <input
                                        type="number"
                                        placeholder='Maximum darabszám egy dobozban'
                                        name='maxPieceInBox'
                                        className='form-control'
                                        value={maxPieceInBox}
                                        onChange={(e) => setMaxPieceInBox(e.target.value)} />
                                </div>
                                <button className='btn btn-success m-2' onClick={(e) => createOrModifyPart(e)}>Mentés</button>
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

export default CreateOrModifyPartComponent;