import { useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Bell, MessageCircle, Package, Gift, ChevronRight, Trash2, Sparkles } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Tabs, TabsList, TabsTrigger } from '../components/ui/tabs';
import BottomNavigation from '../components/BottomNavigation';
import MobileShell from '../MobileShell';

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
      link: '/order/1',
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
      link: '/trip-in-progress',
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
      link: '/deals',
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
      link: '/order/2',
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
      link: '/profile',
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
      link: '/wallet',
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
      link: '/order/3',
    },
  ];

  const conversations = [
    {
      id: 1,
      name: '在线客服',
      lastMessage: '好的，您的问题我已经记录，稍后会有专人联系您',
      time: '10分钟前',
      unread: 0,
      isOnline: true,
    },
    {
      id: 2,
      name: '订单助手',
      lastMessage: '您的订单已经确认，祝您旅途愉快！',
      time: '2小时前',
      unread: 1,
      isOnline: true,
    },
    {
      id: 3,
      name: '行程顾问',
      lastMessage: '关于您咨询的行程规划问题...',
      time: '1天前',
      unread: 0,
      isOnline: false,
    },
  ];

  const filteredMessages = useMemo(() => {
    if (selectedTab === 'all') return messages;
    return messages.filter((msg) => msg.type === selectedTab);
  }, [messages, selectedTab]);

  const unreadCount = messages.filter((msg) => !msg.isRead).length;
  const unreadConversations = conversations.filter((conversation) => conversation.unread > 0);

  return (
    <MobileShell>
      <div className="space-y-5">
        <div className="rounded-[30px] bg-gradient-to-br from-slate-900 via-slate-800 to-sky-600 p-5 text-white shadow-[0_18px_55px_rgba(15,23,42,0.28)]">
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
              <p className="text-sm text-white/75">通知与会话</p>
              <h1 className="mt-1 text-[28px] font-semibold tracking-tight">消息中心</h1>
            </div>
            {unreadCount > 0 && (
              <Button variant="outline" size="sm" className="rounded-2xl border-white/20 bg-white/10 text-white hover:bg-white/20">
                全部已读
              </Button>
            )}
          </div>

          <div className="mb-4 inline-flex items-center gap-2 rounded-full bg-white/10 px-3 py-1.5 text-xs font-medium text-white/85 backdrop-blur-sm">
            <Sparkles size={14} />
            订单、活动与系统提醒统一汇总
          </div>

          <div className="grid grid-cols-3 gap-3">
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">{unreadCount}</p>
              <p className="mt-1 text-xs text-white/70">未读消息</p>
            </Card>
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">{conversations.length}</p>
              <p className="mt-1 text-xs text-white/70">服务会话</p>
            </Card>
            <Card className="rounded-[24px] border-white/10 bg-white/10 p-4 text-center text-white backdrop-blur-sm">
              <p className="text-2xl font-semibold">24h</p>
              <p className="mt-1 text-xs text-white/70">在线支持</p>
            </Card>
          </div>
        </div>

        <Tabs value={selectedTab} onValueChange={setSelectedTab}>
          <TabsList className="grid h-auto w-full grid-cols-4 rounded-[24px] bg-white/88 p-1 shadow-[0_10px_30px_rgba(15,23,42,0.05)]">
            <TabsTrigger value="all" className="rounded-[18px] py-3 text-sm">
              全部
              {unreadCount > 0 && <Badge className="ml-1 bg-rose-500 text-white text-[10px]">{unreadCount}</Badge>}
            </TabsTrigger>
            <TabsTrigger value="order" className="rounded-[18px] py-3 text-sm">订单</TabsTrigger>
            <TabsTrigger value="system" className="rounded-[18px] py-3 text-sm">系统</TabsTrigger>
            <TabsTrigger value="promotion" className="rounded-[18px] py-3 text-sm">活动</TabsTrigger>
          </TabsList>
        </Tabs>

        <Card
          className="rounded-[28px] border-teal-100 bg-gradient-to-r from-teal-50 via-cyan-50 to-sky-50 p-4 shadow-[0_14px_35px_rgba(15,23,42,0.05)]"
          onClick={() => {
            /* 打开客服对话 */
          }}
        >
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-gradient-to-br from-teal-500 to-sky-500 text-white">
                <MessageCircle size={22} />
              </div>
              <div>
                <h3 className="font-semibold text-slate-900">在线客服</h3>
                <p className="text-sm text-slate-500">24小时为您服务</p>
              </div>
            </div>
            <div className="flex items-center gap-2">
              <Badge className="bg-emerald-500 text-white">在线</Badge>
              <ChevronRight className="text-slate-400" size={20} />
            </div>
          </div>
        </Card>

        {selectedTab === 'all' && unreadConversations.length > 0 && (
          <section>
            <div className="mb-3 px-1">
              <h3 className="text-sm font-semibold text-slate-700">服务会话</h3>
            </div>
            <div className="space-y-3">
              {unreadConversations.map((conversation) => (
                <Card
                  key={conversation.id}
                  className="rounded-[26px] border-white/70 bg-white/90 p-4 shadow-[0_12px_30px_rgba(15,23,42,0.05)]"
                  onClick={() => {
                    /* 打开对话 */
                  }}
                >
                  <div className="flex items-center gap-3">
                    <div className="relative">
                      <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-gradient-to-br from-teal-500 to-sky-500 text-white font-semibold">
                        {conversation.name.charAt(0)}
                      </div>
                      {conversation.isOnline && <div className="absolute bottom-0 right-0 h-3 w-3 rounded-full border-2 border-white bg-emerald-500" />}
                    </div>
                    <div className="min-w-0 flex-1">
                      <div className="mb-1 flex items-center justify-between gap-3">
                        <h4 className="font-medium text-slate-900">{conversation.name}</h4>
                        <span className="text-xs text-slate-500">{conversation.time}</span>
                      </div>
                      <p className="line-clamp-1 text-sm text-slate-500">{conversation.lastMessage}</p>
                    </div>
                    {conversation.unread > 0 && <Badge className="bg-rose-500 text-white">{conversation.unread}</Badge>}
                  </div>
                </Card>
              ))}
            </div>
          </section>
        )}

        <section>
          <div className="space-y-3">
            {filteredMessages.length > 0 ? (
              filteredMessages.map((message) => {
                const Icon = message.icon;
                return (
                  <Card
                    key={message.id}
                    className={`rounded-[28px] border-white/70 bg-white/90 p-4 shadow-[0_12px_30px_rgba(15,23,42,0.05)] ${!message.isRead ? 'ring-1 ring-sky-100' : ''}`}
                    onClick={() => navigate(message.link)}
                  >
                    <div className="flex items-start gap-3">
                      <div className={`relative flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-2xl ${message.iconBg}`}>
                        {!message.isRead && <div className="absolute -right-1 -top-1 h-3 w-3 rounded-full bg-rose-500" />}
                        <Icon className={message.iconColor} size={22} />
                      </div>
                      <div className="min-w-0 flex-1">
                        <div className="mb-1 flex items-start justify-between gap-3">
                          <h4 className={`font-medium ${!message.isRead ? 'text-slate-900' : 'text-slate-700'}`}>{message.title}</h4>
                          <span className="text-xs text-slate-500">{message.time}</span>
                        </div>
                        <p className="line-clamp-2 text-sm leading-6 text-slate-500">{message.content}</p>
                      </div>
                      <ChevronRight className="mt-1 text-slate-400" size={18} />
                    </div>
                  </Card>
                );
              })
            ) : (
              <Card className="rounded-[28px] border-white/70 bg-white/90 py-16 text-center shadow-[0_12px_30px_rgba(15,23,42,0.05)]">
                <Bell size={48} className="mx-auto mb-4 text-slate-300" />
                <p className="text-slate-500">暂无消息</p>
              </Card>
            )}
          </div>
        </section>

        {filteredMessages.length > 0 && (
          <Button variant="outline" className="h-12 w-full rounded-2xl border-slate-200 bg-white text-slate-600 hover:bg-slate-50">
            <Trash2 size={16} className="mr-2" />
            清空已读消息
          </Button>
        )}
      </div>

      <BottomNavigation />
    </MobileShell>
  );
}
