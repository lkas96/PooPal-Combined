import { Component, OnInit, ViewChild } from '@angular/core';
import { PooService } from '../../../services/poo.service';
import { AuthService } from '../../../services/auth.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { PooRecordViewing } from '../../../models/pooRecordViewing';
import { Router } from '@angular/router';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css'],
  standalone: false
})
export class RecordsComponent implements OnInit {

  userId!: string;
  records: PooRecordViewing[] = [];
  dataSource!: MatTableDataSource<PooRecordViewing>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private ps: PooService, private gauth: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.gauth.getUserId().then((userId) => {
      this.userId = userId || '';

      this.ps.getRecords(this.userId).subscribe(
        (records) => {
          this.records = records.map((record) => ({
            ...record,
            timestampRaw: record.timestamp,
            timestamp: this.formatDate(record.timestamp)
          }));

          this.dataSource = new MatTableDataSource(this.records);

          if (this.paginator) {
            this.dataSource.paginator = this.paginator;
          }
          if (this.sort) {
            this.dataSource.sort = this.sort;
          }
        },
        (error) => {
          console.error('Error getting records:', error);
        }
      );
    });
  }

  private formatDate(timestamp: string): string {
    const date = new Date(timestamp);
    return isNaN(date.getTime()) ? 'Invalid Date' : date.toLocaleString();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value
      .trim()
      .toLowerCase();
    this.dataSource.filter = filterValue;
  }

  editRecord(recordId: number) {
    this.router.navigate(['/tracker/records/edit', recordId]);
  }
  
  deleteRecord(record: number){
    const record2 = record.toString();

    this.ps.deleteRecord(this.userId, record2).subscribe(
      (data) => {
        console.log('Record Deleted:', data);
        this.router.navigate(['tracker/records']).then(() => {window.location.reload();});
        //force update
      },
      (error) => {
        console.error('Error deleting record:', error);
      }
    );
  }
}
