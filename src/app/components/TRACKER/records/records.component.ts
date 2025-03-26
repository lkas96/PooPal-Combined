import { Component, OnInit, ViewChild } from '@angular/core';
import { PooService } from '../../../services/poo.service';
import { AuthService } from '../../../services/auth.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { PooRecordViewing } from '../../../models/pooRecordViewing';
import { Router } from '@angular/router';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css'],
  standalone: false,
})
export class RecordsComponent implements OnInit {
  userId!: string;
  records: PooRecordViewing[] = [];
  dataSource!: MatTableDataSource<PooRecordViewing>;
  userName!: string;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private ps: PooService,
    private gauth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.gauth.getUser().subscribe((user) => {
      this.userName = user?.displayName || '';
    });

    this.gauth.getUserId().then((userId) => {
      this.userId = userId || '';

      this.ps.getRecords(this.userId).subscribe(
        (records) => {
          this.records = records.map((record) => ({
            ...record,
            timestampRaw: record.timestamp,
            timestamp: this.formatDate(record.timestamp),
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

    if (isNaN(date.getTime())) return 'Invalid Date';

    // Add 8 hours (8 * 60 * 60 * 1000 milliseconds)
    const adjustedDate = new Date(date.getTime() + 8 * 60 * 60 * 1000);

    return adjustedDate.toLocaleString('en-SG', {
      hour12: true,
    });
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

  deleteRecord(record: number) {
    const record2 = record.toString();

    this.ps.deleteRecord(this.userId, record2).subscribe(
      (data) => {
        console.log('Record Deleted:', data);
        this.router.navigate(['tracker/records']).then(() => {
          window.location.reload();
        });
        //force update
      },
      (error) => {
        console.error('Error deleting record:', error);
      }
    );
  }

  downloadPDF() {
    const pdf = new jsPDF('p', 'mm', 'a4');
    const records = this.dataSource.filteredData;
    const logo = new Image();
    logo.src = 'assets/images/logo-rounded.png';
  
    logo.onload = () => {
      let y = 10;
      let recordCount = 0;
  
      const addHeader = () => {
        pdf.addImage(logo, 'PNG', 14, y, 20, 20);
        pdf.setFontSize(16);
        pdf.text(`${this.userName}'s PooPal Records`, 40, y + 10);
        y += 25;
      };
  
      addHeader();
  
      records.forEach((r) => {
        if (recordCount > 0 && recordCount % 3 === 0) {
          pdf.addPage();
          y = 10;
          addHeader();
        }
  
        // time where good
        autoTable(pdf, {
          startY: y,
          head: [['Timestamp', 'Where Did You Poo', 'Poo Experience']],
          body: [[r.timestamp, r.pooWhere === 'home' ? 'Home' : 'Outside', r.satisfactionLevel || 'Unknown']],
          styles: { fontSize: 9, halign: 'left' },
          columnStyles: {
            0: { cellWidth: 60.6, halign: 'left' },
            1: { cellWidth: 60.6, halign: 'left' },
            2: { cellWidth: 60.6, halign: 'left' },
          },
          tableWidth: 'wrap',
          margin: { left: 14, right: 14 },
        });
        y = (pdf as any).lastAutoTable.finalY;
  
        // type color urngen
        autoTable(pdf, {
          startY: y,
          head: [['Bristol Poo Type', 'Poo Color', 'Urgent']],
          body: [[
            r.pooType,
            r.pooColor,
            r.urgent === 'yes' ? 'Urgent' : 'Not Urgent',
          ]],
          styles: { fontSize: 9, halign: 'left' },
          columnStyles: {
            0: { cellWidth: 60.6, halign: 'left' },
            1: { cellWidth: 60.6, halign: 'left' },
            2: { cellWidth: 60.6, halign: 'left' },
          },
          tableWidth: 'wrap',
          margin: { left: 14, right: 14 },
        });
        y = (pdf as any).lastAutoTable.finalY;
  
        // pain levels
        autoTable(pdf, {
          startY: y,
          head: [['Pain Before', 'Pain During', 'Pain After']],
          body: [[r.painBefore, r.painDuring, r.painAfter]],
          styles: { fontSize: 9, halign: 'left' },
          columnStyles: {
            0: { cellWidth: 60.6, halign: 'left' },
            1: { cellWidth: 60.6, halign: 'left' },
            2: { cellWidth: 60.6, halign: 'left' },
          },
          tableWidth: 'wrap',
          margin: { left: 14, right: 14 },
        });
        y = (pdf as any).lastAutoTable.finalY;
  
        // 2 stuff
        autoTable(pdf, {
          startY: y,
          head: [['Laxative Used', 'Bleeding']],
          body: [[r.laxative ? 'Yes' : 'No', r.bleeding ? 'Yes' : 'No']],
          styles: { fontSize: 9, halign: 'left' },
          columnStyles: {
            0: { halign: 'left' },
            1: { halign: 'left' },
          },
          tableWidth: 'auto',
          margin: { left: 14, right: 14 },
        });
        y = (pdf as any).lastAutoTable.finalY;
  
        // notes comments
        const notes = r.notes?.trim() || 'No comments';
        autoTable(pdf, {
          startY: y,
          head: [['Notes & Comments']],
          body: [[notes]],
          styles: { fontSize: 9 },
          margin: { left: 14, right: 14 },
        });
  
        y = (pdf as any).lastAutoTable.finalY + 10;
        recordCount++;
      });
  
      //export name dyanmic
      pdf.save(`${this.userName}-poo-records.pdf`);
    };
  }
  
}
