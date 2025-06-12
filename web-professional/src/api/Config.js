// Pretpostavljeni portovi za vaše mikroservise
const API_BASE_URLS = {
    JOB_SERVICE: 'http://localhost:8082/api/jobs',
    CONTENT_SERVICE: 'http://localhost:8083/api/content',
    TEST_SKILLS_SERVICE: 'http://localhost:8081/api/tests',
    MESSAGE_SERVICE: 'http://localhost:8084/api/messages',
    // Socket.IO server za chat
    SOCKET_URL: 'http://localhost:8084'
};

export default API_BASE_URLS;