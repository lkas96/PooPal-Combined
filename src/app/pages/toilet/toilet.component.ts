import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-toilet',
  standalone: false,
  templateUrl: './toilet.component.html',
  styleUrl: './toilet.component.css'
})
export class ToiletComponent {

  constructor(private router: Router) {}
  
  navigate(view: string) {
    this.router.navigate([`/toilet/${view}`]);  // Ensure it stays within /tracker
  }
}
