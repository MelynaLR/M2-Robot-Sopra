import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './index.css'; // Import CSS file
import Header from './Header.js';
import Gauge from './Gauge.js';
import RuleCard from './RuleCard.js';
import loadingGif from './Spinner-2.gif';

function App() {
	const [agilityScore, setAgilityScore] = useState(null);
    const [user, setUser] = useState(null);
    const [globalScore, setGlobalScore] = useState(null);
    const [scoreComplexity, setScoreComplexity] = useState(null);
    const [error, setError] = useState(null);
    const [project_id, setProjectId] = useState(13);
    const [rules, setRules] = useState(null);
    const [isOpen, setIsOpen] = useState(false);
    const [dropdownStates, setDropdownStates] = useState({});
    const [chatGPTData, setChatGPTData] = useState(null);
    const [isLoadingChatGPT, setIsLoadingChatGPT] = useState(false);
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
            }
        };

        fetchData();
    }, []);

    const toggleDataVisibility = () => {
        setShowData(!showData);
    };
	const fetchChatGPTData = async () => {
        try {
            setIsLoadingChatGPT(true);
            const chatGPTResponse = await axios.get('http://localhost:8080/chatgpt');
            setChatGPTData(chatGPTResponse.data);
            setIsLoadingChatGPT(false);
            setShowData(true);
        } catch (error) {
            console.error('Error fetching ChatGPT data:', error);
            setError('Error fetching ChatGPT data. Please try again later.');
            setIsLoadingChatGPT(false);
        }
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
    
           
            <div className="app-container">
                <h2>Votre conseil personnalisé réalisé par ChatGPT</h2>
                <button onClick={fetchChatGPTData}>
                    Lancer ChatGPT
                </button>
                <div className="chatGPT-container">
                    {isLoadingChatGPT && (
                        <div className="loading-container">
                            <img src={loadingGif} alt="Loading..." style={{ width: '50px', height: '50px' }} />
                        </div>
                    )}
                    {!isLoadingChatGPT && showData && chatGPTData && (
                        <button className="show-data-button" onClick={toggleDataVisibility}>
                            {showData ? <span>&#9660;</span> : <span>&#9654;</span>}
                        </button>
                    )}
                    {!isLoadingChatGPT && showData && chatGPTData && chatGPTData.split(/\d+\./).filter(item => item.trim().length > 0).map((item, index) => (
                        <p key={index}>{item.trim()}</p>
                    ))}
                    {/* <TextField id="standard-basic" label="Standard" variant="standard" /> */}
                </div>
            </div>
			</div>
       
    );
}

export default App;
