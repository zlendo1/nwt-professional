import React, { useState, useEffect, createContext, useContext, useMemo } from 'react';
import { configureStore, createSlice } from '@reduxjs/toolkit';
import { Provider, useSelector, useDispatch } from 'react-redux';
import axios from 'axios';

// =================================================================================
// 0. MOCK DATA (za UI prikaz dok se ne poveže pravi backend)
// U stvarnoj aplikaciji, ovi podaci dolaze iz vaših mikroservisa.
// =================================================================================

const mockUser = {
  id: '1',
  name: 'Ana Anić',
  email: 'ana.anic@example.com',
  title: 'Software Developer @ TechCorp',
  avatarUrl: 'https://placehold.co/100x100/E2E8F0/4A5568?text=AA',
};

const mockJobs = [
  { id: 'job1', title: 'Senior Frontend Developer', company: 'InnovateTech', location: 'Sarajevo', description: 'Tražimo iskusnog frontend developera...'},
  { id: 'job2', title: 'Java Spring Boot Developer', company: 'LogicSoft', location: 'Remote', description: 'Rad na razvoju enterprise rješenja...'},
  { id: 'job3', title: 'UX/UI Dizajner', company: 'CreativeMinds', location: 'Mostar', description: 'Pridružite se našem kreativnom timu...'},
];

const mockPosts = [
    { id: 'post1', author: { name: 'Marko Marković', avatarUrl: 'https://placehold.co/50x50/CBD5E0/4A5568?text=MM' }, content: 'Upravo sam završio certifikaciju za AWS! #aws #cloud #devops', timestamp: 'prije 2 sata'},
    { id: 'post2', author: { name: 'Jelena Jelić', avatarUrl: 'https://placehold.co/50x50/A3BFFA/4A5568?text=JJ' }, content: 'Naš tim traži nove članove! Ako ste strastveni oko Jave, javite se! #posao #java #hiring', timestamp: 'prije 1 dan'},
];

const mockTest = {
    id: 'test1',
    jobTitle: 'Senior Frontend Developer',
    questions: [
        { id: 'q1', text: 'Što je React hook?', options: ['Funkcija', 'Objekt', 'Klasa', 'Promise'] },
        { id: 'q2', text: 'Koja metoda se koristi za renderiranje liste u Reactu?', options: ['.map()', '.filter()', '.reduce()', '.forEach()'] }
    ]
};

const mockConversations = [
    { id: 'conv1', withUser: { id: '2', name: 'Petar Petrović', avatarUrl: 'https://placehold.co/50x50/FBBF24/4A5568?text=PP' }, lastMessage: 'Hvala na informacijama!', timestamp: '10:45' },
    { id: 'conv2', withUser: { id: '3', name: 'Ivana Ivić', avatarUrl: 'https://placehold.co/50x50/9AE6B4/4A5568?text=II' }, lastMessage: 'Može, čujemo se sutra.', timestamp: 'jučer' },
];

const mockMessages = {
    'conv1': [
        { id: 'm1', senderId: '2', text: 'Pozdrav Ana, vidio sam tvoj profil i zainteresiran sam za suradnju.' },
        { id: 'm2', senderId: '1', text: 'Pozdrav Petre! Drago mi je čuti. O čemu se radi?' },
        { id: 'm3', senderId: '2', text: 'Radimo na jednom zanimljivom projektu u financijskom sektoru. Treba nam iskusan React developer.' },
        { id: 'm4', senderId: '1', text: 'Zvuči zanimljivo! Možeš li mi poslati više detalja?' },
        { id: 'm5', senderId: '2', text: 'Naravno, šaljem link na opis projekta.' },
        { id: 'm6', senderId: '1', text: 'Hvala na informacijama!' },
    ]
};


// =================================================================================
// 1. API CONFIG & SERVICES
const API_GATEWAY_URL = 'http://localhost:10001';

const apiClient = axios.create({
    baseURL: API_GATEWAY_URL, // Svi zahtjevi idu preko Gatewaya
});
apiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
// Centralno mjesto za konfiguraciju i komunikaciju sa Spring Boot mikroservisima.
// =================================================================================

// 📁 src/api/apiConfig.js
const API_BASE_URLS = {
    JOB_SERVICE: 'http://localhost:8081/api/jobs',
    CONTENT_SERVICE: 'http://localhost:8082/api/content',
    TEST_SKILLS_SERVICE: 'http://localhost:8083/api/tests',
    MESSAGE_SERVICE: 'http://localhost:8084/api/messages',
    AUTH_SERVICE: 'http://localhost:8080/api/auth', // Pretpostavljeni auth servis
    SOCKET_URL: 'http://localhost:8084'
};

// Funkcija za kreiranje Axios instance sa presretačem za autorizaciju
const createApiClient = (baseURL) => {
    const apiClient = axios.create({ baseURL });

    apiClient.interceptors.request.use((config) => {
        const token = localStorage.getItem('authToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    });

    return apiClient;
};

// Inicijalizacija klijenata za svaki mikroservis
const authApiClient = createApiClient(API_BASE_URLS.AUTH_SERVICE);
const jobApiClient = createApiClient(API_BASE_URLS.JOB_SERVICE);
const contentApiClient = createApiClient(API_BASE_URLS.CONTENT_SERVICE);
const testSkillsApiClient = createApiClient(API_BASE_URLS.TEST_SKILLS_SERVICE);
const messageApiClient = createApiClient(API_BASE_URLS.MESSAGE_SERVICE);


// 📁 src/api/authService.js
export const authService = {
    login: (credentials) => authApiClient.post('/login', credentials),
    register: (userData) => authApiClient.post('/register', userData),
    getProfile: () => authApiClient.get('/profile'), // Pretpostavljeni endpoint
};

// 📁 src/api/jobService.js
export const jobService = {
    getJobs: () => jobApiClient.get('/'),
    getJobById: (id) => jobApiClient.get(`/${id}`),
    applyToJob: (id, applicationData) => jobApiClient.post(`/${id}/apply`, applicationData),
};


// ... Slično za contentService, testSkillsService, messageService ...


// =================================================================================
// 2. REDUX - Upravljanje stanjem
// Koristimo Redux Toolkit za upravljanje stanjem autentifikacije i korisnika.
// =================================================================================

// 📁 src/redux/authSlice.js
const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: null,
    token: localStorage.getItem('authToken') || null,
    isAuthenticated: !!localStorage.getItem('authToken'),
    status: 'idle', // 'idle' | 'loading' | 'succeeded' | 'failed'
  },
  reducers: {
    setCredentials(state, action) {
      const { user, token } = action.payload;
      state.user = user;
      state.token = token;
      state.isAuthenticated = true;
      localStorage.setItem('authToken', token);
    },
    logout(state) {
      state.user = null;
      state.token = null;
      state.isAuthenticated = false;
      localStorage.removeItem('authToken');
    },
    setStatus(state, action) {
        state.status = action.payload;
    }
  },
});

export const { setCredentials, logout, setStatus } = authSlice.actions;

// 📁 src/redux/store.js
const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
  },
});


// =================================================================================
// 3. CONTEXT & HOOKS
// Kontekst za navigaciju i custom hooks za lakši pristup podacima.
// =================================================================================

// Context za upravljanje navigacijom (zamjena za react-router-dom)
const NavigationContext = createContext();

const NavigationProvider = ({ children }) => {
    const [page, setPage] = useState('login');
    const [params, setParams] = useState({});

    const navigate = (path, newParams = {}) => {
        setPage(path);
        setParams(newParams);
    };

    const value = { page, params, navigate };

    return (
        <NavigationContext.Provider value={value}>
            {children}
        </NavigationContext.Provider>
    );
};

export const useNavigation = () => useContext(NavigationContext);

// Custom hook za lakši pristup auth podacima
export const useAuth = () => useSelector((state) => state.auth);


// =================================================================================
// 4. COMMON COMPONENTS - Reusable UI elementi
// =================================================================================

// 📁 src/components/common/Button.js
const Button = ({ children, onClick, type = 'button', variant = 'primary', className = '' }) => {
    const baseStyle = "px-4 py-2 rounded-lg font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 transition-colors duration-200";
    const variants = {
        primary: "bg-blue-600 text-white hover:bg-blue-700 focus:ring-blue-500",
        secondary: "bg-gray-200 text-gray-800 hover:bg-gray-300 focus:ring-gray-400",
    };
    return (
        <button type={type} onClick={onClick} className={`${baseStyle} ${variants[variant]} ${className}`}>
            {children}
        </button>
    );
};

// 📁 src/components/common/Card.js
const Card = ({ children, className = '' }) => (
    <div className={`bg-white rounded-lg shadow-md p-6 ${className}`}>
        {children}
    </div>
);

// 📁 src/components/common/Spinner.js
const Spinner = () => (
    <div className="flex justify-center items-center h-full">
        <div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-blue-500"></div>
    </div>
);


// =================================================================================
// 5. LAYOUT COMPONENTS
// =================================================================================

// 📁 src/components/layout/Navbar.js
const Navbar = () => {
    const { isAuthenticated, user } = useAuth();
    const dispatch = useDispatch();
    const { navigate } = useNavigation();

    if (!isAuthenticated) return null;

    return (
        <header className="bg-white shadow-md sticky top-0 z-50">
            <nav className="container mx-auto px-6 py-3 flex justify-between items-center">
                <div className="text-2xl font-bold text-blue-600 cursor-pointer" onClick={() => navigate('feed')}>
                    JobNet
                </div>
                <div className="hidden md:flex items-center space-x-6">
                    <NavItem icon={<HomeIcon />} text="Početna" onClick={() => navigate('feed')} />
                    <NavItem icon={<BriefcaseIcon />} text="Poslovi" onClick={() => navigate('jobs')} />
                    <NavItem icon={<MessageIcon />} text="Poruke" onClick={() => navigate('messages')} />
                    {/* Add more nav items here */}
                </div>
                <div className="flex items-center space-x-4">
                    <div className="flex items-center space-x-2 cursor-pointer" onClick={() => navigate('profile', { userId: user.id })}>
                        <img src={user?.avatarUrl || mockUser.avatarUrl} alt="User" className="w-10 h-10 rounded-full" />
                        <span className="hidden sm:block font-semibold">{user?.name || mockUser.name}</span>
                    </div>
                     <Button onClick={() => dispatch(logout())} variant="secondary" className="text-sm">Odjava</Button>
                </div>
            </nav>
        </header>
    );
};

const NavItem = ({ icon, text, onClick }) => (
    <button onClick={onClick} className="flex flex-col items-center text-gray-600 hover:text-blue-600 transition-colors">
        {icon}
        <span className="text-xs font-medium">{text}</span>
    </button>
);


// 📁 src/components/layout/PrivateRoute.js
const PrivateRoute = ({ children }) => {
    const { isAuthenticated } = useAuth();
    const { navigate } = useNavigation();

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('login');
        }
    }, [isAuthenticated, navigate]);

    return isAuthenticated ? children : <Spinner />;
};


// =================================================================================
// 6. FEATURE PAGES
// =================================================================================

// 📁 src/pages/LoginPage.js
const LoginPage = () => {
    const [email, setEmail] = useState('ana.anic@example.com');
    const [password, setPassword] = useState('password123');
    const dispatch = useDispatch();
    const { navigate } = useNavigation();

    const handleSubmit = async (e) => {
        e.preventDefault();
        // U stvarnoj aplikaciji, ovdje ide poziv ka authService.login
        // Za sada, simuliramo uspješnu prijavu
        console.log("Pokušaj prijave sa:", { email, password });
        dispatch(setStatus('loading'));
        setTimeout(() => {
            dispatch(setCredentials({ user: mockUser, token: 'fake-jwt-token' }));
            dispatch(setStatus('succeeded'));
            navigate('feed');
        }, 1000);
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <Card className="w-full max-w-md">
                <h1 className="text-3xl font-bold text-center mb-6 text-gray-800">Dobrodošli na JobNet</h1>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-700 mb-2" htmlFor="email">Email</label>
                        <input type="email" id="email" value={email} onChange={e => setEmail(e.target.value)} className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" required />
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-700 mb-2" htmlFor="password">Lozinka</label>
                        <input type="password" id="password" value={password} onChange={e => setPassword(e.target.value)} className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" required />
                    </div>
                    <Button type="submit" className="w-full">Prijavi se</Button>
                </form>
            </Card>
        </div>
    );
};

// 📁 src/pages/FeedPage.js
const FeedPage = () => {
    // const [posts, setPosts] = useState([]);
    // useEffect(() => {
    //     contentService.getPosts().then(res => setPosts(res.data));
    // }, []);

    const [posts] = useState(mockPosts);

    return (
        <div className="container mx-auto mt-8 grid grid-cols-1 md:grid-cols-3 gap-8">
            {/* Left sidebar - User Profile */}
            <aside className="md:col-span-1">
                 <Card>
                    <div className="flex flex-col items-center">
                       <img src={mockUser.avatarUrl} alt="User" className="w-24 h-24 rounded-full mb-4" />
                       <h2 className="text-xl font-bold">{mockUser.name}</h2>
                       <p className="text-gray-600">{mockUser.title}</p>
                    </div>
                 </Card>
            </aside>

            {/* Main content - Feed */}
            <main className="md:col-span-2 space-y-6">
                <CreatePost />
                {posts.map(post => <Post key={post.id} post={post} />)}
            </main>
        </div>
    );
};

// 📁 src/components/feed/CreatePost.js
const CreatePost = () => (
    <Card>
        <div className="flex items-start space-x-4">
            <img src={mockUser.avatarUrl} alt="User" className="w-12 h-12 rounded-full" />
            <textarea className="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" placeholder={`Što vam je na umu, ${mockUser.name.split(' ')[0]}?`}></textarea>
        </div>
        <div className="flex justify-end mt-4">
            <Button>Objavi</Button>
        </div>
    </Card>
);

// 📁 src/components/feed/Post.js
const Post = ({ post }) => (
    <Card>
        <div className="flex items-center mb-4">
            <img src={post.author.avatarUrl} alt={post.author.name} className="w-12 h-12 rounded-full mr-4"/>
            <div>
                <p className="font-bold">{post.author.name}</p>
                <p className="text-sm text-gray-500">{post.timestamp}</p>
            </div>
        </div>
        <p className="text-gray-800">{post.content}</p>
    </Card>
);

// 📁 src/pages/JobsListPage.js
const JobsListPage = () => {
    // const [jobs, setJobs] = useState([]);
    // useEffect(() => {
    //     jobService.getJobs().then(res => setJobs(res.data));
    // }, []);
    const [jobs] = useState(mockJobs);
    const { navigate } = useNavigation();

    return (
        <div className="container mx-auto mt-8">
            <h1 className="text-3xl font-bold mb-6">Otvoreni Poslovi</h1>
            <div className="space-y-4">
                {jobs.map(job => (
                    <Card key={job.id} className="cursor-pointer hover:shadow-lg transition-shadow" onClick={() => navigate('job-details', { jobId: job.id })}>
                        <h2 className="text-xl font-bold text-blue-600">{job.title}</h2>
                        <p className="font-semibold">{job.company}</p>
                        <p className="text-gray-600">{job.location}</p>
                    </Card>
                ))}
            </div>
        </div>
    );
};

// 📁 src/pages/JobDetailsPage.js
const JobDetailsPage = () => {
    const { params } = useNavigation();
    const [job, setJob] = useState(null);
    const { navigate } = useNavigation();

    useEffect(() => {
        // jobService.getJobById(params.jobId).then(res => setJob(res.data));
        setJob(mockJobs.find(j => j.id === params.jobId));
    }, [params.jobId]);

    if (!job) return <Spinner />;

    return (
        <div className="container mx-auto mt-8">
            <Card>
                <h1 className="text-3xl font-bold">{job.title}</h1>
                <h2 className="text-xl font-semibold text-gray-700 mb-4">{job.company} - {job.location}</h2>
                <p className="text-gray-800 whitespace-pre-wrap">{job.description}</p>
                <div className="mt-6 flex space-x-4">
                    <Button>Prijavi se</Button>
                    <Button variant="secondary" onClick={() => navigate('take-test', { testId: 'test1' })}>Riješi Test</Button>
                </div>
            </Card>
        </div>
    );
};

// 📁 src/pages/TakeTestPage.js
const TakeTestPage = () => {
    const [test, setTest] = useState(mockTest);
    const [answers, setAnswers] = useState({});

    const handleAnswerChange = (questionId, answer) => {
        setAnswers(prev => ({ ...prev, [questionId]: answer }));
    };

    const handleSubmit = () => {
        console.log("Predani odgovori:", answers);
        alert("Test je predan!");
    };

    return (
        <div className="container mx-auto mt-8">
            <Card>
                <h1 className="text-2xl font-bold mb-4">Test za poziciju: {test.jobTitle}</h1>
                <div className="space-y-6">
                    {test.questions.map((q, index) => (
                        <div key={q.id}>
                            <p className="font-semibold mb-2">{index + 1}. {q.text}</p>
                            <div className="space-y-1">
                                {q.options.map(opt => (
                                    <label key={opt} className="flex items-center">
                                        <input type="radio" name={q.id} value={opt} onChange={(e) => handleAnswerChange(q.id, e.target.value)} className="mr-2" />
                                        {opt}
                                    </label>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>
                <div className="mt-8 text-right">
                    <Button onClick={handleSubmit}>Predaj Test</Button>
                </div>
            </Card>
        </div>
    );
};

// 📁 src/pages/MessagesPage.js
const MessagesPage = () => {
    const [conversations] = useState(mockConversations);
    const [selectedConv, setSelectedConv] = useState(mockConversations[0]);
    const [messages, setMessages] = useState(mockMessages[selectedConv.id]);
    const [newMessage, setNewMessage] = useState('');

    const handleSelectConv = (conv) => {
        setSelectedConv(conv);
        setMessages(mockMessages[conv.id] || []);
    };

    const handleSendMessage = (e) => {
        e.preventDefault();
        if(!newMessage.trim()) return;
        const msg = { id: `m${messages.length + 1}`, senderId: mockUser.id, text: newMessage };
        setMessages([...messages, msg]);
        setNewMessage('');
        // U pravoj aplikaciji: socket.emit('send_message', msgData);
    };

    return (
        <div className="container mx-auto mt-8 h-[calc(100vh-120px)] flex">
            <Card className="w-1/3 mr-4 flex-shrink-0 overflow-y-auto">
                <h2 className="text-xl font-bold mb-4">Poruke</h2>
                {conversations.map(conv => (
                    <div key={conv.id} onClick={() => handleSelectConv(conv)} className={`p-3 rounded-lg cursor-pointer mb-2 ${selectedConv.id === conv.id ? 'bg-blue-100' : 'hover:bg-gray-100'}`}>
                        <div className="flex items-center">
                            <img src={conv.withUser.avatarUrl} alt={conv.withUser.name} className="w-12 h-12 rounded-full mr-3" />
                            <div>
                               <p className="font-bold">{conv.withUser.name}</p>
                               <p className="text-sm text-gray-600 truncate">{conv.lastMessage}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </Card>
            <Card className="w-2/3 flex flex-col">
                {selectedConv ? (
                    <>
                        <div className="border-b pb-3 mb-4">
                            <h2 className="text-xl font-bold">{selectedConv.withUser.name}</h2>
                        </div>
                        <div className="flex-grow overflow-y-auto pr-4 space-y-4">
                            {messages.map(msg => (
                                <div key={msg.id} className={`flex items-end ${msg.senderId === mockUser.id ? 'justify-end' : 'justify-start'}`}>
                                    <div className={`max-w-xs lg:max-w-md px-4 py-2 rounded-xl ${msg.senderId === mockUser.id ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-800'}`}>
                                        <p>{msg.text}</p>
                                    </div>
                                </div>
                            ))}
                        </div>
                        <form onSubmit={handleSendMessage} className="mt-4 flex">
                            <input
                                type="text"
                                value={newMessage}
                                onChange={(e) => setNewMessage(e.target.value)}
                                placeholder="Napišite poruku..."
                                className="w-full px-3 py-2 border rounded-l-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                            <Button type="submit" className="rounded-l-none">Pošalji</Button>
                        </form>
                    </>
                ) : <div className="flex items-center justify-center h-full"><p>Odaberite razgovor.</p></div>}
            </Card>
        </div>
    );
};

// 📁 src/pages/ProfilePage.js
const ProfilePage = () => {
    // const { params } = useNavigation();
    // const [profile, setProfile] = useState(null);
    // useEffect(() => {
    //     authService.getProfile(params.userId).then(res => setProfile(res.data));
    // }, [params.userId])
    const [profile] = useState(mockUser);

    if(!profile) return <Spinner />

    return (
        <div className="container mx-auto mt-8">
            <Card>
                <div className="flex flex-col items-center sm:flex-row sm:items-start">
                    <img src={profile.avatarUrl} alt={profile.name} className="w-32 h-32 rounded-full mb-4 sm:mb-0 sm:mr-8" />
                    <div>
                        <h1 className="text-3xl font-bold">{profile.name}</h1>
                        <p className="text-xl text-gray-700">{profile.title}</p>
                        <p className="text-gray-500 mt-2">Sarajevo, BiH</p>
                        <Button className="mt-4">Poveži se</Button>
                    </div>
                </div>
            </Card>
        </div>
    );
};

// =================================================================================
// 7. MAIN APP COMPONENT
// =================================================================================

// 📁 src/App.js
function AppContent() {
    const { page } = useNavigation();
    const { isAuthenticated } = useAuth();

    // Ruter Logika
    const CurrentPage = useMemo(() => {
        if (!isAuthenticated) return LoginPage;

        switch (page) {
            case 'feed': return FeedPage;
            case 'jobs': return JobsListPage;
            case 'job-details': return JobDetailsPage;
            case 'take-test': return TakeTestPage;
            case 'messages': return MessagesPage;
            case 'profile': return ProfilePage;
            default: return FeedPage;
        }
    }, [page, isAuthenticated]);

    const MainLayout = ({ children }) => (
        <div className="bg-gray-50 min-h-screen">
          <Navbar />
          <main>
              <PrivateRoute>
                  {children}
              </PrivateRoute>
          </main>
        </div>
    );

    if(!isAuthenticated) {
        return <LoginPage />;
    }

    return (
        <MainLayout>
            <CurrentPage />
        </MainLayout>
    );
}

// Glavni eksport
export default function App() {
  return (
    <Provider store={store}>
        <NavigationProvider>
            <AppContent />
        </NavigationProvider>
    </Provider>
  );
}


// =================================================================================
// 8. ICONS (SVG)
// =================================================================================

const HomeIcon = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" /></svg>;
const BriefcaseIcon = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" /></svg>;
const MessageIcon = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" /></svg>;
