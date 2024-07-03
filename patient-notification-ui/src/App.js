import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PatientList from './components/PatientList';
import NotificationTemplates from './components/NotificationTemplates';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<PatientList />} />
          <Route path="/templates" element={<NotificationTemplates />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;