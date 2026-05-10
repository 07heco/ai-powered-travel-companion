import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import SplashPage from './pages/SplashPage';
import LoginPage from './pages/LoginPage';
import HomePage from './pages/HomePage';
import DestinationListPage from './pages/DestinationListPage';
import DestinationDetailPage from './pages/DestinationDetailPage';
import TripPlannerPage from './pages/TripPlannerPage';
import TripEditorPage from './pages/TripEditorPage';
import FlightBookingPage from './pages/FlightBookingPage';
import HotelBookingPage from './pages/HotelBookingPage';
import TripInProgressPage from './pages/TripInProgressPage';
import PublishNotePage from './pages/PublishNotePage';
import ProfilePage from './pages/ProfilePage';
import OrderDetailPage from './pages/OrderDetailPage';
import DealsPage from './pages/DealsPage';
import NearbyServicesPage from './pages/NearbyServicesPage';
import TravelGuideDetailPage from './pages/TravelGuideDetailPage';
import MessageCenterPage from './pages/MessageCenterPage';
import WalletPage from './pages/WalletPage';
import AiPlannerPage from './pages/AiPlannerPage';
import ArNavigationPage from './pages/ArNavigationPage';
import SmartBudgetPage from './pages/SmartBudgetPage';
import TravelMatesPage from './pages/TravelMatesPage';

export default function App() {
  const [showSplash, setShowSplash] = useState(true);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // 模拟启动页加载
    const timer = setTimeout(() => {
      setShowSplash(false);
    }, 2000);

    // 检查是否已登录
    const loggedIn = localStorage.getItem('isLoggedIn') === 'true';
    setIsLoggedIn(loggedIn);

    return () => clearTimeout(timer);
  }, []);

  if (showSplash) {
    return <SplashPage />;
  }

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage onLogin={() => setIsLoggedIn(true)} />} />
        <Route 
          path="/" 
          element={isLoggedIn ? <HomePage /> : <Navigate to="/login" replace />} 
        />
        <Route path="/destinations" element={<DestinationListPage />} />
        <Route path="/destination/:id" element={<DestinationDetailPage />} />
        <Route path="/trip-planner" element={<TripPlannerPage />} />
        <Route path="/trip-editor/:id" element={<TripEditorPage />} />
        <Route path="/flight-booking" element={<FlightBookingPage />} />
        <Route path="/hotel-booking" element={<HotelBookingPage />} />
        <Route path="/trip-in-progress" element={<TripInProgressPage />} />
        <Route path="/publish-note" element={<PublishNotePage />} />
        <Route path="/profile" element={<ProfilePage onLogout={() => setIsLoggedIn(false)} />} />
        <Route path="/order/:id" element={<OrderDetailPage />} />
        <Route path="/deals" element={<DealsPage />} />
        <Route path="/nearby" element={<NearbyServicesPage />} />
        <Route path="/guide/:id" element={<TravelGuideDetailPage />} />
        <Route path="/messages" element={<MessageCenterPage />} />
        <Route path="/wallet" element={<WalletPage />} />
        <Route path="/ai-planner" element={<AiPlannerPage />} />
        <Route path="/ar-navigation" element={<ArNavigationPage />} />
        <Route path="/smart-budget" element={<SmartBudgetPage />} />
        <Route path="/travel-mates" element={<TravelMatesPage />} />
      </Routes>
    </Router>
  );
}