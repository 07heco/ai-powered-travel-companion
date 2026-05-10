import { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Wallet, Gift, CreditCard, TrendingUp, Clock, ChevronRight, Plus, History, Sparkles } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../components/ui/tabs';
import { Progress } from '../components/ui/progress';
import BottomNavigation from '../components/BottomNavigation';
import MobileShell from '../MobileShell';
import { walletApi, type CouponItem } from '../lib/api';

export default function WalletPage() {
  const navigate = useNavigate();
  const [selectedTab, setSelectedTab] = useState('coupons');
  const [walletInfo, setWalletInfo] = useState({
    balance: 1280.5,
    points: 3560,
    coupons: 12,
  });
  const [coupons, setCoupons] = useState<Array<CouponItem & { color?: string; type?: string }>>([
    {
      id: 1,
      type: '满减券',
      title: '机票立减券',
      amount: 300,
      condition: '满2000元可用',
      validUntil: '2026-03-31',
      status: 'available',
      scope: '国内国际机票',
      color: 'from-orange-500 to-red-500',
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
      color: 'from-purple-500 to-pink-500',
    },
  ]);

  useEffect(() => {
    const userId = localStorage.getItem('userId');
    if (!userId) return;

    const loadWalletData = async () => {
      try {
        const [wallet, userCoupons] = await Promise.all([walletApi.getWallet(userId), walletApi.getCoupons(userId)]);

        setWalletInfo((prev) => ({
          balance: wallet?.balance ?? prev.balance,
          points: wallet?.points ?? prev.points,
          coupons: Array.isArray(userCoupons) ? userCoupons.length : prev.coupons,
        }));

        if (Array.isArray(userCoupons) && userCoupons.length > 0) {
          setCoupons(
            userCoupons.map((coupon, index) => ({
              ...coupon,
              title: coupon.title || coupon.name || '优惠券',
              validUntil: coupon.validUntil || coupon.expireTime,
              type: coupon.discount ? '折扣券' : '满减券',
              color: ['from-orange-500 to-red-500', 'from-purple-500 to-pink-500', 'from-teal-500 to-blue-500'][index % 3],
            }))
          );
        }
      } catch {
        // 保留默认展示数据
      }
    };

    loadWalletData();
  }, []);

  const pointsTasks = [
    { id: 1, title: '每日签到', points: 10, status: 'available', icon: '📅' },
    { id: 2, title: '完善个人资料', points: 50, status: 'completed', icon: '✅' },
    { id: 3, title: '首次预订', points: 200, status: 'completed', icon: '✅' },
    { id: 4, title: '分享给好友', points: 30, status: 'available', icon: '🎁' },
    { id: 5, title: '发布旅行笔记', points: 100, status: 'available', icon: '📝' },
  ];

  const transactions = [
    { id: 1, type: 'expense', title: '东京5日自由行', amount: -3999, time: '2026-02-05 14:23', status: '已支付' },
    { id: 2, type: 'income', title: '退款', amount: 680, time: '2026-02-01 09:15', status: '已到账' },
    { id: 3, type: 'expense', title: '三亚酒店预订', amount: -1588, time: '2026-01-28 16:45', status: '已支付' },
    { id: 4, type: 'income', title: '充值', amount: 1000, time: '2026-01-25 10:30', status: '已到账' },
  ];

  const availableCoupons = useMemo(() => coupons.filter((c) => c.status === 'available'), [coupons]);
  const expiringCoupons = useMemo(() => coupons.filter((c) => c.status === 'expiring'), [coupons]);
  const expiredCoupons = useMemo(() => coupons.filter((c) => c.status === 'expired'), [coupons]);

  return (
    <MobileShell>
      <div className="space-y-5">
        <div className="rounded-[30px] bg-gradient-to-br from-slate-900 via-slate-800 to-teal-600 p-5 text-white shadow-[0_18px_55px_rgba(15,23,42,0.28)]">
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
              <p className="text-sm text-white/75">资产与优惠</p>
              <h1 className="mt-1 text-[28px] font-semibold tracking-tight">我的钱包</h1>
            </div>
            <div className="flex h-11 w-11 items-center justify-center rounded-2xl border border-white/15 bg-white/10">
              <Wallet size={20} />
            </div>
          </div>

          <div className="mb-4 inline-flex items-center gap-2 rounded-full bg-white/10 px-3 py-1.5 text-xs font-medium text-white/85 backdrop-blur-sm">
            <Sparkles size={14} />
            积分、优惠券与账单都在这里统一管理
          </div>

          <Card className="rounded-[28px] border-white/10 bg-white/10 p-4 text-white backdrop-blur-sm">
            <div className="mb-5 flex items-start justify-between gap-3">
              <div>
                <p className="text-sm text-white/70">账户余额</p>
                <p className="mt-2 text-[34px] font-semibold tracking-tight">¥{walletInfo.balance.toFixed(2)}</p>
              </div>
              <Button
                variant="outline"
                size="sm"
                className="rounded-2xl border-white/20 bg-white/10 text-white hover:bg-white/20"
                onClick={() => {
                  /* 充值 */
                }}
              >
                <Plus size={16} className="mr-1" />
                充值
              </Button>
            </div>

            <div className="grid grid-cols-2 gap-3">
              <button
                className="rounded-[22px] bg-white/10 p-4 text-left transition-colors hover:bg-white/15"
                onClick={() => setSelectedTab('points')}
              >
                <div className="mb-2 flex items-center gap-2 text-sm text-white/80">
                  <TrendingUp size={16} />
                  我的积分
                </div>
                <div className="text-2xl font-semibold">{walletInfo.points}</div>
              </button>
              <button
                className="rounded-[22px] bg-white/10 p-4 text-left transition-colors hover:bg-white/15"
                onClick={() => setSelectedTab('coupons')}
              >
                <div className="mb-2 flex items-center gap-2 text-sm text-white/80">
                  <Gift size={16} />
                  优惠券
                </div>
                <div className="text-2xl font-semibold">{walletInfo.coupons}张</div>
              </button>
            </div>
          </Card>
        </div>

        <Tabs value={selectedTab} onValueChange={setSelectedTab}>
          <TabsList className="grid h-auto w-full grid-cols-3 rounded-[24px] bg-white/88 p-1 shadow-[0_10px_30px_rgba(15,23,42,0.05)]">
            <TabsTrigger value="coupons" className="rounded-[18px] py-3 text-sm">
              优惠券
              <Badge className="ml-1 bg-rose-500 text-white text-[10px]">{availableCoupons.length}</Badge>
            </TabsTrigger>
            <TabsTrigger value="points" className="rounded-[18px] py-3 text-sm">积分</TabsTrigger>
            <TabsTrigger value="transactions" className="rounded-[18px] py-3 text-sm">账单</TabsTrigger>
          </TabsList>

          <TabsContent value="coupons" className="mt-4 space-y-4">
            <Card
              className="rounded-[28px] border-orange-100 bg-gradient-to-r from-orange-50 via-rose-50 to-pink-50 p-4 shadow-[0_14px_35px_rgba(15,23,42,0.05)]"
              onClick={() => navigate('/deals')}
            >
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-gradient-to-br from-orange-500 to-rose-500 text-white">
                    <Gift size={22} />
                  </div>
                  <div>
                    <h3 className="font-semibold text-slate-900">领券中心</h3>
                    <p className="text-sm text-slate-500">更多优惠券等你来领</p>
                  </div>
                </div>
                <ChevronRight className="text-slate-400" size={20} />
              </div>
            </Card>

            {expiringCoupons.length > 0 && (
              <section>
                <div className="mb-3 flex items-center gap-2 px-1">
                  <Clock size={16} className="text-orange-500" />
                  <h3 className="text-sm font-semibold text-slate-900">即将过期</h3>
                </div>
                <div className="space-y-3">
                  {expiringCoupons.map((coupon) => (
                    <Card key={coupon.id} className="overflow-hidden rounded-[28px] border-orange-100 bg-white/90 shadow-[0_14px_35px_rgba(15,23,42,0.05)]">
                      <div className={`h-2 bg-gradient-to-r ${coupon.color}`}></div>
                      <div className="p-4">
                        <div className="mb-3 flex items-center justify-between gap-3">
                          <div>
                            <Badge className="mb-2 bg-orange-100 text-orange-600">{coupon.type}</Badge>
                            <h4 className="text-lg font-semibold text-slate-900">{coupon.title}</h4>
                            <div className="my-1 text-3xl font-semibold text-orange-500">{coupon.amount ? `¥${coupon.amount}` : coupon.discount}</div>
                            <p className="text-sm text-slate-500">{coupon.condition}</p>
                          </div>
                          <Button className="rounded-2xl bg-gradient-to-r from-orange-500 to-rose-500" onClick={() => navigate('/destinations')}>
                            立即使用
                          </Button>
                        </div>
                        <div className="flex items-center justify-between border-t pt-3 text-xs text-slate-500">
                          <span>适用：{coupon.scope}</span>
                          <span className="text-orange-500">有效期至 {coupon.validUntil}</span>
                        </div>
                      </div>
                    </Card>
                  ))}
                </div>
              </section>
            )}

            {availableCoupons.length > 0 && (
              <section>
                <h3 className="mb-3 px-1 text-sm font-semibold text-slate-900">可用优惠券</h3>
                <div className="space-y-3">
                  {availableCoupons.map((coupon) => (
                    <Card key={coupon.id} className="overflow-hidden rounded-[28px] border-white/70 bg-white/90 shadow-[0_14px_35px_rgba(15,23,42,0.06)]">
                      <div className={`h-2 bg-gradient-to-r ${coupon.color}`}></div>
                      <div className="p-4">
                        <div className="mb-3 flex items-center justify-between gap-3">
                          <div>
                            <Badge className="mb-2" variant="outline">{coupon.type}</Badge>
                            <h4 className="font-semibold text-slate-900">{coupon.title}</h4>
                            <div className={`my-1 text-2xl font-semibold bg-gradient-to-r ${coupon.color} bg-clip-text text-transparent`}>
                              {coupon.amount ? `¥${coupon.amount}` : coupon.discount}
                            </div>
                            <p className="text-sm text-slate-500">{coupon.condition}</p>
                          </div>
                          <Button variant="outline" className="rounded-2xl" size="sm" onClick={() => navigate('/destinations')}>
                            去使用
                          </Button>
                        </div>
                        <div className="flex items-center justify-between border-t pt-3 text-xs text-slate-500">
                          <span>适用：{coupon.scope}</span>
                          <span>有效期至 {coupon.validUntil}</span>
                        </div>
                      </div>
                    </Card>
                  ))}
                </div>
              </section>
            )}

            {expiredCoupons.length > 0 && (
              <section>
                <h3 className="mb-3 px-1 text-sm font-semibold text-slate-500">已失效</h3>
                <div className="space-y-3 opacity-60">
                  {expiredCoupons.map((coupon) => (
                    <Card key={coupon.id} className="overflow-hidden rounded-[28px] border-white/70 bg-white/90 grayscale">
                      <div className={`h-2 bg-gradient-to-r ${coupon.color}`}></div>
                      <div className="p-4">
                        <div className="mb-3 flex items-center justify-between gap-3">
                          <div>
                            <Badge className="mb-2" variant="secondary">{coupon.type}</Badge>
                            <h4 className="font-semibold text-slate-900">{coupon.title}</h4>
                            <div className="my-1 text-2xl font-semibold text-slate-400">{coupon.amount ? `¥${coupon.amount}` : coupon.discount}</div>
                            <p className="text-sm text-slate-500">{coupon.condition}</p>
                          </div>
                          <Badge variant="secondary">已过期</Badge>
                        </div>
                        <div className="border-t pt-3 text-xs text-slate-500">有效期至 {coupon.validUntil}</div>
                      </div>
                    </Card>
                  ))}
                </div>
              </section>
            )}
          </TabsContent>

          <TabsContent value="points" className="mt-4 space-y-4">
            <Card className="rounded-[28px] border-white/70 bg-white/90 p-4 shadow-[0_14px_35px_rgba(15,23,42,0.06)]">
              <div className="mb-3 flex items-center justify-between">
                <div className="flex items-center gap-2">
                  <CreditCard size={18} className="text-teal-600" />
                  <h3 className="font-semibold text-slate-900">积分成长</h3>
                </div>
                <span className="text-sm text-slate-500">LV.3 旅行达人</span>
              </div>
              <Progress value={72} className="mb-2" />
              <p className="text-xs text-slate-500">距离下一级还差 1400 积分</p>
            </Card>

            <div className="space-y-3">
              {pointsTasks.map((task) => (
                <Card key={task.id} className="rounded-[26px] border-white/70 bg-white/90 p-4 shadow-[0_12px_30px_rgba(15,23,42,0.05)]">
                  <div className="flex items-center justify-between gap-3">
                    <div className="flex items-center gap-3">
                      <div className="text-2xl">{task.icon}</div>
                      <div>
                        <div className="font-medium text-slate-900">{task.title}</div>
                        <div className="text-sm text-slate-500">完成可得 {task.points} 积分</div>
                      </div>
                    </div>
                    <Button size="sm" variant={task.status === 'completed' ? 'outline' : 'default'} disabled={task.status === 'completed'} className="rounded-2xl">
                      {task.status === 'completed' ? '已完成' : '去完成'}
                    </Button>
                  </div>
                </Card>
              ))}
            </div>
          </TabsContent>

          <TabsContent value="transactions" className="mt-4 space-y-3">
            <div className="mb-2 flex items-center gap-2 px-1 text-slate-600">
              <History size={18} />
              <span className="font-medium">最近账单</span>
            </div>
            {transactions.map((transaction) => (
              <Card key={transaction.id} className="rounded-[26px] border-white/70 bg-white/90 p-4 shadow-[0_12px_30px_rgba(15,23,42,0.05)]">
                <div className="flex items-center justify-between gap-3">
                  <div>
                    <div className="font-medium text-slate-900">{transaction.title}</div>
                    <div className="text-sm text-slate-500">{transaction.time}</div>
                  </div>
                  <div className="text-right">
                    <div className={`font-semibold ${transaction.amount > 0 ? 'text-emerald-600' : 'text-slate-900'}`}>
                      {transaction.amount > 0 ? '+' : ''}{transaction.amount}
                    </div>
                    <div className="text-sm text-slate-500">{transaction.status}</div>
                  </div>
                </div>
              </Card>
            ))}
          </TabsContent>
        </Tabs>
      </div>

      <BottomNavigation />
    </MobileShell>
  );
}
