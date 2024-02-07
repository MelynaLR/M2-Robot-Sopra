import React from 'react';

function Header({ project_id, handleRefresh }) {
  return (
    <div style={styles.header}>
      <h1 style={styles.heading}>User, voici votre profil d'agilité sur Jira</h1>
      <select name="thelist" onChange={(e) => console.log(e.target.value)}>
        <option>Projet {project_id}</option>
      </select>
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