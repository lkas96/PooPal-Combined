import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tracker',
  standalone: false,
  templateUrl: './tracker.component.html',
  styleUrl: './tracker.component.css'
})
export class TrackerComponent {
  constructor(private router: Router) {}

  navigate(view: string) {
    this.router.navigate([`/tracker/${view}`]);  // Ensure it stays within /tracker
  }

}
