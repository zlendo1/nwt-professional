import React, { useState, useEffect, useRef } from 'react';
import { Box, TextField, Button, List, ListItem, ListItemText, Paper } from '@mui/material';
import axios from 'axios';
import auth from '../../api/auth'; // Corrected import position
import { useAuth } from '../../contexts/AuthContext'; // Corrected import position

const ChatPage = () => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const { user } = useAuth();
  const messagesEndRef = useRef(null);

  // Check if auth and auth.authHeader exist before calling it
  const getAuthHeader = () => {
    if (auth && typeof auth.authHeader === 'function') {
      return auth.authHeader();
    }
    // Return an empty object or throw an error if authHeader is not available,
    // depending on how you want to handle this case.
    // For now, returning an empty object to avoid breaking the app.
    console.warn("auth.authHeader() is not available. API calls might fail.");
    return {};
  };

  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await axios.get('http://localhost:10001/api/communication/messages', {
          headers: getAuthHeader()
        });
        setMessages(response.data);
      } catch (error) {
        console.error('Error fetching messages:', error);
      }
    };
    fetchMessages();

    // Setup WebSocket or polling for real-time updates
    const interval = setInterval(fetchMessages, 5000);
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const handleSend = async () => {
    if (!newMessage.trim()) return;

    try {
      await axios.post('http://localhost:10001/api/communication/messages', {
        content: newMessage,
        senderId: user.id
      }, { headers: getAuthHeader() }); // Corrected call

      setNewMessage('');
    } catch (error) {
      console.error('Error sending message:', error);
    }
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', height: '80vh' }}>
      <Paper sx={{ flexGrow: 1, overflow: 'auto', mb: 2 }}>
        <List>
          {messages.map((message, index) => (
            <ListItem key={index}>
              <ListItemText
                primary={message.content}
                secondary={`${message.senderName} - ${new Date(message.timestamp).toLocaleString()}`}
              />
            </ListItem>
          ))}
          <div ref={messagesEndRef} />
        </List>
      </Paper>
      <Box sx={{ display: 'flex' }}>
        <TextField
          fullWidth
          variant="outlined"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && handleSend()}
        />
        <Button variant="contained" onClick={handleSend} sx={{ ml: 2 }}>
          Send
        </Button>
      </Box>
    </Box>
  );
};

export default ChatPage;