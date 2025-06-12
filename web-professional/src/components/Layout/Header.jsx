// src/components/Layout/Header.jsx
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styles from './Header.module.scss';
import { FaHome, FaBriefcase, FaComments, FaUserCircle, FaBell, FaPlus } from 'react-icons/fa'; // Dodana FaPlus
import { useAuth } from '../../hooks/useAuth';

const Header = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/auth');
  };

  return (
    <header className={styles.header}>
      <div className={styles.leftSection}>
        <Link to="/" className={styles.logo}>
          <FaBriefcase size={24} color="#0a66c2" />
          <span>LinkedApp</span>
        </Link>
        <div className={styles.searchBar}>
          <input type="text" placeholder="Pretraži..." />
        </div>
      </div>
      <nav className={styles.mainNav}>
        <ul>
          <li>
            <Link to="/">
              <FaHome size={20} />
              <span>Početna</span>
            </Link>
          </li>
          <li>
            <Link to="/jobs">
              <FaBriefcase size={20} />
              <span>Poslovi</span>
            </Link>
          </li>
          <li>
            <Link to="/chat">
              <FaComments size={20} />
              <span>Poruke</span>
            </Link>
          </li>
          <li>
            <Link to="/notifications">
              <FaBell size={20} />
              <span>Obavijesti</span>
            </Link>
          </li>
          {/* Dodaj "Kreiraj" gumb kao na LinkedInu */}
          <li>
            <Link to="/create-post"> {/* Može voditi na modal ili stranicu za kreiranje */}
              <FaPlus size={20} />
              <span>Kreiraj</span>
            </Link>
          </li>
          <li className={styles.profileDropdown}>
            {user ? (
              <>
                <Link to="/profile">
                  <FaUserCircle size={20} />
                  <span>Ja</span>
                </Link>
                <div className={styles.dropdownContent}>
                  <Link to="/profile">Prikaži Profil</Link>
                  <button onClick={handleLogout}>Odjavi se</button>
                </div>
              </>
            ) : (
              <Link to="/auth">
                <FaUserCircle size={20} />
                <span>Prijava/Registracija</span>
              </Link>
            )}
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;