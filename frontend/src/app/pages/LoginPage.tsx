import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Eye, EyeOff, Sparkles } from 'lucide-react';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Checkbox } from '../components/ui/checkbox';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';
import { authApi } from '../lib/api';
import MobileShell from '../MobileShell';

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
  const [loading, setLoading] = useState(false);
  const [sendingCode, setSendingCode] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMessage('');
    setSuccessMessage('');

    if (!isLogin && !agreeTerms) {
      setErrorMessage('请先同意用户协议和隐私政策');
      return;
    }

    try {
      setLoading(true);

      if (isLogin) {
        const result = await authApi.login({ phone, password });
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('userPhone', phone);
        if (result?.token) {
          localStorage.setItem('token', result.token);
        }
        if (result?.userId !== undefined && result?.userId !== null) {
          localStorage.setItem('userId', String(result.userId));
        }
        onLogin();
        navigate('/');
        return;
      }

      await authApi.register({ phone, password, code: verificationCode });
      setSuccessMessage('注册成功，请登录');
      setIsLogin(true);
      setVerificationCode('');
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : '操作失败，请稍后重试');
    } finally {
      setLoading(false);
    }
  };

  const handleSendCode = async () => {
    if (!phone) {
      setErrorMessage('请先输入手机号');
      return;
    }

    try {
      setSendingCode(true);
      setErrorMessage('');
      setSuccessMessage('');
      await authApi.sendCode(phone);
      setSuccessMessage('验证码已发送');
    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : '验证码发送失败');
    } finally {
      setSendingCode(false);
    }
  };

  return (
    <MobileShell withBottomSafeArea={false} contentClassName="px-0 pt-0 pb-0">
      <div className="relative overflow-hidden rounded-b-[36px] bg-gradient-to-br from-teal-500 via-cyan-500 to-sky-600 px-6 pb-10 pt-10 text-white shadow-[0_18px_55px_rgba(14,116,144,0.28)]">
        <div className="absolute inset-0 opacity-15">
          <ImageWithFallback
            src="https://images.unsplash.com/photo-1631535152690-ba1a85229136"
            alt="Travel"
            className="h-full w-full object-cover"
          />
        </div>
        <div className="absolute -right-10 top-8 h-32 w-32 rounded-full bg-white/10 blur-2xl" />
        <div className="relative z-10 space-y-4">
          <div className="inline-flex items-center gap-2 rounded-full border border-white/20 bg-white/10 px-3 py-1 text-xs font-medium backdrop-blur-sm">
            <Sparkles size={14} />
            Figma 风格移动端体验
          </div>
          <div>
            <p className="text-sm text-white/80">AI Powered Travel Companion</p>
            <h1 className="mt-2 text-3xl font-semibold tracking-tight">让每一段旅程都更聪明，也更好看</h1>
            <p className="mt-3 max-w-xs text-sm leading-6 text-white/80">登录后即可体验目的地探索、智能行程、钱包优惠与旅行消息中心。</p>
          </div>
        </div>
      </div>

      <div className="px-5 pb-6 pt-6">
        <div className="rounded-[28px] border border-white/70 bg-white/90 p-5 shadow-[0_16px_45px_rgba(15,23,42,0.08)] backdrop-blur-xl">
          <div className="mb-6 flex justify-center gap-2 rounded-2xl bg-slate-50 p-1">
            <button
              onClick={() => setIsLogin(true)}
              className={`flex-1 rounded-2xl px-4 py-3 text-sm font-semibold transition-all ${
                isLogin ? 'bg-white text-teal-600 shadow-sm' : 'text-slate-400'
              }`}
            >
              登录
            </button>
            <button
              onClick={() => setIsLogin(false)}
              className={`flex-1 rounded-2xl px-4 py-3 text-sm font-semibold transition-all ${
                !isLogin ? 'bg-white text-teal-600 shadow-sm' : 'text-slate-400'
              }`}
            >
              注册
            </button>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <label className="text-sm font-medium text-slate-600">手机号</label>
              <Input
                type="tel"
                placeholder="请输入手机号"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                className="h-12 rounded-2xl border-white bg-slate-50/90 shadow-inner"
                required
              />
            </div>

            {!isLogin && (
              <div className="space-y-2">
                <label className="text-sm font-medium text-slate-600">验证码</label>
                <div className="flex gap-2">
                  <Input
                    type="text"
                    placeholder="请输入验证码"
                    value={verificationCode}
                    onChange={(e) => setVerificationCode(e.target.value)}
                    className="h-12 flex-1 rounded-2xl border-white bg-slate-50/90 shadow-inner"
                  />
                  <Button type="button" variant="outline" className="h-12 rounded-2xl border-slate-200 px-4 whitespace-nowrap" onClick={handleSendCode} disabled={sendingCode}>
                    {sendingCode ? '发送中...' : '获取验证码'}
                  </Button>
                </div>
              </div>
            )}

            <div className="space-y-2">
              <label className="text-sm font-medium text-slate-600">密码</label>
              <div className="relative">
                <Input
                  type={showPassword ? 'text' : 'password'}
                  placeholder="请输入密码"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="h-12 rounded-2xl border-white bg-slate-50/90 pr-12 shadow-inner"
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-400"
                >
                  {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
                </button>
              </div>
            </div>

            {!isLogin && (
              <div className="flex items-start gap-2 rounded-2xl bg-slate-50 px-3 py-3">
                <Checkbox
                  id="terms"
                  checked={agreeTerms}
                  onCheckedChange={(checked) => setAgreeTerms(checked as boolean)}
                />
                <label htmlFor="terms" className="text-sm leading-6 text-slate-600">
                  我已阅读并同意<span className="font-medium text-teal-600">《用户协议》</span>和<span className="font-medium text-teal-600">《隐私政策》</span>
                </label>
              </div>
            )}

            {errorMessage && <div className="rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-500">{errorMessage}</div>}
            {successMessage && <div className="rounded-2xl bg-emerald-50 px-4 py-3 text-sm text-emerald-600">{successMessage}</div>}

            <Button
              type="submit"
              className="h-12 w-full rounded-2xl bg-gradient-to-r from-teal-500 via-cyan-500 to-sky-500 text-base font-semibold text-white shadow-[0_12px_30px_rgba(20,184,166,0.28)] hover:from-teal-600 hover:to-sky-600"
              disabled={loading || (!isLogin && !agreeTerms)}
            >
              {loading ? '提交中...' : isLogin ? '登录' : '注册'}
            </Button>
          </form>

          {isLogin && (
            <div className="mt-5 text-center">
              <button
                onClick={() => setIsLogin(false)}
                className="text-sm text-slate-500"
              >
                还没有账号？<span className="font-medium text-teal-600">立即注册</span>
              </button>
            </div>
          )}
        </div>
      </div>
    </MobileShell>
  );
}
