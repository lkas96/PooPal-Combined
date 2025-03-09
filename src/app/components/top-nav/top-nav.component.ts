import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-top-nav',
  templateUrl: './top-nav.component.html',
  styleUrls: ['./top-nav.component.scss'], 
  standalone: false
})
export class TopNavComponent {
  user$: Observable<any>;

  constructor(private authService: AuthService) {
    this.user$ = this.authService.getUser();
  }

  logout() {
    this.authService.signOut();
  }
}