import React from 'react';

function Gauge({ globalScore }) {
  const gaugeColor = getGaugeColor(globalScore);

  return (
    <div className="gauge-container" style={{backgroundColor: gaugeColor}}>
      <div className="gauge-white">
        <p className="gauge-percentage">{globalScore} %</p>
      </div>
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

  return interpolateColor(score, minScore, maxScore);
};

export default Gauge;