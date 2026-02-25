import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Search, SlidersHorizontal, Plane, Loader2 } from 'lucide-react';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Alert, AlertDescription, AlertTitle } from '../components/ui/alert';
import { searchService, bookingService } from '../services';

export default function FlightBookingPage() {
  const navigate = useNavigate();
  const [departure, setDeparture] = useState('北京');
  const [arrival, setArrival] = useState('东京');
  const [date, setDate] = useState('2026-03-15');
  const [flights, setFlights] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const searchFlights = async () => {
    setLoading(true);
    setError(null);
    try {
      const results = await searchService.searchFlights({
        origin: departure,
        destination: arrival,
        departureDate: date,
        passengers: 1,
        class: 'economy',
      });
      setFlights(results);
    } catch (err) {
      setError('搜索航班失败，请稍后重试');
      console.error('Flight search error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleBook = async (flight: any) => {
    try {
      await bookingService.createBooking({
        type: 'flight',
        details: {
          flightId: flight.id,
          airline: flight.airline,
          flightNumber: flight.flightNumber,
          departureAirport: flight.departureAirport,
          arrivalAirport: flight.arrivalAirport,
          departureTime: flight.departureTime,
          arrivalTime: flight.arrivalTime,
          price: flight.price,
          date: date,
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
          <h1 className="text-lg font-semibold flex-1">机票预订</h1>
          <Button variant="ghost" size="icon">
            <SlidersHorizontal size={20} />
          </Button>
        </div>
      </div>

      {/* 搜索区域 */}
      <div className="bg-white p-4 mb-4">
        <div className="space-y-3">
          <div className="flex items-center gap-3">
            <div className="flex-1">
              <label className="text-sm text-gray-600 mb-1 block">出发地</label>
              <Input
                value={departure}
                onChange={(e) => setDeparture(e.target.value)}
                className="h-10"
              />
            </div>
            <button className="mt-6 w-10 h-10 bg-gray-100 rounded-full flex items-center justify-center">
              ⇄
            </button>
            <div className="flex-1">
              <label className="text-sm text-gray-600 mb-1 block">目的地</label>
              <Input
                value={arrival}
                onChange={(e) => setArrival(e.target.value)}
                className="h-10"
              />
            </div>
          </div>
          <div>
            <label className="text-sm text-gray-600 mb-1 block">出行日期</label>
            <Input
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              className="h-10"
            />
          </div>
          <Button 
            className="w-full bg-gradient-to-r from-teal-500 to-blue-500"
            onClick={searchFlights}
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
                搜索航班
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

      {/* 筛选栏 */}
      <div className="bg-white px-4 py-3 mb-4">
        <div className="flex gap-3 overflow-x-auto scrollbar-hide">
          <Badge variant="outline" className="whitespace-nowrap cursor-pointer">
            价格低 - 高
          </Badge>
          <Badge variant="outline" className="whitespace-nowrap cursor-pointer">
            时间早 - 晚
          </Badge>
          <Badge variant="outline" className="whitespace-nowrap cursor-pointer">
            耗时最短
          </Badge>
          <Badge variant="outline" className="whitespace-nowrap cursor-pointer">
            只看直飞
          </Badge>
        </div>
      </div>

      {/* 航班列表 */}
      <div className="px-4 space-y-3">
        {loading ? (
          <div className="flex justify-center items-center py-10">
            <Loader2 size={32} className="animate-spin text-teal-600" />
          </div>
        ) : flights.length === 0 ? (
          <div className="text-center py-10">
            <p className="text-gray-500">暂无航班数据</p>
          </div>
        ) : (
          flights.map((flight) => (
            <Card key={flight.id} className="p-4 hover:shadow-md transition-shadow">
              <div className="flex items-start justify-between mb-3">
                <div>
                  <h3 className="font-semibold">{flight.airline}</h3>
                  <p className="text-sm text-gray-500">{flight.flightNumber}</p>
                </div>
                <div className="text-right">
                  <p className="text-xl font-bold text-teal-600">¥{flight.price}</p>
                  <p className="text-xs text-gray-500">经济舱</p>
                </div>
              </div>

              <div className="flex items-center justify-between mb-3">
                <div className="flex-1">
                  <p className="text-2xl font-bold">{new Date(flight.departureTime).toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })}</p>
                  <p className="text-sm text-gray-600">{flight.departureAirport}</p>
                </div>
                <div className="flex-1 px-4 text-center">
                  <div className="flex items-center justify-center gap-2 mb-1">
                    <div className="flex-1 h-px bg-gray-300" />
                    <Plane size={16} className="text-gray-400" />
                    <div className="flex-1 h-px bg-gray-300" />
                  </div>
                  <p className="text-xs text-gray-500">{flight.duration}</p>
                </div>
                <div className="flex-1 text-right">
                  <p className="text-2xl font-bold">{new Date(flight.arrivalTime).toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })}</p>
                  <p className="text-sm text-gray-600">{flight.arrivalAirport}</p>
                </div>
              </div>

              <div className="flex items-center justify-between pt-3 border-t">
                <p className="text-sm text-gray-500">
                  剩余 <span className="text-orange-500 font-medium">{Math.floor(Math.random() * 20) + 1}</span> 张
                </p>
                <Button 
                  size="sm" 
                  className="bg-gradient-to-r from-teal-500 to-blue-500"
                  onClick={() => handleBook(flight)}
                >
                  预订
                </Button>
              </div>
            </Card>
          ))
        )}
      </div>
    </div>
  );
}
