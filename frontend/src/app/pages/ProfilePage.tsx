import { useNavigate } from 'react-router-dom';
import { 
  ArrowLeft, 
  ChevronRight, 
  User, 
  Heart, 
  Calendar, 
  Notebook, 
  Ticket, 
  MessageCircle,
  Settings,
  LogOut,
  CreditCard,
  Bell,
  Shield,
  Wallet,
  Gift
} from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Avatar, AvatarFallback, AvatarImage } from '../components/ui/avatar';
import BottomNavigation from '../components/BottomNavigation';

interface ProfilePageProps {
  onLogout: () => void;
}

export default function ProfilePage({ onLogout }: ProfilePageProps) {
  const navigate = useNavigate();

  const userInfo = {
    name: '旅行爱好者',
    phone: '138****5678',
    avatar: '',
    points: 3560,
    coupons: 12,
    balance: 1280.50,
  };

  const stats = [
    { label: '全部', count: 12, path: '/profile' },
    { label: '待支付', count: 1, path: '/profile' },
    { label: '待出行', count: 2, path: '/trip-planner' },
    { label: '已完成', count: 9, path: '/profile' },
  ];

  const menuItems = [
    {
      icon: Wallet,
      label: '我的钱包',
      path: '/wallet',
      value: `¥${userInfo.balance.toFixed(2)}`,
    },
    {
      icon: Gift,
      label: '优惠专区',
      path: '/deals',
    },
    {
      icon: Calendar,
      label: '我的行程',
      path: '/trip-planner',
      badge: 2,
    },
    {
      icon: Heart,
      label: '收藏夹',
      path: '/destinations',
    },
    {
      icon: Notebook,
      label: '我的笔记',
      path: '/publish-note',
    },
    {
      icon: Ticket,
      label: '优惠券',
      path: '/wallet',
      badge: userInfo.coupons,
    },
    {
      icon: CreditCard,
      label: '积分',
      path: '/wallet',
      value: userInfo.points.toString(),
    },
    {
      icon: MessageCircle,
      label: '消息中心',
      path: '/messages',
      badge: 3,
    },
  ];

  const settingItems = [
    {
      icon: Bell,
      label: '通知设置',
      path: '/profile',
    },
    {
      icon: Shield,
      label: '隐私设置',
      path: '/profile',
    },
    {
      icon: Settings,
      label: '账号安全',
      path: '/profile',
    },
  ];

  const handleLogout = () => {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userPhone');
    onLogout();
    navigate('/login');
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      {/* 个人信息区 */}
      <div className="bg-gradient-to-r from-teal-500 to-blue-500 text-white p-6 pb-8">
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-xl font-bold">个人中心</h1>
          <Button 
            variant="ghost" 
            size="icon"
            className="text-white hover:bg-white/20"
            onClick={() => navigate('/profile')}
          >
            <Settings size={20} />
          </Button>
        </div>

        <div className="flex items-center gap-4">
          <Avatar className="w-16 h-16 border-2 border-white">
            <AvatarImage src={userInfo.avatar} />
            <AvatarFallback className="bg-white/20 text-white text-xl">
              <User size={32} />
            </AvatarFallback>
          </Avatar>
          <div className="flex-1">
            <h2 className="text-xl font-bold mb-1">{userInfo.name}</h2>
            <p className="text-sm opacity-90">{userInfo.phone}</p>
          </div>
          <Button 
            variant="outline" 
            size="sm"
            className="bg-white/20 border-white/30 text-white hover:bg-white/30"
            onClick={() => navigate('/profile')}
          >
            编辑资料
          </Button>
        </div>

        <div className="grid grid-cols-2 gap-4 mt-6">
          <Card className="bg-white/20 backdrop-blur-sm border-white/30 p-4 text-center">
            <p className="text-2xl font-bold">{userInfo.points}</p>
            <p className="text-sm opacity-90">积分</p>
          </Card>
          <Card className="bg-white/20 backdrop-blur-sm border-white/30 p-4 text-center">
            <p className="text-2xl font-bold">{userInfo.coupons}</p>
            <p className="text-sm opacity-90">优惠券</p>
          </Card>
        </div>
      </div>

      {/* 订单中心 */}
      <div className="bg-white px-4 py-4 mb-4">
        <div className="flex items-center justify-between mb-4">
          <h3 className="font-semibold">订单中心</h3>
          <button 
            className="text-sm text-gray-500 flex items-center gap-1"
            onClick={() => navigate('/profile')}
          >
            查看全部
            <ChevronRight size={16} />
          </button>
        </div>
        <div className="grid grid-cols-4 gap-4">
          {stats.map((stat) => (
            <button
              key={stat.label}
              onClick={() => navigate(stat.path)}
              className="flex flex-col items-center gap-2"
            >
              <div className="relative">
                <div className="w-12 h-12 bg-gray-100 rounded-full flex items-center justify-center">
                  <Ticket size={24} className="text-teal-600" />
                </div>
                {stat.count > 0 && (
                  <span className="absolute -top-1 -right-1 w-5 h-5 bg-red-500 text-white text-xs rounded-full flex items-center justify-center">
                    {stat.count}
                  </span>
                )}
              </div>
              <span className="text-xs text-gray-600">{stat.label}</span>
            </button>
          ))}
        </div>
      </div>

      {/* 功能菜单 */}
      <div className="bg-white mb-4">
        {menuItems.map((item, index) => {
          const Icon = item.icon;
          return (
            <button
              key={item.label}
              onClick={() => navigate(item.path)}
              className={`w-full px-4 py-4 flex items-center justify-between hover:bg-gray-50 transition-colors ${
                index !== menuItems.length - 1 ? 'border-b' : ''
              }`}
            >
              <div className="flex items-center gap-3">
                <Icon size={20} className="text-gray-600" />
                <span>{item.label}</span>
              </div>
              <div className="flex items-center gap-2">
                {item.badge && item.badge > 0 && (
                  <span className="px-2 py-0.5 bg-red-500 text-white text-xs rounded-full">
                    {item.badge}
                  </span>
                )}
                {item.value && (
                  <span className="px-2 py-0.5 bg-gray-300 text-gray-600 text-xs rounded-full">
                    {item.value}
                  </span>
                )}
                <ChevronRight size={18} className="text-gray-400" />
              </div>
            </button>
          );
        })}
      </div>

      {/* 设置菜单 */}
      <div className="bg-white mb-4">
        {settingItems.map((item, index) => {
          const Icon = item.icon;
          return (
            <button
              key={item.label}
              onClick={() => navigate(item.path)}
              className={`w-full px-4 py-4 flex items-center justify-between hover:bg-gray-50 transition-colors ${
                index !== settingItems.length - 1 ? 'border-b' : ''
              }`}
            >
              <div className="flex items-center gap-3">
                <Icon size={20} className="text-gray-600" />
                <span>{item.label}</span>
              </div>
              <ChevronRight size={18} className="text-gray-400" />
            </button>
          );
        })}
      </div>

      {/* 退出登录 */}
      <div className="px-4 mb-4">
        <Button
          variant="outline"
          className="w-full"
          onClick={handleLogout}
        >
          <LogOut size={18} className="mr-2" />
          退出登录
        </Button>
      </div>

      <BottomNavigation />
    </div>
  );
}