import { useNavigate } from 'react-router-dom';
import { ArrowLeft, AlertCircle, MapPin, Phone, Navigation, Volume2, Bus } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';

export default function TripInProgressPage() {
  const navigate = useNavigate();

  const currentTrip = {
    destination: '东京',
    date: '2026-03-15',
    day: 2,
    totalDays: 5,
    weather: '晴天 22°C',
    location: '浅草寺附近',
  };

  const todaySchedule = [
    {
      id: 1,
      time: '09:00',
      title: '浅草寺',
      address: '东京都台东区浅草2-3-1',
      status: 'completed',
      note: '门票已预订，核销码：AB123456',
    },
    {
      id: 2,
      time: '12:00',
      title: '午餐：寿司大',
      address: '筑地市场内',
      status: 'current',
      note: '预约时间：12:30',
    },
    {
      id: 3,
      time: '14:00',
      title: '东京塔',
      address: '东京都港区芝公园4-2-8',
      status: 'pending',
      note: '门票已购买，核销码：CD789012',
    },
    {
      id: 4,
      time: '18:00',
      title: '涩谷十字路口',
      address: '东京都涩谷区',
      status: 'pending',
    },
  ];

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'completed':
        return 'bg-gray-100 text-gray-500';
      case 'current':
        return 'bg-teal-100 text-teal-700 border-2 border-teal-500';
      case 'pending':
        return 'bg-white';
      default:
        return 'bg-white';
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-32">
      {/* 顶部状态栏 */}
      <div className="bg-gradient-to-r from-teal-500 to-blue-500 text-white p-4">
        <button onClick={() => navigate(-1)} className="mb-4">
          <ArrowLeft size={24} />
        </button>
        <div className="flex items-start justify-between">
          <div>
            <h1 className="text-2xl font-bold mb-1">{currentTrip.destination}之旅</h1>
            <p className="text-sm opacity-90">Day {currentTrip.day} / {currentTrip.totalDays} · {currentTrip.date}</p>
          </div>
          <div className="text-right">
            <p className="text-sm opacity-90">{currentTrip.weather}</p>
            <div className="flex items-center gap-1 text-sm mt-1">
              <MapPin size={14} />
              <span>{currentTrip.location}</span>
            </div>
          </div>
        </div>
        <Button
          size="sm"
          variant="destructive"
          className="mt-3 bg-red-500 hover:bg-red-600"
          onClick={() => alert('紧急求助功能')}
        >
          <AlertCircle size={16} className="mr-1" />
          紧急求助
        </Button>
      </div>

      {/* 今日行程时间轴 */}
      <div className="px-4 py-6">
        <h2 className="text-lg font-semibold mb-4">今日行程</h2>
        <div className="relative">
          {/* 时间线 */}
          <div className="absolute left-[18px] top-8 bottom-8 w-0.5 bg-gray-200" />

          <div className="space-y-4">
            {todaySchedule.map((item, index) => (
              <div key={item.id} className="relative pl-12">
                {/* 时间点 */}
                <div className="absolute left-0 top-2 w-10 text-sm text-gray-500 font-medium">
                  {item.time}
                </div>
                <div
                  className={`absolute left-3 top-4 w-8 h-8 rounded-full flex items-center justify-center z-10 ${
                    item.status === 'completed'
                      ? 'bg-gray-300'
                      : item.status === 'current'
                      ? 'bg-teal-500 animate-pulse'
                      : 'bg-white border-2 border-gray-300'
                  }`}
                >
                  {item.status === 'completed' && <span className="text-white">✓</span>}
                  {item.status === 'current' && <MapPin size={16} className="text-white" />}
                </div>

                {/* 行程项卡片 */}
                <Card className={`p-4 ${getStatusColor(item.status)}`}>
                  <div className="flex items-start justify-between mb-2">
                    <h3 className="font-semibold">{item.title}</h3>
                    {item.status === 'current' && (
                      <Badge className="bg-teal-600">进行中</Badge>
                    )}
                  </div>
                  <div className="flex items-start gap-1 text-sm text-gray-600 mb-2">
                    <MapPin size={14} className="mt-0.5 flex-shrink-0" />
                    <span>{item.address}</span>
                  </div>
                  {item.note && (
                    <p className="text-sm text-gray-500 bg-yellow-50 p-2 rounded mb-3">
                      💡 {item.note}
                    </p>
                  )}
                  {item.status !== 'completed' && (
                    <div className="flex gap-2">
                      <Button size="sm" variant="outline" className="flex-1">
                        <Navigation size={14} className="mr-1" />
                        导航
                      </Button>
                      {item.status === 'current' && (
                        <Button size="sm" className="flex-1 bg-teal-600">
                          标记完成
                        </Button>
                      )}
                    </div>
                  )}
                </Card>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* 底部功能栏 */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t shadow-lg">
        <div className="grid grid-cols-4 gap-2 p-4">
          <Button variant="outline" size="sm" className="flex flex-col h-16 gap-1">
            <Navigation size={20} className="text-teal-600" />
            <span className="text-xs">导航</span>
          </Button>
          <Button variant="outline" size="sm" className="flex flex-col h-16 gap-1">
            <Volume2 size={20} className="text-blue-600" />
            <span className="text-xs">语音讲解</span>
          </Button>
          <Button variant="outline" size="sm" className="flex flex-col h-16 gap-1">
            <Bus size={20} className="text-green-600" />
            <span className="text-xs">本地交通</span>
          </Button>
          <Button variant="outline" size="sm" className="flex flex-col h-16 gap-1">
            <Phone size={20} className="text-purple-600" />
            <span className="text-xs">客服</span>
          </Button>
        </div>
      </div>
    </div>
  );
}
