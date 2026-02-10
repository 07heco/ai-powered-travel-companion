import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Bell, MessageCircle, Package, Gift, ChevronRight, Trash2 } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../components/ui/tabs';
import { Avatar } from '../components/ui/avatar';

export default function MessageCenterPage() {
  const navigate = useNavigate();
  const [selectedTab, setSelectedTab] = useState('all');

  const messages = [
    {
      id: 1,
      type: 'order',
      icon: Package,
      iconColor: 'text-blue-500',
      iconBg: 'bg-blue-50',
      title: '订单支付成功',
      content: '您的东京5日自由行订单已支付成功，请关注出行通知',
      time: '2小时前',
      isRead: false,
      link: '/order/1'
    },
    {
      id: 2,
      type: 'system',
      icon: Bell,
      iconColor: 'text-orange-500',
      iconBg: 'bg-orange-50',
      title: '出行提醒',
      content: '距离您的东京之行还有7天，请提前准备好护照等证件',
      time: '5小时前',
      isRead: false,
      link: '/trip-in-progress'
    },
    {
      id: 3,
      type: 'promotion',
      icon: Gift,
      iconColor: 'text-red-500',
      iconBg: 'bg-red-50',
      title: '限时优惠来袭',
      content: '春节特惠｜巴厘岛7日游立减2000元，点击查看详情',
      time: '1天前',
      isRead: true,
      link: '/deals'
    },
    {
      id: 4,
      type: 'order',
      icon: Package,
      iconColor: 'text-blue-500',
      iconBg: 'bg-blue-50',
      title: '航班变动通知',
      content: '您预订的CA183航班起飞时间调整为09:00，请注意查收',
      time: '1天前',
      isRead: true,
      link: '/order/2'
    },
    {
      id: 5,
      type: 'system',
      icon: Bell,
      iconColor: 'text-orange-500',
      iconBg: 'bg-orange-50',
      title: '评价有奖',
      content: '您的京都之行已结束，快来评价赢取100元优惠券',
      time: '2天前',
      isRead: true,
      link: '/profile'
    },
    {
      id: 6,
      type: 'promotion',
      icon: Gift,
      iconColor: 'text-red-500',
      iconBg: 'bg-red-50',
      title: '优惠券即将过期',
      content: '您有3张优惠券将于7天后过期，快去使用吧',
      time: '3天前',
      isRead: true,
      link: '/wallet'
    },
    {
      id: 7,
      type: 'order',
      icon: Package,
      iconColor: 'text-blue-500',
      iconBg: 'bg-blue-50',
      title: '退款成功',
      content: '订单退款已原路退回，请注意查收',
      time: '5天前',
      isRead: true,
      link: '/order/3'
    },
  ];

  const conversations = [
    {
      id: 1,
      name: '在线客服',
      avatar: '',
      lastMessage: '好的，您的问题我已经记录，稍后会有专人联系您',
      time: '10分钟前',
      unread: 0,
      isOnline: true
    },
    {
      id: 2,
      name: '订单助手',
      avatar: '',
      lastMessage: '您的订单已经确认，祝您旅途愉快！',
      time: '2小时前',
      unread: 1,
      isOnline: true
    },
    {
      id: 3,
      name: '行程顾问',
      avatar: '',
      lastMessage: '关于您咨询的行程规划问题...',
      time: '1天前',
      unread: 0,
      isOnline: false
    },
  ];

  const filterMessages = () => {
    if (selectedTab === 'all') return messages;
    return messages.filter(msg => msg.type === selectedTab);
  };

  const unreadCount = messages.filter(msg => !msg.isRead).length;

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center gap-3">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold flex-1">消息中心</h1>
          {unreadCount > 0 && (
            <Button
              variant="ghost"
              size="sm"
              className="text-teal-600"
              onClick={() => {
                // 标记所有为已读
              }}
            >
              全部已读
            </Button>
          )}
        </div>

        {/* Tabs */}
        <div className="px-4 pb-2">
          <Tabs value={selectedTab} onValueChange={setSelectedTab}>
            <TabsList className="w-full grid grid-cols-4 bg-gray-100">
              <TabsTrigger value="all" className="text-sm">
                全部
                {unreadCount > 0 && (
                  <Badge className="ml-1 bg-red-500 text-white text-xs h-4 px-1">
                    {unreadCount}
                  </Badge>
                )}
              </TabsTrigger>
              <TabsTrigger value="order" className="text-sm">订单</TabsTrigger>
              <TabsTrigger value="system" className="text-sm">系统</TabsTrigger>
              <TabsTrigger value="promotion" className="text-sm">活动</TabsTrigger>
            </TabsList>
          </Tabs>
        </div>
      </div>

      {/* 在线客服入口 */}
      <Card 
        className="mx-4 mt-4 mb-2 p-4 bg-gradient-to-r from-teal-50 to-blue-50 border-teal-200 cursor-pointer hover:shadow-lg transition-shadow"
        onClick={() => {/* 打开客服对话 */}}
      >
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-12 h-12 rounded-full bg-gradient-to-r from-teal-500 to-blue-500 flex items-center justify-center text-white">
              <MessageCircle size={24} />
            </div>
            <div>
              <h3 className="font-semibold">在线客服</h3>
              <p className="text-sm text-gray-600">24小时为您服务</p>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <Badge className="bg-green-500">在线</Badge>
            <ChevronRight className="text-gray-400" size={20} />
          </div>
        </div>
      </Card>

      {/* 客服对话列表 */}
      {selectedTab === 'all' && conversations.filter(c => c.unread > 0).length > 0 && (
        <div className="bg-white mb-2">
          <div className="px-4 py-3 border-b">
            <h3 className="text-sm font-semibold text-gray-700">客服对话</h3>
          </div>
          <div>
            {conversations.filter(c => c.unread > 0).map((conversation) => (
              <div
                key={conversation.id}
                className="px-4 py-3 border-b hover:bg-gray-50 cursor-pointer flex items-center gap-3"
                onClick={() => {/* 打开对话 */}}
              >
                <div className="relative">
                  <div className="w-12 h-12 rounded-full bg-gradient-to-r from-teal-500 to-blue-500 flex items-center justify-center text-white font-semibold">
                    {conversation.name.charAt(0)}
                  </div>
                  {conversation.isOnline && (
                    <div className="absolute bottom-0 right-0 w-3 h-3 bg-green-500 rounded-full border-2 border-white"></div>
                  )}
                </div>
                <div className="flex-1">
                  <div className="flex items-center justify-between mb-1">
                    <h4 className="font-medium">{conversation.name}</h4>
                    <span className="text-xs text-gray-500">{conversation.time}</span>
                  </div>
                  <p className="text-sm text-gray-600 line-clamp-1">{conversation.lastMessage}</p>
                </div>
                {conversation.unread > 0 && (
                  <Badge className="bg-red-500 text-white">
                    {conversation.unread}
                  </Badge>
                )}
              </div>
            ))}
          </div>
        </div>
      )}

      {/* 消息列表 */}
      <div className="bg-white">
        {filterMessages().length > 0 ? (
          <div>
            {filterMessages().map((message) => {
              const Icon = message.icon;
              return (
                <div
                  key={message.id}
                  className={`px-4 py-4 border-b hover:bg-gray-50 cursor-pointer relative ${
                    !message.isRead ? 'bg-blue-50/30' : ''
                  }`}
                  onClick={() => navigate(message.link)}
                >
                  {!message.isRead && (
                    <div className="absolute top-4 left-2 w-2 h-2 bg-red-500 rounded-full"></div>
                  )}
                  
                  <div className="flex gap-3">
                    <div className={`w-12 h-12 rounded-full ${message.iconBg} flex items-center justify-center flex-shrink-0`}>
                      <Icon className={message.iconColor} size={24} />
                    </div>
                    
                    <div className="flex-1">
                      <div className="flex items-start justify-between mb-1">
                        <h4 className={`font-medium ${!message.isRead ? 'text-gray-900' : 'text-gray-700'}`}>
                          {message.title}
                        </h4>
                        <span className="text-xs text-gray-500 ml-2 flex-shrink-0">{message.time}</span>
                      </div>
                      <p className="text-sm text-gray-600 line-clamp-2 mb-2">
                        {message.content}
                      </p>
                    </div>

                    <ChevronRight className="text-gray-400 flex-shrink-0" size={20} />
                  </div>
                </div>
              );
            })}
          </div>
        ) : (
          <div className="text-center py-20 text-gray-400">
            <Bell size={48} className="mx-auto mb-4 opacity-50" />
            <p>暂无消息</p>
          </div>
        )}
      </div>

      {/* 清空按钮 */}
      {filterMessages().length > 0 && (
        <div className="px-4 py-6">
          <Button
            variant="outline"
            className="w-full"
            onClick={() => {
              // 清空消息
            }}
          >
            <Trash2 size={16} className="mr-2" />
            清空已读消息
          </Button>
        </div>
      )}
    </div>
  );
}
