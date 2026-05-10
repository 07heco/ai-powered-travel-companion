import { Compass } from 'lucide-react';

export default function SplashPage() {
  return (
    <div className="fixed inset-0 overflow-hidden bg-[radial-gradient(circle_at_top,#5eead4_0%,#0f766e_24%,#0f172a_100%)]">
      <div
        className="absolute inset-0 opacity-20"
        style={{
          backgroundImage: 'url(https://images.unsplash.com/photo-1683041133891-613b76cbebc7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtb3VudGFpbiUyMGxha2UlMjBsYW5kc2NhcGUlMjBzY2VuaWN8ZW58MXx8fHwxNzcwMzc0NDgwfDA&ixlib=rb-4.1.0&q=80&w=1080)',
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      />
      <div className="absolute -top-10 left-1/2 h-56 w-56 -translate-x-1/2 rounded-full bg-white/10 blur-3xl" />
      <div className="relative z-10 flex min-h-screen flex-col items-center justify-center px-8 text-center">
        <div className="mb-8 rounded-[32px] border border-white/20 bg-white/10 p-5 shadow-[0_20px_50px_rgba(15,23,42,0.25)] backdrop-blur-xl">
          <Compass className="h-14 w-14 text-white" strokeWidth={1.8} />
        </div>
        <div className="space-y-3">
          <p className="text-sm font-medium uppercase tracking-[0.38em] text-white/70">AI Powered Travel Companion</p>
          <h1 className="text-4xl font-semibold tracking-tight text-white">智能旅游助手</h1>
          <p className="mx-auto max-w-xs text-sm leading-6 text-white/75">发现世界，规划旅程，用更优雅的移动端体验完成从灵感到出行的每一步。</p>
        </div>
        <div className="mt-10 h-1.5 w-36 overflow-hidden rounded-full bg-white/15">
          <div className="h-full w-2/3 rounded-full bg-gradient-to-r from-white via-teal-100 to-white animate-pulse" />
        </div>
      </div>
    </div>
  );
}
