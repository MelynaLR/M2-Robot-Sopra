import React from 'react';

function Gauge({ globalScore }) {
  const gaugeColor = getGaugeColor(globalScore);

  return (
    <div style={{ ...styles.gaugeContainer, backgroundColor: gaugeColor }}>
      <p style={styles.agilityScore}>Global Score: {globalScore}</p>
    </div>
  );
}

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

const styles = {
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
};

export default Gauge;