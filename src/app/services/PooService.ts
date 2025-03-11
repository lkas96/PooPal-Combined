import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { PooRecord } from '../models/pooRecord';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PooService{


  constructor(private httpClient: HttpClient, private gauth: AuthService) { }

  private baseURL = "http://localhost:9090/poo/records";

  //springboot endpoint to save POST METHOD to /poo/records/new
  savePooRecord(userId: string, pooRecord: PooRecord) : Observable<PooRecord>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.post<PooRecord>(`${this.baseURL}/new`, pooRecord, { headers } // ✅ Pass headers in the third parameter
    );
  }



}

// getAll() : Observable<Employee []> {
//   return this.httpClient.get<Employee[]>(this.baseURL);
// }

// getById(id: number) : Observable<Employee> {
//   return this.httpClient.get<Employee>(this.baseURL+ '/' + id);
// }

// create(employee: Employee) : Observable<Employee>{
//   return this.httpClient.post<Employee>(this.baseURL, employee);
// }

// update(id:number, employee: Employee) : Observable<Object>{
//   return this.httpClient.put<Employee>(this.baseURL+ '/'+ id, employee);
// }

// deleteById(id:number) : Observable<Object>{
//   return this.httpClient.delete<Employee>(this.baseURL +'/'+ id)
// }