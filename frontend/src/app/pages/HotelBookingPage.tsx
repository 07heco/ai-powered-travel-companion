import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Search, SlidersHorizontal, MapPin, Star, Loader2 } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Tabs, TabsList, TabsTrigger } from '../components/ui/tabs';
import { Alert, AlertDescription, AlertTitle } from '../components/ui/alert';
import { searchService, bookingService } from '../services';

export default function HotelBookingPage() {
  const navigate = useNavigate();
  const [destination, setDestination] = useState('东京');
  const [checkIn, setCheckIn] = useState('2026-03-15');
  const [checkOut, setCheckOut] = useState('2026-03-20');
  const [viewMode, setViewMode] = useState<'list' | 'map'>('list');
  const [hotels, setHotels] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const searchHotels = async () => {
    setLoading(true);
    setError(null);
    try {
      const results = await searchService.searchHotels({
        destination: destination,
        checkInDate: checkIn,
        checkOutDate: checkOut,
        guests: 2,
        rooms: 1,
      });
      setHotels(results);
    } catch (err) {
      setError('搜索酒店失败，请稍后重试');
      console.error('Hotel search error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleBook = async (hotel: any) => {
    try {
      await bookingService.createBooking({
        type: 'hotel',
        details: {
          hotelId: hotel.id,
          name: hotel.name,
          location: hotel.location,
          pricePerNight: hotel.pricePerNight,
          checkInDate: checkIn,
          checkOutDate: checkOut,
          imageUrl: hotel.imageUrl,
        },
      });
      alert('预订成功！');
    } catch (err) {
      alert('预订失败，请稍后重试');
      console.error('Booking error:', err);
    }
  };

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
          <Button 
            className="w-full bg-gradient-to-r from-teal-500 to-blue-500"
            onClick={searchHotels}
            disabled={loading}
          >
            {loading ? (
              <>
                <Loader2 size={18} className="mr-2 animate-spin" />
                搜索中...
              </>
            ) : (
              <>
                <Search size={18} className="mr-2" />
                搜索酒店
              </>
            )}
          </Button>
        </div>
      </div>

      {/* 错误提示 */}
      {error && (
        <div className="px-4 mb-4">
          <Alert variant="destructive">
            <AlertTitle>搜索失败</AlertTitle>
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        </div>
      )}

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
        {loading ? (
          <div className="flex justify-center items-center py-10">
            <Loader2 size={32} className="animate-spin text-teal-600" />
          </div>
        ) : hotels.length === 0 ? (
          <div className="text-center py-10">
            <p className="text-gray-500">暂无酒店数据</p>
          </div>
        ) : (
          hotels.map((hotel) => (
            <Card key={hotel.id} className="overflow-hidden hover:shadow-md transition-shadow">
              <div className="flex gap-3">
                <ImageWithFallback
                  src={hotel.imageUrl}
                  alt={hotel.name}
                  className="w-32 h-full object-cover flex-shrink-0"
                />
                <div className="flex-1 p-3">
                  <div className="flex items-start justify-between mb-2">
                    <h3 className="font-semibold text-base">{hotel.name}</h3>
                    <div className="text-right">
                      <p className="text-lg font-bold text-teal-600">¥{hotel.pricePerNight}</p>
                      <p className="text-xs text-gray-500">起/晚</p>
                    </div>
                  </div>

                  <div className="flex items-center gap-2 mb-2">
                    <div className="flex items-center gap-1">
                      <Star size={14} className="fill-yellow-400 text-yellow-400" />
                      <span className="text-sm font-medium">{hotel.rating}</span>
                    </div>
                    <span className="text-xs text-gray-500">(100+条评价)</span>
                  </div>

                  <div className="flex items-start gap-1 mb-2 text-xs text-gray-600">
                    <MapPin size={12} className="mt-0.5 flex-shrink-0" />
                    <span>{hotel.location}</span>
                  </div>

                  <div className="flex flex-wrap gap-1 mb-2">
                    <Badge variant="secondary" className="text-xs">近地铁</Badge>
                    <Badge variant="secondary" className="text-xs">免费WiFi</Badge>
                  </div>

                  <div className="flex items-center gap-3 text-xs text-gray-500 mb-3">
                    {hotel.amenities?.slice(0, 3).map((amenity: any, index: number) => (
                      <span key={index}>✓ {amenity}</span>
                    ))}
                  </div>

                  <div className="flex items-center justify-between">
                    <p className="text-xs text-orange-500">
                      仅剩 {Math.floor(Math.random() * 10) + 1} 间
                    </p>
                    <Button 
                      size="sm" 
                      className="bg-gradient-to-r from-teal-500 to-blue-500"
                      onClick={() => handleBook(hotel)}
                    >
                      预订
                    </Button>
                  </div>
                </div>
              </div>
            </Card>
          ))
        )}
      </div>
    </div>
  );
}
