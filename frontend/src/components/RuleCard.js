import React from 'react';
import NumberSelector from './NumberSelector.js';
var newIntLimitTicketsTooHeavy = 0;
function RuleCard({ rule, index, handleDropdownToggle, sendWeightToBackend, isOpen, intLimitTicketsTooHeavy }) {
  	return (
    	<div className='card-container'>
      		<div className="card">
        		<div className='description-container'>
          			<button onClick={() => handleDropdownToggle(index)} className="issues-button">
            			{isOpen ? (<span>&#9660;</span>) : (<span>&#9654;</span>)}
          			</button>
          		<div className='description'> {rule.description} </div>
          		<div className="progress-bar" style={{'--progress': `${rule.score}%`}}></div>
          		<div className='percentage'> {rule.score} % </div>
        	</div>
	        <div>
		      	{isOpen && (
				  	<div>
			        	<NumberSelector
			          		ruleWeight={rule.weight}
			          		onChange={(newWeight) => sendWeightToBackend(newWeight, index,rule.description)}
			          		ruleIndex={index}
			        	/>
			        	{rule.description === 'Tickets avec un nombre de story points trop élevés' && (
							 <div className = 'saisieInt'>
					        <label>Saisir un entier :</label>
					        <input
					          type="number"				          
					          onChange={(e) => intLimitTicketsTooHeavy =parseInt(e.target.value)}
					        />
					      </div>)}
			        	<div className='advice-container'>
			        		<div className='text'>
			        			Conseil :
			        		</div>
			        		<div className='advice'>
			        			{rule.manualAdvice}
			        		</div>
			        	</div>				     
			        	<div className='issues-container'>
			        		<div className='tickets-concernes'> {rule.issues.length} tickets concernés </div>
			          		{rule.issues.map(issue => (
			            		<div key={issue.id} className="issues">
									<div className='id'>ID {issue.id}</div>
									<div className='description'>{issue.description}</div>
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