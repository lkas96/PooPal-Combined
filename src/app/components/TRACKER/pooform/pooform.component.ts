import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { PooRecord } from '../../../models/pooRecord';
import { PooService } from '../../../services/PooService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pooform',
  standalone: false,
  templateUrl: './pooform.component.html',
  styleUrl: './pooform.component.css'
})

export class PooformComponent implements OnInit {

  form!: FormGroup;
  pooRecord!: PooRecord;

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

  constructor(private ps: PooService, private router: Router) { }
  
  ngOnInit(): void {
    this.form = new FormGroup({
      isPublic: new FormControl(''),
      pooType: new FormControl(''),
      pooColor: new FormControl(''),
      painBefore: new FormControl(''),
      painDuring: new FormControl(''),
      painAfter: new FormControl(''),
      urgent: new FormControl(''),
      laxative: new FormControl(''),
      bleeding: new FormControl(''),
      notes: new FormControl('')

    });
  }
  
  submitPoo() {
    console.log('Form Post');

    this.pooRecord = this.form.value;
    console.log(this.pooRecord);

    this.savePooRecord();

    //redirect to this url endpoint
    //so after create and submit form, will show all the records
    this.router.navigate(['poo/list']);
  }

  savePooRecord() {
    this.ps.savePooRecord(this.pooRecord).subscribe(
      (data) => {
        console.log('Poo Record Saved');
      });
  }

  

}
