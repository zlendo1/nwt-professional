// src/routes.js
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';
import ProfilePage from './pages/ProfilePage';
import ChatPage from './pages/ChatPage';
import JobsPage from './pages/JobsPage';
import JobDetailsPage from './pages/JobDetailsPage';
import QuizPage from './pages/QuizPage';
import CreateJobPage from './pages/CreateJobPage'; // Nova stranica
import CreateQuizPage from './pages/CreateQuizPage'; // Nova stranica
import PrivateRoute from './components/Common/PrivateRoute';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/auth" element={<AuthPage />} />
      <Route
        path="/"
        element={
          <PrivateRoute>
            <HomePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/profile/:userId?"
        element={
          <PrivateRoute>
            <ProfilePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/chat"
        element={
          <PrivateRoute>
            <ChatPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/jobs"
        element={
          <PrivateRoute>
            <JobsPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/jobs/create"
        element={
          <PrivateRoute>
            <CreateJobPage /> {/* Ruta za kreiranje posla */}
          </PrivateRoute>
        }
      />
      <Route
        path="/jobs/:jobId"
        element={
          <PrivateRoute>
            <JobDetailsPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/jobs/:jobId/quiz"
        element={
          <PrivateRoute>
            <QuizPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/quizzes/create"
        element={
          <PrivateRoute>
            <CreateQuizPage /> {/* Ruta za kreiranje kviza (može biti vezana za posao ili opšta) */}
          </PrivateRoute>
        }
      />
      {/* Dodaj ostale rute po potrebi (npr. /notifications, /company-page) */}
      <Route path="*" element={<h1>404 - Stranica nije pronađena</h1>} />
    </Routes>
  );
};

export default AppRoutes;