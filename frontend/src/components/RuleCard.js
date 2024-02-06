import React from 'react';
import NumberSelector from './NumberSelector.js';

function RuleCard({ rule, index, handleDropdownToggle, handleWeightChange, isOpen }) {
  	return (
    	<div className='card-container'>
      		<div className="card">
        		<div className='description-container'>
          			<button onClick={() => handleDropdownToggle(index)} className="issues-button">
            			{isOpen ? (<span>&#9660;</span>) : (<span>&#9654;</span>)}
          			</button>
          		<div className='description'> {rule.description} </div>
          		<div className="progress-bar" style={{'--progress': `${rule.score}%`}}></div>
        	</div>
	        <div>
		      	{isOpen && (
				  	<div>
				  		<p>Score: {rule.score}</p>
			        	<NumberSelector
			          		ruleWeight={rule.weight}
			          		onChange={(newWeight) => handleWeightChange(newWeight, index)}
			          		ruleIndex={index}
			        	/>
			        	<div className='issues-container'>
			        		<div className='tickets-concernes'> Tickets concernés </div>
			          		{rule.issues.map(issue => (
			            		<div key={issue.id} className="issues">
			              			{issue.description}
			            		</div>
			          		))}
			        	</div>
			      	</div>
		      	)}
		    </div>
    	</div>
    </div>
  );
}

export default RuleCard;