import { useNavigate } from 'react-router-dom';
import {
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
  Gift,
  Crown,
} from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Avatar, AvatarFallback, AvatarImage } from '../components/ui/avatar';
import BottomNavigation from '../components/BottomNavigation';
import MobileShell from '../MobileShell';

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
    balance: 1280.5,
  };

  const stats = [
    { label: '全部', count: 12, path: '/profile' },
    { label: '待支付', count: 1, path: '/profile' },
    { label: '待出行', count: 2, path: '/trip-planner' },
    { label: '已完成', count: 9, path: '/profile' },
  ];

  const menuItems = [
    { icon: Wallet, label: '我的钱包', path: '/wallet', value: `¥${userInfo.balance.toFixed(2)}` },
    { icon: Gift, label: '优惠专区', path: '/deals' },
    { icon: Calendar, label: '我的行程', path: '/trip-planner', badge: 2 },
    { icon: Heart, label: '收藏夹', path: '/destinations' },
    { icon: Notebook, label: '我的笔记', path: '/publish-note' },
    { icon: Ticket, label: '优惠券', path: '/wallet', badge: userInfo.coupons },
    { icon: CreditCard, label: '积分', path: '/wallet', value: userInfo.points.toString() },
    { icon: MessageCircle, label: '消息中心', path: '/messages', badge: 3 },
  ];

  const settingItems = [
    { icon: Bell, label: '通知设置', path: '/profile' },
    { icon: Shield, label: '隐私设置', path: '/profile' },
    { icon: Settings, label: '账号安全', path: '/profile' },
  ];

  const handleLogout = () => {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userPhone');
    onLogout();
    navigate('/login');
  };

  return (
    <MobileShell>
      <div className="space-y-5">
        <div className="rounded-[30px] bg-gradient-to-br from-slate-900 via-slate-800 to-teal-700 p-5 text-white shadow-[0_18px_55px_rgba(15,23,42,0.28)]">
          <div className="mb-5 flex items-center justify-between">
            <div>
              <p className="text-sm text-white/70">个人中心</p>
              <h1 className="mt-1 text-[28px] font-semibold tracking-tight">欢迎回来</h1>
            </div>
            <Button variant="ghost" size="icon" className="rounded-2xl bg-white/10 text-white hover:bg-white/20" onClick={() => navigate('/profile')}>
              <Settings size={20} />
            </Button>
          </div>

          <div className="mb-6 flex items-center gap-4">
            <Avatar className="h-18 w-18 border-2 border-white/20">
              <AvatarImage src={userInfo.avatar} />
              <AvatarFallback className="bg-white/10 text-white text-xl">
                <User size={28} />
              </AvatarFallback>
            </Avatar>
            <div className="flex-1">
              <div className="mb-2 inline-flex items-center gap-2 rounded-full bg-white/10 px-3 py-1 text-xs font-medium text-white/80">
                <Crown size={13} />
                旅行达人会员
              </div>
              <h2 className="text-xl font-semibold">{userInfo.name}</h2>
              <p className="text-sm text-white/70">{userInfo.phone}</p>
            </div>
            <Button variant="outline" size="sm" className="rounded-2xl border-white/20 bg-white/10 text-white hover:bg-white/20" onClick={() => navigate('/profile')}>
              编辑资料
            </Button>
          </div>

          <div className="grid grid-cols-3 gap-3">
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">{userInfo.points}</p>
              <p className="mt-1 text-xs text-white/70">积分</p>
            </Card>
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">{userInfo.coupons}</p>
              <p className="mt-1 text-xs text-white/70">优惠券</p>
            </Card>
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">¥{Math.floor(userInfo.balance)}</p>
              <p className="mt-1 text-xs text-white/70">余额</p>
            </Card>
          </div>
        </div>

        <Card className="rounded-[28px] border-white/70 bg-white/88 p-4 shadow-[0_14px_35px_rgba(15,23,42,0.06)]">
          <div className="mb-4 flex items-center justify-between">
            <h3 className="text-base font-semibold text-slate-900">订单中心</h3>
            <button className="flex items-center gap-1 text-sm font-medium text-teal-600" onClick={() => navigate('/profile')}>
              查看全部
              <ChevronRight size={16} />
            </button>
          </div>
          <div className="grid grid-cols-4 gap-3">
            {stats.map((stat) => (
              <button key={stat.label} onClick={() => navigate(stat.path)} className="flex flex-col items-center gap-2 rounded-3xl p-2 hover:bg-slate-50">
                <div className="relative flex h-12 w-12 items-center justify-center rounded-2xl bg-teal-50 text-teal-600">
                  <Ticket size={22} />
                  {stat.count > 0 && (
                    <span className="absolute -right-1 -top-1 flex h-5 min-w-5 items-center justify-center rounded-full bg-rose-500 px-1 text-[10px] text-white">
                      {stat.count}
                    </span>
                  )}
                </div>
                <span className="text-xs font-medium text-slate-600">{stat.label}</span>
              </button>
            ))}
          </div>
        </Card>

        <Card className="overflow-hidden rounded-[28px] border-white/70 bg-white/88 shadow-[0_14px_35px_rgba(15,23,42,0.06)]">
          {menuItems.map((item, index) => {
            const Icon = item.icon;
            return (
              <button
                key={item.label}
                onClick={() => navigate(item.path)}
                className={`flex w-full items-center justify-between px-4 py-4 transition-colors hover:bg-slate-50 ${index !== menuItems.length - 1 ? 'border-b border-slate-100' : ''}`}
              >
                <div className="flex items-center gap-3">
                  <div className="flex h-11 w-11 items-center justify-center rounded-2xl bg-slate-100 text-slate-700">
                    <Icon size={18} />
                  </div>
                  <span className="font-medium text-slate-800">{item.label}</span>
                </div>
                <div className="flex items-center gap-2">
                  {item.badge && item.badge > 0 && <span className="rounded-full bg-rose-500 px-2 py-1 text-xs text-white">{item.badge}</span>}
                  {item.value && <span className="rounded-full bg-slate-100 px-2.5 py-1 text-xs text-slate-500">{item.value}</span>}
                  <ChevronRight size={18} className="text-slate-400" />
                </div>
              </button>
            );
          })}
        </Card>

        <Card className="overflow-hidden rounded-[28px] border-white/70 bg-white/88 shadow-[0_14px_35px_rgba(15,23,42,0.06)]">
          {settingItems.map((item, index) => {
            const Icon = item.icon;
            return (
              <button
                key={item.label}
                onClick={() => navigate(item.path)}
                className={`flex w-full items-center justify-between px-4 py-4 transition-colors hover:bg-slate-50 ${index !== settingItems.length - 1 ? 'border-b border-slate-100' : ''}`}
              >
                <div className="flex items-center gap-3">
                  <div className="flex h-11 w-11 items-center justify-center rounded-2xl bg-slate-100 text-slate-700">
                    <Icon size={18} />
                  </div>
                  <span className="font-medium text-slate-800">{item.label}</span>
                </div>
                <ChevronRight size={18} className="text-slate-400" />
              </button>
            );
          })}
        </Card>

        <Button variant="outline" className="h-12 w-full rounded-2xl border-rose-100 bg-white text-rose-500 hover:bg-rose-50" onClick={handleLogout}>
          <LogOut size={18} className="mr-2" />
          退出登录
        </Button>
      </div>

      <BottomNavigation />
    </MobileShell>
  );
}
