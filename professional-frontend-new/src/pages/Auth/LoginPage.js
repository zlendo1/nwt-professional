import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import './LoginPage.css';

const LoginPage = () => {
  const [userData, setUserData] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

const handleSubmit = async (e) => {
  e.preventDefault();

  if (!userData.email || !userData.password) {
    setError('Please enter both email and password.');
    return;
  }

  try {
    await login(userData); // koristi objekat direktno
    navigate('/dashboard');
  } catch (err) {
    setError('Login failed. Please check your credentials.');
  }
};


  return (
    <div className="login-container">
      <div className="slogan-container">
        <div className="slogan-content">
          <h1>Professional</h1>
          <h1>Digital City</h1>
          <p>Create your path to opportunity</p>
        </div>
      </div>

      <div className="login-box">
        <h2>Login</h2>
        {error && <div className="error">{error}</div>}
        <form onSubmit={handleSubmit}>
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
          <button type="submit">Login</button>
          <p className="register-link">
            Don't have an account? <a href="/register">Register!</a>
          </p>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
