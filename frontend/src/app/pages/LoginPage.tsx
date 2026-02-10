import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Eye, EyeOff } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Checkbox } from '../components/ui/checkbox';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';

interface LoginPageProps {
  onLogin: () => void;
}

export default function LoginPage({ onLogin }: LoginPageProps) {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(true);
  const [showPassword, setShowPassword] = useState(false);
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [verificationCode, setVerificationCode] = useState('');
  const [agreeTerms, setAgreeTerms] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (isLogin || agreeTerms) {
      localStorage.setItem('isLoggedIn', 'true');
      localStorage.setItem('userPhone', phone);
      onLogin();
      navigate('/');
    }
  };

  return (
    <div className="min-h-screen bg-white flex flex-col">
      {/* 品牌宣传区 */}
      <div className="relative h-64 bg-gradient-to-br from-teal-400 to-blue-500 overflow-hidden">
        <div className="absolute inset-0 grid grid-cols-3 gap-2 p-4 opacity-80">
          <ImageWithFallback 
            src="https://images.unsplash.com/photo-1631535152690-ba1a85229136" 
            alt="Beach" 
            className="w-full h-full object-cover rounded-lg"
          />
          <ImageWithFallback 
            src="https://images.unsplash.com/photo-1760995142740-f7577917e1c7" 
            alt="City" 
            className="w-full h-full object-cover rounded-lg"
          />
          <ImageWithFallback 
            src="https://images.unsplash.com/photo-1557772328-e7a1a09bcec2" 
            alt="Japan" 
            className="w-full h-full object-cover rounded-lg"
          />
        </div>
        <div className="absolute inset-0 bg-gradient-to-b from-transparent to-white" />
      </div>

      {/* 表单区域 */}
      <div className="flex-1 px-6 -mt-12 relative z-10">
        <div className="bg-white rounded-2xl shadow-lg p-6">
          <div className="flex justify-center gap-4 mb-6">
            <button
              onClick={() => setIsLogin(true)}
              className={`text-lg font-semibold pb-2 px-4 transition-colors ${
                isLogin ? 'text-teal-600 border-b-2 border-teal-600' : 'text-gray-400'
              }`}
            >
              登录
            </button>
            <button
              onClick={() => setIsLogin(false)}
              className={`text-lg font-semibold pb-2 px-4 transition-colors ${
                !isLogin ? 'text-teal-600 border-b-2 border-teal-600' : 'text-gray-400'
              }`}
            >
              注册
            </button>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="text-sm text-gray-600 mb-1 block">手机号</label>
              <Input
                type="tel"
                placeholder="请输入手机号"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                className="h-12"
                required
              />
            </div>

            {!isLogin && (
              <div>
                <label className="text-sm text-gray-600 mb-1 block">验证码</label>
                <div className="flex gap-2">
                  <Input
                    type="text"
                    placeholder="请输入验证码"
                    value={verificationCode}
                    onChange={(e) => setVerificationCode(e.target.value)}
                    className="h-12 flex-1"
                  />
                  <Button type="button" variant="outline" className="h-12 px-4 whitespace-nowrap">
                    获取验证码
                  </Button>
                </div>
              </div>
            )}

            <div>
              <label className="text-sm text-gray-600 mb-1 block">密码</label>
              <div className="relative">
                <Input
                  type={showPassword ? 'text' : 'password'}
                  placeholder="请输入密码"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="h-12 pr-12"
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400"
                >
                  {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
                </button>
              </div>
            </div>

            {!isLogin && (
              <div className="flex items-center gap-2">
                <Checkbox
                  id="terms"
                  checked={agreeTerms}
                  onCheckedChange={(checked) => setAgreeTerms(checked as boolean)}
                />
                <label htmlFor="terms" className="text-sm text-gray-600">
                  我已阅读并同意<span className="text-teal-600">《用户协议》</span>和<span className="text-teal-600">《隐私政策》</span>
                </label>
              </div>
            )}

            <Button
              type="submit"
              className="w-full h-12 bg-gradient-to-r from-teal-500 to-blue-500 hover:from-teal-600 hover:to-blue-600 text-white font-semibold"
              disabled={!isLogin && !agreeTerms}
            >
              {isLogin ? '登录' : '注册'}
            </Button>
          </form>

          {isLogin && (
            <div className="text-center mt-4">
              <button
                onClick={() => setIsLogin(false)}
                className="text-sm text-gray-500"
              >
                还没有账号？<span className="text-teal-600">立即注册</span>
              </button>
            </div>
          )}

          <div className="mt-6 pt-6 border-t border-gray-100">
            <p className="text-center text-sm text-gray-500 mb-4">第三方登录</p>
            <div className="flex justify-center gap-6">
              <button className="w-12 h-12 rounded-full bg-green-500 flex items-center justify-center text-white hover:bg-green-600 transition-colors">
                <span className="text-lg font-bold">微</span>
              </button>
              <button className="w-12 h-12 rounded-full bg-blue-500 flex items-center justify-center text-white hover:bg-blue-600 transition-colors">
                <span className="text-lg font-bold">支</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
