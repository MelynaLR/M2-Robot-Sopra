import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

function App() {
  const [agilityScore, setAgilityScore] = useState(null);
  const [user, setUser] = useState(null);
  const [globalScore, setGlobalScore] = useState(null);
  const [error, setError] = useState(null);
  const project_id = 13;

  useEffect(() => {
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
        setError('Error fetching data. Please try again later.');
      });
  }, []);
  
  useEffect(() => {
    const apiUrl = 'http://localhost:8080/globalScore';
  
    axios.get(apiUrl)
      .then(response => {
        console.log('Data from API:', response.data);
        const { globalScore } = response.data;
        setGlobalScore(globalScore);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
        setError('Error fetching data. Please try again later.');
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
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {globalScore !== null && (
        <>
          <h1 style={styles.heading}>{user}, voici votre profil d'agilité sur Jira</h1>
          <select name="thelist" onChange="combo(this, 'theinput')">
              <option>Projet {project_id}</option>
            </select>
          <div style={{ ...styles.gaugeContainer, backgroundColor: getGaugeColor(agilityScore) }}>
             
            <p style={styles.agilityScore}>Global Score: {globalScore}</p>
          </div>
          <h2 style={styles.sprintProgress}>Règle 1: Cas des tickets non résoluts à la fin d'un Sprint  </h2>
          <p style={styles.sprintProgress}>Score de la règle 1 : {agilityScore}</p>
          <p>Conseil : </p>
          <p>Tickets concernés : </p>
          <h2 style={styles.sprintProgress}>Règle 2: Cas des tickets avec des story points trop élevés</h2>
          <p style={styles.sprintProgress}>Score de la règle 2 : {agilityScore}</p>
          <p>Conseil : </p>
          <p>Tickets concernés : </p>
        </>
      )}
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
    color: '#ffffff', //blanc
    fontSize: '18px',
    margin: '0',
  },
};

ReactDOM.createRoot(document.getElementById('root')).render(<App />);
