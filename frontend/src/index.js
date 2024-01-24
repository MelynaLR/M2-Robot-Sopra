import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

function App() {
  const [agilityScore, setAgilityScore] = useState(null);
  const [user, setUser] = useState(null);

  useEffect(() => {
    // const apiUrl = 'http://192.168.56.1:3000/static/api/data';
    const apiUrl = 'http://localhost:8080/static/api/data';

    axios.get(apiUrl)
      .then(response => {
        console.log('Data from API:', response.data);
        const [agilityScore, user] = response.data;
        setAgilityScore(agilityScore);
        setUser(user);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []);

  const getGaugeColor = (score) => {
    if (score >= 75) {
      return '#4CAF50'; // Green
    } else if (score >= 50) {
      return '#FFC107'; // Yellow
    } else {
      return '#FF5733'; // Red
    }
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.heading}>{user}, voici votre profil d'agilité sur Jira</h1>
      <div style={{ ...styles.gaugeContainer, backgroundColor: getGaugeColor(agilityScore) }}>
        <p style={styles.agilityScore}>Score d'agilité : {agilityScore}</p>
      </div>
      <h2 style={styles.sprintProgress}>Taux d'avancement du sprint : </h2>
    </div>
  );
}

const styles = {
  container: {
    fontFamily: 'Arial, sans-serif',
    maxWidth: '600px',
    margin: '20px auto',
    padding: '20px',
    backgroundColor: '#f5f5f5',
    borderRadius: '8px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
  },
  heading: {
    color: '#172b4d',
    fontSize: '24px',
    marginBottom: '15px',
  },
  gaugeContainer: {
    padding: '15px',
    borderRadius: '5px',
  },
  agilityScore: {
    color: '#ffffff', // White text on colored background
    fontSize: '18px',
    margin: '0',
  },
};

ReactDOM.createRoot(document.getElementById('root')).render(<App />);
