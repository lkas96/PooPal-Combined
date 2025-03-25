import { Component, OnInit } from '@angular/core';
import { PooService } from '../../../services/poo.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { PooRecordViewing } from '../../../models/pooRecordViewing';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-summary',
  standalone: false,
  templateUrl: './summary.component.html',
  styleUrl: './summary.component.css'
})
export class TrackerSummaryComponent implements OnInit {
  
  constructor(private ps: PooService, private router: Router, private gauth: AuthService) {}

  userId!: string;
  topPooType: string = '';
  topPooColor: string = '';
  pooCount: number = 0;
  urgentCountYes: number = 0;
  urgentCountNo: number = 0;
  satisfyingCountGood: number = 0;
  satisfyingCountMid: number = 0;
  satisfyingCountBad: number = 0;

  latestPooArray: PooRecordViewing[] = [];
  dataSource: MatTableDataSource<PooRecordViewing> = new MatTableDataSource<PooRecordViewing>([]);
  
  ngOnInit(): void {
    this.gauth.getUserId().then(userId => {
      this.userId = userId || '';
      if (this.userId) {
        this.getPooData();
      }
    }).catch(error => {
      console.error('Error getting user ID:', error);
    });
  }

  async getPooData() {
    this.ps.totalPooCounts(this.userId).subscribe((data: any) => {
      this.pooCount = data.totalPoos;
    });

    this.ps.topPooType(this.userId).subscribe((data: any) => {
      this.topPooType = data.topPooType;
    });

    this.ps.topPooColor(this.userId).subscribe((data: any) => {
      this.topPooColor = data.topPooColor;
    });

    this.ps.urgentPooCount(this.userId).subscribe((data: any) => {
      this.urgentCountYes = data.totalUrgentYes;
      this.urgentCountNo = data.totalUrgentNo;
    });

    this.ps.satisfyingPooCount(this.userId).subscribe((data: any) => {
      this.satisfyingCountGood = data.totalGood;
      this.satisfyingCountMid = data.totalMid;
      this.satisfyingCountBad = data.totalBad;
    });

    this.ps.latestPoo(this.userId).subscribe((data: any) => {
      this.latestPooArray = data.map((record: any) => ({
        ...record,
        timestampRaw: record.timestamp,
        timestamp: this.formatDate(record.timestamp)
      }));
      this.dataSource = new MatTableDataSource<PooRecordViewing>(this.latestPooArray);
    }, error => {
      console.error('Error getting latest poo:', error);
    });
  }

  private formatDate(timestamp: string): string {
    const date = new Date(timestamp);
  
    if (isNaN(date.getTime())) return 'Invalid Date';
  
    // Add 8 hours (8 * 60 * 60 * 1000 milliseconds)
    const adjustedDate = new Date(date.getTime() + 8 * 60 * 60 * 1000);
  
    return adjustedDate.toLocaleString('en-SG', {
      hour12: true,
    });
  }

  editRecord(recordId: number) {
    this.router.navigate(['/tracker/records/edit', recordId]);
  }

  deleteRecord(record: number) {
    const record2 = record.toString();
    this.ps.deleteRecord(this.userId, record2).subscribe(
      (data) => {
        console.log('Record Deleted:', data);
        this.getPooData();
      },
      (error) => {
        console.error('Error deleting record:', error);
      }
    );
  }
}
