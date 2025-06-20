import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import './RegisterPage.css';

const RegisterPage = () => {
  const [userData, setUserData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState('');
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validacija: sva polja su obavezna
    for (const field in userData) {
      if (!userData[field]) {
        setError('Please fill out all fields.');
        return;
      }
    }

    // Validacija: lozinke moraju biti identične
    if (userData.password !== userData.confirmPassword) {
      setError('Passwords do not match.');
      return;
    }

    try {
      await register(userData);
      navigate('/login');
    } catch (err) {
      setError('Registration failed. Please check your details.');
    }
  };

  return (
    <div className="register-container">
      <div className="slogan-container">
        <div className="slogan-content">
          <h1>Professional</h1>
          <h1>Digital City</h1>
          <p>Create your path to opportunity</p>
        </div>
      </div>

      <div className="register-box">
        <h2>Register</h2>
        {error && <div className="error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="First Name"
            value={userData.firstName}
            onChange={(e) => setUserData({ ...userData, firstName: e.target.value })}
            required
          />
          <input
            type="text"
            placeholder="Last Name"
            value={userData.lastName}
            onChange={(e) => setUserData({ ...userData, lastName: e.target.value })}
            required
          />
          <input
            type="email"
            placeholder="Email"
            value={userData.email}
            onChange={(e) => setUserData({ ...userData, email: e.target.value })}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={userData.password}
            onChange={(e) => setUserData({ ...userData, password: e.target.value })}
            required
          />
          <input
            type="password"
            placeholder="Confirm Password"
            value={userData.confirmPassword}
            onChange={(e) => setUserData({ ...userData, confirmPassword: e.target.value })}
            required
          />
          <button type="submit">Register</button>
          <p className="register-link">
            Already have an account? <a href="/login">Log in!</a>
          </p>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;
