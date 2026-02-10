import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, MapPin, Heart, Star, Plane, Hotel, Ticket, UtensilsCrossed, Car, Notebook, Bell, Tag, Navigation, Sparkles, Scan, Users, TrendingUp } from 'lucide-react';
import BottomNavigation from '../components/BottomNavigation';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Input } from '../components/ui/input';
import { Card } from '../components/ui/card';

export default function HomePage() {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');

  const banners = [
    { id: 1, image: 'https://images.unsplash.com/photo-1637576306143-0262e56c5231', title: '马尔代夫度假天堂' },
    { id: 2, image: 'https://images.unsplash.com/photo-1676730056228-7e38cbb88edc', title: '圣托里尼蓝白世界' },
    { id: 3, image: 'https://images.unsplash.com/photo-1488415032361-b7e238421f1b', title: '冰岛极光之旅' },
  ];

  const functionNav = [
    { icon: MapPin, label: '目的地', color: 'text-teal-500', path: '/destinations' },
    { icon: Plane, label: '机票', color: 'text-blue-500', path: '/flight-booking' },
    { icon: Hotel, label: '酒店', color: 'text-purple-500', path: '/hotel-booking' },
    { icon: Ticket, label: '门票', color: 'text-orange-500', path: '/destinations' },
    { icon: UtensilsCrossed, label: '美食', color: 'text-red-500', path: '/nearby' },
    { icon: Tag, label: '优惠', color: 'text-pink-500', path: '/deals' },
    { icon: Notebook, label: '笔记', color: 'text-indigo-500', path: '/publish-note' },
    { icon: Navigation, label: '附近', color: 'text-green-500', path: '/nearby' },
    { icon: Sparkles, label: '活动', color: 'text-yellow-500', path: '/events' },
    { icon: Scan, label: '扫描', color: 'text-gray-500', path: '/scan' },
    { icon: Users, label: '社区', color: 'text-cyan-500', path: '/community' },
    { icon: TrendingUp, label: '趋势', color: 'text-pink-500', path: '/trends' },
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
    { id: 5, image: 'https://images.unsplash.com/photo-1637576306143-0262e56c5231', title: '马代选岛终极攻略', author: '海岛控', likes: 2890 },
    { id: 6, image: 'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2', title: '京都赏樱时间表', author: '樱花季', likes: 1756 },
  ];

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      {/* 顶部搜索栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center gap-3">
          <button className="flex items-center gap-1 text-sm text-gray-600">
            <MapPin size={16} />
            <span>北京</span>
          </button>
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
            <Input
              type="text"
              placeholder="搜索目的地 / 景点 / 行程"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="pl-10 h-10 bg-gray-100 border-0"
            />
          </div>
        </div>
      </div>

      {/* 轮播图 */}
      <div className="px-4 py-4">
        <div className="flex gap-3 overflow-x-auto scrollbar-hide">
          {banners.map((banner) => (
            <div
              key={banner.id}
              className="flex-shrink-0 w-[85%] h-40 rounded-2xl overflow-hidden relative cursor-pointer"
              onClick={() => navigate('/destinations')}
            >
              <ImageWithFallback
                src={banner.image}
                alt={banner.title}
                className="w-full h-full object-cover"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent flex items-end p-4">
                <h3 className="text-white font-semibold text-lg">{banner.title}</h3>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* 功能导航栏 */}
      <div className="bg-white px-4 py-4 mb-4">
        <div className="flex gap-6 overflow-x-auto scrollbar-hide">
          {functionNav.map((item) => {
            const Icon = item.icon;
            return (
              <button
                key={item.label}
                onClick={() => navigate(item.path)}
                className="flex flex-col items-center gap-2 flex-shrink-0"
              >
                <div className={`w-12 h-12 rounded-full bg-gray-50 flex items-center justify-center ${item.color}`}>
                  <Icon size={24} />
                </div>
                <span className="text-xs text-gray-700">{item.label}</span>
              </button>
            );
          })}
        </div>
      </div>

      {/* AI智能功能推广 */}
      <div className="px-4 mb-6">
        <div className="grid grid-cols-2 gap-3">
          {/* AI规划助手 */}
          <Card 
            className="p-4 bg-gradient-to-br from-purple-500 to-pink-500 text-white cursor-pointer hover:shadow-xl transition-all hover:scale-105"
            onClick={() => navigate('/ai-planner')}
          >
            <Sparkles size={28} className="mb-2" />
            <h3 className="font-bold mb-1">AI规划助手</h3>
            <p className="text-xs opacity-90">智能生成专属行程</p>
            <div className="mt-3 text-xs bg-white/20 backdrop-blur-sm px-2 py-1 rounded-full inline-block">
              ✨ 新功能
            </div>
          </Card>

          {/* AR导览 */}
          <Card 
            className="p-4 bg-gradient-to-br from-teal-500 to-blue-500 text-white cursor-pointer hover:shadow-xl transition-all hover:scale-105"
            onClick={() => navigate('/ar-navigation')}
          >
            <Scan size={28} className="mb-2" />
            <h3 className="font-bold mb-1">AR实景导览</h3>
            <p className="text-xs opacity-90">拍照识别景点信息</p>
            <div className="mt-3 text-xs bg-white/20 backdrop-blur-sm px-2 py-1 rounded-full inline-block">
              🔥 热门
            </div>
          </Card>

          {/* 智能预算 */}
          <Card 
            className="p-4 bg-gradient-to-br from-orange-500 to-red-500 text-white cursor-pointer hover:shadow-xl transition-all hover:scale-105"
            onClick={() => navigate('/smart-budget')}
          >
            <TrendingUp size={28} className="mb-2" />
            <h3 className="font-bold mb-1">智能预算</h3>
            <p className="text-xs opacity-90">AI消费分析预测</p>
            <div className="mt-3 text-xs bg-white/20 backdrop-blur-sm px-2 py-1 rounded-full inline-block">
              💡 推荐
            </div>
          </Card>

          {/* 旅伴匹配 */}
          <Card 
            className="p-4 bg-gradient-to-br from-blue-500 to-cyan-500 text-white cursor-pointer hover:shadow-xl transition-all hover:scale-105"
            onClick={() => navigate('/travel-mates')}
          >
            <Users size={28} className="mb-2" />
            <h3 className="font-bold mb-1">旅伴匹配</h3>
            <p className="text-xs opacity-90">找到志同道合的TA</p>
            <div className="mt-3 text-xs bg-white/20 backdrop-blur-sm px-2 py-1 rounded-full inline-block">
              👥 社交
            </div>
          </Card>
        </div>
      </div>

      {/* 为你推荐 */}
      <div className="px-4 mb-6">
        <div className="flex justify-between items-center mb-3">
          <h2 className="text-lg font-semibold">为你推荐</h2>
          <button className="text-sm text-teal-600" onClick={() => navigate('/destinations')}>
            查看更多 →
          </button>
        </div>
        <div className="flex gap-4 overflow-x-auto scrollbar-hide">
          {recommendedDestinations.map((dest) => (
            <Card
              key={dest.id}
              className="flex-shrink-0 w-44 cursor-pointer hover:shadow-md transition-shadow"
              onClick={() => navigate(`/destination/${dest.id}`)}
            >
              <div className="relative">
                <ImageWithFallback
                  src={dest.image}
                  alt={dest.name}
                  className="w-full h-32 object-cover rounded-t-lg"
                />
                <button className="absolute top-2 right-2 w-8 h-8 bg-white/80 backdrop-blur-sm rounded-full flex items-center justify-center">
                  <Heart size={16} className="text-gray-600" />
                </button>
              </div>
              <div className="p-3">
                <h3 className="font-semibold mb-1">{dest.name}</h3>
                <div className="flex items-center gap-1 mb-1">
                  <Star size={14} className="fill-yellow-400 text-yellow-400" />
                  <span className="text-sm text-gray-600">{dest.rating}</span>
                </div>
                <p className="text-xs text-gray-500">{dest.description}</p>
              </div>
            </Card>
          ))}
        </div>
      </div>

      {/* 热门行程模板 */}
      <div className="px-4 mb-6">
        <div className="flex justify-between items-center mb-3">
          <h2 className="text-lg font-semibold">热门行程模板</h2>
          <button className="text-sm text-teal-600" onClick={() => navigate('/trip-planner')}>
            查看更多 →
          </button>
        </div>
        <div className="space-y-3">
          {tripTemplates.map((template) => (
            <Card
              key={template.id}
              className="flex gap-3 cursor-pointer hover:shadow-md transition-shadow p-3"
              onClick={() => navigate('/trip-planner')}
            >
              <ImageWithFallback
                src={template.image}
                alt={template.title}
                className="w-24 h-24 object-cover rounded-lg flex-shrink-0"
              />
              <div className="flex-1">
                <h3 className="font-semibold mb-2">{template.title}</h3>
                <div className="flex gap-4 text-xs text-gray-500">
                  <span>{template.days}天</span>
                  <span>{template.spots}个景点</span>
                  <span className="text-teal-600">{template.theme}</span>
                </div>
              </div>
            </Card>
          ))}
        </div>
      </div>

      {/* 种草笔记（瀑布流） */}
      <div className="px-4 mb-6">
        <h2 className="text-lg font-semibold mb-3">种草笔记</h2>
        <div className="grid grid-cols-2 gap-3">
          {notes.map((note) => (
            <Card
              key={note.id}
              className="cursor-pointer hover:shadow-md transition-shadow overflow-hidden"
              onClick={() => navigate('/guide/1')}
            >
              <ImageWithFallback
                src={note.image}
                alt={note.title}
                className="w-full h-48 object-cover"
              />
              <div className="p-3">
                <h3 className="text-sm font-semibold mb-2 line-clamp-2">{note.title}</h3>
                <div className="flex items-center justify-between text-xs text-gray-500">
                  <span>{note.author}</span>
                  <div className="flex items-center gap-1">
                    <Heart size={12} />
                    <span>{note.likes}</span>
                  </div>
                </div>
              </div>
            </Card>
          ))}
        </div>
      </div>

      <BottomNavigation />
    </div>
  );
}