import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Wallet, Gift, Ticket, CreditCard, TrendingUp, Clock, ChevronRight, Plus, History } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../components/ui/tabs';
import { Progress } from '../components/ui/progress';

export default function WalletPage() {
  const navigate = useNavigate();
  const [selectedTab, setSelectedTab] = useState('coupons');

  // 用户余额和积分
  const walletInfo = {
    balance: 1280.50,
    points: 3560,
    coupons: 12
  };

  // 优惠券数据
  const coupons = [
    {
      id: 1,
      type: '满减券',
      title: '机票立减券',
      amount: 300,
      condition: '满2000元可用',
      validUntil: '2026-03-31',
      status: 'available',
      scope: '国内国际机票',
      color: 'from-orange-500 to-red-500'
    },
    {
      id: 2,
      type: '折扣券',
      title: '酒店9折券',
      discount: '9折',
      condition: '满1000元可用',
      validUntil: '2026-04-15',
      status: 'available',
      scope: '全场酒店',
      color: 'from-purple-500 to-pink-500'
    },
    {
      id: 3,
      type: '满减券',
      title: '门票优惠券',
      amount: 50,
      condition: '满300元可用',
      validUntil: '2026-03-20',
      status: 'available',
      scope: '景区门票',
      color: 'from-teal-500 to-blue-500'
    },
    {
      id: 4,
      type: '满减券',
      title: '周边游特惠',
      amount: 100,
      condition: '满500元可用',
      validUntil: '2026-02-28',
      status: 'expiring',
      scope: '周边游产品',
      color: 'from-green-500 to-teal-500'
    },
    {
      id: 5,
      type: '满减券',
      title: '春节大礼包',
      amount: 500,
      condition: '满3000元可用',
      validUntil: '2026-02-20',
      status: 'expiring',
      scope: '全场通用',
      color: 'from-red-500 to-orange-500'
    },
    {
      id: 6,
      type: '折扣券',
      title: '新用户专享',
      discount: '8折',
      condition: '满800元可用',
      validUntil: '2026-01-31',
      status: 'expired',
      scope: '全场通用',
      color: 'from-gray-400 to-gray-500'
    },
  ];

  // 积分任务
  const pointsTasks = [
    { id: 1, title: '每日签到', points: 10, status: 'available', icon: '📅' },
    { id: 2, title: '完善个人资料', points: 50, status: 'completed', icon: '✅' },
    { id: 3, title: '首次预订', points: 200, status: 'completed', icon: '✅' },
    { id: 4, title: '分享给好友', points: 30, status: 'available', icon: '🎁' },
    { id: 5, title: '发布旅行笔记', points: 100, status: 'available', icon: '📝' },
  ];

  // 交易记录
  const transactions = [
    { id: 1, type: 'expense', title: '东京5日自由行', amount: -3999, time: '2026-02-05 14:23', status: '已支付' },
    { id: 2, type: 'income', title: '退款', amount: +680, time: '2026-02-01 09:15', status: '已到账' },
    { id: 3, type: 'expense', title: '三亚酒店预订', amount: -1588, time: '2026-01-28 16:45', status: '已支付' },
    { id: 4, type: 'income', title: '充值', amount: +1000, time: '2026-01-25 10:30', status: '已到账' },
  ];

  const availableCoupons = coupons.filter(c => c.status === 'available');
  const expiringCoupons = coupons.filter(c => c.status === 'expiring');
  const expiredCoupons = coupons.filter(c => c.status === 'expired');

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-gradient-to-r from-teal-500 to-blue-500">
        <div className="px-4 py-3 flex items-center gap-3 text-white">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold flex-1">我的钱包</h1>
          <Wallet size={20} />
        </div>

        {/* 余额卡片 */}
        <div className="px-4 pb-6">
          <Card className="bg-white/10 backdrop-blur-sm border-white/20 text-white p-4">
            <div className="flex items-center justify-between mb-4">
              <div>
                <div className="text-sm opacity-90 mb-1">账户余额</div>
                <div className="text-3xl font-bold">¥{walletInfo.balance.toFixed(2)}</div>
              </div>
              <Button 
                variant="outline" 
                size="sm"
                className="bg-white/20 border-white/40 text-white hover:bg-white/30"
                onClick={() => {/* 充值 */}}
              >
                <Plus size={16} className="mr-1" />
                充值
              </Button>
            </div>
            
            <div className="grid grid-cols-2 gap-4">
              <div 
                className="bg-white/10 rounded-lg p-3 cursor-pointer hover:bg-white/20 transition-colors"
                onClick={() => setSelectedTab('points')}
              >
                <div className="flex items-center gap-2 mb-1">
                  <TrendingUp size={16} />
                  <span className="text-sm opacity-90">我的积分</span>
                </div>
                <div className="text-xl font-bold">{walletInfo.points}</div>
              </div>
              <div 
                className="bg-white/10 rounded-lg p-3 cursor-pointer hover:bg-white/20 transition-colors"
                onClick={() => setSelectedTab('coupons')}
              >
                <div className="flex items-center gap-2 mb-1">
                  <Gift size={16} />
                  <span className="text-sm opacity-90">优惠券</span>
                </div>
                <div className="text-xl font-bold">{walletInfo.coupons}张</div>
              </div>
            </div>
          </Card>
        </div>
      </div>

      {/* Tabs */}
      <div className="px-4 -mt-2 mb-4">
        <Tabs value={selectedTab} onValueChange={setSelectedTab}>
          <TabsList className="w-full grid grid-cols-3 bg-white">
            <TabsTrigger value="coupons" className="text-sm">
              优惠券
              <Badge className="ml-1 bg-red-500 text-white text-xs h-4 px-1">
                {availableCoupons.length}
              </Badge>
            </TabsTrigger>
            <TabsTrigger value="points" className="text-sm">积分</TabsTrigger>
            <TabsTrigger value="transactions" className="text-sm">账单</TabsTrigger>
          </TabsList>

          {/* 优惠券 Tab */}
          <TabsContent value="coupons" className="mt-4">
            {/* 领券中心入口 */}
            <Card 
              className="mb-4 p-4 bg-gradient-to-r from-orange-50 to-red-50 border-orange-200 cursor-pointer hover:shadow-lg transition-shadow"
              onClick={() => navigate('/deals')}
            >
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="w-12 h-12 rounded-full bg-gradient-to-r from-orange-500 to-red-500 flex items-center justify-center text-white">
                    <Gift size={24} />
                  </div>
                  <div>
                    <h3 className="font-semibold">领券中心</h3>
                    <p className="text-sm text-gray-600">更多优惠券等你来领</p>
                  </div>
                </div>
                <ChevronRight className="text-gray-400" size={20} />
              </div>
            </Card>

            {/* 即将过期 */}
            {expiringCoupons.length > 0 && (
              <div className="mb-4">
                <div className="flex items-center gap-2 mb-3">
                  <Clock size={16} className="text-orange-500" />
                  <h3 className="font-semibold text-sm">即将过期</h3>
                </div>
                <div className="space-y-3">
                  {expiringCoupons.map((coupon) => (
                    <Card 
                      key={coupon.id}
                      className="overflow-hidden border-orange-200"
                    >
                      <div className={`h-2 bg-gradient-to-r ${coupon.color}`}></div>
                      <div className="p-4">
                        <div className="flex items-center justify-between mb-3">
                          <div>
                            <Badge className="mb-2 bg-orange-100 text-orange-600">
                              {coupon.type}
                            </Badge>
                            <h4 className="font-semibold text-lg">{coupon.title}</h4>
                            <div className="text-3xl font-bold text-orange-600 my-1">
                              {coupon.amount ? `¥${coupon.amount}` : coupon.discount}
                            </div>
                            <p className="text-sm text-gray-600">{coupon.condition}</p>
                          </div>
                          <Button 
                            size="sm"
                            className="bg-gradient-to-r from-orange-500 to-red-500"
                            onClick={() => navigate('/destinations')}
                          >
                            立即使用
                          </Button>
                        </div>
                        <div className="flex items-center justify-between text-xs text-gray-500 pt-2 border-t">
                          <span>适用：{coupon.scope}</span>
                          <span className="text-orange-600">有效期至 {coupon.validUntil}</span>
                        </div>
                      </div>
                    </Card>
                  ))}
                </div>
              </div>
            )}

            {/* 可用优惠券 */}
            {availableCoupons.length > 0 && (
              <div className="mb-4">
                <h3 className="font-semibold text-sm mb-3">可用优惠券</h3>
                <div className="space-y-3">
                  {availableCoupons.map((coupon) => (
                    <Card 
                      key={coupon.id}
                      className="overflow-hidden"
                    >
                      <div className={`h-2 bg-gradient-to-r ${coupon.color}`}></div>
                      <div className="p-4">
                        <div className="flex items-center justify-between mb-3">
                          <div>
                            <Badge className="mb-2" variant="outline">
                              {coupon.type}
                            </Badge>
                            <h4 className="font-semibold">{coupon.title}</h4>
                            <div className={`text-2xl font-bold bg-gradient-to-r ${coupon.color} bg-clip-text text-transparent my-1`}>
                              {coupon.amount ? `¥${coupon.amount}` : coupon.discount}
                            </div>
                            <p className="text-sm text-gray-600">{coupon.condition}</p>
                          </div>
                          <Button 
                            variant="outline"
                            size="sm"
                            onClick={() => navigate('/destinations')}
                          >
                            去使用
                          </Button>
                        </div>
                        <div className="flex items-center justify-between text-xs text-gray-500 pt-2 border-t">
                          <span>适用：{coupon.scope}</span>
                          <span>有效期至 {coupon.validUntil}</span>
                        </div>
                      </div>
                    </Card>
                  ))}
                </div>
              </div>
            )}

            {/* 已过期 */}
            {expiredCoupons.length > 0 && (
              <div>
                <h3 className="font-semibold text-sm mb-3 text-gray-500">已过期</h3>
                <div className="space-y-3">
                  {expiredCoupons.map((coupon) => (
                    <Card 
                      key={coupon.id}
                      className="overflow-hidden opacity-50"
                    >
                      <div className="bg-gray-300 h-2"></div>
                      <div className="p-4">
                        <div className="flex items-center justify-between">
                          <div>
                            <Badge className="mb-2" variant="secondary">
                              已过期
                            </Badge>
                            <h4 className="font-semibold text-gray-600">{coupon.title}</h4>
                            <div className="text-2xl font-bold text-gray-400 my-1">
                              {coupon.amount ? `¥${coupon.amount}` : coupon.discount}
                            </div>
                          </div>
                        </div>
                      </div>
                    </Card>
                  ))}
                </div>
              </div>
            )}
          </TabsContent>

          {/* 积分 Tab */}
          <TabsContent value="points" className="mt-4">
            {/* 积分总览 */}
            <Card className="mb-4 p-4 bg-gradient-to-r from-teal-50 to-blue-50">
              <div className="text-center">
                <div className="text-sm text-gray-600 mb-2">当前积分</div>
                <div className="text-4xl font-bold bg-gradient-to-r from-teal-500 to-blue-500 bg-clip-text text-transparent mb-4">
                  {walletInfo.points}
                </div>
                <div className="flex items-center gap-2 justify-center text-sm text-gray-600">
                  <span>距离下一等级还需</span>
                  <span className="font-semibold text-teal-600">440积分</span>
                </div>
                <Progress value={88.9} className="mt-2 h-2" />
              </div>
            </Card>

            {/* 积分任务 */}
            <div className="mb-4">
              <h3 className="font-semibold mb-3">赚积分任务</h3>
              <div className="space-y-2">
                {pointsTasks.map((task) => (
                  <Card 
                    key={task.id}
                    className={`p-4 ${task.status === 'completed' ? 'opacity-60' : 'cursor-pointer hover:shadow-lg transition-shadow'}`}
                  >
                    <div className="flex items-center justify-between">
                      <div className="flex items-center gap-3">
                        <span className="text-2xl">{task.icon}</span>
                        <div>
                          <h4 className="font-medium">{task.title}</h4>
                          <div className="text-sm text-orange-600 font-semibold">
                            +{task.points} 积分
                          </div>
                        </div>
                      </div>
                      {task.status === 'available' ? (
                        <Button size="sm" className="bg-gradient-to-r from-teal-500 to-blue-500">
                          去完成
                        </Button>
                      ) : (
                        <Badge variant="secondary">已完成</Badge>
                      )}
                    </div>
                  </Card>
                ))}
              </div>
            </div>

            {/* 积分说明 */}
            <Card className="p-4">
              <h3 className="font-semibold mb-3">积分说明</h3>
              <div className="text-sm text-gray-600 space-y-2">
                <p>• 1积分 = 1元，可用于抵扣订单金额</p>
                <p>• 积分有效期为获得后1年</p>
                <p>• 完成任务、评价订单可获得积分</p>
                <p>• 积分达到一定等级可享受专属权益</p>
              </div>
            </Card>
          </TabsContent>

          {/* 账单 Tab */}
          <TabsContent value="transactions" className="mt-4">
            <div className="space-y-3">
              {transactions.map((transaction) => (
                <Card 
                  key={transaction.id}
                  className="p-4 cursor-pointer hover:shadow-lg transition-shadow"
                  onClick={() => {
                    if (transaction.type === 'expense') {
                      navigate('/order/1');
                    }
                  }}
                >
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-3">
                      <div className={`w-10 h-10 rounded-full flex items-center justify-center ${
                        transaction.type === 'expense' 
                          ? 'bg-red-50 text-red-500' 
                          : 'bg-green-50 text-green-500'
                      }`}>
                        {transaction.type === 'expense' ? (
                          <CreditCard size={20} />
                        ) : (
                          <TrendingUp size={20} />
                        )}
                      </div>
                      <div>
                        <h4 className="font-medium">{transaction.title}</h4>
                        <div className="text-xs text-gray-500 flex items-center gap-2">
                          <span>{transaction.time}</span>
                          <Badge variant="outline" className="text-xs">
                            {transaction.status}
                          </Badge>
                        </div>
                      </div>
                    </div>
                    <div className={`text-lg font-bold ${
                      transaction.type === 'expense' ? 'text-gray-900' : 'text-green-600'
                    }`}>
                      {transaction.amount > 0 ? '+' : ''}¥{Math.abs(transaction.amount)}
                    </div>
                  </div>
                </Card>
              ))}
            </div>

            <div className="mt-6 text-center">
              <Button variant="outline" className="w-full">
                <History size={16} className="mr-2" />
                查看更多记录
              </Button>
            </div>
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
}
