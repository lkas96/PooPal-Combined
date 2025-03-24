import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { NearestToilet, Toilet } from '../models/toilet';
import { Review } from '../models/review';

@Injectable({
  providedIn: 'root',
})
export class ToiletService {
  constructor(private httpclient: HttpClient) {}

  // private baseURL = "http://localhost:9090/toilet";
  private baseURL = 'https://poopal-server-production.up.railway.app/toilet';

  getAllToilets(): Observable<Toilet[]> {
    return this.httpclient.get<Toilet[]>(`${this.baseURL}/browse/all`);
  }

  getNearestToilets(
    latitude: number,
    longitude: number
  ): Observable<NearestToilet[]> {
    return this.httpclient.get<NearestToilet[]>(
      `${this.baseURL}/browse/nearest`,
      {
        params: {
          lat: latitude,
          lon: longitude,
        },
        //sending it over a query parammeters beacuse requirements lmao
      }
    );
  }

  getToiletReviews(toiletId: string): Observable<Review[]> {
    return this.httpclient.get<Review[]>(
      `${this.baseURL}/getreviews/${toiletId}`
    );
  }

  getToiletDetails(toiletId: string) : Observable<Toilet> {
    return this.httpclient.get<Toilet>(
      `${this.baseURL}/getdetails/${toiletId}`
    );
  }

  
}
