import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { PooService } from '../../../services/PooService';
import { AuthService } from '../../../services/auth.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { PooRecordViewing } from '../../../models/pooRecordViewing';
import { Router } from '@angular/router';

@Component({
  selector: 'app-records',
  standalone: false,
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css'],
})
export class RecordsComponent implements OnInit {
deleteRecord(arg0: number) {
throw new Error('Method not implemented.');
}
editRecord(_t17: PooRecordViewing) {
throw new Error('Method not implemented.');
}
  userId!: string;
  records: PooRecordViewing[] = [];
  dataSource!: MatTableDataSource<PooRecordViewing>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private ps: PooService, private gauth: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.gauth.getUserId()
      .then((userId) => {
        this.userId = userId || '';
  
        this.ps.getRecords(this.userId).subscribe(
          (records) => {
            this.records = records.map((record) => ({
              ...record,
              timestamp: this.convertToDate(record.timestamp) // Ensure timestamp is a Date object
            }));
  
            // Assign records to MatTableDataSource
            this.dataSource = new MatTableDataSource(this.records);
  
            // Set paginator & sorting after view initialization
            setTimeout(() => {
              if (this.paginator) {
                this.dataSource.paginator = this.paginator;
              }
              if (this.sort) {
                this.dataSource.sort = this.sort;
              }
            });
  
          },
          (error) => {
            console.error('Error getting records:', error);
          }
        );
      })
      .catch((error) => {
        console.error('Error getting user ID:', error);
      });
  }
  
  

  private convertToDate(timestamp: any): Date {
    if (!timestamp) return new Date(); // Fallback for undefined/null
  
    if (Array.isArray(timestamp)) {
      return new Date(
        timestamp[0], // Year
        timestamp[1] - 1, // Month (0-based)
        timestamp[2], // Day
        timestamp[3] || 0, // Hour
        timestamp[4] || 0, // Minute
        timestamp[5] || 0  // Second
      );
    } else if (typeof timestamp === 'string') {
      const date = new Date(timestamp);
      return isNaN(date.getTime()) ? new Date() : date; // Fallback if invalid
    } else if (timestamp instanceof Date) {
      return timestamp;
    } else {
      return new Date(); // Default fallback
    }
  }

  //for front end helper
  getFormattedDate(timestamp: any): string {
    if (!timestamp) return 'Invalid Date';
    const date = this.convertToDate(timestamp);
    return date instanceof Date && !isNaN(date.getTime()) ? date.toLocaleString() : 'Invalid Date';
  }
  
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  viewDetails(record: PooRecordViewing) {
    this.router.navigate(['/record-details', record.id]);
  }
}
