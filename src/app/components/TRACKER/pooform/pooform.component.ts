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

  selectedColor: string = '';

  pooTypes = [
    {label: 'Type 1', value: 'Type 1'},
    {label: 'Type 2', value: 'Type 2'},
    {label: 'Type 3', value: 'Type 3'},
    {label: 'Type 4', value: 'Type 4'},
    {label: 'Type 5', value: 'Type 5'},
    {label: 'Type 6', value: 'Type 6'},
    {label: 'Type 7', value: 'Type 7'}
  ];

  poopColors = [
    { label: 'Brown', value: 'Brown' },
    { label: 'Bright Brown', value: 'Bright Brown' },
    { label: 'Yellowish', value: 'Yellowish' },
    { label: 'White or Clay Colored', value: 'White Clay Colored' },
    { label: 'Green', value: 'Green' },
    { label: 'Bright Red', value: 'Bright Red' },
    { label: 'Reddish', value: 'Reddish' },
    { label: 'Black or Dark Brown', value: 'Black or Dark Brown' }
  ];

  painLevels = [
    { value: 'Unbearable', label: 'Unbearable' },
    { value: 'Severe', label : 'Severe' },
    { value: 'Moderate', label: 'Moderate' },
    { value: 'Mild', label: 'Mild' },
    { value: 'None', label: 'None' }
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
