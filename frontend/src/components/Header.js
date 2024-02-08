import React from 'react';

function Header({ project, handleRefresh, onProjectChange }) {
  const handleProjectChange = (e) => {
    const projectId = parseInt(e.target.value, 10);
    console.log(projectId);
    onProjectChange(projectId);
  };

  return (
    <div style={styles.header}>
      <h1 style={styles.heading}>User, voici votre profil d'agilité sur Jira</h1>
      

       	<div>
        <label htmlFor="projectDropdown">Sélectionner le projet : </label>
        <select
			  id="projectDropdown"
			  onChange={handleProjectChange}
			>
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
      
      
      <button type="button" onClick={handleRefresh} style={styles.refreshButton}>
        Rafraîchir
      </button>
      
    </div>
  );
}

const styles = {
  header: {
    marginBottom: '20px',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  heading: {
    color: '#172b4d',
    fontSize: '24px',
    margin: '0',
  },
  refreshButton: {
    padding: '10px 20px',
    fontSize: '16px',
    backgroundColor: '#0052cc',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
};

export default Header;