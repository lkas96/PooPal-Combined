import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: false
})
export class LoginComponent implements OnInit{
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.authService.getUser().subscribe(user => {
      if (user) {
        this.router.navigate(['/home']); //redirect if already logged in, cannot see login page. 
      }
    });
  }

  login() {
    this.authService.signInWithGoogle();
  }
}
