import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { FileUploadService } from '../../../services/file-upload.service';

@Component({
  selector: 'app-toilet-review-form',
  standalone: false,
  templateUrl: './toilet-review-form.component.html',
  styleUrl: './toilet-review-form.component.css',
})
export class ToiletReviewFormComponent {

  form!: FormGroup;
  dataUri!: string;
  blob!: Blob;
  userId!: string;
  toiletId!: string;

  cleanlinessOptions = [1, 2, 3, 4, 5];
  smellOptions = [1, 2, 3, 4, 5];

  constructor(private gauth: AuthService, private router: Router, private fus: FileUploadService, private fb: FormBuilder, private route: ActivatedRoute) {}

  ngOnInit(): void {
    //get the userId from authguard first
    this.gauth.getUserId().then(userId => {
      this.userId = userId || '';
    }).catch(error => {
      console.error('Error getting user ID:', error);
    });

    // Get toiletId from route params
    this.toiletId = this.route.snapshot.paramMap.get('id') || '';

    //instantiate the form shit
    this.form = new FormGroup({
      cleanliness: new FormControl(null, Validators.required),
      smell: new FormControl(null, Validators.required),
      recommended: new FormControl(null, Validators.required),
      comments: new FormControl(null),
      file: new FormControl()
    });
  }


  upload() {
    if (this.form.invalid || !this.dataUri) {
      return;
    }

    this.blob = this.dataURItoBlob(this.dataUri);
    const formValue = this.form.value;

    this.fus.upload(formValue, this.blob, this.userId, this.toiletId).subscribe((result) => {
    //redirect to listing review
    //navigate to review page
    this.router.navigate(['/review/details/' + this.toiletId]);
    });
  }

  dataURItoBlob(dataUri: string): Blob {
    const [meta, base64Data] = dataUri.split(',');
    const mimeMatch = meta.match(/:(.*?);/);

    const mimeType = mimeMatch ? mimeMatch[1] : 'application/octet-stream';
    const byteString = atob(base64Data);
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);

    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }

    return new Blob([ia], { type: mimeType });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.dataUri = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  submitReview() {
    this.upload();
  }
}
