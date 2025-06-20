import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ProfilePage.css';
import { useAuth } from '../../contexts/AuthContext';
import auth from '../../api/auth';

const ProfilePage = () => {
  const { user } = useAuth();
  const [profile, setProfile] = useState({
    firstName: '',
    lastName: '',
    email: '',
    dateOfBirth: '',
    imgUrl: '',
    role: '',
    password: ''
  });

  const [loading, setLoading] = useState(true);
  console.log(user.id);
  useEffect(() => {
    axios
      .get("http://localhost:10001/api/user/${user.id}", {
        headers: auth.authHeader()
      })
      .then((res) => {
        const data = res.data;
        setProfile({
          firstName: data.firstName || '',
          lastName: data.lastName || '',
          email: data.email || '',
          dateOfBirth: data.dateOfBirth || '',
          imgUrl: data.imgUrl || '',
          role: data.role || '',
          password: ''
        });
        setLoading(false);
      })
      .catch((err) => {
        console.error('Greška pri dohvaćanju profila:', err);
        setLoading(false);
      });
  }, []);

  const handleChange = (e) => {
    setProfile({ ...profile, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    const payload = { ...profile };
    if (!payload.password) delete payload.password;

    axios
      .put('http://localhost:10001/api/user/me', payload, {
        headers: auth.authHeader()
      })
      .then(() => alert('Profil uspješno ažuriran!'))
      .catch((err) =>
        alert('Greška pri spremanju izmjena: ' + err?.response?.data?.message || err.message)
      );
  };

  if (loading) return <div className="profile-page">Učitavanje profila...</div>;

  return (
    <div className="profile-page">
      <div className="profile-card box-dark">
        <h2>Moj Profil</h2>

        <div className="profile-picture">
          <img
            src={profile.imgUrl || '/assets/default-profile.png'}
            alt="Profile"
          />
        </div>

        <div className="form-group">
          <label>Avatar URL</label>
          <input
            type="text"
            name="imgUrl"
            value={profile.imgUrl}
            onChange={handleChange}
            placeholder="https://example.com/avatar.jpg"
          />
        </div>

        <div className="form-group">
          <label>Ime</label>
          <input
            type="text"
            name="firstName"
            value={profile.firstName}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Prezime</label>
          <input
            type="text"
            name="lastName"
            value={profile.lastName}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Email (nepromjenjiv)</label>
          <input type="email" value={profile.email} readOnly />
        </div>

        <div className="form-group">
          <label>Datum rođenja</label>
          <input
            type="date"
            name="dateOfBirth"
            value={profile.dateOfBirth}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Uloga</label>
          <input type="text" value={profile.role} readOnly />
        </div>

        <div className="form-group">
          <label>Nova lozinka (opcionalno)</label>
          <input
            type="password"
            name="password"
            value={profile.password}
            onChange={handleChange}
          />
        </div>

        <button onClick={handleSubmit}>Spasi izmjene</button>
      </div>
    </div>
  );
};

export default ProfilePage;
