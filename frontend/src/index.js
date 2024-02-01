import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

function App() {
  const [agilityScore, setAgilityScore] = useState(null);
  const [user, setUser] = useState(null);
  const [globalScore, setGlobalScore] = useState(null);
  const [scoreComplexity, setScoreComplexity] = useState(null);
  const [error, setError] = useState(null);
  const project_id = 13;
  const [rules, setRules] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const apiUrl = 'http://localhost:8080/static/api/data';
        const response = await axios.get(apiUrl);
        console.log('Data from API (Agility Score):', response.data);
        const [agilityScore, user] = response.data;
        setAgilityScore(agilityScore);
        setUser(user);
      } catch (error) {
        console.error('Error fetching agility score:', error);
        setError('Error fetching data. Please try again later.');
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const apiUrl = 'http://localhost:8080/globalScore';
        const response = await axios.get(apiUrl);
        console.log('Data from API (Global Score):', response.data);
        setGlobalScore(response.data);
      } catch (error) {
        console.error('Error fetching global score:', error);
        setError('Error fetching data. Please try again later.');
      }
    };

    fetchData();
  }, []);
  
  useEffect(() => {
  const fetchData = async () => {
    try {
      const apiUrl = 'http://localhost:8080/retrieveRules';
      const response = await axios.get(apiUrl);
      console.log('Calculation Rules from API (Rules):', response.data);
      setRules(response.data);
    } catch (error) {
      console.error('Error fetching rules:', error);
      setError('Error fetching data. Please try again later.');
    }
  };

  fetchData();
}, []);

  const handleRefresh = () => {
    
    axios.get('http://localhost:8080')
      .then(response => {
        console.log('Refreshed data:', response.data);
        window.location.reload(); // Refresh the page
        window.location.reload();
        // setTimeout(() => {
        //   window.location.reload();
        // }, 100);
      })
      .catch(error => {
        console.error('Error refreshing data:', error);
        setError('Error refreshing data. Please try again later.');
      });
  };
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
          <select name="thelist" onChange={(e) => console.log(e.target.value)}>
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
          <p style={styles.sprintProgress}>Score de la règle 2 : {scoreComplexity}</p>
          <p>Conseil : </p>
          <p>Tickets concernés : </p>
          <button type="button" onClick={handleRefresh}>Rafraichir</button>
          
          <h1 style={styles.heading}> TEST LISTE AVEC RULES </h1>
          {rules && rules.map((rule, index) => (
      		<div key={index}>
        	<p>Weight: {rule.weight}</p>
        	<p>Score: {rule.score}</p>
        	<p>Description: {rule.description}</p>       
      </div>
    ))}
        </>
      )}
    </div>
    
  );
}

const styles = {
  container: {
    fontFamily: 'Arial, sans-serif',
    maxWidth: '800px',
    margin: '20px auto',
    padding: '20px',
    backgroundColor: '#fff',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    border: '1px solid #dfe1e6',
  },
  heading: {
    color: '#172b4d',
    fontSize: '24px',
    marginBottom: '15px',
  },
  gaugeContainer: {
    padding: '15px',
    borderRadius: '5px',
    backgroundColor: '#f4f5f7',
    marginTop: '20px',
  },
  agilityScore: {
    color: '#172b4d',
    fontSize: '20px',
    margin: '0',
  },
  sprintProgress: {
    color: '#172b4d',
    fontSize: '18px',
    margin: '10px 0',
  },
  select: {
    padding: '8px 12px',
    fontSize: '16px',
    marginBottom: '20px',
  },
  button: {
    padding: '10px 20px',
    fontSize: '16px',
    backgroundColor: '#0052cc',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    marginTop: '20px',
  },
};

ReactDOM.createRoot(document.getElementById('root')).render(<App />);
