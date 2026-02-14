import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Search, SlidersHorizontal, MapPin, Loader2 } from 'lucide-react';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';
import { Alert, AlertDescription, AlertTitle } from '../components/ui/alert';
import { searchService, bookingService } from '../services';

export default function TicketBookingPage() {
  const navigate = useNavigate();
  const [attraction, setAttraction] = useState('东京迪士尼');
  const [date, setDate] = useState('2026-03-15');
  const [tickets, setTickets] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const searchTickets = async () => {
    setLoading(true);
    setError(null);
    try {
      const results = await searchService.searchTickets({
        attraction: attraction,
        date: date,
      });
      setTickets(results);
    } catch (err) {
      setError('搜索门票失败，请稍后重试');
      console.error('Ticket search error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleBook = async (ticket: any) => {
    try {
      await bookingService.createBooking({
        type: 'ticket',
        details: {
          ticketId: ticket.id,
          name: ticket.name,
          attraction: ticket.attraction,
          price: ticket.price,
          type: ticket.type,
          validity: ticket.validity,
          date: date,
        },
      });
      alert('预订成功！');
    } catch (err) {
      alert('预订失败，请稍后重试');
      console.error('Booking error:', err);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-6">
      {/* 顶部栏 */}
      <div className="sticky top-0 z-40 bg-white shadow-sm">
        <div className="px-4 py-3 flex items-center gap-3">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold flex-1">门票预订</h1>
          <Button variant="ghost" size="icon">
            <SlidersHorizontal size={20} />
          </Button>
        </div>
      </div>

      {/* 搜索区域 */}
      <div className="bg-white p-4 mb-4">
        <div className="space-y-3">
          <div>
            <label className="text-sm text-gray-600 mb-1 block">景点</label>
            <Input
              value={attraction}
              onChange={(e) => setAttraction(e.target.value)}
              className="h-10"
            />
          </div>
          <div>
            <label className="text-sm text-gray-600 mb-1 block">游玩日期</label>
            <Input
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              className="h-10"
            />
          </div>
          <Button 
            className="w-full bg-gradient-to-r from-teal-500 to-blue-500"
            onClick={searchTickets}
            disabled={loading}
          >
            {loading ? (
              <>
                <Loader2 size={18} className="mr-2 animate-spin" />
                搜索中...
              </>
            ) : (
              <>
                <Search size={18} className="mr-2" />
                搜索门票
              </>
            )}
          </Button>
        </div>
      </div>

      {/* 错误提示 */}
      {error && (
        <div className="px-4 mb-4">
          <Alert variant="destructive">
            <AlertTitle>搜索失败</AlertTitle>
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        </div>
      )}

      {/* 门票列表 */}
      <div className="px-4 space-y-4">
        {loading ? (
          <div className="flex justify-center items-center py-10">
            <Loader2 size={32} className="animate-spin text-teal-600" />
          </div>
        ) : tickets.length === 0 ? (
          <div className="text-center py-10">
            <p className="text-gray-500">暂无门票数据</p>
          </div>
        ) : (
          tickets.map((ticket) => (
            <Card key={ticket.id} className="p-4 hover:shadow-md transition-shadow">
              <div className="flex items-start justify-between mb-3">
                <div>
                  <h3 className="font-semibold">{ticket.name}</h3>
                  <p className="text-sm text-gray-500">{ticket.attraction}</p>
                </div>
                <div className="text-right">
                  <p className="text-xl font-bold text-teal-600">¥{ticket.price}</p>
                  <Badge variant="secondary" className="text-xs mt-1">{ticket.type}</Badge>
                </div>
              </div>

              <div className="flex items-center gap-2 mb-3 text-sm text-gray-600">
                <MapPin size={16} />
                <span>有效期：{ticket.validity}</span>
              </div>

              <div className="flex items-center justify-between pt-3 border-t">
                <p className="text-sm text-gray-500">
                  剩余 <span className="text-orange-500 font-medium">{Math.floor(Math.random() * 50) + 1}</span> 张
                </p>
                <Button 
                  size="sm" 
                  className="bg-gradient-to-r from-teal-500 to-blue-500"
                  onClick={() => handleBook(ticket)}
                >
                  预订
                </Button>
              </div>
            </Card>
          ))
        )}
      </div>
    </div>
  );
}
