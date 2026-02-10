import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Tag, Percent, TrendingDown, Clock, MapPin, Plane, Hotel, Ticket } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../components/ui/tabs';
import BottomNavigation from '../components/BottomNavigation';

export default function DealsPage() {
  const navigate = useNavigate();
  const [selectedCategory, setSelectedCategory] = useState('all');

  const categories = [
    { id: 'all', label: '全部', icon: Tag },
    { id: 'flight', label: '机票', icon: Plane },
    { id: 'hotel', label: '酒店', icon: Hotel },
    { id: 'ticket', label: '门票', icon: Ticket },
  ];

  const flashDeals = [
    {
      id: 1,
      type: '机票',
      title: '北京-东京往返机票',
      originalPrice: 3580,
      price: 1999,
      discount: '5.6折',
      image: 'https://images.unsplash.com/photo-1436491865332-7a61a109cc05',
      endTime: '2小时后结束',
      stock: 12,
      tag: '限时抢购'
    },
    {
      id: 2,
      type: '酒店',
      title: '三亚亚特兰蒂斯酒店',
      originalPrice: 2888,
      price: 1588,
      discount: '5.5折',
      image: 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4',
      endTime: '1小时后结束',
      stock: 8,
      tag: '限时抢购'
    },
    {
      id: 3,
      type: '门票',
      title: '迪士尼乐园家庭套票',
      originalPrice: 1999,
      price: 1499,
      discount: '7.5折',
      image: 'https://images.unsplash.com/photo-1643330683233-ff2ac89b002c',
      endTime: '3小时后结束',
      stock: 20,
      tag: '限时抢购'
    },
  ];

  const packageDeals = [
    {
      id: 1,
      title: '日本关西5日自由行',
      subtitle: '含往返机票+4晚酒店',
      price: 3999,
      originalPrice: 6999,
      image: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63',
      tags: ['含早餐', '可退改', '赠Wi-Fi'],
      saved: 3000,
      rating: 4.8,
      reviews: 1230
    },
    {
      id: 2,
      title: '三亚海岛度假4日游',
      subtitle: '含往返机票+3晚海景房',
      price: 2599,
      originalPrice: 4299,
      image: 'https://images.unsplash.com/photo-1559827260-dc66d52bef19',
      tags: ['含早餐', '接送机', '赠SPA'],
      saved: 1700,
      rating: 4.9,
      reviews: 856
    },
    {
      id: 3,
      title: '巴厘岛7日蜜月之旅',
      subtitle: '含往返机票+6晚豪华别墅',
      price: 8999,
      originalPrice: 13999,
      image: 'https://images.unsplash.com/photo-1581032841303-0ba9e894ebc3',
      tags: ['私人泳池', '蜜月套餐', '赠SPA'],
      saved: 5000,
      rating: 4.9,
      reviews: 2340
    },
  ];

  const weekendDeals = [
    {
      id: 1,
      title: '苏州园林一日游',
      price: 199,
      originalPrice: 358,
      image: 'https://images.unsplash.com/photo-1604935630652-f7b9e8e542e0',
      location: '苏州',
      type: '周边游',
      discount: '5.6折'
    },
    {
      id: 2,
      title: '莫干山民宿2日游',
      price: 599,
      originalPrice: 899,
      image: 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb',
      location: '德清',
      type: '周边游',
      discount: '6.7折'
    },
    {
      id: 3,
      title: '黄山云海3日游',
      price: 1299,
      originalPrice: 1899,
      image: 'https://images.unsplash.com/photo-1562820509-484649b9dd58',
      location: '黄山',
      type: '周边游',
      discount: '6.8折'
    },
    {
      id: 4,
      title: '青岛海鲜美食2日',
      price: 699,
      originalPrice: 1099,
      image: 'https://images.unsplash.com/photo-1590394156973-c0db3de2c572',
      location: '青岛',
      type: '周边游',
      discount: '6.4折'
    },
  ];

  const coupons = [
    { id: 1, title: '机票立减', amount: 300, condition: '满2000可用', color: 'from-orange-500 to-red-500' },
    { id: 2, title: '酒店优惠', amount: 200, condition: '满1000可用', color: 'from-purple-500 to-pink-500' },
    { id: 3, title: '门票折扣', amount: 50, condition: '满300可用', color: 'from-teal-500 to-blue-500' },
    { id: 4, title: '周边游', amount: 100, condition: '满500可用', color: 'from-green-500 to-teal-500' },
  ];

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-gradient-to-r from-teal-500 to-blue-500">
        <div className="px-4 py-3 flex items-center gap-3 text-white">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold flex-1">优惠专区</h1>
          <Tag size={20} />
        </div>
      </div>

      {/* 优惠券区域 */}
      <div className="bg-white px-4 py-4 mb-2">
        <div className="flex items-center justify-between mb-3">
          <h2 className="font-semibold flex items-center gap-2">
            <Percent className="text-orange-500" size={18} />
            领券中心
          </h2>
          <button 
            className="text-sm text-teal-600"
            onClick={() => navigate('/wallet')}
          >
            全部优惠券 &gt;
          </button>
        </div>
        <div className="flex gap-3 overflow-x-auto scrollbar-hide">
          {coupons.map((coupon) => (
            <div
              key={coupon.id}
              className={`flex-shrink-0 w-40 h-24 rounded-xl bg-gradient-to-r ${coupon.color} p-3 text-white cursor-pointer hover:scale-105 transition-transform`}
            >
              <div className="text-2xl font-bold">¥{coupon.amount}</div>
              <div className="text-xs opacity-90 mt-1">{coupon.title}</div>
              <div className="text-xs opacity-80 mt-1">{coupon.condition}</div>
            </div>
          ))}
        </div>
      </div>

      {/* 限时抢购 */}
      <div className="bg-white px-4 py-4 mb-2">
        <div className="flex items-center justify-between mb-3">
          <h2 className="font-semibold flex items-center gap-2">
            <TrendingDown className="text-red-500" size={18} />
            限时抢购
          </h2>
          <span className="text-sm text-gray-500 flex items-center gap-1">
            <Clock size={14} />
            距结束 02:34:56
          </span>
        </div>
        <div className="space-y-3">
          {flashDeals.map((deal) => (
            <Card 
              key={deal.id} 
              className="p-3 cursor-pointer hover:shadow-lg transition-shadow"
              onClick={() => {
                if (deal.type === '机票') navigate('/flight-booking');
                else if (deal.type === '酒店') navigate('/hotel-booking');
                else navigate('/destinations');
              }}
            >
              <div className="flex gap-3">
                <div className="w-24 h-24 rounded-lg overflow-hidden flex-shrink-0">
                  <ImageWithFallback
                    src={deal.image}
                    alt={deal.title}
                    className="w-full h-full object-cover"
                  />
                </div>
                <div className="flex-1 flex flex-col justify-between">
                  <div>
                    <Badge variant="destructive" className="mb-1 text-xs">
                      {deal.tag}
                    </Badge>
                    <h3 className="font-medium text-sm line-clamp-1">{deal.title}</h3>
                    <Badge variant="outline" className="text-xs mt-1">
                      {deal.type}
                    </Badge>
                  </div>
                  <div className="flex items-end justify-between">
                    <div>
                      <div className="flex items-baseline gap-2">
                        <span className="text-red-600 font-bold text-lg">¥{deal.price}</span>
                        <span className="text-gray-400 text-xs line-through">¥{deal.originalPrice}</span>
                      </div>
                      <Badge className="bg-red-100 text-red-600 text-xs mt-1">
                        {deal.discount}
                      </Badge>
                    </div>
                    <div className="text-right">
                      <div className="text-xs text-orange-600">{deal.endTime}</div>
                      <div className="text-xs text-gray-500">仅剩{deal.stock}份</div>
                    </div>
                  </div>
                </div>
              </div>
            </Card>
          ))}
        </div>
      </div>

      {/* 组合套餐 */}
      <div className="bg-white px-4 py-4 mb-2">
        <h2 className="font-semibold mb-3 flex items-center gap-2">
          <Tag className="text-teal-500" size={18} />
          超值套餐
        </h2>
        <div className="space-y-3">
          {packageDeals.map((deal) => (
            <Card 
              key={deal.id} 
              className="overflow-hidden cursor-pointer hover:shadow-lg transition-shadow"
              onClick={() => navigate('/destinations')}
            >
              <div className="relative h-40">
                <ImageWithFallback
                  src={deal.image}
                  alt={deal.title}
                  className="w-full h-full object-cover"
                />
                <Badge className="absolute top-2 left-2 bg-red-600">
                  立省¥{deal.saved}
                </Badge>
              </div>
              <div className="p-3">
                <h3 className="font-semibold mb-1">{deal.title}</h3>
                <p className="text-sm text-gray-600 mb-2">{deal.subtitle}</p>
                <div className="flex flex-wrap gap-1 mb-2">
                  {deal.tags.map((tag, index) => (
                    <Badge key={index} variant="outline" className="text-xs">
                      {tag}
                    </Badge>
                  ))}
                </div>
                <div className="flex items-center justify-between">
                  <div>
                    <span className="text-red-600 font-bold text-xl">¥{deal.price}</span>
                    <span className="text-gray-400 text-sm line-through ml-2">¥{deal.originalPrice}</span>
                  </div>
                  <div className="text-sm text-gray-500">
                    ⭐ {deal.rating} ({deal.reviews}条评价)
                  </div>
                </div>
              </div>
            </Card>
          ))}
        </div>
      </div>

      {/* 周末游特惠 */}
      <div className="bg-white px-4 py-4 mb-2">
        <h2 className="font-semibold mb-3 flex items-center gap-2">
          <MapPin className="text-blue-500" size={18} />
          周末游特惠
        </h2>
        <div className="grid grid-cols-2 gap-3">
          {weekendDeals.map((deal) => (
            <Card 
              key={deal.id} 
              className="overflow-hidden cursor-pointer hover:shadow-lg transition-shadow"
              onClick={() => navigate('/destinations')}
            >
              <div className="relative h-32">
                <ImageWithFallback
                  src={deal.image}
                  alt={deal.title}
                  className="w-full h-full object-cover"
                />
                <Badge className="absolute top-2 left-2 bg-orange-600 text-xs">
                  {deal.discount}
                </Badge>
              </div>
              <div className="p-2">
                <h3 className="font-medium text-sm line-clamp-1 mb-1">{deal.title}</h3>
                <div className="flex items-center gap-1 mb-2">
                  <MapPin size={12} className="text-gray-400" />
                  <span className="text-xs text-gray-500">{deal.location}</span>
                  <Badge variant="outline" className="text-xs ml-auto">
                    {deal.type}
                  </Badge>
                </div>
                <div className="flex items-baseline gap-1">
                  <span className="text-red-600 font-bold">¥{deal.price}</span>
                  <span className="text-gray-400 text-xs line-through">¥{deal.originalPrice}</span>
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
