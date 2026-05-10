import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Users, MapPin, Calendar, Heart, MessageCircle, UserPlus, Filter, Search, Sparkles, Star, Check } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Input } from '../components/ui/input';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../components/ui/tabs';

export default function TravelMatesPage() {
  const navigate = useNavigate();
  const [selectedTab, setSelectedTab] = useState('recommend');

  const userProfile = {
    interests: ['美食', '摄影', '文化', '徒步'],
    travelStyle: '深度游',
    age: 28,
    gender: '女'
  };

  // AI推荐的旅伴
  const recommendedMates = [
    {
      id: 1,
      name: '小雨',
      avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330',
      age: 26,
      gender: '女',
      location: '北京',
      matchScore: 95,
      destination: '东京',
      travelDate: '2026-03-15',
      duration: '7天',
      interests: ['美食', '摄影', '购物'],
      travelStyle: '深度游',
      verified: true,
      trips: 12,
      rating: 4.9,
      bio: '热爱旅行和摄影的90后，喜欢探索小众景点和当地美食',
      lookingFor: '寻找1-2位女生同游东京，AA制，性格开朗好相处',
      images: [
        'https://images.unsplash.com/photo-1648871647634-0c99b483cb63',
        'https://images.unsplash.com/photo-1581032841303-0ba9e894ebc3'
      ]
    },
    {
      id: 2,
      name: '阿伦',
      avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d',
      age: 30,
      gender: '男',
      location: '上海',
      matchScore: 88,
      destination: '东京',
      travelDate: '2026-03-18',
      duration: '6天',
      interests: ['文化', '历史', '美食'],
      travelStyle: '文化深度游',
      verified: true,
      trips: 18,
      rating: 4.8,
      bio: '历史文化爱好者，喜欢博物馆和古迹，慢节奏旅行',
      lookingFor: '寻找志同道合的伙伴一起探索日本文化',
      images: [
        'https://images.unsplash.com/photo-1557772328-e7a1a09bcec2',
        'https://images.unsplash.com/photo-1508804185872-d7badad00f7d'
      ]
    },
    {
      id: 3,
      name: 'Lily',
      avatar: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80',
      age: 27,
      gender: '女',
      location: '广州',
      matchScore: 92,
      destination: '东京',
      travelDate: '2026-03-12',
      duration: '8天',
      interests: ['摄影', '美食', '动漫'],
      travelStyle: '自由行',
      verified: true,
      trips: 15,
      rating: 5.0,
      bio: '摄影师 | 美食博主 | 日漫爱好者',
      lookingFor: '寻找喜欢拍照和打卡的小伙伴，可以互拍！',
      images: [
        'https://images.unsplash.com/photo-1542640244-7e672d6cef4e',
        'https://images.unsplash.com/photo-1676730056228-7e38cbb88edc'
      ]
    }
  ];

  // 旅伴招募帖
  const recruitPosts = [
    {
      id: 1,
      author: {
        name: '旅行达人',
        avatar: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb',
        verified: true
      },
      destination: '巴厘岛',
      date: '2026-04-20',
      duration: '10天',
      currentMembers: 2,
      maxMembers: 4,
      tags: ['海岛度假', '潜水', '瑜伽'],
      budget: '8000-10000元',
      description: '计划去巴厘岛深度游，已有2位女生，再找2位同行，喜欢潜水和瑜伽优先',
      postedTime: '2小时前'
    },
    {
      id: 2,
      author: {
        name: '户外探险者',
        avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d',
        verified: true
      },
      destination: '西藏',
      date: '2026-05-01',
      duration: '15天',
      currentMembers: 3,
      maxMembers: 6,
      tags: ['徒步', '摄影', '朝圣'],
      budget: '6000-8000元',
      description: '5月西藏拉萨-林芝-珠峰大环线，寻找体力好、有高原经验的伙伴',
      postedTime: '5小时前'
    }
  ];

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-gradient-to-r from-teal-500 to-blue-500">
        <div className="px-4 py-3 flex items-center gap-3 text-white">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <div className="flex-1">
            <h1 className="text-lg font-semibold">旅伴匹配</h1>
            <p className="text-xs opacity-90">AI智能匹配志同道合的旅伴</p>
          </div>
          <Sparkles size={20} />
        </div>

        {/* 搜索栏 */}
        <div className="px-4 pb-3">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-white/70" size={18} />
            <Input
              type="text"
              placeholder="搜索目的地、时间..."
              className="pl-10 bg-white/20 border-white/30 text-white placeholder:text-white/70"
            />
          </div>
        </div>
      </div>

      {/* Tabs */}
      <div className="px-4 py-4">
        <Tabs value={selectedTab} onValueChange={setSelectedTab}>
          <TabsList className="w-full grid grid-cols-2 bg-white">
            <TabsTrigger value="recommend" className="text-sm">
              AI推荐
              <Badge className="ml-2 bg-red-500 text-white text-xs">
                {recommendedMates.length}
              </Badge>
            </TabsTrigger>
            <TabsTrigger value="recruit" className="text-sm">招募帖</TabsTrigger>
          </TabsList>

          {/* AI推荐 Tab */}
          <TabsContent value="recommend" className="mt-4">
            {/* AI匹配说明 */}
            <Card className="p-4 mb-4 bg-gradient-to-r from-purple-50 to-pink-50 border-purple-200">
              <div className="flex items-center gap-2 mb-2">
                <Sparkles className="text-purple-600" size={18} />
                <h3 className="font-semibold">AI智能匹配</h3>
              </div>
              <p className="text-sm text-gray-700">
                基于你的兴趣爱好、旅行风格、目的地和时间，为你智能匹配最合适的旅伴
              </p>
            </Card>

            {/* 推荐旅伴列表 */}
            <div className="space-y-4">
              {recommendedMates.map((mate) => (
                <Card key={mate.id} className="overflow-hidden hover:shadow-lg transition-shadow">
                  {/* 头部信息 */}
                  <div className="p-4 bg-gradient-to-r from-teal-50 to-blue-50">
                    <div className="flex items-start justify-between mb-3">
                      <div className="flex items-center gap-3">
                        <div className="relative">
                          <div className="w-16 h-16 rounded-full overflow-hidden border-2 border-white shadow">
                            <ImageWithFallback
                              src={mate.avatar}
                              alt={mate.name}
                              className="w-full h-full object-cover"
                            />
                          </div>
                          {mate.verified && (
                            <div className="absolute bottom-0 right-0 w-5 h-5 bg-blue-500 rounded-full flex items-center justify-center border-2 border-white">
                              <Check size={12} className="text-white" />
                            </div>
                          )}
                        </div>
                        <div>
                          <div className="flex items-center gap-2 mb-1">
                            <h3 className="font-semibold text-lg">{mate.name}</h3>
                            <Badge variant="outline" className="text-xs">
                              {mate.age}岁 · {mate.gender}
                            </Badge>
                          </div>
                          <div className="flex items-center gap-2 text-sm text-gray-600">
                            <MapPin size={14} />
                            <span>{mate.location}</span>
                            <span>·</span>
                            <Star size={14} className="text-yellow-500" />
                            <span>{mate.rating}</span>
                          </div>
                        </div>
                      </div>
                      <Badge className="bg-gradient-to-r from-purple-500 to-pink-500">
                        匹配度 {mate.matchScore}%
                      </Badge>
                    </div>

                    {/* 旅行计划 */}
                    <div className="bg-white rounded-lg p-3 mb-3">
                      <div className="grid grid-cols-3 gap-2 text-sm">
                        <div>
                          <div className="flex items-center gap-1 text-gray-600 mb-1">
                            <MapPin size={12} />
                            <span className="text-xs">目的地</span>
                          </div>
                          <p className="font-semibold">{mate.destination}</p>
                        </div>
                        <div>
                          <div className="flex items-center gap-1 text-gray-600 mb-1">
                            <Calendar size={12} />
                            <span className="text-xs">日期</span>
                          </div>
                          <p className="font-semibold text-xs">{mate.travelDate}</p>
                        </div>
                        <div>
                          <div className="flex items-center gap-1 text-gray-600 mb-1">
                            <Users size={12} />
                            <span className="text-xs">时长</span>
                          </div>
                          <p className="font-semibold">{mate.duration}</p>
                        </div>
                      </div>
                    </div>

                    {/* 个人简介 */}
                    <p className="text-sm text-gray-700 mb-2">{mate.bio}</p>
                    <p className="text-sm text-teal-700 font-medium mb-3">"{mate.lookingFor}"</p>

                    {/* 兴趣标签 */}
                    <div className="flex flex-wrap gap-2 mb-3">
                      {mate.interests.map((interest, index) => (
                        <Badge key={index} variant="outline" className="text-xs">
                          {interest}
                        </Badge>
                      ))}
                      <Badge variant="secondary" className="text-xs">
                        {mate.travelStyle}
                      </Badge>
                    </div>
                  </div>

                  {/* 照片墙 */}
                  <div className="grid grid-cols-2 gap-1 px-4 py-3 bg-gray-50">
                    {mate.images.map((image, index) => (
                      <div key={index} className="aspect-square rounded-lg overflow-hidden">
                        <ImageWithFallback
                          src={image}
                          alt={`Travel ${index + 1}`}
                          className="w-full h-full object-cover"
                        />
                      </div>
                    ))}
                  </div>

                  {/* 操作按钮 */}
                  <div className="p-4 flex gap-2">
                    <Button 
                      variant="outline" 
                      className="flex-1"
                      onClick={() => {/* 查看详情 */}}
                    >
                      <Users size={16} className="mr-2" />
                      查看详情
                    </Button>
                    <Button 
                      className="flex-1 bg-gradient-to-r from-teal-500 to-blue-500"
                      onClick={() => {/* 发送消息 */}}
                    >
                      <MessageCircle size={16} className="mr-2" />
                      发送消息
                    </Button>
                    <Button 
                      variant="ghost"
                      size="icon"
                      onClick={() => {/* 收藏 */}}
                    >
                      <Heart size={20} />
                    </Button>
                  </div>
                </Card>
              ))}
            </div>

            {/* 查看更多 */}
            <div className="text-center py-6">
              <Button variant="outline">
                查看更多推荐
              </Button>
            </div>
          </TabsContent>

          {/* 招募帖 Tab */}
          <TabsContent value="recruit" className="mt-4">
            {/* 发布按钮 */}
            <Card 
              className="p-4 mb-4 bg-gradient-to-r from-teal-50 to-blue-50 border-teal-200 cursor-pointer hover:shadow-lg transition-shadow"
              onClick={() => {/* 发布招募 */}}
            >
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="w-12 h-12 rounded-full bg-gradient-to-r from-teal-500 to-blue-500 flex items-center justify-center text-white">
                    <UserPlus size={24} />
                  </div>
                  <div>
                    <h3 className="font-semibold">发布招募帖</h3>
                    <p className="text-sm text-gray-600">寻找志同道合的旅伴</p>
                  </div>
                </div>
                <Button size="sm" className="bg-gradient-to-r from-teal-500 to-blue-500">
                  发布
                </Button>
              </div>
            </Card>

            {/* 招募帖列表 */}
            <div className="space-y-4">
              {recruitPosts.map((post) => (
                <Card key={post.id} className="p-4 hover:shadow-lg transition-shadow cursor-pointer">
                  {/* 作者信息 */}
                  <div className="flex items-center gap-3 mb-3">
                    <div className="relative">
                      <div className="w-10 h-10 rounded-full overflow-hidden">
                        <ImageWithFallback
                          src={post.author.avatar}
                          alt={post.author.name}
                          className="w-full h-full object-cover"
                        />
                      </div>
                      {post.author.verified && (
                        <div className="absolute bottom-0 right-0 w-4 h-4 bg-blue-500 rounded-full flex items-center justify-center border border-white">
                          <Check size={10} className="text-white" />
                        </div>
                      )}
                    </div>
                    <div className="flex-1">
                      <h4 className="font-semibold text-sm">{post.author.name}</h4>
                      <p className="text-xs text-gray-500">{post.postedTime}</p>
                    </div>
                    <Badge variant="outline" className="text-xs">
                      {post.currentMembers}/{post.maxMembers}人
                    </Badge>
                  </div>

                  {/* 旅行信息 */}
                  <div className="mb-3">
                    <h3 className="font-semibold text-lg mb-2">{post.destination} {post.duration}</h3>
                    <div className="flex items-center gap-4 text-sm text-gray-600 mb-2">
                      <div className="flex items-center gap-1">
                        <Calendar size={14} />
                        <span>{post.date}</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <Users size={14} />
                        <span>还差{post.maxMembers - post.currentMembers}人</span>
                      </div>
                    </div>
                    <div className="flex items-center gap-2 text-sm mb-2">
                      <span className="text-gray-600">预算:</span>
                      <span className="font-semibold text-orange-600">{post.budget}</span>
                    </div>
                  </div>

                  {/* 标签 */}
                  <div className="flex flex-wrap gap-2 mb-3">
                    {post.tags.map((tag, index) => (
                      <Badge key={index} variant="outline" className="text-xs">
                        {tag}
                      </Badge>
                    ))}
                  </div>

                  {/* 描述 */}
                  <p className="text-sm text-gray-700 mb-3 line-clamp-2">
                    {post.description}
                  </p>

                  {/* 进度条 */}
                  <div className="mb-3">
                    <div className="flex items-center justify-between text-xs text-gray-600 mb-1">
                      <span>招募进度</span>
                      <span>{post.currentMembers}/{post.maxMembers}</span>
                    </div>
                    <div className="h-2 bg-gray-200 rounded-full overflow-hidden">
                      <div 
                        className="h-full bg-gradient-to-r from-teal-500 to-blue-500"
                        style={{ width: `${(post.currentMembers / post.maxMembers) * 100}%` }}
                      ></div>
                    </div>
                  </div>

                  {/* 操作按钮 */}
                  <div className="flex gap-2">
                    <Button variant="outline" className="flex-1" size="sm">
                      查看详情
                    </Button>
                    <Button className="flex-1 bg-gradient-to-r from-teal-500 to-blue-500" size="sm">
                      申请加入
                    </Button>
                  </div>
                </Card>
              ))}
            </div>
          </TabsContent>
        </Tabs>
      </div>

      {/* 安全提示 */}
      <Card className="mx-4 mt-4 p-3 bg-yellow-50 border-yellow-200">
        <div className="flex gap-2 text-sm">
          <div className="text-yellow-600">⚠️</div>
          <div className="flex-1 text-yellow-800">
            <p className="font-semibold mb-1">安全提示</p>
            <ul className="text-xs space-y-1">
              <li>• 出行前充分沟通，了解对方背景</li>
              <li>• 保护个人隐私，不要透露敏感信息</li>
              <li>• AA制费用需提前协商清楚</li>
              <li>• 如遇异常情况，及时联系平台客服</li>
            </ul>
          </div>
        </div>
      </Card>
    </div>
  );
}
