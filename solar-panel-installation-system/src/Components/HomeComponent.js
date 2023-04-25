import React, { useEffect, useState } from 'react';
import AuthService from '../Service/AuthService';


const HomeComponent = () => {


    useEffect(() => {

    }, [])


    return (
        <div>
            <h1>Solar panel installation system</h1>
            <div>
                {
                    AuthService.getCurrentUser() && (
                        <span>Üdvözlünk {AuthService.getCurrentUser().username}</span>
                    )
                }




            </div>
            <img
                src="https://www.cnet.com/a/img/resize/19394aca4affc504651051d009160d0c0d216218/hub/2022/10/10/f2ff9ef7-f016-459d-b88a-a8a68270c315/solar-gettyimages-525206743.jpg?auto=webp&fit=crop&height=675&width=1200"
                alt="solar"
                height={500}
                width={800}
            />
        </div>
    );
}

export default HomeComponent;