import React, { useState, useEffect } from 'react';

function NumberSelector({ ruleWeight, onChange, ruleIndex }) {
const [selectedNumber, setSelectedNumber] = useState(ruleWeight);

  const handleNumberClick = (number) => {
    setSelectedNumber(number);
    onChange(number, ruleIndex); // Pass both number and ruleIndex to onChange
  };

  useEffect(() => {
    // Mettre à jour selectedNumber lorsque ruleWeight change
    setSelectedNumber(ruleWeight);
  }, [ruleWeight]);

  return (
    <div>
      <p>Importance de la règle :</p>
      <div className="number-container">
        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((number) => (
          <button
			  key={number}
			  className={`number-button ${selectedNumber === number ? 'selected' : ''}`}
			  onClick={() => handleNumberClick(number)}
		  >
			  {number}
		  </button>
        ))}
      </div>
      {selectedNumber && <p>Vous avez choisi le nombre {selectedNumber}.</p>}
    </div>
  );
}

export default NumberSelector;