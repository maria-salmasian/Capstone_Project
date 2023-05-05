import React, { useState } from 'react';
import axios from 'axios';

function FaceDetection() {
    const [userId, setUserId] = useState("");
    const [status, setStatus] = useState("");

    const startFaceDetection = () => {
        setStatus("Starting face detection...");
        axios.post(`http://localhost:5001//face_detection/${userId}`)
            .then((response) => {
                setStatus(response.data.message);
            })
            .catch((error) => {
                console.log(error);
                setStatus("Error during face detection.");
            });
    }

    return (
        <div>
            <input type="text" placeholder="Enter user ID" value={userId} onChange={(e) => setUserId(e.target.value)} />
            <button onClick={startFaceDetection}>Start facial detection</button>
            <p>{status}</p>
        </div>
    );
}

export default FaceDetection;
