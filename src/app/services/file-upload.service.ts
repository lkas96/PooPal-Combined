import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Review } from '../models/review';
import { lastValueFrom, Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})

export class FileUploadService {
  constructor(private httpClient: HttpClient, private gauth: AuthService) {}

  // private baseURL = "http://localhost:9090/poo";
  private baseURL = 'https://poopal-server-production.up.railway.app/toilet';

  upload(form: any, image: Blob, userId: string, toiletId: string) : Observable<Review> {
    const formData = new FormData();
    formData.set('cleanliness', form.cleanliness);
    formData.set('smell', form.smell);
    formData.set('recommended', form.recommended);
    formData.set('comments', form.comments);
    formData.set('file', image);

    const headers = new HttpHeaders({ 'userId': userId, 'toiletId': toiletId });

    return this.httpClient.post<Review>(`${this.baseURL}/review/upload`, formData, { headers });
  }

  //retrieve image to display for details reviews
  getImage(reviewId: string) {
    return lastValueFrom(
      this.httpClient.get<Review>(`${this.baseURL}/review/get-image/${reviewId}`)
    );
  }
}
