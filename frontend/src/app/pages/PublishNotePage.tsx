import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, X, Plus, MapPin, Hash, Image as ImageIcon } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Textarea } from '../components/ui/textarea';
import { Switch } from '../components/ui/switch';
import { Label } from '../components/ui/label';

export default function PublishNotePage() {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [tags, setTags] = useState('');
  const [location, setLocation] = useState('东京');
  const [isPublic, setIsPublic] = useState(true);
  const [images, setImages] = useState<string[]>([]);

  const handlePublish = () => {
    if (title && images.length > 0) {
      alert('笔记发布成功！');
      navigate('/');
    } else {
      alert('请填写标题并上传至少一张图片');
    }
  };

  const addPlaceholderImage = () => {
    if (images.length < 9) {
      setImages([...images, `https://images.unsplash.com/photo-1648871647634-0c99b483cb63?w=400`]);
    }
  };

  const removeImage = (index: number) => {
    setImages(images.filter((_, i) => i !== index));
  };

  return (
    <div className="min-h-screen bg-white pb-24">
      {/* 顶部操作栏 */}
      <div className="sticky top-0 z-40 bg-white border-b">
        <div className="px-4 py-3 flex items-center justify-between">
          <button onClick={() => navigate(-1)}>
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold">发布笔记</h1>
          <Button
            size="sm"
            variant="ghost"
            onClick={() => navigate('/')}
          >
            预览
          </Button>
        </div>
      </div>

      <div className="p-4 space-y-6">
        {/* 图片上传区 */}
        <div>
          <label className="text-sm font-medium mb-2 block">上传图片（最多9张）</label>
          <div className="grid grid-cols-3 gap-3">
            {images.map((image, index) => (
              <div key={index} className="relative aspect-square bg-gray-100 rounded-lg overflow-hidden">
                <img src={image} alt={`上传图片 ${index + 1}`} className="w-full h-full object-cover" />
                <button
                  onClick={() => removeImage(index)}
                  className="absolute top-1 right-1 w-6 h-6 bg-black/60 rounded-full flex items-center justify-center text-white"
                >
                  <X size={14} />
                </button>
              </div>
            ))}
            {images.length < 9 && (
              <button
                onClick={addPlaceholderImage}
                className="aspect-square bg-gray-100 rounded-lg flex flex-col items-center justify-center text-gray-400 hover:bg-gray-200 transition-colors"
              >
                <Plus size={32} />
                <span className="text-xs mt-1">添加图片</span>
              </button>
            )}
          </div>
          <p className="text-xs text-gray-500 mt-2">
            点击添加按钮选择图片（演示模式使用默认图片）
          </p>
        </div>

        {/* 标题输入 */}
        <div>
          <label className="text-sm font-medium mb-2 block">笔记标题</label>
          <Input
            placeholder="添加笔记标题，吸引更多人关注"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="text-lg"
          />
        </div>

        {/* 正文输入 */}
        <div>
          <label className="text-sm font-medium mb-2 block">正文</label>
          <Textarea
            placeholder="分享你的旅行故事、攻略和感受..."
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="min-h-40 resize-none"
          />
        </div>

        {/* 标签输入 */}
        <div>
          <label className="text-sm font-medium mb-2 block">
            <Hash size={16} className="inline mr-1" />
            添加标签
          </label>
          <Input
            placeholder="例如：#周末去哪儿 #东京旅行"
            value={tags}
            onChange={(e) => setTags(e.target.value)}
          />
          <div className="flex flex-wrap gap-2 mt-2">
            <span className="px-3 py-1 bg-teal-50 text-teal-600 text-xs rounded-full cursor-pointer">
              #周末去哪儿
            </span>
            <span className="px-3 py-1 bg-teal-50 text-teal-600 text-xs rounded-full cursor-pointer">
              #旅行攻略
            </span>
            <span className="px-3 py-1 bg-teal-50 text-teal-600 text-xs rounded-full cursor-pointer">
              #美食打卡
            </span>
          </div>
        </div>

        {/* 位置选择 */}
        <div>
          <label className="text-sm font-medium mb-2 block">
            <MapPin size={16} className="inline mr-1" />
            添加位置
          </label>
          <Input
            placeholder="选择位置"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />
        </div>

        {/* 可见性设置 */}
        <div className="flex items-center justify-between py-4 border-t border-b">
          <div>
            <Label htmlFor="public" className="text-sm font-medium">
              公开发布
            </Label>
            <p className="text-xs text-gray-500">所有人都可以看到这篇笔记</p>
          </div>
          <Switch
            id="public"
            checked={isPublic}
            onCheckedChange={setIsPublic}
          />
        </div>
      </div>

      {/* 底部发布栏 */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t p-4">
        <div className="flex gap-3">
          <div className="flex items-center gap-2 text-sm text-gray-600">
            <Switch checked={isPublic} onCheckedChange={setIsPublic} />
            <span>{isPublic ? '公开' : '仅自己可见'}</span>
          </div>
          <Button
            className="flex-1 bg-gradient-to-r from-orange-400 to-pink-500 hover:from-orange-500 hover:to-pink-600"
            onClick={handlePublish}
            disabled={!title || images.length === 0}
          >
            发布
          </Button>
        </div>
      </div>
    </div>
  );
}
