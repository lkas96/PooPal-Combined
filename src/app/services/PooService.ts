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

  private baseURL = "http://localhost:9090/poo";

  //springboot endpoint to save POST METHOD to /poo/records/new
  savePooRecord(userId: string, pooRecord: PooRecord) : Observable<PooRecord>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.post<PooRecord>(`${this.baseURL}/records/new`, pooRecord, { headers } // ✅ Pass headers in the third parameter
    );
  }

  //springboot endpoint to get GET METHOD to /poo/records/all
  getRecords(userId: string) : Observable<PooRecordViewing[]>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.get<PooRecordViewing[]>(`${this.baseURL}/records/all`, { headers }
    );
  }

  //get a single record
  getRecord(userId: string, recordId: string) : Observable<PooRecordViewing>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.get<PooRecordViewing>(`${this.baseURL}/records/${recordId}`, { headers }
    );
  }

  //update a record ok functioning
  updatePooRecord(userId: string, retrievedRecord: PooRecordViewing) : Observable<PooRecordViewing>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.put<PooRecordViewing>(`${this.baseURL}/records/edit/${retrievedRecord.id}`,retrievedRecord, { headers }
    );
  }

  //delete a record
  deleteRecord(userId: string, recordId: string) : Observable<any>{
    const headers = new HttpHeaders({ 'userId': userId});
    return this.httpClient.delete<any>(`${this.baseURL}/records/delete/${recordId}`, { headers }
    );
  }


    //FOR SUMMARY PAGES
    /// 1. COUNT NUMBER OF POOS
    /// 2. COUNT NUMBER OF POOS BY TYPE
    /// 3. COUNT NUMBER OF POOS BY COLOR
    /// 4. COUNT NUMBER OF URGENT POOS
    /// 5. COUNT NUMBER OF SATISFYING POOS
    
    totalPooCounts(userId: string) : Observable<any>{
      const headers = new HttpHeaders({ 'userId': userId});
      return this.httpClient.get<number>(`${this.baseURL}/summary/pooCount`, { headers }
      );
    }

    topPooType(userId: string) : Observable<any>{
      const headers = new HttpHeaders({ 'userId': userId});
      return this.httpClient.get<any>(`${this.baseURL}/summary/topPooType`, { headers }
      );
    }

    topPooColor(userId: string) : Observable<any>{
      const headers = new HttpHeaders({ 'userId': userId});
      return this.httpClient.get<any>(`${this.baseURL}/summary/topPooColor`, { headers }
      );
    }

    urgentPooCount(userId: string) : Observable<any>{
      const headers = new HttpHeaders({ 'userId': userId});
      return this.httpClient.get<number>(`${this.baseURL}/summary/urgentPooCount`, { headers }
      );
    }

    satisfyingPooCount(userId: string) : Observable<any>{
      const headers = new HttpHeaders({ 'userId': userId});
      return this.httpClient.get<number>(`${this.baseURL}/summary/satisfyingPooCount`, { headers }
      );
    }

    latestPoo(userId: string) : Observable<PooRecordViewing>{
      const headers = new HttpHeaders({ 'userId': userId});
      return this.httpClient.get<PooRecordViewing>(`${this.baseURL}/summary/latestPoo`, { headers }
      );
    }

    
}