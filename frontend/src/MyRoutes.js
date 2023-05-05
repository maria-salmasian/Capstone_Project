import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import App from './App';
import FaceDetection from './FaceDetection';

function MyRoutes() {
    return (
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>
                            <Link to="/face-detection">Face Detection</Link>
                        </li>
                    </ul>
                </nav>

                <Routes>
                    <Route path="/" element={<App />} />
                    <Route path="/face-detection" element={<FaceDetection />} />
                </Routes>
            </div>
    );
}

export default MyRoutes;
