import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './index.css'; // Import CSS file
import Header from './Header.js';
import Gauge from './Gauge.js';
import RuleCard from './RuleCard.js';
import loadingGif from './Spinner-2.gif';

function App() {
    const [globalScore, setGlobalScore] = useState(null);
    const [rules, setRules] = useState(null);
    const [dropdownStates, setDropdownStates] = useState({});
    const [chatGPTData, setChatGPTData] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [showData, setShowData] = useState(false);
    const project_id = 13;

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
            }
        };

        fetchData();
    }, []);

    const toggleDataVisibility = () => {
        setShowData(!showData);
    };

    const handleRefresh = () => {
        axios.get('http://localhost:8080')
            .then(response => {
                console.log('Refreshed data:', response.data);
                window.location.reload();
            })
            .catch(error => {
                console.error('Error refreshing data:', error);
            });
    };

    const toggleDropdown = (ruleIndex) => {
        setDropdownStates((prevState) => ({
            ...prevState,
            [ruleIndex]: !prevState[ruleIndex],
        }));
    };

    return (
        <div className='main-container'>
            <Header project_id={project_id} handleRefresh={handleRefresh} />
            {globalScore !== null && (
                <>
                    <Gauge globalScore={globalScore} />
                    {rules && rules.map((rule, index) => (
                        <RuleCard
                            key={index}
                            rule={rule}
                            index={index}
                            handleDropdownToggle={() => toggleDropdown(index)}
                            isOpen={dropdownStates[index]}
                        />
                    ))}
                </>
            )}
      <div className="chatGPT-container">
    <h2>ChatGPT Data{' '}
        {isLoading ? (
            <span>
                <img src={loadingGif} alt="Loading..." style={{ width: '50px', height: '50px' }} />
                <span>Loading...</span>
            </span>
        ) : (
            <button className="show-data-button" onClick={toggleDataVisibility}>
                {showData ? <span>&#9660;</span> : <span>&#9654;</span>}
            </button>
        )}
    </h2>
    {!isLoading && showData && chatGPTData && chatGPTData.split(/\d+\./).filter(item => item.trim().length > 0).map((item, index) => (
        <p key={index}>{item.trim()}</p>
    ))}
</div>

        </div>
    );
}

export default App;
