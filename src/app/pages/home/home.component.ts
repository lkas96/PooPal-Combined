import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{

  constructor(private authService: AuthService, private router: Router) {}
    
  ngOnInit() {
    this.authService.getUser().subscribe(user => {
      if (user) {
        this.router.navigate(['/tracker/summary']); //redirect if already logged in, cannot see login page. 
      }
    });
  }

  login() {
    this.authService.signInWithGoogle();
  }

  //dynamic content for features
  features = [
    {
      icon: 'insights',
      title: 'Poo Tracker',
      description: 'Log your poo sessions and view or edit your records to stay on top of your gut health.'
    },
    {
      icon: 'bar_chart',
      title: 'Poo Trends',
      description: 'View charts of your bowel movements and download a PDF summary for easy tracking.'
    },
    {
      icon: 'smart_toy',
      title: 'PooPal AI',
      description: 'Get personalized insights and health tips powered by Gemini AI to improve your bowel habits.'
    },
    {
      icon: 'map',
      title: 'Nearby Toilets',
      description: 'Find toilets near you, get directions, and explore user-submitted reviews and ratings.'
    },
    {
      icon: 'rate_review',
      title: 'Toilet Reviews',
      description: 'Add, edit, or delete reviews with cleanliness, smell, and photo ratings of restrooms.'
    },
    {
      icon: 'chat',
      title: 'Live Poop Chatroom',
      description: 'Join live, anonymous chats with others who are pooping right now. Keep it fresh!'
    }
  ];

  //add screenshots mockups later
  mockups = [
    'assets/Mockups/Samsung Galaxy S21 Ultra Screenshot 1.png',
    'assets/Mockups/Samsung Galaxy S21 Ultra Screenshot 5.png',
    'assets/Mockups/mockuuups-holding-smartphone-mockup.jpg',
    'assets/Mockups/Samsung Galaxy S21 Ultra Screenshot 2.png',
    'assets/Mockups/Samsung Galaxy S21 Ultra Screenshot 4.png',
    'assets/Mockups/mockuuups-hand-holding-iphone-bike-mockup.jpg',
    'assets/Mockups/Samsung Galaxy S21 Ultra Screenshot 6.png',
    'assets/Mockups/Samsung Galaxy S21 Ultra Screenshot 3.png',
  ];

  //footer get current year automatically
  currentYear = new Date().getFullYear();
}
