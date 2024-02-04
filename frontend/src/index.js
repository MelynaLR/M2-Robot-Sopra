import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import './index.css';


function App() {
  const [agilityScore, setAgilityScore] = useState(null);
  const [user, setUser] = useState(null);
  const [globalScore, setGlobalScore] = useState(null);
  const [scoreComplexity, setScoreComplexity] = useState(null);
  const [error, setError] = useState(null);
  const project_id = 13;
  const [rules, setRules] = useState(null);
  const [isOpen, setIsOpen] = useState(false);
  const [dropdownStates, setDropdownStates] = useState({});



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
  const minScore = 0;
  const maxScore = 100;

  // Interpolation linéaire pour obtenir une couleur entre rouge et vert
  const interpolateColor = (value, min, max) => {
    const normalizedValue = (value - min) / (max - min);
    const red = Math.round(255 * (1 - normalizedValue));
    const green = Math.round(255 * normalizedValue);
    const blue = 0;
    return `rgb(${red}, ${green}, ${blue})`;
  };

  // Appliquer l'interpolation pour obtenir la couleur en fonction du score
  return interpolateColor(score, minScore, maxScore);
};
  

const toggleDropdown = (ruleIndex) => {
  setDropdownStates((prevState) => ({
    ...prevState,
    [ruleIndex]: !prevState[ruleIndex],
  }));
};
  
const DropdownIssues = ({ rule, isOpen }) => {
  return (
    <div className="dropdown">
      {isOpen && (
        <div>
          {rule.issues.map(issue => (
            <div key={issue.id} className="issues">
              {issue.description}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};



  return (
	<body>
	    <div style={styles.container}>
	   		{error && <p style={{ color: 'darkorange' }}>{error}</p>}
	    	{globalScore !== null && (
	    	<>
	        	<h1 style={styles.heading}>User, voici votre profil d'agilité sur Jira</h1>
	          	<select name="thelist" onChange={(e) => console.log(e.target.value)}>
	            	<option>Projet {project_id}</option>
	        	</select>
	        	<div style={{ ...styles.gaugeContainer, backgroundColor: getGaugeColor(globalScore) }}>
	          		<p style={styles.agilityScore}>Global Score: {globalScore}</p>
	        	</div>
	
	          	<button type="button" onClick={handleRefresh}>Rafraichir</button>
	          
	
	          	{rules && rules.map((rule, index) => (
				  	<div className='card-container'>
					  	<div key={index} className="card">
					  		<div className='description-container'>
						  		<button onClick={() => toggleDropdown(index)} className="issues-button">
						          	{dropdownStates[index] ? (<span>&#9660;</span>) : (<span>&#9654;</span>)}
						        </button>
					  			<div className='description'> {rule.description} </div>
					  			<div className="progress-bar" style={{'--progress': `${rule.score}%`}}></div>
					  		</div>
			        		<p>Weight: {rule.weight}</p>
			        		<p>Score: {rule.score}</p>
			        		<p>Description: {rule.description}</p> 
			        		
			        		 <DropdownIssues rule={rule} isOpen={dropdownStates[index]} />
						</div>
					</div>
			    ))}
	        </>
	      )}
	    </div>
    </body>
    
  );
}

const styles = {
  container: {
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
