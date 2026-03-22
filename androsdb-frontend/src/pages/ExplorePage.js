import React from 'react';
import Navbar from '../components/Navbar';
import { useStore } from '../store';
import './ExplorePage.css';

const ExplorePage = () => {
    const { searchQuery, activeTab, setActiveTab } = useStore();
    const tabs = ['People', 'Events', 'Surname', 'Surname Events', 'Churches', 'Places', 'Sources'];

    return (
        <div className="explore-container">
            <Navbar />
            <div className="explore-header">
                <h1>Search Results {searchQuery && `for "${searchQuery}"`}</h1>
            </div>

            <div className="explore-content">
                {/* Left Column: Filters */}
                <aside className="filters-sidebar">
                    <h3>Filters</h3>
                    <div className="filter-group">
                        <label>Origin Category</label>
                        <select><option>All</option><option>Higher</option></select>
                    </div>
                    <div className="filter-group">
                        <label>Year Range</label>
                        <input type="range" min="1670" max="1925" />
                    </div>
                </aside>

                {/* Right Column: Data & Tabs */}
                <main className="results-area">
                    <div className="tabs">
                        {tabs.map(tab => (
                            <button
                                key={tab}
                                className={activeTab === tab.toLowerCase() ? 'active' : ''}
                                onClick={() => setActiveTab(tab.toLowerCase())}
                            >
                                {tab}
                            </button>
                        ))}
                    </div>

                    <div className="data-table-container">
                        <p>Total records found: 1,245</p>
                        <table className="data-table">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name/Detail</th>
                                <th>Location</th>
                                <th>Year</th>
                            </tr>
                            </thead>
                            <tbody>
                            {/* Map your flattened DTOs here */}
                            <tr><td>1</td><td>John Doe</td><td>Apoikia</td><td>1850</td></tr>
                            </tbody>
                        </table>

                        {/* Pagination: 30 records per page as requested */}
                        <div className="pagination">
                            <button disabled>&lt;</button>
                            <button className="active">1</button>
                            <button>2</button>
                            <button>3</button>
                            <span>...</span>
                            <button>9</button>
                            <button>&gt;</button>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    );
};

export default ExplorePage;