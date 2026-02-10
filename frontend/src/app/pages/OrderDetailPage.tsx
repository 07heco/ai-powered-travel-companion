import { useNavigate, useParams } from 'react-router-dom';
import { ArrowLeft, Copy, Phone, FileText, Star } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Separator } from '../components/ui/separator';

export default function OrderDetailPage() {
  const navigate = useNavigate();
  const { id } = useParams();

  const order = {
    id: 'OD202603150001',
    type: '机票',
    status: '待出行',
    createTime: '2026-02-20 14:30:25',
    payAmount: 2580,
    flightInfo: {
      airline: '中国国际航空',
      flightNo: 'CA183',
      departTime: '2026-03-15 08:30',
      arriveTime: '2026-03-15 12:45',
      departAirport: '北京首都机场 T3',
      arriveAirport: '东京成田机场 T1',
      passenger: '张三',
      idCard: '110101199001011234',
    },
    refundPolicy: '起飞前24小时免费退票，起飞前2-24小时收取10%手续费，起飞前2小时内不可退票',
    contactPhone: '400-123-4567',
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case '待支付':
        return 'bg-orange-500';
      case '待出行':
        return 'bg-blue-500';
      case '已完成':
        return 'bg-green-500';
      case '已取消':
        return 'bg-gray-500';
      default:
        return 'bg-gray-500';
    }
  };

  const handleCopy = (text: string) => {
    navigator.clipboard.writeText(text);
    alert('已复制到剪贴板');
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center gap-3">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold">订单详情</h1>
        </div>
      </div>

      {/* 订单状态 */}
      <div className="bg-gradient-to-r from-teal-500 to-blue-500 text-white p-6">
        <div className="flex items-center gap-3 mb-2">
          <Badge className={`${getStatusColor(order.status)} text-white`}>
            {order.status}
          </Badge>
          <span className="text-lg font-semibold">{order.type}订单</span>
        </div>
        <p className="text-sm opacity-90">订单已确认，请按时出行</p>
      </div>

      {/* 订单基础信息 */}
      <Card className="mx-4 -mt-4 mb-4 p-4">
        <div className="space-y-3 text-sm">
          <div className="flex justify-between">
            <span className="text-gray-600">订单号</span>
            <div className="flex items-center gap-2">
              <span className="font-medium">{order.id}</span>
              <button onClick={() => handleCopy(order.id)}>
                <Copy size={14} className="text-teal-600" />
              </button>
            </div>
          </div>
          <div className="flex justify-between">
            <span className="text-gray-600">下单时间</span>
            <span className="font-medium">{order.createTime}</span>
          </div>
          <div className="flex justify-between">
            <span className="text-gray-600">支付金额</span>
            <span className="font-bold text-teal-600 text-lg">¥{order.payAmount}</span>
          </div>
        </div>
      </Card>

      {/* 预订信息 */}
      <div className="px-4 mb-4">
        <h3 className="font-semibold mb-3">预订信息</h3>
        <Card className="p-4">
          <div className="space-y-4">
            <div>
              <p className="font-semibold text-lg mb-2">{order.flightInfo.airline}</p>
              <p className="text-sm text-gray-600">{order.flightInfo.flightNo}</p>
            </div>

            <Separator />

            <div className="flex items-center justify-between">
              <div>
                <p className="text-xl font-bold">{order.flightInfo.departTime.split(' ')[1]}</p>
                <p className="text-sm text-gray-600 mt-1">{order.flightInfo.departAirport}</p>
              </div>
              <div className="flex-1 px-4 text-center">
                <div className="h-px bg-gray-300 mb-1" />
                <p className="text-xs text-gray-500">✈</p>
              </div>
              <div className="text-right">
                <p className="text-xl font-bold">{order.flightInfo.arriveTime.split(' ')[1]}</p>
                <p className="text-sm text-gray-600 mt-1">{order.flightInfo.arriveAirport}</p>
              </div>
            </div>

            <Separator />

            <div className="space-y-2 text-sm">
              <div className="flex justify-between">
                <span className="text-gray-600">乘客姓名</span>
                <span className="font-medium">{order.flightInfo.passenger}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">身份证号</span>
                <span className="font-medium">{order.flightInfo.idCard}</span>
              </div>
            </div>
          </div>
        </Card>
      </div>

      {/* 退改规则 */}
      <div className="px-4 mb-4">
        <h3 className="font-semibold mb-3">退改规则</h3>
        <Card className="p-4">
          <p className="text-sm text-gray-600 leading-relaxed">
            {order.refundPolicy}
          </p>
        </Card>
      </div>

      {/* 商家联系方式 */}
      <div className="px-4 mb-4">
        <h3 className="font-semibold mb-3">服务信息</h3>
        <Card className="p-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <Phone size={18} className="text-teal-600" />
              <span className="text-sm">客服电话</span>
            </div>
            <a href={`tel:${order.contactPhone}`} className="text-teal-600 font-medium">
              {order.contactPhone}
            </a>
          </div>
        </Card>
      </div>

      {/* 底部操作按钮 */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t p-4">
        <div className="grid grid-cols-3 gap-3">
          {order.status === '待出行' && (
            <>
              <Button 
                variant="outline"
                onClick={() => navigate('/trip-planner')}
              >
                查看行程
              </Button>
              <Button 
                variant="outline"
                onClick={() => alert('改签功能')}
              >
                改签
              </Button>
              <Button 
                variant="outline"
                onClick={() => alert('退票功能')}
              >
                退票
              </Button>
            </>
          )}
          {order.status === '已完成' && (
            <>
              <Button 
                variant="outline"
                onClick={() => alert('评价功能')}
              >
                <Star size={16} className="mr-1" />
                评价
              </Button>
              <Button 
                variant="outline"
                onClick={() => alert('发票功能')}
              >
                <FileText size={16} className="mr-1" />
                申请发票
              </Button>
              <Button 
                className="bg-teal-600"
                onClick={() => navigate('/flight-booking')}
              >
                再次预订
              </Button>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
