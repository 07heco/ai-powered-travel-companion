import { useNavigate, useLocation } from 'react-router-dom';
import { Home, Map, Calendar, User, PenSquare } from 'lucide-react';

export default function BottomNavigation() {
  const navigate = useNavigate();
  const location = useLocation();

  const navItems = [
    { icon: Home, label: '首页', path: '/' },
    { icon: Map, label: '目的地', path: '/destinations' },
    { icon: Calendar, label: '行程', path: '/trip-planner' },
    { icon: User, label: '我的', path: '/profile' },
  ];

  return (
    <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 z-50">
      <div className="flex justify-around items-center h-16 max-w-md mx-auto">
        {navItems.map((item) => {
          const Icon = item.icon;
          const isActive = location.pathname === item.path;
          return (
            <button
              key={item.path}
              onClick={() => navigate(item.path)}
              className={`flex flex-col items-center justify-center flex-1 h-full transition-colors ${
                isActive ? 'text-teal-600' : 'text-gray-400'
              }`}
            >
              <Icon size={24} strokeWidth={isActive ? 2.5 : 2} />
              <span className="text-xs mt-1">{item.label}</span>
            </button>
          );
        })}
      </div>
      
      {/* 浮动发布按钮 */}
      <button
        onClick={() => navigate('/publish-note')}
        className="absolute bottom-20 right-6 w-14 h-14 bg-gradient-to-r from-orange-400 to-pink-500 rounded-full shadow-lg flex items-center justify-center text-white hover:scale-110 transition-transform"
      >
        <PenSquare size={24} />
      </button>
    </div>
  );
}
