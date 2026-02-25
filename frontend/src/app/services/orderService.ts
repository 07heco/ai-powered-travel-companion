import { apiClient } from './apiClient';

export interface Order {
  id: string;
  userId: string;
  bookingId: string;
  amount: number;
  status: 'pending' | 'paid' | 'cancelled' | 'refunded';
  paymentMethod: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateOrderRequest {
  bookingId: string;
  amount: number;
  paymentMethod: string;
}

export interface UpdateOrderStatusRequest {
  status: 'pending' | 'paid' | 'cancelled' | 'refunded';
}

export interface PaymentCallbackRequest {
  orderId: string;
  transactionId: string;
  status: 'success' | 'failure';
  paymentMethod: string;
}

export const orderService = {
  async createOrder(data: CreateOrderRequest): Promise<Order> {
    return apiClient.post<Order>('/orders', data);
  },

  async getUserOrders(): Promise<Order[]> {
    return apiClient.get<Order[]>('/orders');
  },

  async getOrderDetail(orderId: string): Promise<Order> {
    return apiClient.get<Order>(`/orders/${orderId}`);
  },

  async updateOrderStatus(orderId: string, data: UpdateOrderStatusRequest): Promise<Order> {
    return apiClient.put<Order>(`/orders/${orderId}/status`, data);
  },

  async handlePaymentCallback(data: PaymentCallbackRequest): Promise<Order> {
    return apiClient.post<Order>('/orders/payment/callback', data);
  },
};
