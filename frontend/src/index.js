import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import './index.css';
import loadingGif from './Spinner-2.gif';

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
  const [chatGPTData, setChatGPTData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [showData, setShowData] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const apiUrlGlobalScore = 'http://localhost:8080/globalScore';
        const responseGlobalScore = await axios.get(apiUrlGlobalScore);
        setGlobalScore(responseGlobalScore.data);

        const apiUrlRules = 'http://localhost:8080/retrieveRules';
        const responseRules = await axios.get(apiUrlRules);
        setRules(responseRules.data);

        const chatGPTResponse = await axios.get('http://localhost:8080/chatgpt');
        setChatGPTData(chatGPTResponse.data);
        setIsLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setError('Error fetching data. Please try again later.');
      }
    };

    fetchData();
  }, []);
  function formatChatGPTData(data) {
    if (!data) return null;

    return data.split(/\d+\./).filter(item => item.trim().length > 0).map((item, index) => (
      <p key={index}>{item.trim()}</p>
    ));
  }


  function RenderChatGPTData() {
    const [showData, setShowData] = useState(false);
  
    const toggleDataVisibility = () => {
      setShowData(!showData);
    };
  
    return (
      <div className="chatGPT-container">
        {!isLoading && (
          <h2>
            ChatGPT Data{' '}
            <button className="show-data-button" onClick={toggleDataVisibility}>
              {showData ? <span>&#9660;</span> : <span>&#9654;</span>}
            </button>
          </h2>
        )}
        {isLoading ? (
          <div className="loading-container">
            <img src={loadingGif} alt="Loading..." style={{ width: '50px', height: '50px' }} />
          </div>
        ) : (
          <div>
            <p>{chatGPTData}</p>
          </div>
        )}
      </div>
    );
  }
  

  const LoadingLogo = () => {
    return (
      <div className="loading-container">
        <div className="loading-logo">
          <img src={loadingGif} alt="Loading..." style={{ width: '50px', height: '50px' }} />
        </div>
      </div>
    );
  };

  const DropdownIssues = ({ rule, isOpen }) => {
    return (
      <div className="dropdown">
        {isOpen && (
          <div className="dropdown-content">
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

  const handleRefresh = () => {
    axios.get('http://localhost:8080')
      .then(response => {
        console.log('Refreshed data:', response.data);
        window.location.reload();
      })
      .catch(error => {
        console.error('Error refreshing data:', error);
        setError('Error refreshing data. Please try again later.');
      });
  };

  const getGaugeColor = (score) => {
    const minScore = 0;
    const maxScore = 100;

    const interpolateColor = (value, min, max) => {
      const normalizedValue = (value - min) / (max - min);
      const red = Math.round(255 * (1 - normalizedValue));
      const green = Math.round(255 * normalizedValue);
      const blue = 0;
      return `rgb(${red}, ${green}, ${blue})`;
    };

    return interpolateColor(score, minScore, maxScore);
  };

  const toggleDropdown = (ruleIndex) => {
    setDropdownStates((prevState) => ({
      ...prevState,
      [ruleIndex]: !prevState[ruleIndex],
    }));
  };

  const handleWeightChange = (newWeight, ruleIndex) => {
    setRules((prevRules) => {
      const updatedRules = [...prevRules];
      updatedRules[ruleIndex] = { ...updatedRules[ruleIndex], weight: newWeight };
      return updatedRules;
    });
  };

  const NumberSelector = ({ ruleWeight, onChange }) => {
    const [selectedNumber, setSelectedNumber] = useState(ruleWeight);

    const handleNumberClick = (number) => {
      setSelectedNumber(number);
      onChange(number);
    };

    useEffect(() => {
      setSelectedNumber(ruleWeight);
    }, [ruleWeight]);
  };

  const toggleDataVisibility = () => {
    setShowData(!showData);
  };

    return (
      <body>
        <div style={styles.container}>
          {error && <p style={{ color: 'darkorange' }}>{error}</p>}
          <h1 style={styles.heading}>User, voici votre profil d'agilité sur Jira</h1>
          <select name="thelist" onChange={(e) => console.log(e.target.value)}>
            <option>Projet {project_id}</option>
          </select>
  
          <div className="app-container">
            <h2>Votre conseil personnalisé réalisé par ChatGPT</h2>
            <div className="chatGPT-container">
              {isLoading ? (
                <div className="loading-container">
                  <img src={loadingGif} alt="Loading..." style={{ width: '50px', height: '50px' }} />
                </div>
              ) : (
                <div>
                  <button className="show-data-button" onClick={toggleDataVisibility}>
                    {showData ? <span>&#9660;</span> : <span>&#9654;</span>}
                  </button>
                  {showData && formatChatGPTData(chatGPTData)}
                </div>
              )}
            </div>
          </div>
  
          {globalScore !== null && (
            <div style={{ ...styles.gaugeContainer, backgroundColor: getGaugeColor(globalScore) }}>
              <p style={styles.agilityScore}>Global Score: {globalScore}</p>
            </div>
          )}
  
          <button type="button" onClick={handleRefresh}>Rafraichir</button>
  
          {rules && rules.map((rule, index) => (
            <div className='card-container' key={index}>
              <div className="card">
                <div className='description-container'>
                  <button onClick={() => toggleDropdown(index)} className="issues-button">
                    {dropdownStates[index] ? (<span>&#9660;</span>) : (<span>&#9654;</span>)}
                  </button>
                  <div className='description'> {rule.description} </div>
                  <div className="progress-bar" style={{'--progress': `${rule.score}%`}}></div>
                </div>
                <p>Weight: {rule.weight}</p>
                <p>Score: {rule.score}</p>
  
                <NumberSelector ruleWeight={rule.weight} onChange={(newWeight) => handleWeightChange(newWeight)} />
  
                <DropdownIssues rule={rule} isOpen={dropdownStates[index]} />
              </div>
            </div>
          ))}
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
