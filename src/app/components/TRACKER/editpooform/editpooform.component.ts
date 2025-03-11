import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PooRecordViewing } from '../../../models/pooRecordViewing';
import { PooService } from '../../../services/PooService';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-editpooform',
  templateUrl: './editpooform.component.html',
  styleUrls: ['./editpooform.component.css'],
  standalone: false,
})
export class EditPooformComponent implements OnInit {
  form!: FormGroup;
  retrievedRecord!: PooRecordViewing | null;
  userId!: string;
  recordId!: string;

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
    { label: 'Black or Dark Brown', value: 'Black or Dark Brown' },
  ];

  painLevels = [
    { value: 'Unbearable', label: 'Unbearable' },
    { value: 'Severe', label: 'Severe' },
    { value: 'Moderate', label: 'Moderate' },
    { value: 'Mild', label: 'Mild' },
    { value: 'None', label: 'None' },
  ];

  constructor(
    private ps: PooService,
    private router: Router,
    private gauth: AuthService,
    private activeRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef // Inject ChangeDetectorRef
  ) {
    // Create an empty form initially
    this.form = new FormGroup({
      timestamp: new FormControl('', Validators.required),
      pooWhere: new FormControl('', Validators.required),
      pooType: new FormControl('', Validators.required),
      pooColor: new FormControl('', Validators.required),
      painBefore: new FormControl('', Validators.required),
      painDuring: new FormControl('', Validators.required),
      painAfter: new FormControl('', Validators.required),
      urgent: new FormControl('', Validators.required),
      laxative: new FormControl('', Validators.required),
      bleeding: new FormControl('', Validators.required),
      notes: new FormControl(''),
    });
  }

  ngOnInit(): void {
    // First, retrieve the user ID
    this.gauth.getUserId().then((userId) => {
      this.userId = userId || '';

      // Retrieve record ID from route params
      this.activeRoute.params.subscribe((params) => {
        this.recordId = params['id'];

        //confirm userid and recordid
        //okay passed printing correctly
        console.log('User ID:', this.userId);
        console.log('Record ID:', this.recordId);

        // Fetch the record only after user ID is available
        this.ps.getRecord(this.userId, this.recordId).subscribe(
          (record) => {
            this.retrievedRecord = record;
            console.log('Retrieved Record:', this.retrievedRecord);
            this.populateForm();
          },
          (error) => {
            console.error('Error retrieving record:', error);
          }
        );
      });
    });
  }

  // Populate the form with retrieved record values
  populateForm() {
    if (!this.retrievedRecord || !this.retrievedRecord.timestamp) {
      console.warn("No record found or missing timestamp, skipping form population.");
      return;
    }
  
    console.log("Populating form with record:", this.retrievedRecord);
  
    const timestampStr = this.retrievedRecord.timestamp;
    let timestampDate = new Date(timestampStr);
    
    // Add 8 hours (UTC+8)
    timestampDate.setHours(timestampDate.getHours() + 8);
    
    this.cdr.detectChanges();
    
    // Convert timestamp to input format: 'YYYY-MM-DDTHH:MM'
    const formattedTimestamp = timestampDate.toISOString().slice(0, 16);
    
    console.log("Formatted Timestamp with UTC+8:", formattedTimestamp);
  
    // Update form with retrieved values
    this.form.patchValue({
      timestamp: formattedTimestamp,
      pooWhere: this.retrievedRecord.pooWhere || '',
      pooType: this.retrievedRecord.pooType || '',
      pooColor: this.retrievedRecord.pooColor || '',
      painBefore: this.retrievedRecord.painBefore || '',
      painDuring: this.retrievedRecord.painDuring || '',
      painAfter: this.retrievedRecord.painAfter || '',
      urgent: this.retrievedRecord.urgent || '',
      laxative: this.retrievedRecord.laxative || '',
      bleeding: this.retrievedRecord.bleeding || '',
      notes: this.retrievedRecord.notes || '',
    });
  
    console.log("Form populated with:", this.form.value);
  }
  

  get timestampControl(): FormControl | null {
    return this.form.get('timestamp') as FormControl;
  }

  submitPoo() {
    console.log('Form Post');

    if (this.form.invalid) {
      console.error('Form is invalid:', this.form.errors);
      return;
    }

    const updatedRecord: PooRecordViewing = {
      ...this.retrievedRecord!,
      ...this.form.value,
    };

    console.log('Updating Poo Record:', updatedRecord);
    this.updatePooRecord(updatedRecord);
  }

  updatePooRecord(updatedRecord: PooRecordViewing) {
    if (!updatedRecord) {
      console.error('Cannot save! PooRecord is undefined.');
      return;
    }

    this.ps.updatePooRecord(this.userId, updatedRecord).subscribe(
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
