import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowLeft, Heart, Share2, Bookmark, ThumbsUp, MessageCircle, Eye, Clock, MapPin, Star, ChevronRight, User } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Avatar } from '../components/ui/avatar';
import { Separator } from '../components/ui/separator';

export default function TravelGuideDetailPage() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [isLiked, setIsLiked] = useState(false);
  const [isBookmarked, setIsBookmarked] = useState(false);

  // 模拟攻略数据
  const guide = {
    id: 1,
    title: '东京7日深度游完整攻略｜第一次去东京必看',
    subtitle: '涵盖交通、住宿、美食、景点的超详细指南',
    coverImage: 'https://images.unsplash.com/photo-1648871647634-0c99b483cb63',
    author: {
      name: '旅行达人小红',
      avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330',
      level: 'LV8',
      followers: 12340,
      isFollowing: false
    },
    publishTime: '2026-01-15',
    views: 45320,
    likes: 3420,
    comments: 289,
    collections: 1890,
    tags: ['东京', '深度游', '美食', '购物', '文化体验'],
    
    sections: [
      {
        type: 'text',
        content: '📍 行程概览\n\n这是我第3次来东京，这次整整待了7天，把东京的精华都玩遍了！这篇攻略会从行前准备、交通、住宿、景点、美食等方面全方位分享，保证让第一次去东京的你不踩雷！'
      },
      {
        type: 'image',
        url: 'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2',
        caption: '浅草寺雷门 - 东京必打卡地标'
      },
      {
        type: 'heading',
        content: '✈️ 行前准备'
      },
      {
        type: 'text',
        content: '【签证】\n现在日本签证非常方便，可以找旅行社代办，一般7个工作日就能出签。需要准备护照、照片、在职证明等基础材料。\n\n【机票】\n建议提前1-2个月预订，北京/上海往返东京的机票淡季大概2000-3000元，旺季会涨到4000-5000元。'
      },
      {
        type: 'heading',
        content: '🚇 交通指南'
      },
      {
        type: 'text',
        content: '【机场往返】\n成田机场→市区：推荐Skyliner特快，41分钟到上野，2520日元\n羽田机场→市区：单轨电车，18分钟到滨松町，500日元\n\n【市内交通】\n强烈推荐购买西瓜卡（Suica）！地铁、JR、便利店都能用，超级方便。如果一天要去多个景点，可以买东京地铁一日券（800日元）。'
      },
      {
        type: 'image',
        url: 'https://images.unsplash.com/photo-1536098561742-ca998e48cbcc',
        caption: '东京地铁四通八达，非常方便'
      },
      {
        type: 'heading',
        content: '🏨 住宿推荐'
      },
      {
        type: 'text',
        content: '【新宿区域】★★★★★\n优点：交通超方便，购物餐饮一应俱全\n推荐酒店：新宿王子酒店、京王广场酒店\n价格：800-1500元/晚\n\n【上野/浅草区域】★★★★\n优点：性价比高，文化氛围浓厚\n推荐酒店：上野三井花园酒店\n价格：600-1000元/晚\n\n【银座区域】★★★★★\n优点：高端奢华，购物天堂\n推荐酒店：银座东急酒店\n价格：1200-2500元/晚'
      },
      {
        type: 'heading',
        content: '📅 7日行程安排'
      },
      {
        type: 'text',
        content: 'Day 1：抵达东京 → 新宿 → 歌舞伎町\nDay 2：浅草寺 → 晴空塔 → 秋叶原\nDay 3：筑地市场 → 银座 → 东京塔\nDay 4：明治神宫 → 原宿 → 涩谷\nDay 5：镰仓一日游（江之电、灌篮高手取景地）\nDay 6：迪士尼乐园/海洋\nDay 7：台场 → 购物 → 返程'
      },
      {
        type: 'image',
        url: 'https://images.unsplash.com/photo-1542640244-7e672d6cef4e',
        caption: '涩谷全向十字路口 - 东京最繁华的路口'
      },
      {
        type: 'heading',
        content: '🍜 美食推荐'
      },
      {
        type: 'text',
        content: '【寿司】\n🌟 筑地寿司大：需要排队2小时，但真的值得！人均300元\n🌟 回转寿司：推荐くら寿司，性价比超高，人均80元\n\n【拉面】\n🌟 一兰拉面：必打卡，24小时营业，人均60元\n🌟 一风堂：豚骨拉面很浓郁，人均70元\n\n【烤肉】\n🌟 叙叙苑：和牛烤肉，人均500元\n🌟 牛角：连锁烤肉店，人均200元\n\n【甜品】\n🌟 Pierre Hermé：马卡龙超级好吃\n🌟 Pablo：芝士挞必吃！'
      },
      {
        type: 'image',
        url: 'https://images.unsplash.com/photo-1555939594-58d7cb561ad1',
        caption: '新鲜美味的日式料理'
      },
      {
        type: 'heading',
        content: '🛍️ 购物指南'
      },
      {
        type: 'text',
        content: '【药妆】\n推荐：松本清、堂吉诃德（激安殿堂）\n必买：面膜、眼药水、酵素、防晒霜\n💡 Tips：不要在景区买，去新宿或池袋的药妆店更便宜\n\n【电器】\n推荐：BicCamera、友都八喜\n必买：吹风机、美容仪、相机\n💡 Tips：记得退税，需带护照\n\n【服饰】\n优衣库、GU：基本款性价比高\n涩谷109：潮流女装\n表参道：奢侈品和设计师品牌'
      },
      {
        type: 'heading',
        content: '💰 费用总结'
      },
      {
        type: 'text',
        content: '机票：2500元\n住宿：7000元（7晚×1000元）\n交通：1000元\n门票：1500元（迪士尼、晴空塔等）\n餐饮：3500元（500元/天）\n购物：5000元\n其他：500元\n\n总计：约21000元/人\n\n如果住经济型酒店，少购物的话，15000元也能玩得很好！'
      },
      {
        type: 'heading',
        content: '⚠️ 注意事项'
      },
      {
        type: 'text',
        content: '1. 日本垃圾分类很严格，请随身携带垃圾袋\n2. 电车上不要打电话，保持安静\n3. 不要边走边吃东西\n4. 进餐厅、温泉等地方要脱鞋\n5. 小费文化不流行，不需要给小费\n6. 准备好现金，很多小店不支持信用卡\n7. 下载Google Maps和换乘案内APP\n8. 买一张Suica卡，真的超方便！'
      },
      {
        type: 'text',
        content: '\n\n好啦，这就是我的东京7日游全攻略！如果有任何问题欢迎在评论区问我哦～\n\n祝大家东京之旅愉快！🎌🗾'
      }
    ],
    
    relatedGuides: [
      {
        id: 2,
        title: '京都5日赏枫攻略',
        image: 'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2',
        author: '樱花季',
        likes: 2340
      },
      {
        id: 3,
        title: '大阪美食完全指南',
        image: 'https://images.unsplash.com/photo-1590559899731-a382839e5549',
        author: '美食探索者',
        likes: 1890
      },
      {
        id: 4,
        title: '北海道冬季旅行攻略',
        image: 'https://images.unsplash.com/photo-1605177410729-25629a4b3d47',
        author: '雪国控',
        likes: 3120
      }
    ]
  };

  return (
    <div className="min-h-screen bg-white pb-20">
      {/* 顶部图片 */}
      <div className="relative h-64">
        <ImageWithFallback
          src={guide.coverImage}
          alt={guide.title}
          className="w-full h-full object-cover"
        />
        
        {/* 返回按钮 */}
        <button
          onClick={() => navigate(-1)}
          className="absolute top-4 left-4 w-10 h-10 rounded-full bg-black/30 backdrop-blur-sm flex items-center justify-center text-white"
        >
          <ArrowLeft size={20} />
        </button>

        {/* 分享和收藏 */}
        <div className="absolute top-4 right-4 flex gap-2">
          <button
            onClick={() => setIsBookmarked(!isBookmarked)}
            className="w-10 h-10 rounded-full bg-black/30 backdrop-blur-sm flex items-center justify-center text-white"
          >
            <Bookmark size={18} className={isBookmarked ? 'fill-white' : ''} />
          </button>
          <button className="w-10 h-10 rounded-full bg-black/30 backdrop-blur-sm flex items-center justify-center text-white">
            <Share2 size={18} />
          </button>
        </div>

        {/* 数据统计 */}
        <div className="absolute bottom-0 left-0 right-0 bg-gradient-to-t from-black/70 to-transparent p-4">
          <div className="flex gap-4 text-white text-sm">
            <div className="flex items-center gap-1">
              <Eye size={14} />
              <span>{guide.views.toLocaleString()}</span>
            </div>
            <div className="flex items-center gap-1">
              <ThumbsUp size={14} />
              <span>{guide.likes.toLocaleString()}</span>
            </div>
            <div className="flex items-center gap-1">
              <MessageCircle size={14} />
              <span>{guide.comments}</span>
            </div>
          </div>
        </div>
      </div>

      {/* 标题区域 */}
      <div className="px-4 py-4">
        <h1 className="text-xl font-bold mb-2">{guide.title}</h1>
        <p className="text-sm text-gray-600 mb-3">{guide.subtitle}</p>
        
        {/* 标签 */}
        <div className="flex flex-wrap gap-2 mb-4">
          {guide.tags.map((tag, index) => (
            <Badge key={index} variant="outline" className="text-xs">
              #{tag}
            </Badge>
          ))}
        </div>

        {/* 作者信息 */}
        <div className="flex items-center justify-between pb-4 border-b">
          <div className="flex items-center gap-3">
            <div className="w-12 h-12 rounded-full overflow-hidden bg-gray-200">
              <ImageWithFallback
                src={guide.author.avatar}
                alt={guide.author.name}
                className="w-full h-full object-cover"
              />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <span className="font-medium">{guide.author.name}</span>
                <Badge variant="secondary" className="text-xs">
                  {guide.author.level}
                </Badge>
              </div>
              <div className="text-xs text-gray-500 flex items-center gap-3">
                <span>{guide.author.followers.toLocaleString()} 粉丝</span>
                <span className="flex items-center gap-1">
                  <Clock size={12} />
                  {guide.publishTime}
                </span>
              </div>
            </div>
          </div>
          <Button
            variant="outline"
            size="sm"
            className="text-teal-600 border-teal-600"
          >
            + 关注
          </Button>
        </div>
      </div>

      {/* 正文内容 */}
      <div className="px-4 pb-6">
        {guide.sections.map((section, index) => {
          if (section.type === 'heading') {
            return (
              <h2 key={index} className="text-lg font-bold mt-6 mb-3">
                {section.content}
              </h2>
            );
          } else if (section.type === 'text') {
            return (
              <div key={index} className="text-gray-700 leading-relaxed whitespace-pre-line mb-4">
                {section.content}
              </div>
            );
          } else if (section.type === 'image') {
            return (
              <div key={index} className="my-4">
                <div className="rounded-lg overflow-hidden">
                  <ImageWithFallback
                    src={section.url}
                    alt={section.caption || ''}
                    className="w-full object-cover"
                  />
                </div>
                {section.caption && (
                  <p className="text-sm text-gray-500 text-center mt-2">
                    {section.caption}
                  </p>
                )}
              </div>
            );
          }
          return null;
        })}
      </div>

      {/* 相关攻略 */}
      <div className="px-4 py-6 bg-gray-50">
        <h3 className="font-semibold mb-4">相关攻略推荐</h3>
        <div className="space-y-3">
          {guide.relatedGuides.map((related) => (
            <Card
              key={related.id}
              className="p-3 cursor-pointer hover:shadow-lg transition-shadow"
              onClick={() => navigate(`/guide/${related.id}`)}
            >
              <div className="flex gap-3">
                <div className="w-24 h-24 rounded-lg overflow-hidden flex-shrink-0">
                  <ImageWithFallback
                    src={related.image}
                    alt={related.title}
                    className="w-full h-full object-cover"
                  />
                </div>
                <div className="flex-1 flex flex-col justify-between">
                  <h4 className="font-medium line-clamp-2">{related.title}</h4>
                  <div className="flex items-center justify-between text-sm text-gray-500">
                    <span>{related.author}</span>
                    <div className="flex items-center gap-1">
                      <ThumbsUp size={14} />
                      <span>{related.likes}</span>
                    </div>
                  </div>
                </div>
              </div>
            </Card>
          ))}
        </div>
      </div>

      {/* 底部操作栏 */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t shadow-lg px-4 py-3 flex items-center gap-3">
        <Button
          variant="ghost"
          size="sm"
          onClick={() => setIsLiked(!isLiked)}
          className="flex flex-col items-center gap-1"
        >
          <ThumbsUp size={20} className={isLiked ? 'fill-teal-500 text-teal-500' : ''} />
          <span className="text-xs">点赞</span>
        </Button>
        <Button
          variant="ghost"
          size="sm"
          className="flex flex-col items-center gap-1"
        >
          <MessageCircle size={20} />
          <span className="text-xs">评论</span>
        </Button>
        <Button
          variant="ghost"
          size="sm"
          onClick={() => setIsBookmarked(!isBookmarked)}
          className="flex flex-col items-center gap-1"
        >
          <Bookmark size={20} className={isBookmarked ? 'fill-teal-500 text-teal-500' : ''} />
          <span className="text-xs">收藏</span>
        </Button>
        <Button className="flex-1 bg-gradient-to-r from-teal-500 to-blue-500">
          制定我的行程
        </Button>
      </div>
    </div>
  );
}
