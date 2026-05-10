import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowLeft, Share2, Download, Plus, GripVertical, Trash2, Clock, MapPin, Navigation } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Input } from '../components/ui/input';
import { Textarea } from '../components/ui/textarea';

interface TripItem {
  id: string;
  time: string;
  title: string;
  type: 'spot' | 'transport' | 'hotel' | 'food';
  address?: string;
  note?: string;
}

export default function TripEditorPage() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [tripName, setTripName] = useState('东京5日游');
  const [currentDay, setCurrentDay] = useState(1);
  const [showAddMenu, setShowAddMenu] = useState(false);

  const days = [1, 2, 3, 4, 5];

  const [dayItems, setDayItems] = useState<TripItem[]>([
    { id: '1', time: '09:00', title: '浅草寺', type: 'spot', address: '东京都台东区浅草2-3-1', note: '记得早起避开人群' },
    { id: '2', time: '12:00', title: '午餐：寿司大', type: 'food', address: '筑地市场内', note: '需要提前排队' },
    { id: '3', time: '14:00', title: '东京塔', type: 'spot', address: '东京都港区芝公园4-2-8', note: '建议买展望台门票' },
    { id: '4', time: '18:00', title: '涩谷十字路口', type: 'spot', address: '东京都涩谷区' },
    { id: '5', time: '20:00', title: '酒店：新宿希尔顿', type: 'hotel', address: '新宿区西新宿6-6-2' },
  ]);

  const getTypeIcon = (type: string) => {
    switch (type) {
      case 'spot':
        return <MapPin size={16} className="text-teal-600" />;
      case 'transport':
        return <Navigation size={16} className="text-blue-600" />;
      case 'hotel':
        return <Clock size={16} className="text-purple-600" />;
      case 'food':
        return <Clock size={16} className="text-orange-600" />;
      default:
        return <MapPin size={16} />;
    }
  };

  const handleDeleteItem = (itemId: string) => {
    setDayItems(dayItems.filter(item => item.id !== itemId));
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-24">
      {/* 顶部操作栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center justify-between">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <Input
            value={tripName}
            onChange={(e) => setTripName(e.target.value)}
            className="flex-1 mx-4 text-center font-semibold border-0 focus-visible:ring-0"
          />
          <div className="flex gap-2">
            <Button variant="ghost" size="icon">
              <Share2 size={20} />
            </Button>
            <Button variant="ghost" size="icon">
              <Download size={20} />
            </Button>
          </div>
        </div>
      </div>

      {/* 日期切换栏 */}
      <div className="bg-white border-b px-4 py-3">
        <div className="flex gap-3 overflow-x-auto scrollbar-hide">
          {days.map((day) => (
            <button
              key={day}
              onClick={() => setCurrentDay(day)}
              className={`px-6 py-2 rounded-full whitespace-nowrap transition-colors ${
                currentDay === day
                  ? 'bg-teal-600 text-white'
                  : 'bg-gray-100 text-gray-600'
              }`}
            >
              Day {day}
            </button>
          ))}
        </div>
      </div>

      {/* 行程内容区 */}
      <div className="px-4 py-4">
        <div className="relative">
          {/* 时间线 */}
          <div className="absolute left-[18px] top-8 bottom-8 w-0.5 bg-gray-200" />

          <div className="space-y-4">
            {dayItems.map((item, index) => (
              <div key={item.id} className="relative pl-12">
                {/* 时间点 */}
                <div className="absolute left-0 top-2 w-10 text-sm text-gray-500 font-medium">
                  {item.time}
                </div>
                <div className="absolute left-3 top-4 w-8 h-8 bg-white border-2 border-teal-600 rounded-full flex items-center justify-center z-10">
                  {getTypeIcon(item.type)}
                </div>

                {/* 行程项卡片 */}
                <Card className="p-4 hover:shadow-md transition-shadow group">
                  <div className="flex items-start gap-3">
                    <button className="mt-1 opacity-0 group-hover:opacity-100 transition-opacity cursor-grab">
                      <GripVertical size={18} className="text-gray-400" />
                    </button>
                    <div className="flex-1">
                      <h3 className="font-semibold mb-1">{item.title}</h3>
                      {item.address && (
                        <div className="flex items-start gap-1 text-sm text-gray-600 mb-2">
                          <MapPin size={14} className="mt-0.5 flex-shrink-0" />
                          <span>{item.address}</span>
                        </div>
                      )}
                      {item.note && (
                        <p className="text-sm text-gray-500 bg-yellow-50 p-2 rounded">
                          💡 {item.note}
                        </p>
                      )}
                    </div>
                    <button
                      onClick={() => handleDeleteItem(item.id)}
                      className="opacity-0 group-hover:opacity-100 transition-opacity text-red-500"
                    >
                      <Trash2 size={18} />
                    </button>
                  </div>
                </Card>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* 底部添加栏 */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t p-4 shadow-lg">
        <div className="grid grid-cols-4 gap-2">
          <Button
            variant="outline"
            size="sm"
            className="flex flex-col h-16 gap-1"
            onClick={() => setShowAddMenu(!showAddMenu)}
          >
            <Plus size={20} />
            <span className="text-xs">景点</span>
          </Button>
          <Button
            variant="outline"
            size="sm"
            className="flex flex-col h-16 gap-1"
            onClick={() => setShowAddMenu(!showAddMenu)}
          >
            <Plus size={20} />
            <span className="text-xs">交通</span>
          </Button>
          <Button
            variant="outline"
            size="sm"
            className="flex flex-col h-16 gap-1"
            onClick={() => setShowAddMenu(!showAddMenu)}
          >
            <Plus size={20} />
            <span className="text-xs">住宿</span>
          </Button>
          <Button
            variant="outline"
            size="sm"
            className="flex flex-col h-16 gap-1"
            onClick={() => setShowAddMenu(!showAddMenu)}
          >
            <Plus size={20} />
            <span className="text-xs">备注</span>
          </Button>
        </div>
      </div>
    </div>
  );
}
