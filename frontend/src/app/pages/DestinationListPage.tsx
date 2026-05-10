import { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Search, SlidersHorizontal, Heart, Star, Sparkles, MapPin, Compass } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import BottomNavigation from '../components/BottomNavigation';
import MobileShell from '../MobileShell';
import { destinationApi, type DestinationItem } from '../lib/api';

const fallbackDestinations: DestinationItem[] = [
  { id: 1, name: '东京', image: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63', rating: 4.8, bestTime: '3-5月, 9-11月', attractionCount: 156, country: '日本', description: '霓虹都市与传统文化交融' },
  { id: 2, name: '纽约', image: 'https://images.unsplash.com/photo-1514565131-fce0801e5785', rating: 4.7, bestTime: '4-6月, 9-11月', attractionCount: 203, country: '美国', description: '城市活力与艺术街区并存' },
  { id: 3, name: '迪拜', image: 'https://images.unsplash.com/photo-1706798636444-d4eb076fb63c', rating: 4.9, bestTime: '11月-次年3月', attractionCount: 98, country: '阿联酋', description: '沙漠奇观与奢华天际线' },
  { id: 4, name: '悉尼', image: 'https://images.unsplash.com/photo-1523059623039-a9ed027e7fad', rating: 4.8, bestTime: '9-11月', attractionCount: 134, country: '澳大利亚', description: '海港风光与悠闲假日氛围' },
];

export default function DestinationListPage() {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('全部');
  const [destinations, setDestinations] = useState<DestinationItem[]>(fallbackDestinations);
  const [loading, setLoading] = useState(false);

  const categories = ['全部', '城市', '海岛', '古镇', '雪山', '亲子', '小众'];

  useEffect(() => {
    const loadDestinations = async () => {
      try {
        setLoading(true);
        const data = await destinationApi.list();
        if (Array.isArray(data) && data.length > 0) {
          setDestinations(data);
        }
      } catch {
        setDestinations(fallbackDestinations);
      } finally {
        setLoading(false);
      }
    };

    loadDestinations();
  }, []);

  const filteredDestinations = useMemo(() => {
    const keyword = searchQuery.trim().toLowerCase();
    return destinations.filter((dest) => {
      if (!keyword) return true;
      return [dest.name, dest.country, dest.description]
        .filter(Boolean)
        .some((value) => String(value).toLowerCase().includes(keyword));
    });
  }, [destinations, searchQuery]);

  return (
    <MobileShell>
      <div className="space-y-5">
        <div className="rounded-[30px] bg-gradient-to-br from-sky-600 via-cyan-500 to-teal-500 p-5 text-white shadow-[0_18px_55px_rgba(14,116,144,0.26)]">
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
              <p className="text-sm text-white/75">全球灵感库</p>
              <h1 className="mt-1 text-[28px] font-semibold tracking-tight">发现下一站目的地</h1>
            </div>
            <div className="flex h-11 w-11 items-center justify-center rounded-2xl border border-white/15 bg-white/10">
              <Compass size={20} />
            </div>
          </div>

          <div className="mb-4 inline-flex items-center gap-2 rounded-full bg-white/10 px-3 py-1.5 text-xs font-medium text-white/85 backdrop-blur-sm">
            <Sparkles size={14} />
            根据你的偏好精选城市、海岛与假日路线
          </div>

          <div className="relative">
            <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
            <Input
              type="text"
              placeholder="搜索国家 / 城市 / 玩法"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="h-12 rounded-2xl border-white/30 bg-white/92 pl-11 pr-12 text-slate-900 shadow-inner"
            />
            <button className="absolute right-3 top-1/2 flex h-9 w-9 -translate-y-1/2 items-center justify-center rounded-2xl bg-slate-100 text-slate-500">
              <SlidersHorizontal size={18} />
            </button>
          </div>
        </div>

        <Card className="rounded-[28px] border-white/70 bg-white/88 p-4 shadow-[0_14px_35px_rgba(15,23,42,0.06)]">
          <div className="mb-3 flex items-center justify-between">
            <div>
              <h2 className="text-base font-semibold text-slate-900">探索分类</h2>
              <p className="text-sm text-slate-500">轻松切换旅行主题</p>
            </div>
            <span className="rounded-full bg-teal-50 px-3 py-1 text-xs font-medium text-teal-600">{filteredDestinations.length} 个结果</span>
          </div>
          <div className="flex gap-2 overflow-x-auto scrollbar-hide">
            {categories.map((category) => (
              <button
                key={category}
                onClick={() => setSelectedCategory(category)}
                className={`whitespace-nowrap rounded-full px-4 py-2 text-sm font-medium transition-all ${
                  selectedCategory === category
                    ? 'bg-slate-900 text-white shadow-[0_10px_20px_rgba(15,23,42,0.14)]'
                    : 'bg-slate-100 text-slate-600'
                }`}
              >
                {category}
              </button>
            ))}
          </div>
        </Card>

        {loading && (
          <Card className="rounded-[24px] border-white/70 bg-white/88 p-4 text-sm text-slate-500 shadow-[0_14px_35px_rgba(15,23,42,0.05)]">
            正在加载目的地数据...
          </Card>
        )}

        <div className="space-y-4">
          {filteredDestinations.map((dest) => (
            <Card
              key={dest.id}
              className="overflow-hidden rounded-[28px] border-white/70 bg-white/90 shadow-[0_16px_38px_rgba(15,23,42,0.07)]"
              onClick={() => navigate(`/destination/${dest.id}`)}
            >
              <div className="relative h-48">
                <ImageWithFallback
                  src={dest.coverImage || dest.image || 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e'}
                  alt={dest.name}
                  className="h-full w-full object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-slate-950/70 via-slate-900/10 to-transparent" />
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                  }}
                  className="absolute right-4 top-4 flex h-10 w-10 items-center justify-center rounded-2xl bg-white/80 text-slate-600 backdrop-blur-sm"
                >
                  <Heart size={16} />
                </button>
                <div className="absolute inset-x-0 bottom-0 p-4 text-white">
                  <div className="mb-2 inline-flex items-center gap-2 rounded-full bg-white/15 px-3 py-1 text-xs backdrop-blur-sm">
                    <MapPin size={12} />
                    {dest.country || '热门目的地'}
                  </div>
                  <div className="flex items-end justify-between gap-3">
                    <div>
                      <h3 className="text-xl font-semibold">{dest.name}</h3>
                      <p className="mt-1 text-sm text-white/75">{dest.description || '在山海与城市之间找到你的理想假期。'}</p>
                    </div>
                    <div className="rounded-2xl bg-white/15 px-3 py-2 text-center backdrop-blur-sm">
                      <div className="flex items-center gap-1 text-sm font-medium">
                        <Star size={14} className="fill-amber-400 text-amber-400" />
                        {dest.rating ?? 4.8}
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-3 p-4">
                <div className="rounded-[22px] bg-slate-50 p-3">
                  <p className="text-xs text-slate-500">最佳旅行时间</p>
                  <p className="mt-1 text-sm font-medium text-slate-800">{dest.bestTime || '全年皆宜'}</p>
                </div>
                <div className="rounded-[22px] bg-slate-50 p-3">
                  <p className="text-xs text-slate-500">可探索景点</p>
                  <p className="mt-1 text-sm font-medium text-slate-800">{dest.attractionCount ?? 0} 个</p>
                </div>
              </div>

              <div className="px-4 pb-4">
                <Button className="h-11 w-full rounded-2xl bg-gradient-to-r from-teal-500 via-cyan-500 to-sky-500 text-white hover:from-teal-600 hover:to-sky-600">
                  查看详情
                </Button>
              </div>
            </Card>
          ))}
        </div>
      </div>

      <BottomNavigation />
    </MobileShell>
  );
}
