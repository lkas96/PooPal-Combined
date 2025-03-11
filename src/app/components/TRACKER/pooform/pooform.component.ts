import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PooRecord } from '../../../models/pooRecord';
import { PooService } from '../../../services/PooService';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-pooform',
  standalone: false,
  templateUrl: './pooform.component.html',
  styleUrl: './pooform.component.css'
})
export class PooformComponent implements OnInit {
  form!: FormGroup;
  pooRecord!: PooRecord;

  userId!: string;

  pooTypes: number[] = [1, 2, 3, 4, 5, 6, 7];

  selectedColor: string = '';
  poopColors = [
    { label: 'Brown', value: 'brown' },
    { label: 'Bright Brown', value: 'bright-brown' },
    { label: 'Yellowish', value: 'yellowish' },
    { label: 'White or Clay-colored', value: 'white-clay' },
    { label: 'Green', value: 'green' },
    { label: 'Bright Red', value: 'bright-red' },
    { label: 'Reddish', value: 'reddish' },
    { label: 'Black or Dark Brown', value: 'black-dark-brown' }
  ];

  constructor(private ps: PooService, private router: Router, private gauth: AuthService) {}

  ngOnInit(): void {

    //get the userId from authguard first
    this.gauth.getUserId().then(userId => {
      this.userId = userId || '';
    }).catch(error => {
      console.error('Error getting user ID:', error);
    });

    //instantiate the form shit
    this.form = new FormGroup({
      pooWhere: new FormControl(null, Validators.required),
      pooType: new FormControl(null, Validators.required),
      pooColor: new FormControl(null, Validators.required),
      painBefore: new FormControl(null, Validators.required),
      painDuring: new FormControl(null, Validators.required),
      painAfter: new FormControl(null, Validators.required),
      urgent: new FormControl(null, Validators.required),
      laxative: new FormControl(null, Validators.required),
      bleeding: new FormControl(null, Validators.required),
      notes: new FormControl('')
    });
  }

  trackByFn(index: number, item: any): number {
    return item;
  }

  submitPoo() {
    console.log('Form Post');

    if (this.form.invalid) {
      console.error('Form is invalid:', this.form.errors);
      return;
    }

    this.pooRecord = this.form.value;

    if (!this.pooRecord) {
      console.error('Error: pooRecord is undefined!');
      return;
    }

    console.log('Submitting Poo Record:', this.pooRecord);

    this.savePooRecord();
  }

  savePooRecord() {
    if (!this.pooRecord) {
      console.error('Cannot save! pooRecord is undefined.');
      return;
    }

    this.ps.savePooRecord(this.userId, this.pooRecord).subscribe(
      (data) => {
        console.log('Poo Record Saved:', data);
        this.router.navigate(['tracker/records']); // Navigate after successful save
      },
      (error) => {
        console.error('Error saving poo record:', error);
      }
    );
  }
}
