import { Component, HostListener, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'poopal-client';
  
  user$: Observable<any>;

  constructor(private authService: AuthService) {
    this.user$ = this.authService.getUser();
  }

  logout() {
    this.authService.signOut();
  }

  isMobile: boolean = false;

  ngOnInit() {
    this.checkDevice();
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.checkDevice();
  }

  checkDevice() {
    const isTouchDevice = navigator.maxTouchPoints > 0 || 'ontouchstart' in window;
    this.isMobile = isTouchDevice && window.innerWidth < 992; // Show bottom navbar only on mobile
  }
}