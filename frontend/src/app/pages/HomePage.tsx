import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, MapPin, Heart, Star, Plane, Hotel, Ticket, UtensilsCrossed, Notebook, Tag, Navigation, Sparkles, Scan, Users, TrendingUp, Bell } from 'lucide-react';
import BottomNavigation from '../components/BottomNavigation';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Input } from '../components/ui/input';
import { Card } from '../components/ui/card';
import MobileShell from '../MobileShell';

export default function HomePage() {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');

  const banners = [
    { id: 1, image: 'https://images.unsplash.com/photo-1637576306143-0262e56c5231', title: '马尔代夫度假天堂' },
    { id: 2, image: 'https://images.unsplash.com/photo-1676730056228-7e38cbb88edc', title: '圣托里尼蓝白世界' },
    { id: 3, image: 'https://images.unsplash.com/photo-1488415032361-b7e238421f1b', title: '冰岛极光之旅' },
  ];

  const functionNav = [
    { icon: MapPin, label: '目的地', color: 'from-teal-400 to-cyan-500', path: '/destinations' },
    { icon: Plane, label: '机票', color: 'from-sky-400 to-blue-500', path: '/flight-booking' },
    { icon: Hotel, label: '酒店', color: 'from-violet-400 to-fuchsia-500', path: '/hotel-booking' },
    { icon: Ticket, label: '门票', color: 'from-orange-400 to-amber-500', path: '/destinations' },
    { icon: UtensilsCrossed, label: '美食', color: 'from-rose-400 to-red-500', path: '/nearby' },
    { icon: Tag, label: '优惠', color: 'from-pink-400 to-rose-500', path: '/deals' },
    { icon: Notebook, label: '笔记', color: 'from-indigo-400 to-violet-500', path: '/publish-note' },
    { icon: Navigation, label: '附近', color: 'from-emerald-400 to-teal-500', path: '/nearby' },
  ];

  const recommendedDestinations = [
    { id: 1, name: '马尔代夫', image: 'https://images.unsplash.com/photo-1637576306143-0262e56c5231', rating: 4.9, description: '纯净海岛，潜水天堂' },
    { id: 2, name: '圣托里尼', image: 'https://images.unsplash.com/photo-1676730056228-7e38cbb88edc', rating: 4.8, description: '蓝白建筑，浪漫爱琴海' },
    { id: 3, name: '瑞士阿尔卑斯', image: 'https://images.unsplash.com/photo-1640156818332-77c8139e8d68', rating: 4.9, description: '雪山童话，徒步天堂' },
    { id: 4, name: '巴厘岛', image: 'https://images.unsplash.com/photo-1581032841303-0ba9e894ebc3', rating: 4.7, description: '热带风情，文化体验' },
  ];

  const tripTemplates = [
    { id: 1, title: '巴黎5日浪漫之旅', days: 5, spots: 12, theme: '蜜月', image: 'https://images.unsplash.com/photo-1595441857632-71570ef36580' },
    { id: 2, title: '京都7日文化探索', days: 7, spots: 18, theme: '文化', image: 'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2' },
    { id: 3, title: '冰岛10日极光追寻', days: 10, spots: 15, theme: '探险', image: 'https://images.unsplash.com/photo-1488415032361-b7e238421f1b' },
  ];

  const notes = [
    { id: 1, image: 'https://images.unsplash.com/photo-1759299710388-690bf2305e59', title: '曼谷街头美食攻略', author: '旅行美食家', likes: 2453 },
    { id: 2, image: 'https://images.unsplash.com/photo-1581032841303-0ba9e894ebc3', title: '巴厘岛小众打卡地', author: '小红薯', likes: 1892 },
    { id: 3, image: 'https://images.unsplash.com/photo-1640156818332-77c8139e8d68', title: '瑞士徒步完全指南', author: '户外探险者', likes: 3201 },
    { id: 4, image: 'https://images.unsplash.com/photo-1676730056228-7e38cbb88edc', title: '圣托里尼拍照秘籍', author: '摄影达人', likes: 4567 },
  ];

  return (
    <MobileShell>
      <div className="space-y-6">
        <div className="rounded-[30px] bg-gradient-to-br from-teal-500 via-cyan-500 to-sky-600 p-5 text-white shadow-[0_18px_55px_rgba(8,145,178,0.26)]">
          <div className="mb-5 flex items-center justify-between">
            <div>
              <p className="text-sm text-white/75">早上好，欢迎回来</p>
              <h1 className="mt-1 text-[28px] font-semibold tracking-tight">下一段旅程，想去哪里？</h1>
            </div>
            <button className="flex h-11 w-11 items-center justify-center rounded-2xl border border-white/15 bg-white/10 backdrop-blur-sm">
              <Bell size={20} />
            </button>
          </div>

          <div className="mb-4 flex items-center gap-2 text-sm text-white/80">
            <div className="flex items-center gap-1 rounded-full bg-white/10 px-3 py-1.5 backdrop-blur-sm">
              <MapPin size={14} />
              北京
            </div>
            <div className="rounded-full bg-white/10 px-3 py-1.5 backdrop-blur-sm">晴天 24°C</div>
          </div>

          <div className="relative">
            <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
            <Input
              type="text"
              placeholder="搜索目的地 / 景点 / 行程"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="h-12 rounded-2xl border-white/30 bg-white/92 pl-11 text-slate-900 shadow-inner"
            />
          </div>
        </div>

        <div className="-mx-1 flex gap-3 overflow-x-auto px-1 scrollbar-hide">
          {banners.map((banner) => (
            <div
              key={banner.id}
              className="relative h-44 w-[84%] flex-shrink-0 overflow-hidden rounded-[28px] shadow-[0_16px_32px_rgba(15,23,42,0.14)]"
              onClick={() => navigate('/destinations')}
            >
              <ImageWithFallback src={banner.image} alt={banner.title} className="h-full w-full object-cover" />
              <div className="absolute inset-0 bg-gradient-to-t from-slate-950/70 via-slate-900/10 to-transparent" />
              <div className="absolute inset-x-0 bottom-0 p-5 text-white">
                <div className="mb-2 inline-flex rounded-full bg-white/15 px-3 py-1 text-xs backdrop-blur-sm">精选推荐</div>
                <h3 className="text-xl font-semibold">{banner.title}</h3>
              </div>
            </div>
          ))}
        </div>

        <Card className="rounded-[28px] border-white/70 bg-white/86 p-4 shadow-[0_14px_40px_rgba(15,23,42,0.06)] backdrop-blur-xl">
          <div className="mb-4 flex items-center justify-between">
            <div>
              <h2 className="text-lg font-semibold text-slate-900">探索功能</h2>
              <p className="text-sm text-slate-500">移动端常用入口</p>
            </div>
            <button className="text-sm font-medium text-teal-600">全部</button>
          </div>
          <div className="grid grid-cols-4 gap-3">
            {functionNav.map((item) => {
              const Icon = item.icon;
              return (
                <button key={item.label} onClick={() => navigate(item.path)} className="flex flex-col items-center gap-2 rounded-3xl p-2 transition-transform hover:-translate-y-0.5">
                  <div className={`flex h-14 w-14 items-center justify-center rounded-[22px] bg-gradient-to-br ${item.color} text-white shadow-[0_10px_20px_rgba(15,23,42,0.12)]`}>
                    <Icon size={22} />
                  </div>
                  <span className="text-[12px] font-medium text-slate-600">{item.label}</span>
                </button>
              );
            })}
          </div>
        </Card>

        <div className="grid grid-cols-2 gap-3">
          <Card className="rounded-[28px] border-0 bg-gradient-to-br from-violet-500 to-fuchsia-500 p-4 text-white shadow-[0_16px_32px_rgba(139,92,246,0.28)]" onClick={() => navigate('/ai-planner')}>
            <Sparkles size={26} className="mb-8" />
            <h3 className="text-base font-semibold">AI规划助手</h3>
            <p className="mt-1 text-xs text-white/80">智能生成专属行程</p>
          </Card>
          <Card className="rounded-[28px] border-0 bg-gradient-to-br from-teal-500 to-sky-500 p-4 text-white shadow-[0_16px_32px_rgba(20,184,166,0.28)]" onClick={() => navigate('/ar-navigation')}>
            <Scan size={26} className="mb-8" />
            <h3 className="text-base font-semibold">AR实景导览</h3>
            <p className="mt-1 text-xs text-white/80">拍照识别景点信息</p>
          </Card>
          <Card className="rounded-[28px] border-0 bg-gradient-to-br from-orange-500 to-rose-500 p-4 text-white shadow-[0_16px_32px_rgba(249,115,22,0.28)]" onClick={() => navigate('/smart-budget')}>
            <TrendingUp size={26} className="mb-8" />
            <h3 className="text-base font-semibold">智能预算</h3>
            <p className="mt-1 text-xs text-white/80">AI消费分析预测</p>
          </Card>
          <Card className="rounded-[28px] border-0 bg-gradient-to-br from-sky-500 to-cyan-500 p-4 text-white shadow-[0_16px_32px_rgba(14,165,233,0.28)]" onClick={() => navigate('/travel-mates')}>
            <Users size={26} className="mb-8" />
            <h3 className="text-base font-semibold">旅伴匹配</h3>
            <p className="mt-1 text-xs text-white/80">找到志同道合的TA</p>
          </Card>
        </div>

        <section>
          <div className="mb-3 flex items-center justify-between px-1">
            <div>
              <h2 className="text-lg font-semibold text-slate-900">为你推荐</h2>
              <p className="text-sm text-slate-500">根据你的偏好精选目的地</p>
            </div>
            <button className="text-sm font-medium text-teal-600" onClick={() => navigate('/destinations')}>查看更多</button>
          </div>
          <div className="-mx-1 flex gap-4 overflow-x-auto px-1 scrollbar-hide">
            {recommendedDestinations.map((dest) => (
              <Card key={dest.id} className="w-44 flex-shrink-0 overflow-hidden rounded-[28px] border-white/70 bg-white/88 shadow-[0_14px_35px_rgba(15,23,42,0.08)]" onClick={() => navigate(`/destination/${dest.id}`)}>
                <div className="relative">
                  <ImageWithFallback src={dest.image} alt={dest.name} className="h-36 w-full object-cover" />
                  <button className="absolute right-3 top-3 flex h-9 w-9 items-center justify-center rounded-2xl bg-white/80 text-slate-600 backdrop-blur-sm">
                    <Heart size={16} />
                  </button>
                </div>
                <div className="space-y-2 p-4">
                  <div className="flex items-center justify-between">
                    <h3 className="font-semibold text-slate-900">{dest.name}</h3>
                    <div className="flex items-center gap-1 text-sm text-slate-500">
                      <Star size={14} className="fill-amber-400 text-amber-400" />
                      {dest.rating}
                    </div>
                  </div>
                  <p className="text-sm leading-6 text-slate-500">{dest.description}</p>
                </div>
              </Card>
            ))}
          </div>
        </section>

        <section>
          <div className="mb-3 flex items-center justify-between px-1">
            <h2 className="text-lg font-semibold text-slate-900">热门行程模板</h2>
            <button className="text-sm font-medium text-teal-600" onClick={() => navigate('/trip-planner')}>查看更多</button>
          </div>
          <div className="space-y-3">
            {tripTemplates.map((template) => (
              <Card key={template.id} className="flex gap-3 rounded-[28px] border-white/70 bg-white/88 p-3 shadow-[0_14px_35px_rgba(15,23,42,0.06)]" onClick={() => navigate('/trip-planner')}>
                <ImageWithFallback src={template.image} alt={template.title} className="h-24 w-24 rounded-[20px] object-cover" />
                <div className="flex-1 py-1">
                  <div className="mb-2 inline-flex rounded-full bg-teal-50 px-3 py-1 text-xs font-medium text-teal-600">{template.theme}</div>
                  <h3 className="font-semibold text-slate-900">{template.title}</h3>
                  <div className="mt-3 flex gap-4 text-xs text-slate-500">
                    <span>{template.days}天</span>
                    <span>{template.spots}个景点</span>
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </section>

        <section>
          <div className="mb-3 px-1">
            <h2 className="text-lg font-semibold text-slate-900">种草笔记</h2>
            <p className="text-sm text-slate-500">灵感内容，轻松发现旅行玩法</p>
          </div>
          <div className="grid grid-cols-2 gap-3">
            {notes.map((note) => (
              <Card key={note.id} className="overflow-hidden rounded-[26px] border-white/70 bg-white/88 shadow-[0_12px_30px_rgba(15,23,42,0.06)]" onClick={() => navigate('/guide/1')}>
                <ImageWithFallback src={note.image} alt={note.title} className="h-44 w-full object-cover" />
                <div className="space-y-2 p-3">
                  <h3 className="line-clamp-2 text-sm font-semibold text-slate-900">{note.title}</h3>
                  <div className="flex items-center justify-between text-xs text-slate-500">
                    <span>{note.author}</span>
                    <div className="flex items-center gap-1">
                      <Heart size={12} />
                      {note.likes}
                    </div>
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </section>
      </div>

      <BottomNavigation />
    </MobileShell>
  );
}
