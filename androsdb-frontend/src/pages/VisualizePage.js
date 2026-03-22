import React, { useEffect } from 'react';
import Navbar from '../components/Navbar';
import { useStore } from '../store';
import { MapContainer, TileLayer } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import './VisualizePage.css';

const VisualizePage = () => {
    const { timelineYear, setTimelineYear } = useStore();

    useEffect(() => {
        setTimelineYear(1670); // Forces the slider to your minimum value on load
    }, [setTimelineYear]);

    const androsBounds = [
        [37.69, 24.68], // Bottom-Left corner
        [38.00, 25.10]  // Top-Right corner
    ];

    return (
        <div className="visualize-container">
            <Navbar />
            <main className="map-area">
                <MapContainer
                    bounds={androsBounds}
                    maxBounds={androsBounds}
                    maxBoundsViscosity={1.0}
                    style={{ height: '100%', width: '100%' }}
                >
                    {/* Clean Base Layer: Esri Satellite Imagery Only */}
                    <TileLayer
                        url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
                        attribution='Tiles &copy; Esri &mdash; Source: Esri, i-cubed, USDA, USGS, AEX, GeoEye, Getmapping, Aerogrid, IGN, IGP, UPR-EGP, and the GIS User Community'
                    />
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