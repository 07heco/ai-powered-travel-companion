import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Search, SlidersHorizontal, MapPin, Star, Wifi, Coffee, Car } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Tabs, TabsList, TabsTrigger } from '../components/ui/tabs';

export default function HotelBookingPage() {
  const navigate = useNavigate();
  const [destination, setDestination] = useState('东京');
  const [checkIn, setCheckIn] = useState('2026-03-15');
  const [checkOut, setCheckOut] = useState('2026-03-20');
  const [viewMode, setViewMode] = useState<'list' | 'map'>('list');

  const hotels = [
    {
      id: 1,
      name: '东京新宿希尔顿酒店',
      image: 'https://images.unsplash.com/photo-1629140727571-9b5c6f6267b4',
      rating: 4.8,
      reviews: 2456,
      price: 1280,
      location: '新宿区西新宿6-6-2',
      distance: '0.8km',
      amenities: ['免费WiFi', '免费早餐', '健身房', '游泳池'],
      tags: ['近地铁', '商务推荐'],
      remaining: 3,
    },
    {
      id: 2,
      name: '东京银座凯悦酒店',
      image: 'https://images.unsplash.com/photo-1637730826933-54287f79e1c3',
      rating: 4.9,
      reviews: 1823,
      price: 1580,
      location: '中央区银座6-13-16',
      distance: '1.2km',
      amenities: ['免费WiFi', '健身房', '商务中心', '接送服务'],
      tags: ['豪华', '购物方便'],
      remaining: 5,
    },
    {
      id: 3,
      name: '东京浅草民宿',
      image: 'https://images.unsplash.com/photo-1731336478850-6bce7235e320',
      rating: 4.6,
      reviews: 892,
      price: 680,
      location: '台东区浅草2-8-5',
      distance: '2.5km',
      amenities: ['免费WiFi', '厨房', '洗衣机'],
      tags: ['性价比高', '文化体验'],
      remaining: 2,
    },
    {
      id: 4,
      name: '东京台场温泉度假村',
      image: 'https://images.unsplash.com/photo-1729717949782-f40c4a07e3c4',
      rating: 4.7,
      reviews: 1567,
      price: 980,
      location: '港区台场1-6-1',
      distance: '5.8km',
      amenities: ['免费WiFi', '温泉', '海景', '游泳池'],
      tags: ['度假', '家庭友好'],
      remaining: 8,
    },
  ];

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center gap-3">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold flex-1">住宿预订</h1>
          <Button variant="ghost" size="icon">
            <SlidersHorizontal size={20} />
          </Button>
        </div>
      </div>

      {/* 搜索区域 */}
      <div className="bg-white p-4 mb-4">
        <div className="space-y-3">
          <div>
            <label className="text-sm text-gray-600 mb-1 block">目的地</label>
            <Input
              value={destination}
              onChange={(e) => setDestination(e.target.value)}
              className="h-10"
            />
          </div>
          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="text-sm text-gray-600 mb-1 block">入住日期</label>
              <Input
                type="date"
                value={checkIn}
                onChange={(e) => setCheckIn(e.target.value)}
                className="h-10"
              />
            </div>
            <div>
              <label className="text-sm text-gray-600 mb-1 block">退房日期</label>
              <Input
                type="date"
                value={checkOut}
                onChange={(e) => setCheckOut(e.target.value)}
                className="h-10"
              />
            </div>
          </div>
          <Button className="w-full bg-gradient-to-r from-teal-500 to-blue-500">
            <Search size={18} className="mr-2" />
            搜索酒店
          </Button>
        </div>
      </div>

      {/* 视图切换 */}
      <div className="bg-white px-4 py-3 mb-4 flex items-center justify-between">
        <Tabs value={viewMode} onValueChange={(v) => setViewMode(v as 'list' | 'map')}>
          <TabsList>
            <TabsTrigger value="list">列表</TabsTrigger>
            <TabsTrigger value="map">地图</TabsTrigger>
          </TabsList>
        </Tabs>
        <div className="flex gap-2">
          <Badge variant="outline" className="cursor-pointer">价格排序</Badge>
          <Badge variant="outline" className="cursor-pointer">评分排序</Badge>
        </div>
      </div>

      {/* 酒店列表 */}
      <div className="px-4 space-y-4">
        {hotels.map((hotel) => (
          <Card key={hotel.id} className="overflow-hidden hover:shadow-md transition-shadow">
            <div className="flex gap-3">
              <ImageWithFallback
                src={hotel.image}
                alt={hotel.name}
                className="w-32 h-full object-cover flex-shrink-0"
              />
              <div className="flex-1 p-3">
                <div className="flex items-start justify-between mb-2">
                  <h3 className="font-semibold text-base">{hotel.name}</h3>
                  <div className="text-right">
                    <p className="text-lg font-bold text-teal-600">¥{hotel.price}</p>
                    <p className="text-xs text-gray-500">起/晚</p>
                  </div>
                </div>

                <div className="flex items-center gap-2 mb-2">
                  <div className="flex items-center gap-1">
                    <Star size={14} className="fill-yellow-400 text-yellow-400" />
                    <span className="text-sm font-medium">{hotel.rating}</span>
                  </div>
                  <span className="text-xs text-gray-500">({hotel.reviews}条评价)</span>
                </div>

                <div className="flex items-start gap-1 mb-2 text-xs text-gray-600">
                  <MapPin size={12} className="mt-0.5 flex-shrink-0" />
                  <span>{hotel.location} · {hotel.distance}</span>
                </div>

                <div className="flex flex-wrap gap-1 mb-2">
                  {hotel.tags.map((tag) => (
                    <Badge key={tag} variant="secondary" className="text-xs">
                      {tag}
                    </Badge>
                  ))}
                </div>

                <div className="flex items-center gap-3 text-xs text-gray-500 mb-3">
                  {hotel.amenities.slice(0, 3).map((amenity, index) => (
                    <span key={index}>✓ {amenity}</span>
                  ))}
                </div>

                <div className="flex items-center justify-between">
                  <p className="text-xs text-orange-500">
                    仅剩 {hotel.remaining} 间
                  </p>
                  <Button size="sm" className="bg-gradient-to-r from-teal-500 to-blue-500">
                    查看详情
                  </Button>
                </div>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}
