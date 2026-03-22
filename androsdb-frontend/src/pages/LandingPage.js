import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { useStore } from '../store';
import './LandingPage.css';

const LandingPage = () => {
    const [localQuery, setLocalQuery] = useState('');
    const { setSearchQuery } = useStore();
    const navigate = useNavigate();

    const handleSearch = (e) => {
        e.preventDefault();
        setSearchQuery(localQuery);
        navigate('/explore');
    };

    return (
        <div className="landing-container">
            <Navbar />

            {/* Top Half: Image Background */}
            <div className="landing-top">
                <div className="hero-content">
                    {/* Updated Header Text */}
                    <h1>Search the Heritage of Andros</h1>
                    <form onSubmit={handleSearch} className="search-bar">
                        <input
                            type="text"
                            placeholder="Search surnames, places, or people..."
                            value={localQuery}
                            onChange={(e) => setLocalQuery(e.target.value)}
                        />
                        <button type="submit" className="btn-accent">Search</button>
                    </form>
                </div>
            </div>

            <div className="landing-bottom">

                {/* Section 1: Explore */}
                <div className="explore-split-section">

                    {/* Text on the Left */}
                    <div className="section-header">
                        <h2>Explore</h2>
                        {/* Restored Subtext */}
                        <p>Browse People, Events, Surnames, Surname Events, Churches, Places, and Sources</p>
                    </div>

                    {/* Table on the Right */}
                    <div className="explore-menu-card">

                        <div className="explore-menu-item" onClick={() => navigate('/explore')}>
                            <div className="item-left">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
                                <span>People</span>
                            </div>
                            <div className="item-right">
                                <span className="count">731,371</span>
                                <span className="chevron">&gt;</span>
                            </div>
                        </div>

                        <div className="explore-menu-item" onClick={() => navigate('/explore')}>
                            <div className="item-left">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
                                <span>Events</span>
                            </div>
                            <div className="item-right">
                                <span className="count">473,922</span>
                                <span className="chevron">&gt;</span>
                            </div>
                        </div>

                        <div className="explore-menu-item" onClick={() => navigate('/explore')}>
                            <div className="item-left">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M20.59 13.41l-7.17 7.17a2 2 0 0 1-2.83 0L2 12V2h10l8.59 8.59a2 2 0 0 1 0 2.82z"></path><line x1="7" y1="7" x2="7.01" y2="7"></line></svg>
                                <span>Surname</span>
                            </div>
                            <div className="item-right">
                                <span className="count">1,245</span>
                                <span className="chevron">&gt;</span>
                            </div>
                        </div>

                        <div className="explore-menu-item" onClick={() => navigate('/explore')}>
                            <div className="item-left">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><circle cx="12" cy="12" r="10"></circle><polyline points="12 6 12 12 16 14"></polyline></svg>
                                <span>Surname Events</span>
                            </div>
                            <div className="item-right">
                                <span className="count">8,432</span>
                                <span className="chevron">&gt;</span>
                            </div>
                        </div>

                        <div className="explore-menu-item" onClick={() => navigate('/explore')}>
                            <div className="item-left">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M3 21v-8a2 2 0 0 1 1-1.73l7-4a2 2 0 0 1 2 0l7 4A2 2 0 0 1 21 13v8"></path><path d="M12 3v6"></path><path d="M9 6h6"></path><path d="M9 21v-6a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v6"></path></svg>
                                <span>Churches</span>
                            </div>
                            <div className="item-right">
                                <span className="count">142</span>
                                <span className="chevron">&gt;</span>
                            </div>
                        </div>

                        <div className="explore-menu-item" onClick={() => navigate('/explore')}>
                            <div className="item-left">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path><circle cx="12" cy="10" r="3"></circle></svg>
                                <span>Places</span>
                            </div>
                            <div className="item-right">
                                <span className="count">10,348</span>
                                <span className="chevron">&gt;</span>
                            </div>
                        </div>

                        <div className="explore-menu-item" onClick={() => navigate('/explore')}>
                            <div className="item-left">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
                                <span>Sources</span>
                            </div>
                            <div className="item-right">
                                <span className="count">3,939</span>
                                <span className="chevron">&gt;</span>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            {/* Section 2: Visualize Map Band */}
            <div className="visualize-band">
                <div className="visualize-content">
                    <div className="visualize-text">
                        <h2>Visualize</h2>
                        <p>See how Families moved in Real Time</p>
                    </div>
                    <button className="btn-accent large" onClick={() => navigate('/visualize')}>
                        Visualize Map
                    </button>
                </div>
            </div>

        </div>
    );
};

export default LandingPage;