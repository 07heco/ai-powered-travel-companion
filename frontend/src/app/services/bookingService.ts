import { apiClient } from './apiClient';

export interface Booking {
  id: string;
  type: 'flight' | 'hotel' | 'ticket';
  userId: string;
  status: 'confirmed' | 'pending' | 'cancelled';
  createdAt: string;
  updatedAt: string;
  details: any;
}

export interface CreateBookingRequest {
  type: 'flight' | 'hotel' | 'ticket';
  details: any;
}

export interface UpdateBookingRequest {
  details: any;
}

export const bookingService = {
  async createBooking(data: CreateBookingRequest): Promise<Booking> {
    return apiClient.post<Booking>('/bookings', data);
  },

  async getUserBookings(): Promise<Booking[]> {
    return apiClient.get<Booking[]>('/bookings');
  },

  async getBookingDetail(bookingId: string): Promise<Booking> {
    return apiClient.get<Booking>(`/bookings/${bookingId}`);
  },

  async cancelBooking(bookingId: string): Promise<Booking> {
    return apiClient.put<Booking>(`/bookings/${bookingId}/cancel`);
  },

  async updateBooking(bookingId: string, data: UpdateBookingRequest): Promise<Booking> {
    return apiClient.put<Booking>(`/bookings/${bookingId}`, data);
  },
};
