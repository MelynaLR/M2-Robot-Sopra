import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './index.css'; // Import CSS file
import Header from './Header.js';
import RuleCard from './RuleCard.js';
import loadingGif from './Spinner-2.gif';

function App() {
    const [agilityScore, setAgilityScore] = useState(null);
    const [user, setUser] = useState(null);
    const [globalScore, setGlobalScore] = useState(null);
    const [scoreComplexity, setScoreComplexity] = useState(null);
    const [error, setError] = useState(null);
    const [projects, setProjects] = useState(null);
    const [project_id, setProjectId] = useState(13);
    const [rules, setRules] = useState(null);
    const [isOpen, setIsOpen] = useState(false);
    const [dropdownStates, setDropdownStates] = useState({});
    const [chatGPTData, setChatGPTData] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [isLoadingChatGPT, setIsLoadingChatGPT] = useState(false);
    const [showData, setShowData] = useState(false);

    const toggleDataVisibility = () => {
        setShowData(!showData);
    };


    const fetchProjects = async () => {
        try {
            setProjects(null);
            const apiUrl = 'http://localhost:8080/retrieveProjects';
            const response = await axios.get(apiUrl);
            console.log('Projects from API:', response.data);
            setProjects(response.data);
            console.log(projects);
        } catch (error) {
            console.error('Error fetching projects:', error);
            setError('Error fetching data. Please try again later.');
        }
    };

    const fetchGlobalScore = async () => {
        try {
            setGlobalScore(null);
            const apiUrl = `http://localhost:8080/globalScore/idProject/${project_id}`;
            const response = await axios.get(apiUrl);
            console.log('Data from API (Global Score):', response.data);
            setGlobalScore(response.data);
        } catch (error) {
            console.error('Error fetching global score:', error);
            setError('Error fetching data. Please try again later.');
        }
    };

    const fetchChatGPTData = async () => {
        try {
            setIsLoadingChatGPT(true);
            const chatGPTResponse = await axios.get(`http://localhost:8080/chatgpt/idProject/${project_id}`);
            setChatGPTData(chatGPTResponse.data);
            setIsLoadingChatGPT(false);
            console.log('Data from ChatGPT:', chatGPTResponse.data);
            setShowData(true);
        } catch (error) {
            console.error('Error fetching chatGPT data:', error);
            setError('Error fetching data. Please try again later.');
            setIsLoadingChatGPT(false);
        }
    };

    const fetchRules = async () => {
        try {
            setRules(null);
            const apiUrl = `http://localhost:8080/retrieveRules/idProject/${project_id}`;
            const response = await axios.get(apiUrl);
            console.log('Calculation Rules from API (Rules):', response.data);
            setRules(response.data);
        } catch (error) {
            console.error('Error fetching rules:', error);
            setError('Error fetching data. Please try again later.');
        }
    };

    const sendWeightToBackend = async (newWeight, ruleIndex, descriptionRule) => {
        try {
            setRules(null);
            setGlobalScore(null);
            const description = descriptionRule;
            const idProject = project_id;
            const apiUrl = `http://localhost:8080/changeWeight/${description}/newWeight/${newWeight}/idProject/${idProject}`;
            const response = await axios.get(apiUrl);
            if (Array.isArray(response.data) && response.data.length > 0) {
                resetRules();
                setGlobalScore(response.data[response.data.length - 1].score);
                setRules(response.data.slice(0, -1));
                console.log(response.data.slice(0, -1));
            } else {
                console.error('Invalid response data:', response.data);
            }
            handleWeightChange(newWeight, ruleIndex);
            console.log('Weight update successful:', response.data);
        } catch (error) {
            console.error('Error updating weight:', error);
        }
    };

    const resetRules = () => {
        setRules(null);
        setGlobalScore(null);
    };
	  	
   	useEffect(() => {
		setIsLoading(true);	
		fetchProjects();
		setRules(null);
    	fetchRules();
    	fetchGlobalScore();
    	fetchChatGPTData();
    	
  	},[]);
			  
	
	
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

    const handleWeightChange = (newWeight, ruleIndex) => {
        setRules((prevRules) => {
            const updatedRules = [...prevRules];
            updatedRules[ruleIndex] = { ...updatedRules[ruleIndex], weight: newWeight };
            return updatedRules;
        });
    };

    const onProjectChange = (idProject) => {
        setProjectId(idProject);
        fetchGlobalScore();
        fetchRules();
    };

    const toggleDropdown = (ruleIndex) => {
        setDropdownStates((prevState) => ({
            ...prevState,
            [ruleIndex]: !prevState[ruleIndex],
        }));
    };

    useEffect(() => {
        setIsLoading(true);
        fetchProjects();
        setRules(null);
        fetchRules();
        fetchGlobalScore();
        // fetchChatGPTData(); 
    }, []);

    return (
        <div className='main-container'>
            <Header project={projects} handleRefresh={handleRefresh} onProjectChange={onProjectChange} globalScore={globalScore}/>
            {globalScore !== null && (
                <>
                    {rules && rules.map((rule, index) => (
                        <RuleCard
                            key={index}
                            rule={rule}
                            index={index}
                            handleDropdownToggle={() => toggleDropdown(index)}
                            sendWeightToBackend={(newWeight) => sendWeightToBackend(newWeight, index,rule.description)}
                            isOpen={dropdownStates[index]}
                            
                        />
                    ))}
                </>
            )}

		<div className="app-container">
		    <h2>Votre conseil personnalisé réalisé par ChatGPT</h2>
		    {isLoadingChatGPT && (
		        <div className="loading-container">
		            <img src={loadingGif} alt="Loading..." style={{ width: '50px', height: '50px' }} />
		        </div>
		    )}
		
		    {!isLoadingChatGPT && chatGPTData && (
		        <div className="chatGPT-container">
		            <button className="show-data-button" onClick={toggleDataVisibility}>
		                {showData ? <span>&#9660;</span> : <span>&#9654;</span>}
		            </button>
		            {showData && chatGPTData.split(/\d+\./).filter(item => item.trim().length > 0).map((item, index) => (
		                <p key={index}>{item.trim()}</p>
		            ))}
		        </div>
		    )}
		
		    {!isLoadingChatGPT && !chatGPTData && (
		        <button onClick={fetchChatGPTData}>
		            Lancer ChatGPT
		        </button>
		    )}
		</div>


        </div>
    );
}

export default App;