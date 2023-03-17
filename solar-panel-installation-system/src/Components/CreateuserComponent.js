import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import AuthService from '../Service/AuthService';

const CreateuserComponent = () => {

    const [userName, setUserName] = useState("")
    const [emailAddress, setEmailAddress] = useState("")
    const [password, setPassword] = useState("")

    useEffect(() => {

    }, [])

    const createUser = (e) => {
        e.preventDefault();
        AuthService.createUser(userName,emailAddress,password).then((response) => {

        },
        (error) => {
            console.log(error)
        })

    }




    return (
        <div>
            <div className='container m-3'>
                <div className='container'>
                    <div className='card col-md-6 offset-md-3 offset-md-3'>
                        <h3 className='text-center'>Új felhasználó hozzáadása</h3>
                        <div className='card-body'>
                            <form>
                                <div className='form-group'>
                                    <label className='form-label'>Felhasználói név</label>
                                    <input
                                        type="text"
                                        placeholder='Felhasználói név'
                                        name='partName'
                                        className='form-control'
                                        value={userName}
                                        onChange={(e) => setUserName(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Email cím</label>
                                    <input
                                        type="text"
                                        placeholder='Email cím'
                                        name='price'
                                        className='form-control'
                                        value={emailAddress}
                                        onChange={(e) => setEmailAddress(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Jelszó</label>
                                    <input
                                        type="text"
                                        placeholder='Jelszó'
                                        name='maxPieceInBox'
                                        className='form-control'
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)} />
                                </div>
                                <div>
                                    <select className='form-select mt-2 mb-2'>
                                        <option>admin</option>
                                        <option>expert</option>
                                        <option>storeemployee</option>
                                        <option>storeleader</option>
                                    </select>
                                </div>
                                <button className='btn btn-success m-2' onClick={(e) => createUser(e)}>Mentés</button>
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

export default CreateuserComponent;