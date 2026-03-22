import React from 'react';
import Navbar from '../components/Navbar';
import { useStore } from '../store';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import './VisualizePage.css';

const VisualizePage = () => {
    const { timelineYear, setTimelineYear } = useStore();

    return (
        <div className="visualize-container">
            <Navbar />

            <main className="map-area">
                <MapContainer center={[37.8333, 24.9333]} zoom={12} style={{ height: '100%', width: '100%' }}>
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution='&copy; OpenStreetMap contributors'
                    />
                    {/* Example Marker - You will map your Cache payload here based on timelineYear */}
                    <Marker position={[37.8541, 24.9124]}>
                        <Popup>Apoikia Population in {timelineYear}: 150</Popup>
                    </Marker>
                </MapContainer>
            </main>

            <footer className="timeline-controls">
                <div className="slider-container">
                    <label>Year: {timelineYear}</label>
                    <input
                        type="range"
                        min="1670"
                        max="1925"
                        value={timelineYear}
                        onChange={(e) => setTimelineYear(parseInt(e.target.value))}
                        className="year-slider"
                    />
                </div>
            </footer>
        </div>
    );
};

export default VisualizePage;