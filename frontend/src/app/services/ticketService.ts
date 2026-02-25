import { apiClient } from './apiClient';

export interface TicketType {
  id: string;
  name: string;
  description: string;
  price: number;
  validity: string;
}

export interface TicketDetail {
  id: string;
  typeId: string;
  attraction: string;
  location: string;
  description: string;
  imageUrl: string;
  availableSlots: number;
  bookingInstructions: string;
}

export interface BookTicketRequest {
  typeId: string;
  quantity: number;
  date: string;
  customerInfo: {
    name: string;
    email: string;
    phone: string;
  };
}

export interface ValidateTicketRequest {
  ticketId: string;
  verificationCode: string;
}

export interface ValidatedTicket {
  valid: boolean;
  ticket: TicketDetail;
  message: string;
}

export const ticketService = {
  async getTicketTypes(): Promise<TicketType[]> {
    return apiClient.get<TicketType[]>('/tickets/types');
  },

  async getTicketDetails(ticketId: string): Promise<TicketDetail> {
    return apiClient.get<TicketDetail>(`/tickets/${ticketId}`);
  },

  async bookTicket(data: BookTicketRequest): Promise<any> {
    return apiClient.post('/tickets/book', data);
  },

  async validateTicket(data: ValidateTicketRequest): Promise<ValidatedTicket> {
    return apiClient.post<ValidatedTicket>('/tickets/validate', data);
  },
};
