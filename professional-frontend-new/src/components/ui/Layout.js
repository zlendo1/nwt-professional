import React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import './Layout.css';

const Layout = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <div className="layout-container">
      <header className="layout-header">
        <div className="header-left" onClick={() => navigate('/')}>
          <img src="/assets/logo.png" alt="Logo" className="header-logo" />
          <span className="header-title">Digital City</span>
        </div>

        {user && (
          <nav className="header-nav">
            <button onClick={() => navigate('/dashboard')}>Dashboard</button>
            <button onClick={() => navigate('/jobs')}>Jobs</button>
            <button onClick={() => navigate('/profile')}>Profile</button>
            <button onClick={logout}>Logout</button>
          </nav>
        )}
      </header>

      <main className="layout-main">
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;
