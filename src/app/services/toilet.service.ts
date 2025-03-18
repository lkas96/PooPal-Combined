import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Toilet } from '../models/toilet';

@Injectable({
    providedIn: 'root'
})

export class ToiletService {
    constructor(private httpclient: HttpClient) { }

    // private baseURL = "http://localhost:9090/toilet";
    private baseURL = "http://poopal.me:9090/toilet";

    getAllToilets(): Observable<Toilet[]> {
        return this.httpclient.get<Toilet[]>(`${this.baseURL}/browse/all`);
    }
}