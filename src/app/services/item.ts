import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Item {
  id?: number;
  itemId?: number;
  itemName: string;
  title?: string;
  category: string;
  description: string;
  location: string;
  date?: string;
  type?: string;   // 'LOST' | 'FOUND'
  status?: string; // 'OPEN' | 'CLAIMED' | 'RETURNED' | 'CLOSED'
  imageUrl?: string;
  photo?: string;
  user?: {
    id: number;
    name?: string;
    email?: string;
  };
  createdAt?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private apiUrl = 'http://localhost:8081/api/items';

  constructor(private http: HttpClient) {}

  getItems(filters?: { type?: string; category?: string; status?: string; search?: string }): Observable<Item[]> {
    let params = new HttpParams();
    if (filters?.type) params = params.set('type', filters.type);
    if (filters?.category) params = params.set('category', filters.category);
    if (filters?.status) params = params.set('status', filters.status);
    if (filters?.search) params = params.set('search', filters.search);

    return this.http.get<Item[]>(this.apiUrl, { params });
  }

  getItemById(id: number): Observable<Item> {
    return this.http.get<Item>(`${this.apiUrl}/${id}`);
  }

  reportLost(item: Partial<Item>): Observable<Item> {
    return this.http.post<Item>(`${this.apiUrl}/lost`, item);
  }

  reportFound(item: Partial<Item>): Observable<Item> {
    return this.http.post<Item>(`${this.apiUrl}/found`, item);
  }

  updateStatus(id: number, status: string): Observable<Item> {
    return this.http.patch<Item>(`${this.apiUrl}/${id}/status`, { status });
  }

  updateItem(id: number, item: Partial<Item>, userId?: number): Observable<Item> {
    let params = new HttpParams();
    if (userId) params = params.set('userId', userId.toString());
    return this.http.put<Item>(`${this.apiUrl}/${id}`, item, { params });
  }

  deleteItem(id: number, userId?: number): Observable<void> {
    let params = new HttpParams();
    if (userId) params = params.set('userId', userId.toString());
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { params });
  }
}
