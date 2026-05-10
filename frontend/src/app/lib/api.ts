export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export interface ApiResponse<T> {
  code: number;
  msg: string;
  data: T;
}

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(init?.headers || {}),
    },
    ...init,
  });

  const payload: ApiResponse<T> = await response.json();

  if (!response.ok || payload.code !== 200) {
    throw new Error(payload?.msg || '请求失败');
  }

  return payload.data;
}

export interface LoginRequest {
  phone: string;
  password: string;
}

export interface RegisterRequest {
  phone: string;
  password: string;
  code: string;
}

export interface LoginResponse {
  token: string;
  userId?: number | string;
  nickname?: string;
}

export interface UserInfo {
  id?: number | string;
  nickname?: string;
  phone?: string;
  avatar?: string;
}

export interface DestinationItem {
  id: number | string;
  name: string;
  coverImage?: string;
  image?: string;
  rating?: number;
  bestTime?: string;
  attractionCount?: number;
  country?: string;
  description?: string;
}

export interface DealItem {
  id: number | string;
  title?: string;
  name?: string;
  type?: string;
  discountType?: string;
  discountValue?: number;
  amount?: number;
  condition?: string;
  description?: string;
  status?: number | string;
}

export interface WalletInfo {
  id?: number | string;
  userId?: number | string;
  balance?: number;
  points?: number;
}

export interface CouponItem {
  id: number | string;
  name?: string;
  title?: string;
  amount?: number;
  discount?: string | number;
  condition?: string;
  expireTime?: string;
  validUntil?: string;
  status?: number | string;
  scope?: string;
}

export const authApi = {
  login: (data: LoginRequest) => request<LoginResponse>('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify(data),
  }),
  register: (data: RegisterRequest) => request<unknown>('/api/auth/register', {
    method: 'POST',
    body: JSON.stringify(data),
  }),
  sendCode: (phone: string) => request<unknown>(`/api/auth/send-code?phone=${encodeURIComponent(phone)}`, {
    method: 'POST',
  }),
  getUserInfo: (token: string) => request<UserInfo>('/api/auth/user-info', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }),
};

export const destinationApi = {
  list: () => request<DestinationItem[]>('/api/destination/list'),
};

export const dealsApi = {
  active: () => request<DealItem[]>('/api/deals/active'),
};

export const walletApi = {
  getWallet: (userId: number | string) => request<WalletInfo>(`/api/wallet/${userId}`),
  getCoupons: (userId: number | string) => request<CouponItem[]>(`/api/wallet/coupon/user/${userId}`),
};
