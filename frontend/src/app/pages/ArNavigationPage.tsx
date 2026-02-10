import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Camera, Scan, Info, MapPin, Navigation, Star, Clock, Image as ImageIcon, Zap, Target, AlertCircle } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { Button } from '../components/ui/button';
import { Card } from '../components/ui/card';
import { Badge } from '../components/ui/badge';

export default function ArNavigationPage() {
  const navigate = useNavigate();
  const [isScanning, setIsScanning] = useState(false);
  const [recognizedPlace, setRecognizedPlace] = useState<any>(null);

  const featuredPlaces = [
    {
      id: 1,
      name: '故宫博物院',
      image: 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d',
      distance: '500m',
      direction: '东北方向'
    },
    {
      id: 2,
      name: '天安门广场',
      image: 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d',
      distance: '300m',
      direction: '正北方向'
    },
    {
      id: 3,
      name: '景山公园',
      image: 'https://images.unsplash.com/photo-1509023464722-18d996393ca8',
      distance: '800m',
      direction: '北方向'
    },
  ];

  const handleStartScan = () => {
    setIsScanning(true);
    // 模拟识别过程
    setTimeout(() => {
      setRecognizedPlace({
        name: '故宫博物院',
        englishName: 'The Palace Museum',
        category: '世界文化遗产',
        rating: 4.9,
        description: '故宫博物院建立于1925年，是在明清两代皇宫紫禁城的基础上建立起来的，是中国最大的古代文化艺术博物馆。',
        history: '始建于1406年，距今已有600余年历史',
        openTime: '08:30-17:00',
        ticketPrice: '60元',
        highlights: ['太和殿', '养心殿', '珍宝馆', '钟表馆'],
        tips: [
          '建议预留3-4小时游览时间',
          '周一闭馆，需提前预约',
          '建议从午门进入开始参观'
        ],
        distance: '距您 500米',
        image: 'https://images.unsplash.com/photo-1508804185872-d7badad00f7d'
      });
      setIsScanning(false);
    }, 2000);
  };

  const handleStopScan = () => {
    setIsScanning(false);
    setRecognizedPlace(null);
  };

  return (
    <div className="min-h-screen bg-black">
      {/* 顶部栏 */}
      <div className="absolute top-0 left-0 right-0 z-50 bg-gradient-to-b from-black/70 to-transparent">
        <div className="px-4 py-3 flex items-center justify-between text-white">
          <button onClick={() => navigate(-1)} className="w-10 h-10 rounded-full bg-black/50 backdrop-blur-sm flex items-center justify-center">
            <ArrowLeft size={24} />
          </button>
          <div className="flex items-center gap-2">
            <Scan className="animate-pulse" size={20} />
            <span className="font-semibold">AR实景导览</span>
          </div>
          <button className="w-10 h-10 rounded-full bg-black/50 backdrop-blur-sm flex items-center justify-center">
            <Info size={20} />
          </button>
        </div>
      </div>

      {/* 相机预览区域 */}
      <div className="relative h-screen flex items-center justify-center">
        {!isScanning && !recognizedPlace && (
          <div className="w-full h-full bg-gradient-to-b from-gray-900 to-gray-800 flex items-center justify-center">
            <ImageWithFallback
              src="https://images.unsplash.com/photo-1508804185872-d7badad00f7d"
              alt="Camera view"
              className="w-full h-full object-cover opacity-40"
            />
            <div className="absolute inset-0 flex items-center justify-center">
              <div className="text-center text-white">
                <Camera size={64} className="mx-auto mb-4 opacity-50" />
                <p className="text-lg mb-2">将相机对准景点</p>
                <p className="text-sm opacity-70">AI将自动识别并提供详细信息</p>
              </div>
            </div>
          </div>
        )}

        {isScanning && (
          <div className="w-full h-full bg-gradient-to-b from-gray-900 to-gray-800 flex items-center justify-center">
            <ImageWithFallback
              src="https://images.unsplash.com/photo-1508804185872-d7badad00f7d"
              alt="Scanning"
              className="w-full h-full object-cover opacity-60"
            />
            {/* AR扫描框 */}
            <div className="absolute inset-0 flex items-center justify-center">
              <div className="relative w-80 h-80">
                {/* 扫描框四角 */}
                <div className="absolute top-0 left-0 w-12 h-12 border-t-4 border-l-4 border-teal-500 animate-pulse"></div>
                <div className="absolute top-0 right-0 w-12 h-12 border-t-4 border-r-4 border-teal-500 animate-pulse"></div>
                <div className="absolute bottom-0 left-0 w-12 h-12 border-b-4 border-l-4 border-teal-500 animate-pulse"></div>
                <div className="absolute bottom-0 right-0 w-12 h-12 border-b-4 border-r-4 border-teal-500 animate-pulse"></div>
                
                {/* 扫描线 */}
                <div className="absolute top-0 left-0 right-0 h-1 bg-gradient-to-r from-transparent via-teal-500 to-transparent animate-scan"></div>
                
                <div className="absolute inset-0 flex items-center justify-center">
                  <div className="text-white text-center">
                    <Target size={48} className="mx-auto mb-2 animate-spin" />
                    <p className="text-lg font-semibold">正在识别...</p>
                    <p className="text-sm opacity-70 mt-1">AI分析中</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {recognizedPlace && (
          <div className="w-full h-full relative">
            <ImageWithFallback
              src={recognizedPlace.image}
              alt={recognizedPlace.name}
              className="w-full h-full object-cover"
            />
            
            {/* AR信息叠加层 */}
            <div className="absolute inset-0 bg-gradient-to-b from-transparent via-transparent to-black/80">
              {/* 顶部识别标记 */}
              <div className="absolute top-1/3 left-1/2 -translate-x-1/2">
                <div className="relative">
                  <div className="w-32 h-32 border-4 border-teal-500 rounded-full animate-ping absolute"></div>
                  <div className="w-32 h-32 border-4 border-teal-500 rounded-full flex items-center justify-center bg-teal-500/20 backdrop-blur-sm">
                    <MapPin size={48} className="text-teal-500" />
                  </div>
                </div>
              </div>

              {/* 底部信息卡片 */}
              <div className="absolute bottom-0 left-0 right-0 p-4">
                <Card className="bg-white/95 backdrop-blur-lg border-0 shadow-2xl">
                  <div className="p-4">
                    {/* 标题区 */}
                    <div className="flex items-start justify-between mb-3">
                      <div className="flex-1">
                        <div className="flex items-center gap-2 mb-1">
                          <h2 className="text-2xl font-bold">{recognizedPlace.name}</h2>
                          <Badge className="bg-gradient-to-r from-teal-500 to-blue-500">
                            AI识别
                          </Badge>
                        </div>
                        <p className="text-sm text-gray-600">{recognizedPlace.englishName}</p>
                        <div className="flex items-center gap-3 mt-2">
                          <Badge variant="outline">{recognizedPlace.category}</Badge>
                          <div className="flex items-center gap-1">
                            <Star size={14} className="text-yellow-500 fill-yellow-500" />
                            <span className="text-sm font-semibold">{recognizedPlace.rating}</span>
                          </div>
                        </div>
                      </div>
                      <button 
                        onClick={handleStopScan}
                        className="w-10 h-10 rounded-full bg-gray-200 flex items-center justify-center"
                      >
                        <ArrowLeft size={20} />
                      </button>
                    </div>

                    {/* 快速信息 */}
                    <div className="grid grid-cols-3 gap-2 mb-3 p-3 bg-gray-50 rounded-lg">
                      <div className="text-center">
                        <Clock size={16} className="mx-auto mb-1 text-teal-600" />
                        <div className="text-xs text-gray-600">{recognizedPlace.openTime}</div>
                      </div>
                      <div className="text-center">
                        <Badge className="mb-1 bg-orange-500">¥{recognizedPlace.ticketPrice}</Badge>
                        <div className="text-xs text-gray-600">门票</div>
                      </div>
                      <div className="text-center">
                        <Navigation size={16} className="mx-auto mb-1 text-teal-600" />
                        <div className="text-xs text-gray-600">{recognizedPlace.distance}</div>
                      </div>
                    </div>

                    {/* 描述 */}
                    <p className="text-sm text-gray-700 mb-3 line-clamp-2">
                      {recognizedPlace.description}
                    </p>

                    {/* 亮点 */}
                    <div className="mb-3">
                      <h4 className="text-xs font-semibold text-gray-700 mb-2">必看亮点</h4>
                      <div className="flex flex-wrap gap-2">
                        {recognizedPlace.highlights.map((highlight: string, index: number) => (
                          <Badge key={index} variant="outline" className="text-xs">
                            <Zap size={12} className="mr-1 text-orange-500" />
                            {highlight}
                          </Badge>
                        ))}
                      </div>
                    </div>

                    {/* 操作按钮 */}
                    <div className="flex gap-2">
                      <Button 
                        className="flex-1 bg-gradient-to-r from-teal-500 to-blue-500"
                        onClick={() => navigate('/destination/1')}
                      >
                        <Info size={16} className="mr-1" />
                        查看详情
                      </Button>
                      <Button 
                        variant="outline"
                        onClick={() => {/* 导航 */}}
                      >
                        <Navigation size={16} />
                      </Button>
                      <Button 
                        variant="outline"
                        onClick={() => navigate('/guide/1')}
                      >
                        <ImageIcon size={16} />
                      </Button>
                    </div>
                  </div>
                </Card>
              </div>
            </div>
          </div>
        )}

        {/* 附近景点标记 */}
        {!recognizedPlace && !isScanning && (
          <div className="absolute top-1/4 left-0 right-0 px-4">
            {featuredPlaces.map((place, index) => (
              <div
                key={place.id}
                className="absolute bg-teal-500/90 backdrop-blur-sm text-white px-3 py-2 rounded-full text-sm flex items-center gap-2 cursor-pointer hover:bg-teal-600 transition-all"
                style={{
                  left: `${20 + index * 25}%`,
                  top: `${index * 60}px`
                }}
                onClick={() => navigate('/destination/1')}
              >
                <MapPin size={16} />
                <div>
                  <div className="font-semibold">{place.name}</div>
                  <div className="text-xs opacity-90">{place.distance} · {place.direction}</div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* 底部控制栏 */}
      <div className="absolute bottom-0 left-0 right-0 pb-8 px-4">
        {!recognizedPlace && (
          <div className="flex justify-center gap-4">
            {!isScanning ? (
              <>
                <Button
                  size="lg"
                  className="bg-gradient-to-r from-teal-500 to-blue-500 rounded-full w-20 h-20 shadow-2xl"
                  onClick={handleStartScan}
                >
                  <Scan size={32} />
                </Button>
                <Button
                  size="lg"
                  variant="outline"
                  className="bg-white/90 backdrop-blur-sm rounded-full"
                  onClick={() => navigate('/nearby')}
                >
                  <MapPin size={20} className="mr-2" />
                  附近
                </Button>
              </>
            ) : (
              <Button
                size="lg"
                variant="outline"
                className="bg-white/90 backdrop-blur-sm rounded-full"
                onClick={handleStopScan}
              >
                停止扫描
              </Button>
            )}
          </div>
        )}
      </div>

      {/* 功能提示 */}
      {!recognizedPlace && !isScanning && (
        <div className="absolute bottom-32 left-0 right-0 px-4">
          <Card className="bg-white/90 backdrop-blur-sm border-0 p-3">
            <div className="flex items-start gap-3">
              <AlertCircle className="text-teal-500 flex-shrink-0 mt-0.5" size={20} />
              <div className="text-sm">
                <p className="font-semibold mb-1">如何使用AR导览？</p>
                <ul className="text-xs text-gray-600 space-y-1">
                  <li>• 对准景点建筑，点击扫描按钮</li>
                  <li>• AI将自动识别并展示详细信息</li>
                  <li>• 查看AR标记了解附近景点位置</li>
                </ul>
              </div>
            </div>
          </Card>
        </div>
      )}

      {/* 添加扫描动画的CSS */}
      <style>{`
        @keyframes scan {
          0% { top: 0; }
          50% { top: 100%; }
          100% { top: 0; }
        }
        .animate-scan {
          animation: scan 2s ease-in-out infinite;
        }
      `}</style>
    </div>
  );
}
