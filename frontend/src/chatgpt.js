import React from 'react';

function DropdownIssues({ rule, isOpen }) {
  return (
    <div className="dropdown">
      {isOpen && (
        <div>
          {rule.issues.map(issue => (
            <div key={issue.id} className="issues">
              {issue.description}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default DropdownIssues;
