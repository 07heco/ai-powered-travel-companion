import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Plus, Calendar, Users, Heart } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import BottomNavigation from '../components/BottomNavigation';

export default function TripPlannerPage() {
  const navigate = useNavigate();
  const [showCreateForm, setShowCreateForm] = useState(false);

  const interests = ['美食', '文化', '自然', '购物', '冒险', '亲子', '摄影', '历史'];

  const templates = [
    { id: 1, title: '东京5日深度游', days: 5, spots: 15, theme: '文化', image: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63', suitable: '首次访问' },
    { id: 2, title: '巴黎7日浪漫之旅', days: 7, spots: 18, theme: '蜜月', image: 'https://images.unsplash.com/photo-1595441857632-71570ef36580', suitable: '情侣' },
    { id: 3, title: '冰岛10日极光追寻', days: 10, spots: 12, theme: '探险', image: 'https://images.unsplash.com/photo-1488415032361-b7e238421f1b', suitable: '摄影爱好者' },
    { id: 4, title: '新加坡4日亲子游', days: 4, spots: 10, theme: '亲子', image: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63', suitable: '家庭出行' },
  ];

  const myTrips = [
    { id: 1, destination: '东京', startDate: '2026-03-15', endDate: '2026-03-20', status: '待出行', image: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63' },
    { id: 2, destination: '巴黎', startDate: '2026-05-10', endDate: '2026-05-17', status: '待出行', image: 'https://images.unsplash.com/photo-1595441857632-71570ef36580' },
    { id: 3, destination: '京都', startDate: '2025-11-20', endDate: '2025-11-25', status: '已完成', image: 'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2' },
  ];

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold">行程规划</h1>
          <Button
            size="sm"
            className="bg-gradient-to-r from-teal-500 to-blue-500"
            onClick={() => setShowCreateForm(!showCreateForm)}
          >
            <Plus size={18} className="mr-1" />
            创建行程
          </Button>
        </div>
      </div>

      {/* 创建行程表单 */}
      {showCreateForm && (
        <div className="bg-white p-4 mb-4 border-b-4 border-teal-500">
          <h2 className="font-semibold mb-4">智能生成行程</h2>
          <div className="space-y-3">
            <div>
              <label className="text-sm text-gray-600 mb-1 block">目的地</label>
              <Input placeholder="输入目的地城市" />
            </div>
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-sm text-gray-600 mb-1 block">出行天数</label>
                <Input type="number" placeholder="天数" defaultValue="5" />
              </div>
              <div>
                <label className="text-sm text-gray-600 mb-1 block">同行人数</label>
                <Input type="number" placeholder="人数" defaultValue="2" />
              </div>
            </div>
            <div>
              <label className="text-sm text-gray-600 mb-2 block">兴趣偏好（多选）</label>
              <div className="flex flex-wrap gap-2">
                {interests.map((interest) => (
                  <Badge
                    key={interest}
                    variant="outline"
                    className="cursor-pointer hover:bg-teal-50 hover:border-teal-500"
                  >
                    {interest}
                  </Badge>
                ))}
              </div>
            </div>
            <div className="flex gap-2 pt-2">
              <Button className="flex-1 bg-gradient-to-r from-teal-500 to-blue-500" onClick={() => navigate('/trip-editor/new')}>
                智能生成
              </Button>
              <Button variant="outline" className="flex-1" onClick={() => setShowCreateForm(false)}>
                取消
              </Button>
            </div>
          </div>
        </div>
      )}

      {/* 热门行程模板 */}
      <div className="px-4 py-4">
        <h2 className="text-lg font-semibold mb-3">热门行程模板</h2>
        <div className="space-y-3">
          {templates.map((template) => (
            <Card
              key={template.id}
              className="flex gap-3 p-3 cursor-pointer hover:shadow-md transition-shadow"
              onClick={() => navigate('/trip-editor/new')}
            >
              <ImageWithFallback
                src={template.image}
                alt={template.title}
                className="w-28 h-28 object-cover rounded-lg flex-shrink-0"
              />
              <div className="flex-1">
                <h3 className="font-semibold mb-2">{template.title}</h3>
                <div className="flex flex-wrap gap-2 mb-2">
                  <Badge variant="secondary">{template.days}天</Badge>
                  <Badge variant="secondary">{template.spots}个景点</Badge>
                  <Badge variant="outline" className="text-teal-600">{template.theme}</Badge>
                </div>
                <p className="text-xs text-gray-500">适合：{template.suitable}</p>
              </div>
              <button className="self-start">
                <Heart size={20} className="text-gray-400" />
              </button>
            </Card>
          ))}
        </div>
      </div>

      {/* 我的行程 */}
      <div className="px-4 py-4">
        <h2 className="text-lg font-semibold mb-3">我的行程</h2>
        <div className="space-y-3">
          {myTrips.map((trip) => (
            <Card
              key={trip.id}
              className="flex gap-3 p-3 cursor-pointer hover:shadow-md transition-shadow"
              onClick={() => navigate(`/trip-editor/${trip.id}`)}
            >
              <ImageWithFallback
                src={trip.image}
                alt={trip.destination}
                className="w-20 h-20 object-cover rounded-lg flex-shrink-0"
              />
              <div className="flex-1">
                <div className="flex items-start justify-between mb-2">
                  <h3 className="font-semibold">{trip.destination}</h3>
                  <Badge variant={trip.status === '待出行' ? 'default' : 'secondary'}>
                    {trip.status}
                  </Badge>
                </div>
                <div className="flex items-center gap-2 text-sm text-gray-600">
                  <Calendar size={14} />
                  <span>{trip.startDate} 至 {trip.endDate}</span>
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
