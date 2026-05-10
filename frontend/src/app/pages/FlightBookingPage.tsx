import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Search, SlidersHorizontal, Plane, Clock } from 'lucide-react';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';

export default function FlightBookingPage() {
  const navigate = useNavigate();
  const [departure, setDeparture] = useState('北京');
  const [arrival, setArrival] = useState('东京');
  const [date, setDate] = useState('2026-03-15');

  const flights = [
    {
      id: 1,
      airline: '中国国际航空',
      flightNo: 'CA183',
      departTime: '08:30',
      arriveTime: '12:45',
      departAirport: '北京首都机场 T3',
      arriveAirport: '东京成田机场 T1',
      duration: '3小时15分',
      price: 2580,
      cabin: '经济舱',
      remaining: 8,
    },
    {
      id: 2,
      airline: '全日空航空',
      flightNo: 'NH964',
      departTime: '10:15',
      arriveTime: '14:30',
      departAirport: '北京首都机场 T3',
      arriveAirport: '东京成田机场 T1',
      duration: '3小时15分',
      price: 2880,
      cabin: '经济舱',
      remaining: 15,
    },
    {
      id: 3,
      airline: '日本航空',
      flightNo: 'JL020',
      departTime: '13:20',
      arriveTime: '17:40',
      departAirport: '北京首都机场 T3',
      arriveAirport: '东京成田机场 T2',
      duration: '3小时20分',
      price: 3180,
      cabin: '经济舱',
      remaining: 6,
    },
    {
      id: 4,
      airline: '中国东方航空',
      flightNo: 'MU525',
      departTime: '15:45',
      arriveTime: '20:10',
      departAirport: '北京首都机场 T2',
      arriveAirport: '东京羽田机场 T3',
      duration: '3小时25分',
      price: 2380,
      cabin: '经济舱',
      remaining: 20,
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
          <Button className="w-full bg-gradient-to-r from-teal-500 to-blue-500">
            <Search size={18} className="mr-2" />
            搜索航班
          </Button>
        </div>
      </div>

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
        {flights.map((flight) => (
          <Card key={flight.id} className="p-4 hover:shadow-md transition-shadow">
            <div className="flex items-start justify-between mb-3">
              <div>
                <h3 className="font-semibold">{flight.airline}</h3>
                <p className="text-sm text-gray-500">{flight.flightNo}</p>
              </div>
              <div className="text-right">
                <p className="text-xl font-bold text-teal-600">¥{flight.price}</p>
                <p className="text-xs text-gray-500">{flight.cabin}</p>
              </div>
            </div>

            <div className="flex items-center justify-between mb-3">
              <div className="flex-1">
                <p className="text-2xl font-bold">{flight.departTime}</p>
                <p className="text-sm text-gray-600">{flight.departAirport}</p>
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
                <p className="text-2xl font-bold">{flight.arriveTime}</p>
                <p className="text-sm text-gray-600">{flight.arriveAirport}</p>
              </div>
            </div>

            <div className="flex items-center justify-between pt-3 border-t">
              <p className="text-sm text-gray-500">
                剩余 <span className="text-orange-500 font-medium">{flight.remaining}</span> 张
              </p>
              <Button size="sm" className="bg-gradient-to-r from-teal-500 to-blue-500">
                预订
              </Button>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}
