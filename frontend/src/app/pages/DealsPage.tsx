import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Tag, Percent, TrendingDown, Clock, MapPin, Plane, Hotel, Ticket, Sparkles, ChevronRight } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import BottomNavigation from '../components/BottomNavigation';
import MobileShell from '../MobileShell';

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
      tag: '限时抢购',
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
      tag: '限时抢购',
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
      tag: '限时抢购',
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
      tags: ['含早餐', '可退改', '赠Wi‑Fi'],
      saved: 3000,
      rating: 4.8,
      reviews: 1230,
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
      reviews: 856,
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
      reviews: 2340,
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
      discount: '5.6折',
    },
    {
      id: 2,
      title: '莫干山民宿2日游',
      price: 599,
      originalPrice: 899,
      image: 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb',
      location: '德清',
      type: '周边游',
      discount: '6.7折',
    },
    {
      id: 3,
      title: '黄山云海3日游',
      price: 1299,
      originalPrice: 1899,
      image: 'https://images.unsplash.com/photo-1562820509-484649b9dd58',
      location: '黄山',
      type: '周边游',
      discount: '6.8折',
    },
    {
      id: 4,
      title: '青岛海鲜美食2日',
      price: 699,
      originalPrice: 1099,
      image: 'https://images.unsplash.com/photo-1590394156973-c0db3de2c572',
      location: '青岛',
      type: '周边游',
      discount: '6.4折',
    },
  ];

  const coupons = [
    { id: 1, title: '机票立减', amount: 300, condition: '满2000可用', color: 'from-orange-500 to-red-500' },
    { id: 2, title: '酒店优惠', amount: 200, condition: '满1000可用', color: 'from-purple-500 to-pink-500' },
    { id: 3, title: '门票折扣', amount: 50, condition: '满300可用', color: 'from-teal-500 to-blue-500' },
    { id: 4, title: '周边游', amount: 100, condition: '满500可用', color: 'from-green-500 to-teal-500' },
  ];

  return (
    <MobileShell>
      <div className="space-y-5">
        <div className="rounded-[30px] bg-gradient-to-br from-rose-500 via-pink-500 to-orange-500 p-5 text-white shadow-[0_18px_55px_rgba(244,63,94,0.26)]">
          <div className="mb-5 flex items-center gap-3">
            <Button
              variant="ghost"
              size="icon"
              className="rounded-2xl bg-white/10 text-white hover:bg-white/20"
              onClick={() => navigate(-1)}
            >
              <ArrowLeft size={20} />
            </Button>
            <div className="flex-1">
              <p className="text-sm text-white/75">今日精选折扣</p>
              <h1 className="mt-1 text-[28px] font-semibold tracking-tight">优惠专区</h1>
            </div>
            <div className="flex h-11 w-11 items-center justify-center rounded-2xl border border-white/15 bg-white/10">
              <Tag size={20} />
            </div>
          </div>

          <div className="mb-4 inline-flex items-center gap-2 rounded-full bg-white/10 px-3 py-1.5 text-xs font-medium text-white/85 backdrop-blur-sm">
            <Sparkles size={14} />
            限时抢购、套餐立减与周末特惠一站看全
          </div>

          <div className="grid grid-cols-3 gap-3">
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">4</p>
              <p className="mt-1 text-xs text-white/70">热门分类</p>
            </Card>
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">¥300</p>
              <p className="mt-1 text-xs text-white/70">最高立减</p>
            </Card>
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">02:34</p>
              <p className="mt-1 text-xs text-white/70">抢购倒计时</p>
            </Card>
          </div>
        </div>

        <Card className="rounded-[28px] border-white/70 bg-white/88 p-4 shadow-[0_14px_35px_rgba(15,23,42,0.06)]">
          <div className="mb-3 flex items-center justify-between">
            <div>
              <h2 className="text-base font-semibold text-slate-900">优惠分类</h2>
              <p className="text-sm text-slate-500">按出行场景快速浏览</p>
            </div>
          </div>
          <div className="grid grid-cols-4 gap-3">
            {categories.map((category) => {
              const Icon = category.icon;
              const active = selectedCategory === category.id;
              return (
                <button
                  key={category.id}
                  onClick={() => setSelectedCategory(category.id)}
                  className="flex flex-col items-center gap-2"
                >
                  <div className={`flex h-14 w-14 items-center justify-center rounded-[22px] transition-all ${active ? 'bg-slate-900 text-white shadow-[0_12px_22px_rgba(15,23,42,0.18)]' : 'bg-slate-100 text-slate-600'}`}>
                    <Icon size={22} />
                  </div>
                  <span className={`text-[12px] font-medium ${active ? 'text-slate-900' : 'text-slate-500'}`}>{category.label}</span>
                </button>
              );
            })}
          </div>
        </Card>

        <section>
          <div className="mb-3 flex items-center justify-between px-1">
            <h2 className="flex items-center gap-2 text-lg font-semibold text-slate-900">
              <Percent className="text-orange-500" size={18} />
              领券中心
            </h2>
            <button className="text-sm font-medium text-rose-500" onClick={() => navigate('/wallet')}>
              查看全部
            </button>
          </div>
          <div className="-mx-1 flex gap-3 overflow-x-auto px-1 scrollbar-hide">
            {coupons.map((coupon) => (
              <div
                key={coupon.id}
                className={`w-44 flex-shrink-0 rounded-[26px] bg-gradient-to-br ${coupon.color} p-4 text-white shadow-[0_16px_32px_rgba(15,23,42,0.14)]`}
              >
                <div className="text-3xl font-semibold">¥{coupon.amount}</div>
                <div className="mt-2 text-sm font-medium">{coupon.title}</div>
                <div className="mt-1 text-xs text-white/80">{coupon.condition}</div>
                <button className="mt-4 rounded-full bg-white/20 px-3 py-1 text-xs">立即领取</button>
              </div>
            ))}
          </div>
        </section>

        <section>
          <div className="mb-3 flex items-center justify-between px-1">
            <h2 className="flex items-center gap-2 text-lg font-semibold text-slate-900">
              <TrendingDown className="text-rose-500" size={18} />
              限时抢购
            </h2>
            <span className="flex items-center gap-1 text-sm text-slate-500">
              <Clock size={14} />
              距结束 02:34:56
            </span>
          </div>
          <div className="space-y-3">
            {flashDeals.map((deal) => (
              <Card
                key={deal.id}
                className="rounded-[28px] border-white/70 bg-white/90 p-3 shadow-[0_14px_35px_rgba(15,23,42,0.06)]"
                onClick={() => {
                  if (deal.type === '机票') navigate('/flight-booking');
                  else if (deal.type === '酒店') navigate('/hotel-booking');
                  else navigate('/destinations');
                }}
              >
                <div className="flex gap-3">
                  <div className="h-24 w-24 overflow-hidden rounded-[22px] flex-shrink-0">
                    <ImageWithFallback src={deal.image} alt={deal.title} className="h-full w-full object-cover" />
                  </div>
                  <div className="flex flex-1 flex-col justify-between">
                    <div>
                      <Badge className="mb-2 bg-rose-500 text-white">{deal.tag}</Badge>
                      <h3 className="font-semibold text-slate-900">{deal.title}</h3>
                      <div className="mt-2 inline-flex rounded-full bg-slate-100 px-2.5 py-1 text-xs text-slate-500">{deal.type}</div>
                    </div>
                    <div className="flex items-end justify-between">
                      <div>
                        <div className="flex items-baseline gap-2">
                          <span className="text-2xl font-semibold text-rose-500">¥{deal.price}</span>
                          <span className="text-xs text-slate-400 line-through">¥{deal.originalPrice}</span>
                        </div>
                        <span className="mt-1 inline-flex rounded-full bg-rose-50 px-2 py-1 text-xs text-rose-500">{deal.discount}</span>
                      </div>
                      <div className="text-right text-xs text-slate-500">
                        <div className="text-orange-500">{deal.endTime}</div>
                        <div>仅剩{deal.stock}份</div>
                      </div>
                    </div>
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </section>

        <section>
          <div className="mb-3 px-1">
            <h2 className="text-lg font-semibold text-slate-900">超值套餐</h2>
            <p className="text-sm text-slate-500">省心组合，适合直接下单的假日方案</p>
          </div>
          <div className="space-y-4">
            {packageDeals.map((deal) => (
              <Card
                key={deal.id}
                className="overflow-hidden rounded-[28px] border-white/70 bg-white/90 shadow-[0_16px_38px_rgba(15,23,42,0.07)]"
                onClick={() => navigate('/destinations')}
              >
                <div className="relative h-44">
                  <ImageWithFallback src={deal.image} alt={deal.title} className="h-full w-full object-cover" />
                  <div className="absolute inset-0 bg-gradient-to-t from-slate-950/55 to-transparent" />
                  <Badge className="absolute left-4 top-4 bg-rose-500 text-white">立省¥{deal.saved}</Badge>
                </div>
                <div className="space-y-3 p-4">
                  <div>
                    <h3 className="text-lg font-semibold text-slate-900">{deal.title}</h3>
                    <p className="mt-1 text-sm text-slate-500">{deal.subtitle}</p>
                  </div>
                  <div className="flex flex-wrap gap-2">
                    {deal.tags.map((tag, index) => (
                      <span key={index} className="rounded-full bg-slate-100 px-3 py-1 text-xs text-slate-500">
                        {tag}
                      </span>
                    ))}
                  </div>
                  <div className="flex items-center justify-between">
                    <div>
                      <span className="text-2xl font-semibold text-rose-500">¥{deal.price}</span>
                      <span className="ml-2 text-sm text-slate-400 line-through">¥{deal.originalPrice}</span>
                    </div>
                    <div className="text-sm text-slate-500">⭐ {deal.rating} · {deal.reviews}条评价</div>
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </section>

        <section>
          <div className="mb-3 flex items-center justify-between px-1">
            <h2 className="flex items-center gap-2 text-lg font-semibold text-slate-900">
              <MapPin className="text-sky-500" size={18} />
              周末游特惠
            </h2>
            <button className="text-sm font-medium text-sky-600" onClick={() => navigate('/destinations')}>
              更多灵感
            </button>
          </div>
          <div className="grid grid-cols-2 gap-3">
            {weekendDeals.map((deal) => (
              <Card
                key={deal.id}
                className="overflow-hidden rounded-[26px] border-white/70 bg-white/90 shadow-[0_12px_30px_rgba(15,23,42,0.06)]"
                onClick={() => navigate('/destinations')}
              >
                <div className="relative h-32">
                  <ImageWithFallback src={deal.image} alt={deal.title} className="h-full w-full object-cover" />
                  <span className="absolute left-3 top-3 rounded-full bg-white/85 px-2.5 py-1 text-xs font-medium text-orange-500 backdrop-blur-sm">
                    {deal.discount}
                  </span>
                </div>
                <div className="space-y-2 p-3">
                  <h3 className="line-clamp-1 text-sm font-semibold text-slate-900">{deal.title}</h3>
                  <div className="flex items-center justify-between text-xs text-slate-500">
                    <span>{deal.location}</span>
                    <span className="rounded-full bg-slate-100 px-2 py-1">{deal.type}</span>
                  </div>
                  <div className="flex items-center justify-between">
                    <div>
                      <span className="font-semibold text-rose-500">¥{deal.price}</span>
                      <span className="ml-1 text-xs text-slate-400 line-through">¥{deal.originalPrice}</span>
                    </div>
                    <ChevronRight size={16} className="text-slate-400" />
                  </div>
                </div>
              </Card>
            ))}
          </div>
        </section>
      </div>

      <BottomNavigation />
    </MobileShell>
  );
}
