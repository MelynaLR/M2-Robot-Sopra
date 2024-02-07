import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './index.css';
import Header from './Header.js';
import Gauge from './Gauge.js';
import RuleCard from './RuleCard.js';

function App() {
	
	const [agilityScore, setAgilityScore] = useState(null);
	const [user, setUser] = useState(null);
	const [globalScore, setGlobalScore] = useState(null);
	const [scoreComplexity, setScoreComplexity] = useState(null);
	const [error, setError] = useState(null);
	const [projects, setProjects] = useState(null);
	var project_id= 13;
	const [rules, setRules] = useState(null);
	const [isOpen, setIsOpen] = useState(false);
	const [dropdownStates, setDropdownStates] = useState({});


  	useEffect(() => {
    	const fetchData = async () => {
      		try {
				 setGlobalScore(null);
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
				setRules(null);
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
	
	const sendWeightToBackend = async (newWeight, ruleIndex,descriptionRule) => {
	useEffect(() => {
  		const fetchData = async () => {
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
 		fetchData();
	}, []);
	
	
	const sendWeightToBackend = async (newWeight, ruleIndex,descriptionRule) => {
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
      console.log(response.data.slice(0,-1));
    } else {
      console.error('Invalid response data:', response.data);
      
    }
	handleWeightChange(newWeight,ruleIndex);
    console.log('Weight update successful:', response.data);
    
    // Add other logic to handle the response if necessary.
  	} catch (error) {
    console.error('Error updating weight:', error);
    // Handle errors here, for example, by updating an error variable state.
  		}
  	};
  	
  	const resetRules = () => {
 		 setRules(null);
 		 setGlobalScore(null);
	};


  	const handleRefresh = () => {
    	axios.get('http://localhost:8080')
      	.then(response => {
        	console.log('Refreshed data:', response.data);
        	window.location.reload(); // Refresh the page
        	window.location.reload();
        	setTimeout(() => {
       	 	 window.location.reload();
        	 }, 100);
      	})
      	.catch(error => {
        	console.error('Error refreshing data:', error);
        	setError('Error refreshing data. Please try again later.');
      	});
  	};

  	const handleWeightChange = (newWeight, ruleIndex) => {
  		setRules((prevRules) => {
    		const updatedRules = [...prevRules];
    		updatedRules[ruleIndex] = { ...updatedRules[ruleIndex], weight: newWeight };
    		return updatedRules;
  		});	
  		//sendWeightToBackend(newWeight,ruleIndex);	
	};
	
	const toggleDropdown = (ruleIndex) => {
		setDropdownStates((prevState) => ({
			...prevState,
		    [ruleIndex]: !prevState[ruleIndex],
		}));
	};


  	return (
   		<body>
   		<div className='main-container'>
      	coucou {projects?.[0]?.idProject}
      	<Header project={projects} handleRefresh={handleRefresh} />
      	{globalScore !== null && (
        	<>
          	<Gauge globalScore={globalScore} />
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
      	</div>
    	</body>
  	);
}
}
export default App;