import React, { useState } from 'react';
import './DashboardPage.css';

const DashboardPage = () => {
  const [post, setPost] = useState('');
  const [chatUsers] = useState([
    { id: 1, name: 'Aida', online: true },
    { id: 2, name: 'Nedim', online: true },
    { id: 3, name: 'Lamija', online: false }
  ]);

  const handlePost = () => {
    if (post.trim()) {
      console.log('Post:', post);
      setPost('');
    }
  };

  const handleStartChat = (user) => {
    console.log('Start chat with:', user.name);
    // TODO: Open chat modal or redirect
  };

  return (
    <div className="dashboard-body">
      <div className="feed-column">
        <div className="box-light post-box">
          <h2>Create a Post</h2>
          <div className="form-group">
            <textarea
              placeholder="What's on your mind?"
              value={post}
              onChange={(e) => setPost(e.target.value)}
            />
          </div>
          <button onClick={handlePost}>Post</button>
        </div>

        <div className="box-dark feed-section">
          <p className="placeholder">📰 Feed with user posts coming soon...</p>
        </div>
      </div>

      <div className="side-column">
        <div className="box-darker users-panel">
          <h2>Online Users</h2>
          <ul className="user-list">
            {chatUsers
              .filter((u) => u.online)
              .map((user) => (
                <li key={user.id} onClick={() => handleStartChat(user)}>
                  🟢 {user.name}
                </li>
              ))}
            {chatUsers.filter((u) => u.online).length === 0 && (
              <li className="offline-msg">No users currently online.</li>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
