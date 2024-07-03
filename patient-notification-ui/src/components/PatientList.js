import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './PatientList.css';

function PatientList() {
  const [patients, setPatients] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    const response = await axios.get('http://localhost:8080/api/patient/all');
    setPatients(response.data);
  };

  const handleSearch = async () => {
    const response = await axios.get(`/api/patients?search=${searchTerm}`);
    setPatients(response.data);
  };

  const handleEdit = (id) => {
    console.log(`Edit patient with id: ${id}`);
  };

  const handleDelete = (id) => {
    console.log(`Delete patient with id: ${id}`);
  };

  return (
    <div className="container">
      <h1>Patient List</h1>
      <div className="search-bar">
        <input 
          type="text" 
          value={searchTerm} 
          onChange={(e) => setSearchTerm(e.target.value)} 
          placeholder="Search by name, gender, etc."
        />
        <button onClick={handleSearch}>Search</button>
      </div>
      <table className="patient-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Doğum Tarihi</th>
            <th>Cinsiyet</th>
            <th>Bildirim Tercihi</th>
            <th>İletişim Bilgileri</th>
            <th>Kimlik Bilgileri</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {patients.map(patient => (
            <tr key={patient.id}>
              <td>{patient.names.join(' ')} {patient.surname}</td>
              <td>{patient.birthdate}</td>
              <td>{patient.gender}</td>
              <td>{patient.notificationPreference}</td>
              <td>
                {patient.contacts.map(contact => (
                  <p key={contact.id}>{contact.type}: {contact.value}</p>
                ))}
              </td>
              <td>
                {patient.identifiers.map(identifier => (
                  <p key={identifier.id}>{identifier.type}: {identifier.value}</p>
                ))}
              </td>
              <td>
                <button className="edit-btn" onClick={() => handleEdit(patient.id)}>Edit</button>
                <button className="delete-btn" onClick={() => handleDelete(patient.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PatientList;
