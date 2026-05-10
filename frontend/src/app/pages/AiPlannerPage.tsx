import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Send, Sparkles, User, Bot, MapPin, Calendar, Users, DollarSign, Heart, Zap, Wand2, Copy, Download, Share2 } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Input } from '../components/ui/input';
import { Badge } from '../components/ui/badge';
import { Avatar } from '../components/ui/avatar';
import { ScrollArea } from '../components/ui/scroll-area';

interface Message {
  id: number;
  type: 'user' | 'ai';
  content: string;
  timestamp: Date;
  suggestions?: string[];
  itinerary?: any;
}

export default function AiPlannerPage() {
  const navigate = useNavigate();
  const [messages, setMessages] = useState<Message[]>([
    {
      id: 1,
      type: 'ai',
      content: '你好！我是你的AI旅行规划助手 ✨\n\n我可以帮你：\n• 智能生成个性化行程\n• 推荐最佳游玩路线\n• 预估旅行预算\n• 提供实时旅行建议\n\n告诉我你想去哪里旅行吧！',
      timestamp: new Date(),
      suggestions: ['东京7日游', '巴黎蜜月旅行', '三亚亲子游', '云南深度游']
    }
  ]);
  const [inputValue, setInputValue] = useState('');
  const [isTyping, setIsTyping] = useState(false);
  const scrollAreaRef = useRef<HTMLDivElement>(null);

  const quickQuestions = [
    { icon: MapPin, text: '推荐小众目的地', color: 'text-teal-500' },
    { icon: Calendar, text: '3天周末游', color: 'text-blue-500' },
    { icon: Users, text: '适合家庭的旅行', color: 'text-purple-500' },
    { icon: DollarSign, text: '5000元预算游', color: 'text-orange-500' },
  ];

  // 模拟AI回复
  const generateAiResponse = (userMessage: string): Message => {
    const lowerMessage = userMessage.toLowerCase();
    
    if (lowerMessage.includes('东京') || lowerMessage.includes('日本')) {
      return {
        id: Date.now(),
        type: 'ai',
        content: '太棒了！让我为你规划一次完美的东京之旅 🗾\n\n根据你的需求，我推荐**东京7日深度游**：\n\n📅 **最佳出行时间**：3-5月（樱花季）或10-11月（红叶季）\n💰 **预估预算**：15,000-20,000元/人\n\n**Day 1**：抵达东京 → 新宿 → 都厅夜景\n**Day 2**：浅草寺 → 晴空塔 → 秋叶原\n**Day 3**：筑地市场 → 银座购物 → 东京塔\n**Day 4**：明治神宫 → 原宿 → 涩谷\n**Day 5**：镰仓一日游（江之电）\n**Day 6**：迪士尼乐园/海洋\n**Day 7**：台场 → 购物 → 返程\n\n✨ **AI智能建议**：\n• 购买Suica交通卡，出行更便捷\n• 提前预订米其林餐厅\n• 避开周末和节假日，减少排队时间\n\n需要我为你生成详细的每日行程吗？',
        timestamp: new Date(),
        suggestions: ['生成详细行程', '调整预算方案', '推荐特色美食', '查看住宿推荐'],
        itinerary: {
          destination: '东京',
          days: 7,
          budget: 18000,
          highlights: ['浅草寺', '筑地市场', '迪士尼', '镰仓']
        }
      };
    } else if (lowerMessage.includes('预算') || lowerMessage.includes('5000')) {
      return {
        id: Date.now(),
        type: 'ai',
        content: '了解！我来为你规划一次**高性价比5000元预算游** 💰\n\n推荐目的地：**成都 + 重庆 5日游**\n\n💵 **预算明细**：\n• 交通：1200元（往返机票+市内交通）\n• 住宿：1000元（200元/晚×5晚）\n• 餐饮：1500元（300元/天）\n• 门票：800元\n• 其他：500元\n**总计：5000元**\n\n🎯 **行程亮点**：\n• 宽窄巷子、锦里古街（免费）\n• 大熊猫繁育基地\n• 重庆洪崖洞夜景\n• 解放碑、磁器口\n• 正宗川渝美食体验\n\n这个预算可以玩得很舒服！需要详细的省钱攻略吗？',
        timestamp: new Date(),
        suggestions: ['查看详细行程', '其他高性价比目的地', '住宿推荐', '美食攻略']
      };
    } else if (lowerMessage.includes('家庭') || lowerMessage.includes('亲子')) {
      return {
        id: Date.now(),
        type: 'ai',
        content: '为您推荐适合家庭出游的目的地！👨‍👩‍👧‍👦\n\n**三亚5日亲子度假游**\n\n适合年龄：3-12岁儿童\n\n🏖️ **行程特色**：\n• 亚特兰蒂斯水世界（孩子最爱）\n• 蜈支洲岛浮潜体验\n• 南山寺文化之旅\n• 海滩挖沙、堆城堡\n• 海鲜BBQ派对\n\n👨‍👩‍👧 **亲子友好服务**：\n• 儿童餐具和座椅\n• 婴儿车免费租借\n• 儿童游乐设施\n• 家庭套房住宿\n\n安全、有趣、寓教于乐！需要我推荐亲子酒店吗？',
        timestamp: new Date(),
        suggestions: ['查看酒店推荐', '其他亲子目的地', '注意事项', '打包清单']
      };
    } else if (lowerMessage.includes('周末') || lowerMessage.includes('3天')) {
      return {
        id: Date.now(),
        type: 'ai',
        content: '3天周末游，完美的小长假安排！🎒\n\n**苏杭3日文化之旅**\n\n📍 **行程安排**：\n**Day 1**：苏州（拙政园 → 平江路 → 金鸡湖夜景）\n**Day 2**：苏州 → 杭州（虎丘 → 西湖 → 河坊街）\n**Day 3**：杭州（灵隐寺 → 龙井村 → 返程）\n\n🚄 **交通**：高铁往返，苏杭之间30分钟\n💰 **预算**：2000-3000元/人\n\n🎨 **文化体验**：\n• 园林艺术鉴赏\n• 品龙井茶\n• 尝江南美食\n• 夜游西湖\n\n上有天堂，下有苏杭！需要美食推荐吗？',
        timestamp: new Date(),
        suggestions: ['美食推荐', '其他周末游', '住宿建议', '摄影打卡点']
      };
    } else if (lowerMessage.includes('小众') || lowerMessage.includes('推荐')) {
      return {
        id: Date.now(),
        type: 'ai',
        content: '为你挖掘小众宝藏目的地！💎\n\n**推荐1：甘南藏区**\n• 拉卜楞寺、桑科草原\n• 人少景美，原生态体验\n• 预算：4000-6000元/7天\n\n**推荐2：霞浦**\n• 摄影天堂，滩涂日出日落\n• 渔村文化、海鲜美食\n• 预算：3000-4000元/5天\n\n**推荐3：恩施大峡谷**\n• 中国版科罗拉多大峡谷\n• 狮子关水上栈道\n• 预算：3500-5000元/5天\n\n这些地方商业化程度低，原汁原味！想了解哪个？',
        timestamp: new Date(),
        suggestions: ['甘南详细攻略', '霞浦摄影指南', '恩施行程规划', '更多小众推荐']
      };
    }
    
    return {
      id: Date.now(),
      type: 'ai',
      content: '好的！我理解你的需求了 😊\n\n为了给你最精准的行程规划，可以告诉我：\n1. 出行人数和年龄段？\n2. 大概的预算范围？\n3. 出行天数？\n4. 偏好的旅行类型（文化、美食、自然、购物等）？\n\n或者你可以直接说出你的目的地，我会为你量身定制行程！',
      timestamp: new Date(),
      suggestions: ['东京7日游', '三亚亲子游', '成都美食游', '云南深度游']
    };
  };

  const handleSend = () => {
    if (!inputValue.trim()) return;

    // 添加用户消息
    const userMessage: Message = {
      id: Date.now(),
      type: 'user',
      content: inputValue,
      timestamp: new Date()
    };
    setMessages(prev => [...prev, userMessage]);
    setInputValue('');
    setIsTyping(true);

    // 模拟AI思考时间
    setTimeout(() => {
      const aiResponse = generateAiResponse(inputValue);
      setMessages(prev => [...prev, aiResponse]);
      setIsTyping(false);
    }, 1500);
  };

  const handleSuggestionClick = (suggestion: string) => {
    setInputValue(suggestion);
    setTimeout(() => handleSend(), 100);
  };

  const handleQuickQuestion = (question: string) => {
    setInputValue(question);
    setTimeout(() => handleSend(), 100);
  };

  const saveItinerary = (itinerary: any) => {
    // 保存行程到我的行程
    navigate('/trip-planner');
  };

  useEffect(() => {
    // 自动滚动到底部
    if (scrollAreaRef.current) {
      scrollAreaRef.current.scrollTop = scrollAreaRef.current.scrollHeight;
    }
  }, [messages]);

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-50 to-white flex flex-col">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-gradient-to-r from-teal-500 to-blue-500 shadow-lg">
        <div className="px-4 py-3 flex items-center gap-3 text-white">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <div className="flex-1">
            <div className="flex items-center gap-2">
              <Sparkles size={20} />
              <h1 className="text-lg font-semibold">AI智能规划助手</h1>
            </div>
            <p className="text-xs opacity-90">基于GPT-4的智能旅行顾问</p>
          </div>
          <div className="w-2 h-2 bg-green-400 rounded-full animate-pulse"></div>
        </div>
      </div>

      {/* 快捷问题 */}
      {messages.length <= 1 && (
        <div className="px-4 py-4 bg-white border-b">
          <p className="text-sm text-gray-600 mb-3">💡 试试这些快捷问题：</p>
          <div className="grid grid-cols-2 gap-2">
            {quickQuestions.map((question, index) => {
              const Icon = question.icon;
              return (
                <Button
                  key={index}
                  variant="outline"
                  size="sm"
                  className="justify-start"
                  onClick={() => handleQuickQuestion(question.text)}
                >
                  <Icon size={16} className={`mr-2 ${question.color}`} />
                  <span className="text-xs">{question.text}</span>
                </Button>
              );
            })}
          </div>
        </div>
      )}

      {/* 消息列表 */}
      <div 
        ref={scrollAreaRef}
        className="flex-1 overflow-y-auto px-4 py-4 space-y-4"
      >
        {messages.map((message) => (
          <div
            key={message.id}
            className={`flex gap-3 ${message.type === 'user' ? 'flex-row-reverse' : 'flex-row'}`}
          >
            {/* 头像 */}
            <div className={`w-10 h-10 rounded-full flex items-center justify-center flex-shrink-0 ${
              message.type === 'ai' 
                ? 'bg-gradient-to-r from-teal-500 to-blue-500 text-white' 
                : 'bg-gray-200 text-gray-600'
            }`}>
              {message.type === 'ai' ? <Bot size={20} /> : <User size={20} />}
            </div>

            {/* 消息内容 */}
            <div className={`flex-1 max-w-[80%] ${message.type === 'user' ? 'flex justify-end' : ''}`}>
              <div className={`rounded-2xl px-4 py-3 ${
                message.type === 'ai'
                  ? 'bg-white shadow-md border border-gray-100'
                  : 'bg-gradient-to-r from-teal-500 to-blue-500 text-white'
              }`}>
                <p className="text-sm leading-relaxed whitespace-pre-line">
                  {message.content}
                </p>

                {/* 行程卡片 */}
                {message.itinerary && (
                  <Card className="mt-3 p-3 bg-gradient-to-r from-teal-50 to-blue-50 border-teal-200">
                    <div className="flex items-center justify-between mb-2">
                      <h4 className="font-semibold text-sm">生成的行程方案</h4>
                      <Badge className="bg-teal-500">AI推荐</Badge>
                    </div>
                    <div className="grid grid-cols-2 gap-2 text-xs">
                      <div className="flex items-center gap-1">
                        <MapPin size={12} className="text-teal-600" />
                        <span>{message.itinerary.destination}</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <Calendar size={12} className="text-teal-600" />
                        <span>{message.itinerary.days}天</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <DollarSign size={12} className="text-teal-600" />
                        <span>¥{message.itinerary.budget}</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <Heart size={12} className="text-teal-600" />
                        <span>{message.itinerary.highlights.length}个亮点</span>
                      </div>
                    </div>
                    <div className="flex gap-2 mt-3">
                      <Button 
                        size="sm" 
                        className="flex-1 bg-gradient-to-r from-teal-500 to-blue-500 text-xs"
                        onClick={() => saveItinerary(message.itinerary)}
                      >
                        保存到我的行程
                      </Button>
                      <Button size="sm" variant="outline" className="text-xs">
                        <Share2 size={14} />
                      </Button>
                    </div>
                  </Card>
                )}

                {/* 建议按钮 */}
                {message.suggestions && message.suggestions.length > 0 && (
                  <div className="flex flex-wrap gap-2 mt-3">
                    {message.suggestions.map((suggestion, index) => (
                      <Button
                        key={index}
                        variant="outline"
                        size="sm"
                        className="text-xs"
                        onClick={() => handleSuggestionClick(suggestion)}
                      >
                        <Zap size={12} className="mr-1" />
                        {suggestion}
                      </Button>
                    ))}
                  </div>
                )}
              </div>

              <div className="text-xs text-gray-400 mt-1 px-1">
                {message.timestamp.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })}
              </div>
            </div>
          </div>
        ))}

        {/* AI输入中状态 */}
        {isTyping && (
          <div className="flex gap-3">
            <div className="w-10 h-10 rounded-full bg-gradient-to-r from-teal-500 to-blue-500 flex items-center justify-center text-white flex-shrink-0">
              <Bot size={20} />
            </div>
            <div className="bg-white shadow-md border border-gray-100 rounded-2xl px-4 py-3">
              <div className="flex gap-1">
                <div className="w-2 h-2 bg-teal-500 rounded-full animate-bounce"></div>
                <div className="w-2 h-2 bg-teal-500 rounded-full animate-bounce" style={{ animationDelay: '0.1s' }}></div>
                <div className="w-2 h-2 bg-teal-500 rounded-full animate-bounce" style={{ animationDelay: '0.2s' }}></div>
              </div>
            </div>
          </div>
        )}
      </div>

      {/* 输入框 */}
      <div className="sticky bottom-0 bg-white border-t shadow-lg px-4 py-3">
        <div className="flex gap-2">
          <div className="flex-1 relative">
            <Input
              type="text"
              placeholder="输入你的旅行需求..."
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleSend()}
              className="pr-10"
            />
            <Wand2 className="absolute right-3 top-1/2 -translate-y-1/2 text-teal-500" size={18} />
          </div>
          <Button
            onClick={handleSend}
            disabled={!inputValue.trim() || isTyping}
            className="bg-gradient-to-r from-teal-500 to-blue-500"
            size="icon"
          >
            <Send size={18} />
          </Button>
        </div>
        <p className="text-xs text-gray-500 mt-2 text-center">
          AI生成内容仅供参考，实际行程请以实际情况为准
        </p>
      </div>
    </div>
  );
}
