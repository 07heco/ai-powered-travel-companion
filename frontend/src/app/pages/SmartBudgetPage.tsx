import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, TrendingUp, TrendingDown, DollarSign, PieChart, BarChart3, Lightbulb, AlertTriangle, Sparkles, Plane, Hotel, UtensilsCrossed, ShoppingBag, Ticket } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Progress } from '../components/ui/progress';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../components/ui/tabs';

export default function SmartBudgetPage() {
  const navigate = useNavigate();
  const [selectedTrip, setSelectedTrip] = useState('tokyo');

  // 预算数据
  const budgetData = {
    tokyo: {
      name: '东京7日游',
      totalBudget: 18000,
      spent: 12500,
      remaining: 5500,
      days: 7,
      currentDay: 4,
      breakdown: [
        { category: '交通', icon: Plane, budgeted: 4000, spent: 3800, color: 'from-blue-500 to-blue-600' },
        { category: '住宿', icon: Hotel, budgeted: 6000, spent: 4800, color: 'from-purple-500 to-purple-600' },
        { category: '餐饮', icon: UtensilsCrossed, budgeted: 4500, spent: 2400, color: 'from-orange-500 to-orange-600' },
        { category: '门票', icon: Ticket, budgeted: 2000, spent: 1200, color: 'from-teal-500 to-teal-600' },
        { category: '购物', icon: ShoppingBag, budgeted: 1500, spent: 300, color: 'from-pink-500 to-pink-600' },
      ],
      dailyExpenses: [
        { day: 'Day 1', amount: 3200, date: '02-05' },
        { day: 'Day 2', amount: 1800, date: '02-06' },
        { day: 'Day 3', amount: 2500, date: '02-07' },
        { day: 'Day 4', amount: 5000, date: '02-08' },
      ],
      predictions: {
        expectedTotal: 17200,
        savingsPotential: 800,
        overspendingRisk: 'low'
      },
      aiSuggestions: [
        {
          type: 'warning',
          icon: AlertTriangle,
          title: '餐饮预算富余',
          content: '当前餐饮支出较预算低46%，可以尝试一些米其林餐厅',
          action: '查看美食推荐'
        },
        {
          type: 'success',
          icon: TrendingDown,
          title: '交通费用优化',
          content: '购买西瓜卡节省了约200元，继续保持！',
          action: null
        },
        {
          type: 'tip',
          icon: Lightbulb,
          title: '购物优惠提醒',
          content: '银座百货本周末8折优惠，可安排购物计划',
          action: '查看详情'
        },
        {
          type: 'prediction',
          icon: Sparkles,
          title: 'AI预测',
          content: '根据当前消费趋势，预计总花费17200元，可节省800元',
          action: '查看分析'
        }
      ]
    }
  };

  const trip = budgetData[selectedTrip as keyof typeof budgetData];
  const spentPercentage = (trip.spent / trip.totalBudget) * 100;
  const dailyAverage = trip.spent / trip.currentDay;
  const projectedTotal = dailyAverage * trip.days;
  const maxDailyExpense = Math.max(...trip.dailyExpenses.map(d => d.amount));

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-gradient-to-r from-teal-500 to-blue-500">
        <div className="px-4 py-3 flex items-center gap-3 text-white">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <div className="flex-1">
            <h1 className="text-lg font-semibold">智能预算管理</h1>
            <p className="text-xs opacity-90">AI驱动的消费分析</p>
          </div>
          <Sparkles size={20} />
        </div>

        {/* 总览卡片 */}
        <div className="px-4 pb-6">
          <Card className="bg-white/10 backdrop-blur-sm border-white/20 text-white p-4">
            <div className="flex items-center justify-between mb-3">
              <div>
                <p className="text-sm opacity-90">总预算</p>
                <p className="text-3xl font-bold">¥{trip.totalBudget.toLocaleString()}</p>
              </div>
              <div className="text-right">
                <p className="text-sm opacity-90">已花费</p>
                <p className="text-2xl font-bold">¥{trip.spent.toLocaleString()}</p>
              </div>
            </div>
            
            <Progress value={spentPercentage} className="h-3 mb-2" />
            
            <div className="flex items-center justify-between text-sm">
              <span className="opacity-90">Day {trip.currentDay} / {trip.days}</span>
              <span className={`font-semibold ${spentPercentage > 80 ? 'text-red-300' : 'text-green-300'}`}>
                剩余 ¥{trip.remaining.toLocaleString()}
              </span>
            </div>
          </Card>
        </div>
      </div>

      {/* Tabs */}
      <div className="px-4 mb-4">
        <Tabs defaultValue="overview">
          <TabsList className="w-full grid grid-cols-3 bg-white">
            <TabsTrigger value="overview" className="text-sm">总览</TabsTrigger>
            <TabsTrigger value="breakdown" className="text-sm">分类</TabsTrigger>
            <TabsTrigger value="daily" className="text-sm">每日</TabsTrigger>
          </TabsList>

          {/* 总览 Tab */}
          <TabsContent value="overview" className="mt-4 space-y-4">
            {/* AI分析卡片 */}
            <Card className="p-4 bg-gradient-to-r from-teal-50 to-blue-50 border-teal-200">
              <div className="flex items-center gap-2 mb-3">
                <div className="w-8 h-8 rounded-full bg-gradient-to-r from-teal-500 to-blue-500 flex items-center justify-center">
                  <Sparkles className="text-white" size={16} />
                </div>
                <h3 className="font-semibold">AI智能分析</h3>
              </div>
              
              <div className="grid grid-cols-2 gap-3 mb-3">
                <div className="bg-white rounded-lg p-3">
                  <div className="flex items-center gap-2 mb-1">
                    <TrendingUp className="text-teal-600" size={16} />
                    <span className="text-xs text-gray-600">日均消费</span>
                  </div>
                  <p className="text-lg font-bold">¥{dailyAverage.toFixed(0)}</p>
                </div>
                <div className="bg-white rounded-lg p-3">
                  <div className="flex items-center gap-2 mb-1">
                    <BarChart3 className="text-blue-600" size={16} />
                    <span className="text-xs text-gray-600">预计总额</span>
                  </div>
                  <p className="text-lg font-bold">¥{projectedTotal.toFixed(0)}</p>
                </div>
              </div>

              <div className="bg-white rounded-lg p-3">
                <div className="flex items-center justify-between mb-2">
                  <span className="text-sm font-medium">预算健康度</span>
                  <Badge className={`${
                    spentPercentage < 60 ? 'bg-green-500' : 
                    spentPercentage < 80 ? 'bg-yellow-500' : 'bg-red-500'
                  }`}>
                    {spentPercentage < 60 ? '良好' : spentPercentage < 80 ? '适中' : '偏高'}
                  </Badge>
                </div>
                <Progress value={100 - spentPercentage} className="h-2" />
                <p className="text-xs text-gray-600 mt-2">
                  {spentPercentage < 60 ? '支出控制良好，可以适当提升体验' : 
                   spentPercentage < 80 ? '保持当前消费水平即可' : '建议控制非必要支出'}
                </p>
              </div>
            </Card>

            {/* AI建议 */}
            <div>
              <h3 className="font-semibold mb-3 px-1">AI智能建议</h3>
              <div className="space-y-3">
                {trip.aiSuggestions.map((suggestion, index) => {
                  const Icon = suggestion.icon;
                  return (
                    <Card 
                      key={index}
                      className={`p-4 ${
                        suggestion.type === 'warning' ? 'border-orange-200 bg-orange-50' :
                        suggestion.type === 'success' ? 'border-green-200 bg-green-50' :
                        suggestion.type === 'tip' ? 'border-blue-200 bg-blue-50' :
                        'border-purple-200 bg-purple-50'
                      }`}
                    >
                      <div className="flex gap-3">
                        <div className={`w-10 h-10 rounded-full flex items-center justify-center flex-shrink-0 ${
                          suggestion.type === 'warning' ? 'bg-orange-500' :
                          suggestion.type === 'success' ? 'bg-green-500' :
                          suggestion.type === 'tip' ? 'bg-blue-500' :
                          'bg-purple-500'
                        }`}>
                          <Icon className="text-white" size={20} />
                        </div>
                        <div className="flex-1">
                          <h4 className="font-semibold mb-1">{suggestion.title}</h4>
                          <p className="text-sm text-gray-700 mb-2">{suggestion.content}</p>
                          {suggestion.action && (
                            <Button variant="outline" size="sm" className="text-xs">
                              {suggestion.action}
                            </Button>
                          )}
                        </div>
                      </div>
                    </Card>
                  );
                })}
              </div>
            </div>

            {/* 节省技巧 */}
            <Card className="p-4">
              <h3 className="font-semibold mb-3 flex items-center gap-2">
                <Lightbulb className="text-yellow-500" size={18} />
                省钱小技巧
              </h3>
              <div className="space-y-2 text-sm text-gray-700">
                <div className="flex items-start gap-2">
                  <span className="text-teal-600">•</span>
                  <p>使用西瓜卡乘坐地铁，比单次购票节省约15%</p>
                </div>
                <div className="flex items-start gap-2">
                  <span className="text-teal-600">•</span>
                  <p>午餐选择定食套餐，比晚餐便宜30-50%</p>
                </div>
                <div className="flex items-start gap-2">
                  <span className="text-teal-600">•</span>
                  <p>提前在网上购买景点门票可享折扣</p>
                </div>
                <div className="flex items-start gap-2">
                  <span className="text-teal-600">•</span>
                  <p>使用优惠券和会员卡，药妆店可省10-15%</p>
                </div>
              </div>
            </Card>
          </TabsContent>

          {/* 分类 Tab */}
          <TabsContent value="breakdown" className="mt-4">
            <div className="space-y-3">
              {trip.breakdown.map((item) => {
                const Icon = item.icon;
                const percentage = (item.spent / item.budgeted) * 100;
                return (
                  <Card key={item.category} className="p-4">
                    <div className="flex items-center justify-between mb-3">
                      <div className="flex items-center gap-3">
                        <div className={`w-12 h-12 rounded-xl bg-gradient-to-r ${item.color} flex items-center justify-center`}>
                          <Icon className="text-white" size={24} />
                        </div>
                        <div>
                          <h4 className="font-semibold">{item.category}</h4>
                          <p className="text-xs text-gray-500">预算 ¥{item.budgeted}</p>
                        </div>
                      </div>
                      <div className="text-right">
                        <p className="font-bold text-lg">¥{item.spent}</p>
                        <p className={`text-xs ${percentage > 100 ? 'text-red-600' : 'text-green-600'}`}>
                          {percentage > 100 ? '+' : ''}{(item.spent - item.budgeted).toFixed(0)}
                        </p>
                      </div>
                    </div>
                    <Progress value={percentage} className="h-2 mb-2" />
                    <div className="flex items-center justify-between text-xs text-gray-600">
                      <span>{percentage.toFixed(1)}% 已使用</span>
                      <span>剩余 ¥{item.budgeted - item.spent}</span>
                    </div>
                  </Card>
                );
              })}
            </div>

            {/* 饼图占比 */}
            <Card className="p-4 mt-4">
              <h3 className="font-semibold mb-3">支出占比</h3>
              <div className="space-y-2">
                {trip.breakdown.map((item) => {
                  const total = trip.breakdown.reduce((sum, i) => sum + i.spent, 0);
                  const percentage = (item.spent / total) * 100;
                  return (
                    <div key={item.category}>
                      <div className="flex items-center justify-between text-sm mb-1">
                        <span>{item.category}</span>
                        <span className="font-semibold">{percentage.toFixed(1)}%</span>
                      </div>
                      <div className="h-2 bg-gray-200 rounded-full overflow-hidden">
                        <div 
                          className={`h-full bg-gradient-to-r ${item.color}`}
                          style={{ width: `${percentage}%` }}
                        ></div>
                      </div>
                    </div>
                  );
                })}
              </div>
            </Card>
          </TabsContent>

          {/* 每日 Tab */}
          <TabsContent value="daily" className="mt-4">
            <div className="space-y-3">
              {trip.dailyExpenses.map((expense, index) => (
                <Card key={index} className="p-4">
                  <div className="flex items-center justify-between mb-3">
                    <div>
                      <h4 className="font-semibold">{expense.day}</h4>
                      <p className="text-xs text-gray-500">{expense.date}</p>
                    </div>
                    <div className="text-right">
                      <p className="text-2xl font-bold">¥{expense.amount}</p>
                      <Badge variant={expense.amount > dailyAverage ? 'destructive' : 'secondary'} className="text-xs">
                        {expense.amount > dailyAverage ? '超出平均' : '低于平均'}
                      </Badge>
                    </div>
                  </div>
                  <div className="relative h-2 bg-gray-200 rounded-full overflow-hidden">
                    <div 
                      className="absolute h-full bg-gradient-to-r from-teal-500 to-blue-500"
                      style={{ width: `${(expense.amount / maxDailyExpense) * 100}%` }}
                    ></div>
                  </div>
                </Card>
              ))}

              {/* 未来预测 */}
              <Card className="p-4 bg-gradient-to-r from-purple-50 to-pink-50 border-purple-200">
                <div className="flex items-center gap-2 mb-3">
                  <Sparkles className="text-purple-600" size={18} />
                  <h4 className="font-semibold">未来支出预测</h4>
                </div>
                <div className="space-y-2">
                  {[5, 6, 7].map((day) => (
                    <div key={day} className="flex items-center justify-between text-sm">
                      <span className="text-gray-600">Day {day} 预计</span>
                      <span className="font-semibold">¥{dailyAverage.toFixed(0)}</span>
                    </div>
                  ))}
                </div>
                <div className="mt-3 pt-3 border-t border-purple-200">
                  <div className="flex items-center justify-between">
                    <span className="text-sm text-gray-600">预计总花费</span>
                    <span className="text-lg font-bold text-purple-600">¥{projectedTotal.toFixed(0)}</span>
                  </div>
                </div>
              </Card>
            </div>
          </TabsContent>
        </Tabs>
      </div>

      {/* 底部操作 */}
      <div className="px-4 mt-6">
        <div className="grid grid-cols-2 gap-3">
          <Button 
            variant="outline"
            onClick={() => navigate('/wallet')}
          >
            <DollarSign size={16} className="mr-2" />
            我的钱包
          </Button>
          <Button 
            className="bg-gradient-to-r from-teal-500 to-blue-500"
            onClick={() => {/* 记录支出 */}}
          >
            <PieChart size={16} className="mr-2" />
            记录支出
          </Button>
        </div>
      </div>
    </div>
  );
}
