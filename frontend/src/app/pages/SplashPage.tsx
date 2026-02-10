import { Compass } from 'lucide-react';

export default function SplashPage() {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gradient-to-br from-teal-400 via-blue-500 to-purple-600">
      <div 
        className="absolute inset-0 opacity-30"
        style={{
          backgroundImage: 'url(https://images.unsplash.com/photo-1683041133891-613b76cbebc7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtb3VudGFpbiUyMGxha2UlMjBsYW5kc2NhcGUlMjBzY2VuaWN8ZW58MXx8fHwxNzcwMzc0NDgwfDA&ixlib=rb-4.1.0&q=80&w=1080)',
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      />
      <div className="relative z-10 flex flex-col items-center">
        <div className="flex items-center gap-3 mb-6">
          <div className="p-4 bg-white/20 backdrop-blur-sm rounded-2xl">
            <Compass className="w-12 h-12 text-white" strokeWidth={2} />
          </div>
        </div>
        <h1 className="text-4xl font-bold text-white mb-2">智能旅游助手</h1>
        <p className="text-white/80 text-lg">发现世界，规划旅程</p>
        <div className="mt-8 w-32 h-1 bg-white/30 rounded-full overflow-hidden">
          <div className="h-full w-2/3 bg-white rounded-full animate-pulse" />
        </div>
      </div>
    </div>
  );
}
