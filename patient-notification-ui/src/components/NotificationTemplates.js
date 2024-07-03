import React, { useState, useEffect } from 'react';
import axios from 'axios';

function PatientList() {
  const [patients, setPatients] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    const response = await axios.get('/api/patients');
    setPatients(response.data);
  };

  const handleSearch = async () => {
    const response = await axios.get(`/api/patients?search=${searchTerm}`);
    setPatients(response.data);
  };

  return (
    <div>
      <h1>Patient List</h1>
      <input 
        type="text" 
        value={searchTerm} 
        onChange={(e) => setSearchTerm(e.target.value)} 
        placeholder="Search by name, gender, etc."
      />
      <button onClick={handleSearch}>Search</button>
      <ul>
        {patients.map(patient => (
          <li key={patient.id}>{patient.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default PatientList;
