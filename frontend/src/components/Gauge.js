import React from 'react';
//Gauge funtion for the globalScore
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

	const interpolateColor = (value, min, max) => {
	  const normalizedValue = (value - min) / (max - min);
	
	  let red, green, blue;
	
	  if (normalizedValue < 0.5) {
	    // Interpolation from red to yellow
	    red = Math.round(255 * (1 - normalizedValue * 2));
	    green = Math.round(255 * normalizedValue * 2);
	    blue = 0;
	  } else {
	    // Interpolation from yellow to green
	    red = Math.round(255 * (1 - (normalizedValue - 0.5) * 2));
	    green = Math.round(255 * (1 - (normalizedValue - 0.5) * 2));
	    blue = 0;
	  }
	
	  return `rgb(${red}, ${green}, ${blue})`;
	};

  return interpolateColor(score, minScore, maxScore);
};

export default Gauge;