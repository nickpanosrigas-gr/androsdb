import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className="hero-nav">
            <Link to="/" className="logo">AndrosDB</Link>
            <button className="btn-accent">Admin Login</button>
        </nav>
    );
};

export default Navbar;