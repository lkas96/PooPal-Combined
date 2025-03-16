import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Toilet } from '../models/toilet';

@Injectable({
    providedIn: 'root'
})

export class PooPalAiService {
    constructor(private httpclient: HttpClient) { }

    private baseURL = "http://localhost:9090/ai";

    //posting userPrompt to /ai/chat in the body
    getResponse(userPrompt: string, userId: string): Observable<any> {
        const headers = new HttpHeaders({ 'userId': userId });
        return this.httpclient.post<any>(`${this.baseURL}/chat`, userPrompt, { headers });
    }
}