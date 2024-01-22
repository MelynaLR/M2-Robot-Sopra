import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

function App() {
  console.log('Rendering App component');
  const [data, setData] = useState(null);

  useEffect(() => {
    const apiUrl = 'http://192.168.56.1:3000/static/api/data';

    axios.get(apiUrl)
      .then(response => {
        console.log('Data from API:', response.data);
        setData(response.data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []);

  return (
    <div>
      <h1>React App</h1>
      <p>Data from Spring Boot: {data}</p>
    </div>
  );
}

ReactDOM.createRoot(document.getElementById('root')).render(<App />);
