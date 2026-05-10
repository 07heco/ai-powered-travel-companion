import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, MapPin, Navigation, Search, SlidersHorizontal, Star, Heart, Phone, Clock, DollarSign, UtensilsCrossed, Coffee, ShoppingBag, Building2, Ticket, Landmark } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Input } from '../components/ui/input';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../components/ui/tabs';

export default function NearbyServicesPage() {
  const navigate = useNavigate();
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [currentLocation, setCurrentLocation] = useState('天安门广场');

  const categories = [
    { id: 'all', label: '全部', icon: MapPin },
    { id: 'food', label: '美食', icon: UtensilsCrossed },
    { id: 'scenic', label: '景点', icon: Landmark },
    { id: 'hotel', label: '住宿', icon: Building2 },
    { id: 'shopping', label: '购物', icon: ShoppingBag },
    { id: 'entertainment', label: '娱乐', icon: Ticket },
  ];

  const nearbyPlaces = [
    {
      id: 1,
      name: '故宫博物院',
      category: 'scenic',
      rating: 4.9,
      reviews: 98523,
      distance: '0.8km',
      image: 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d',
      price: '60',
      openTime: '08:30-17:00',
      tags: ['必去景点', '世界遗产', '历史文化'],
      description: '明清两代的皇家宫殿，世界上现存规模最大、保存最完整的木质结构古建筑',
      phone: '010-85007938',
      address: '北京市东城区景山前街4号'
    },
    {
      id: 2,
      name: '全聚德烤鸭店（前门店）',
      category: 'food',
      rating: 4.7,
      reviews: 15420,
      distance: '1.2km',
      image: 'https://images.unsplash.com/photo-1625938145312-c247f983fa59',
      price: '168',
      openTime: '10:00-22:00',
      tags: ['老字号', '北京烤鸭', '必吃美食'],
      description: '始创于1864年的百年老店，以独特的挂炉烤鸭技艺闻名',
      phone: '010-67022786',
      address: '北京市东城区前门西大街14号'
    },
    {
      id: 3,
      name: '北京饭店',
      category: 'hotel',
      rating: 4.8,
      reviews: 8934,
      distance: '1.5km',
      image: 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4',
      price: '1288',
      openTime: '全天营业',
      tags: ['五星酒店', '地理位置好', '商务出行'],
      description: '位于长安街的标志性酒店，紧邻天安门广场和王府井',
      phone: '010-65137766',
      address: '北京市东城区东长安街33号'
    },
    {
      id: 4,
      name: '王府井步行街',
      category: 'shopping',
      rating: 4.6,
      reviews: 23456,
      distance: '1.8km',
      image: 'https://images.unsplash.com/photo-1591347715029-c9ab95038697',
      price: '0',
      openTime: '09:00-22:00',
      tags: ['购物天堂', '小吃一条街', '网红打卡'],
      description: '北京最著名的商业街之一，汇集了众多百货商场和特色小吃',
      phone: '010-65282288',
      address: '北京市东城区王府井大街'
    },
    {
      id: 5,
      name: '南锣鼓巷',
      category: 'entertainment',
      rating: 4.5,
      reviews: 18790,
      distance: '3.2km',
      image: 'https://images.unsplash.com/photo-1580837119756-563d608dd119',
      price: '0',
      openTime: '全天开放',
      tags: ['胡同文化', '文艺小店', '咖啡美食'],
      description: '北京最古老的街区之一，两侧胡同里分布着众多特色小店和餐厅',
      phone: '无',
      address: '北京市东城区南锣鼓巷胡同'
    },
    {
      id: 6,
      name: '国家大剧院',
      category: 'entertainment',
      rating: 4.9,
      reviews: 12340,
      distance: '2.1km',
      image: 'https://images.unsplash.com/photo-1574870111867-089730e5a72b',
      price: '150',
      openTime: '参观10:00-17:00',
      tags: ['建筑地标', '艺术殿堂', '演出场所'],
      description: '中国国家表演艺术的最高殿堂，建筑本身就是一件艺术品',
      phone: '010-66550000',
      address: '北京市西城区西长安街2号'
    },
    {
      id: 7,
      name: '鹿港小镇（王府井店）',
      category: 'food',
      rating: 4.6,
      reviews: 6789,
      distance: '1.9km',
      image: 'https://images.unsplash.com/photo-1562007908-17c67e878c88',
      price: '88',
      openTime: '11:00-22:00',
      tags: ['台湾菜', '环境好', '适合聚餐'],
      description: '正宗台湾风味餐厅，招牌三杯鸡和卤肉饭深受好评',
      phone: '010-65281234',
      address: '北京市东城区王府井大街88号'
    },
    {
      id: 8,
      name: '景山公园',
      category: 'scenic',
      rating: 4.7,
      reviews: 15670,
      distance: '1.3km',
      image: 'https://images.unsplash.com/photo-1509023464722-18d996393ca8',
      price: '2',
      openTime: '06:00-21:00',
      tags: ['俯瞰故宫', '登高望远', '性价比高'],
      description: '明清两代的皇家园林，登上万春亭可俯瞰故宫全景',
      phone: '010-64038098',
      address: '北京市西城区景山西街44号'
    },
  ];

  const filteredPlaces = selectedCategory === 'all' 
    ? nearbyPlaces 
    : nearbyPlaces.filter(place => place.category === selectedCategory);

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center gap-3">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold flex-1">附近</h1>
          <Button variant="ghost" size="icon">
            <SlidersHorizontal size={20} />
          </Button>
        </div>
        
        {/* 当前位置 */}
        <div className="px-4 pb-3 flex items-center justify-between">
          <div className="flex items-center gap-2 text-sm">
            <MapPin size={16} className="text-teal-500" />
            <span className="text-gray-700">{currentLocation}</span>
          </div>
          <Button 
            variant="outline" 
            size="sm"
            className="text-teal-600 border-teal-600"
          >
            <Navigation size={14} className="mr-1" />
            重新定位
          </Button>
        </div>

        {/* 搜索栏 */}
        <div className="px-4 pb-3">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
            <Input
              type="text"
              placeholder="搜索附近的地点、美食、景点..."
              className="pl-10 h-10 bg-gray-100 border-0"
            />
          </div>
        </div>
      </div>

      {/* 分类标签 */}
      <div className="bg-white px-4 py-3 mb-2">
        <div className="flex gap-3 overflow-x-auto scrollbar-hide">
          {categories.map((category) => {
            const Icon = category.icon;
            return (
              <button
                key={category.id}
                onClick={() => setSelectedCategory(category.id)}
                className={`flex-shrink-0 px-4 py-2 rounded-full flex items-center gap-2 transition-colors ${
                  selectedCategory === category.id
                    ? 'bg-gradient-to-r from-teal-500 to-blue-500 text-white'
                    : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                }`}
              >
                <Icon size={16} />
                <span className="text-sm font-medium">{category.label}</span>
              </button>
            );
          })}
        </div>
      </div>

      {/* 地点列表 */}
      <div className="px-4 space-y-3">
        <div className="text-sm text-gray-500 mb-2">
          为您找到 {filteredPlaces.length} 个地点
        </div>
        
        {filteredPlaces.map((place) => (
          <Card 
            key={place.id} 
            className="overflow-hidden cursor-pointer hover:shadow-lg transition-shadow"
            onClick={() => navigate(`/destination/${place.id}`)}
          >
            <div className="flex gap-3 p-3">
              {/* 图片 */}
              <div className="w-28 h-28 rounded-lg overflow-hidden flex-shrink-0">
                <ImageWithFallback
                  src={place.image}
                  alt={place.name}
                  className="w-full h-full object-cover"
                />
              </div>

              {/* 信息 */}
              <div className="flex-1 flex flex-col justify-between">
                <div>
                  <h3 className="font-semibold text-base mb-1 line-clamp-1">{place.name}</h3>
                  
                  {/* 评分和距离 */}
                  <div className="flex items-center gap-3 mb-2">
                    <div className="flex items-center gap-1">
                      <Star size={14} className="text-yellow-500 fill-yellow-500" />
                      <span className="text-sm font-medium">{place.rating}</span>
                      <span className="text-xs text-gray-500">({place.reviews})</span>
                    </div>
                    <div className="flex items-center gap-1 text-teal-600">
                      <Navigation size={12} />
                      <span className="text-xs">{place.distance}</span>
                    </div>
                  </div>

                  {/* 标签 */}
                  <div className="flex flex-wrap gap-1 mb-2">
                    {place.tags.slice(0, 2).map((tag, index) => (
                      <Badge key={index} variant="outline" className="text-xs">
                        {tag}
                      </Badge>
                    ))}
                  </div>
                </div>

                {/* 价格和营业时间 */}
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-1">
                    {place.category === 'scenic' || place.category === 'food' ? (
                      <>
                        <DollarSign size={14} className="text-orange-500" />
                        <span className="text-sm font-semibold text-orange-600">
                          ¥{place.price}{place.category === 'food' && '/人'}
                        </span>
                      </>
                    ) : (
                      <span className="text-sm font-semibold text-orange-600">
                        ¥{place.price}起
                      </span>
                    )}
                  </div>
                  <div className="flex items-center gap-1 text-xs text-gray-500">
                    <Clock size={12} />
                    <span>{place.openTime}</span>
                  </div>
                </div>
              </div>
            </div>

            {/* 描述 */}
            <div className="px-3 pb-3 border-t pt-2">
              <p className="text-sm text-gray-600 line-clamp-2">{place.description}</p>
              
              {/* 操作按钮 */}
              <div className="flex gap-2 mt-3">
                <Button 
                  variant="outline" 
                  size="sm" 
                  className="flex-1"
                  onClick={(e) => {
                    e.stopPropagation();
                    window.open(`tel:${place.phone}`);
                  }}
                >
                  <Phone size={14} className="mr-1" />
                  联系
                </Button>
                <Button 
                  variant="outline" 
                  size="sm" 
                  className="flex-1"
                  onClick={(e) => {
                    e.stopPropagation();
                    // 导航到地图应用
                  }}
                >
                  <Navigation size={14} className="mr-1" />
                  导航
                </Button>
                <Button 
                  variant="ghost" 
                  size="sm"
                  onClick={(e) => {
                    e.stopPropagation();
                  }}
                >
                  <Heart size={16} />
                </Button>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}
