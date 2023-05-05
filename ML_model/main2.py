from datetime import datetime

import cv2
import matplotlib.pyplot as plt
import psycopg2
from flask import Flask, jsonify

# Define the Flask app
app = Flask(__name__)

# Connect to PostgreSQL database
conn = psycopg2.connect(database="postgres", user="postgres", password="12345", host="localhost", port="5432")
cur = conn.cursor()


# Define the face detection model
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

landmark_detector = cv2.face.createFacemarkLBF()

landmark_detector.loadModel("lbfmodel.yml")



# Define the HTTP GET method
# Define the HTTP GET method
@app.route('/face_detection/<userId>', methods=['POST'])
def face_detection(userId):
    # Start the camera
    cap = cv2.VideoCapture(0)

    # Loop over each frame from the camera
    while True:
        # Read the frame
        ret, frame = cap.read()

        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        # Detect faces in the frame
        faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30),
                                              flags=cv2.CASCADE_SCALE_IMAGE)

        # For each face, detect landmarks and check if it's not straight
        for (x, y, w, h) in faces:
            # Crop the face region
            face = gray[y:y + h, x:x + w]

            # Detect landmarks
            _, landmarks = landmark_detector.fit(gray, faces)
            eye_left = landmarks[0][0][0][0]
            eye_right = landmarks[0][0][1][0]
            mouth_top = landmarks[0][0][2][1]
            mouth_bottom = landmarks[0][0][3][1]
            eye_dist = abs(eye_left - eye_right)
            mouth_dist = abs(mouth_top - mouth_bottom)
            percent_not_straight = ((eye_dist / w) + (mouth_dist / h)) / 2.0 * 100.0

            frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

            plt.imshow(frame)
            plt.show(block=False)
            plt.pause(0.01)
            plt.clf()


            # Check if the face is not straight
            if percent_not_straight > 10.0:
                # Insert the percentage into the database
                cur.execute("INSERT INTO face_recognition.attention (date, user, angle) VALUES (%s)", (
                    datetime.now(), userId, percent_not_straight))
                conn.commit()

        # Press 'q' to quit
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break


    # Release the camera and close the window
    cap.release()
    cv2.destroyAllWindows()

    return jsonify({'message': 'Face detection completed successfully.'})


# Run the Flask app
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)

