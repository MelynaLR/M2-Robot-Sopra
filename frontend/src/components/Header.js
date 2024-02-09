import React from 'react';
import Gauge from './Gauge.js';

function Header({ project, handleRefresh, onProjectChange, globalScore }) {
  const handleProjectChange = (e) => {
    const projectId = parseInt(e.target.value, 10);
    console.log(projectId);
    onProjectChange(projectId);
  };

  return (
    <div className='header-container'>
    	<div className='left-part'>
	    	<div className='text'>
		    	Voici votre profil d'agilité sur Jira
		    </div> 
		
	 		<div className='dropdown-container'>
	        	<label htmlFor="projectDropdown">Sélectionner un projet : </label>
	        	<select id="projectDropdown" onChange={handleProjectChange}>
					<option key="-1" value="-1|Global">
		    			Global
		 			</option>
					{project?.map((project) => (
						<option key={project?.idProject} value={`${project?.idProject}|${project?.nameProject}`}>
					      	{`Project ${project?.nameProject}`}
					    </option>
					))}
				</select>
	      	</div>	
		    <button type="button" onClick={handleRefresh} className='number-button'>
		    	Mettre à jour la base de données
		    </button>
	    </div>
	    <div className='gauge-container-header'>
	    	<Gauge globalScore={globalScore} />
	    </div>
    </div>
  );
}


export default Header;