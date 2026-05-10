import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowLeft, Share2, Heart, MapPin, Clock, DollarSign, Thermometer, Globe } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../components/ui/tabs';
import { Card } from '../components/ui/card';
import { Button } from '../components/ui/button';
import { Badge } from '../components/ui/badge';

export default function DestinationDetailPage() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [activeTab, setActiveTab] = useState('detail');

  const destination = {
    id: 1,
    name: '东京',
    country: '日本',
    rating: 4.8,
    image: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63',
    description: '东京是日本的首都，融合了传统与现代，既有古老的寺庙神社，又有繁华的商业区和高科技景观。',
    bestTime: '3-5月, 9-11月',
    climate: '温带海洋性气候，四季分明',
    language: '日语',
    currency: '日元 (¥)',
    spots: [
      { id: 1, name: '东京塔', image: 'https://images.unsplash.com/photo-1729707397425-2e3bcd5e9072', rating: 4.7, price: '¥900', address: '东京都港区芝公园4-2-8' },
      { id: 2, name: '浅草寺', image: 'https://images.unsplash.com/photo-1641385511718-d736d65e6d04', rating: 4.9, price: '免费', address: '东京都台东区浅草2-3-1' },
      { id: 3, name: '涩谷十字路口', image: 'https://images.unsplash.com/photo-1643431452454-1612071b0671', rating: 4.6, price: '免费', address: '东京都涩谷区' },
      { id: 4, name: '明治神宫', image: 'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2', rating: 4.8, price: '免费', address: '东京都涩谷区代代木神园町1-1' },
    ],
    foods: [
      { id: 1, name: '寿司大', image: 'https://images.unsplash.com/photo-1700324822763-956100f79b0d', rating: 4.9, price: '¥3000', type: '寿司' },
      { id: 2, name: '一兰拉面', image: 'https://images.unsplash.com/photo-1759299710388-690bf2305e59', rating: 4.7, price: '¥1000', type: '拉面' },
    ],
    notes: [
      { id: 1, image: 'https://images.unsplash.com/photo-1729707397425-2e3bcd5e9072', title: '东京塔夜景拍摄技巧', author: '摄影师小王', likes: 3456 },
      { id: 2, image: 'https://images.unsplash.com/photo-1643431452454-1612071b0671', title: '涩谷打卡全攻略', author: '旅行达人', likes: 2789 },
    ],
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部图片 */}
      <div className="relative h-64">
        <ImageWithFallback
          src={destination.image}
          alt={destination.name}
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-b from-black/20 via-transparent to-black/60" />
        
        {/* 顶部操作栏 */}
        <div className="absolute top-4 left-4 right-4 flex justify-between items-center">
          <button
            onClick={() => navigate(-1)}
            className="w-10 h-10 bg-white/80 backdrop-blur-sm rounded-full flex items-center justify-center"
          >
            <ArrowLeft size={20} />
          </button>
          <div className="flex gap-2">
            <button className="w-10 h-10 bg-white/80 backdrop-blur-sm rounded-full flex items-center justify-center">
              <Share2 size={20} />
            </button>
            <button className="w-10 h-10 bg-white/80 backdrop-blur-sm rounded-full flex items-center justify-center">
              <Heart size={20} />
            </button>
          </div>
        </div>

        {/* 底部标题 */}
        <div className="absolute bottom-4 left-4 right-4 text-white">
          <h1 className="text-3xl font-bold mb-1">{destination.name}</h1>
          <p className="text-sm opacity-90">{destination.country}</p>
        </div>
      </div>

      {/* 基础信息栏 */}
      <div className="bg-white px-4 py-4 mb-2">
        <div className="grid grid-cols-2 gap-4 text-sm">
          <div className="flex items-center gap-2">
            <Clock className="text-teal-600" size={18} />
            <div>
              <p className="text-gray-500 text-xs">最佳旅行时间</p>
              <p className="font-medium">{destination.bestTime}</p>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <Thermometer className="text-teal-600" size={18} />
            <div>
              <p className="text-gray-500 text-xs">气候</p>
              <p className="font-medium">温带海洋性</p>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <Globe className="text-teal-600" size={18} />
            <div>
              <p className="text-gray-500 text-xs">语言</p>
              <p className="font-medium">{destination.language}</p>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <DollarSign className="text-teal-600" size={18} />
            <div>
              <p className="text-gray-500 text-xs">货币</p>
              <p className="font-medium">{destination.currency}</p>
            </div>
          </div>
        </div>
      </div>

      {/* 标签栏 */}
      <Tabs value={activeTab} onValueChange={setActiveTab} className="bg-white mb-2">
        <TabsList className="w-full justify-start overflow-x-auto border-b">
          <TabsTrigger value="detail">详情</TabsTrigger>
          <TabsTrigger value="spots">景点</TabsTrigger>
          <TabsTrigger value="foods">美食</TabsTrigger>
          <TabsTrigger value="notes">笔记</TabsTrigger>
        </TabsList>

        <TabsContent value="detail" className="px-4 py-4">
          <h3 className="font-semibold mb-2">目的地简介</h3>
          <p className="text-sm text-gray-600 mb-4 leading-relaxed">
            {destination.description}
          </p>
          
          <h3 className="font-semibold mb-2">当地习俗提示</h3>
          <div className="space-y-2 text-sm text-gray-600">
            <p>• 进入室内需要脱鞋</p>
            <p>• 在公共场所保持安静</p>
            <p>• 不要在街上边走边吃</p>
            <p>• 使用筷子时注意礼仪</p>
          </div>
        </TabsContent>

        <TabsContent value="spots" className="px-4 py-4">
          <div className="space-y-3">
            {destination.spots.map((spot) => (
              <Card key={spot.id} className="flex gap-3 p-3 cursor-pointer hover:shadow-md transition-shadow">
                <ImageWithFallback
                  src={spot.image}
                  alt={spot.name}
                  className="w-24 h-24 object-cover rounded-lg flex-shrink-0"
                />
                <div className="flex-1">
                  <div className="flex items-start justify-between mb-1">
                    <h3 className="font-semibold">{spot.name}</h3>
                    <Badge variant="secondary">{spot.price}</Badge>
                  </div>
                  <div className="flex items-center gap-1 mb-2">
                    <span className="text-yellow-500">★</span>
                    <span className="text-sm">{spot.rating}</span>
                  </div>
                  <div className="flex items-start gap-1 text-xs text-gray-500">
                    <MapPin size={12} className="mt-0.5 flex-shrink-0" />
                    <span>{spot.address}</span>
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </TabsContent>

        <TabsContent value="foods" className="px-4 py-4">
          <div className="space-y-3">
            {destination.foods.map((food) => (
              <Card key={food.id} className="flex gap-3 p-3 cursor-pointer hover:shadow-md transition-shadow">
                <ImageWithFallback
                  src={food.image}
                  alt={food.name}
                  className="w-24 h-24 object-cover rounded-lg flex-shrink-0"
                />
                <div className="flex-1">
                  <h3 className="font-semibold mb-1">{food.name}</h3>
                  <Badge variant="outline" className="mb-2">{food.type}</Badge>
                  <div className="flex items-center gap-1 mb-1">
                    <span className="text-yellow-500">★</span>
                    <span className="text-sm">{food.rating}</span>
                  </div>
                  <p className="text-sm text-teal-600">人均 {food.price}</p>
                </div>
              </Card>
            ))}
          </div>
        </TabsContent>

        <TabsContent value="notes" className="px-4 py-4">
          <div className="grid grid-cols-2 gap-3">
            {destination.notes.map((note) => (
              <Card key={note.id} className="cursor-pointer hover:shadow-md transition-shadow overflow-hidden">
                <ImageWithFallback
                  src={note.image}
                  alt={note.title}
                  className="w-full h-40 object-cover"
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
        </TabsContent>
      </Tabs>

      {/* 底部操作按钮 */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t p-4">
        <div className="flex gap-3">
          <Button 
            variant="outline" 
            className="flex-1"
            onClick={() => navigate('/trip-planner')}
          >
            添加到行程
          </Button>
          <Button 
            className="flex-1 bg-gradient-to-r from-teal-500 to-blue-500"
            onClick={() => navigate('/hotel-booking')}
          >
            立即预订
          </Button>
        </div>
      </div>
    </div>
  );
}
