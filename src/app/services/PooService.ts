import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { PooRecord } from '../models/pooRecord';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { PooRecordViewing } from '../models/pooRecordViewing';

@Injectable({
  providedIn: 'root'
})
export class PooService {


  constructor(private httpClient: HttpClient, private gauth: AuthService) { }

  private baseURL = "http://localhost:9090/poo/records";

  //springboot endpoint to save POST METHOD to /poo/records/new
  savePooRecord(userId: string, pooRecord: PooRecord) : Observable<PooRecord>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.post<PooRecord>(`${this.baseURL}/new`, pooRecord, { headers } // ✅ Pass headers in the third parameter
    );
  }

  //springboot endpoint to get GET METHOD to /poo/records/all
  getRecords(userId: string) : Observable<PooRecordViewing[]>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.get<PooRecordViewing[]>(`${this.baseURL}/all`, { headers }
    );
  }

  //get a single record
  getRecord(userId: string, recordId: string) : Observable<PooRecordViewing>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.get<PooRecordViewing>(`${this.baseURL}/${recordId}`, { headers }
    );
  }

  //update a record ok functioning
  updatePooRecord(userId: string, retrievedRecord: PooRecordViewing) : Observable<PooRecordViewing>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.put<PooRecordViewing>(`${this.baseURL}/edit/${retrievedRecord.id}`,retrievedRecord, { headers }
    );
  }

  //delete a record
  deleteRecord(userId: string, recordId: string) : Observable<any>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.delete<any>(`${this.baseURL}/delete/${recordId}`, { headers }
    );
  }

}