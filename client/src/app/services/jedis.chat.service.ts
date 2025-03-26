import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })

export class JedisChatService {

  // private baseURL = "http://localhost:9090/poo";
  private baseURL = 'https://poopal-server-production.up.railway.app/chat';

  constructor(private http: HttpClient) {}

  sendMessage(message: any): Observable<void> {
    return this.http.post<void>(`${this.baseURL}/send`, message);
  }

  getMessages(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseURL}/messages`);
  }
}
