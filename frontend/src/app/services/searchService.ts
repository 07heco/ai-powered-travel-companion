import { apiClient } from './apiClient';

export interface Flight {
  id: string;
  airline: string;
  flightNumber: string;
  departureAirport: string;
  arrivalAirport: string;
  departureTime: string;
  arrivalTime: string;
  price: number;
  duration: string;
  stops: number;
}

export interface Hotel {
  id: string;
  name: string;
  location: string;
  rating: number;
  pricePerNight: number;
  amenities: string[];
  imageUrl: string;
}

export interface Ticket {
  id: string;
  name: string;
  attraction: string;
  price: number;
  type: string;
  validity: string;
}

export interface Destination {
  id: string;
  name: string;
  country: string;
  description: string;
  imageUrl: string;
  popular: boolean;
}

export interface FlightSearchRequest {
  origin: string;
  destination: string;
  departureDate: string;
  returnDate?: string;
  passengers: number;
  class: 'economy' | 'business' | 'first';
}

export interface HotelSearchRequest {
  destination: string;
  checkInDate: string;
  checkOutDate: string;
  guests: number;
  rooms: number;
  rating?: number;
  priceRange?: [number, number];
}

export interface TicketSearchRequest {
  attraction: string;
  date?: string;
  type?: string;
  priceRange?: [number, number];
}

export interface DestinationSearchRequest {
  query: string;
  region?: string;
  type?: string;
}

export const searchService = {
  async searchFlights(params: FlightSearchRequest): Promise<Flight[]> {
    return apiClient.get<Flight[]>('/search/flights', { params });
  },

  async searchHotels(params: HotelSearchRequest): Promise<Hotel[]> {
    return apiClient.get<Hotel[]>('/search/hotels', { params });
  },

  async searchTickets(params: TicketSearchRequest): Promise<Ticket[]> {
    return apiClient.get<Ticket[]>('/search/tickets', { params });
  },

  async searchDestinations(params: DestinationSearchRequest): Promise<Destination[]> {
    return apiClient.get<Destination[]>('/search/destinations', { params });
  },
};
