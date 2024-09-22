import React from 'react';
import { useNavigate } from 'react-router-dom';

const RedirectButton = (data: {name: string, url: string}) => {
    const navigate = useNavigate();

    const handleRedirect = () => {
        navigate(data.url); 
    };

    return (
        <button onClick={handleRedirect}>
            {data.name}
        </button>
    );
};

export default RedirectButton;
