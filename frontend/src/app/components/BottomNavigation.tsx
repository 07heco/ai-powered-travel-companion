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
    <div className="fixed bottom-0 left-0 right-0 z-50">
      <div className="mx-auto w-full max-w-md px-4 pb-4">
        <div className="relative rounded-[28px] border border-white/70 bg-white/88 shadow-[0_18px_50px_rgba(15,23,42,0.16)] backdrop-blur-xl">
          <div className="flex justify-around items-center h-[72px]">
            {navItems.map((item) => {
              const Icon = item.icon;
              const isActive = location.pathname === item.path;
              return (
                <button
                  key={item.path}
                  onClick={() => navigate(item.path)}
                  className={`flex flex-col items-center justify-center flex-1 h-full transition-all ${
                    isActive ? 'text-teal-600' : 'text-slate-400'
                  }`}
                >
                  <div className={`flex h-10 w-10 items-center justify-center rounded-2xl transition-all ${isActive ? 'bg-teal-50 shadow-sm' : ''}`}>
                    <Icon size={22} strokeWidth={isActive ? 2.6 : 2} />
                  </div>
                  <span className="mt-1 text-[11px] font-medium">{item.label}</span>
                </button>
              );
            })}
          </div>

          <button
            onClick={() => navigate('/publish-note')}
            className="absolute -top-7 right-5 flex h-14 w-14 items-center justify-center rounded-[22px] bg-gradient-to-br from-orange-400 via-pink-500 to-fuchsia-500 text-white shadow-[0_16px_32px_rgba(236,72,153,0.35)] transition-transform hover:scale-105"
          >
            <PenSquare size={22} />
          </button>
        </div>
      </div>
    </div>
  );
}
