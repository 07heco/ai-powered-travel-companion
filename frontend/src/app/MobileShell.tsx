import { ReactNode } from 'react';
import { cn } from './components/ui/utils';

interface MobileShellProps {
  children: ReactNode;
  className?: string;
  contentClassName?: string;
  withBottomSafeArea?: boolean;
}

export default function MobileShell({
  children,
  className,
  contentClassName,
  withBottomSafeArea = true,
}: MobileShellProps) {
  return (
    <div className={cn('min-h-screen bg-[radial-gradient(circle_at_top,#ecfeff_0%,#f8fafc_28%,#f8fafc_100%)] text-slate-900', className)}>
      <div className="mx-auto min-h-screen w-full max-w-md bg-white/72 backdrop-blur-xl shadow-[0_12px_60px_rgba(15,23,42,0.12)] ring-1 ring-white/70">
        <div className={cn('min-h-screen px-4 pt-4', withBottomSafeArea ? 'pb-24' : 'pb-6', contentClassName)}>
          {children}
        </div>
      </div>
    </div>
  );
}
