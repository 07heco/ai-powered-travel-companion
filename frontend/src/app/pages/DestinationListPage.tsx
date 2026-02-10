import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Search, SlidersHorizontal, Heart, Star } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import BottomNavigation from '../components/BottomNavigation';

export default function DestinationListPage() {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('全部');

  const categories = ['全部', '城市', '海岛', '古镇', '雪山', '亲子', '小众'];

  const destinations = [
    { id: 1, name: '东京', image: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63', rating: 4.8, bestTime: '3-5月, 9-11月', spots: 156, country: '日本' },
    { id: 2, name: '纽约', image: 'https://images.unsplash.com/photo-1514565131-fce0801e5785', rating: 4.7, bestTime: '4-6月, 9-11月', spots: 203, country: '美国' },
    { id: 3, name: '迪拜', image: 'https://images.unsplash.com/photo-1706798636444-d4eb076fb63c', rating: 4.9, bestTime: '11月-次年3月', spots: 98, country: '阿联酋' },
    { id: 4, name: '悉尼', image: 'https://images.unsplash.com/photo-1523059623039-a9ed027e7fad', rating: 4.8, bestTime: '9-11月', spots: 134, country: '澳大利亚' },
    { id: 5, name: '巴塞罗那', image: 'https://images.unsplash.com/photo-1583422409516-2895a77efded', rating: 4.9, bestTime: '5-6月, 9-10月', spots: 176, country: '西班牙' },
    { id: 6, name: '马尔代夫', image: 'https://images.unsplash.com/photo-1637576306143-0262e56c5231', rating: 4.9, bestTime: '11月-次年4月', spots: 67, country: '马尔代夫' },
    { id: 7, name: '圣托里尼', image: 'https://images.unsplash.com/photo-1676730056228-7e38cbb88edc', rating: 4.8, bestTime: '4-10月', spots: 45, country: '希腊' },
    { id: 8, name: '京都', image: 'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2', rating: 4.9, bestTime: '3-5月, 10-11月', spots: 189, country: '日本' },
  ];

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center gap-3">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
            <Input
              type="text"
              placeholder="搜索目的地"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="pl-10 h-10 bg-gray-100 border-0"
            />
          </div>
          <Button variant="ghost" size="icon">
            <SlidersHorizontal size={20} />
          </Button>
        </div>
      </div>

      {/* 分类标签 */}
      <div className="bg-white px-4 py-3 mb-4">
        <div className="flex gap-3 overflow-x-auto scrollbar-hide">
          {categories.map((category) => (
            <button
              key={category}
              onClick={() => setSelectedCategory(category)}
              className={`px-4 py-2 rounded-full whitespace-nowrap transition-colors ${
                selectedCategory === category
                  ? 'bg-teal-600 text-white'
                  : 'bg-gray-100 text-gray-600'
              }`}
            >
              {category}
            </button>
          ))}
        </div>
      </div>

      {/* 目的地列表 */}
      <div className="px-4 space-y-4">
        {destinations.map((dest) => (
          <Card
            key={dest.id}
            className="flex gap-3 cursor-pointer hover:shadow-md transition-shadow p-3"
            onClick={() => navigate(`/destination/${dest.id}`)}
          >
            <div className="relative flex-shrink-0">
              <ImageWithFallback
                src={dest.image}
                alt={dest.name}
                className="w-32 h-32 object-cover rounded-lg"
              />
              <button
                onClick={(e) => {
                  e.stopPropagation();
                }}
                className="absolute top-2 right-2 w-8 h-8 bg-white/80 backdrop-blur-sm rounded-full flex items-center justify-center"
              >
                <Heart size={16} className="text-gray-600" />
              </button>
            </div>
            <div className="flex-1 flex flex-col justify-between">
              <div>
                <div className="flex items-start justify-between mb-1">
                  <h3 className="font-semibold text-lg">{dest.name}</h3>
                  <div className="flex items-center gap-1">
                    <Star size={14} className="fill-yellow-400 text-yellow-400" />
                    <span className="text-sm">{dest.rating}</span>
                  </div>
                </div>
                <p className="text-xs text-gray-500 mb-2">{dest.country}</p>
                <p className="text-xs text-gray-600 mb-1">
                  <span className="text-teal-600">最佳旅行时间：</span>
                  {dest.bestTime}
                </p>
                <p className="text-xs text-gray-500">{dest.spots} 个景点</p>
              </div>
              <Button size="sm" variant="outline" className="w-full mt-2">
                查看详情
              </Button>
            </div>
          </Card>
        ))}
      </div>

      <BottomNavigation />
    </div>
  );
}
