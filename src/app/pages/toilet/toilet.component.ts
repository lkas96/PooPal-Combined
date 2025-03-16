import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-toilet',
  standalone: false,
  templateUrl: './toilet.component.html',
  styleUrl: './toilet.component.css'
})
export class ToiletComponent {

  selectedTab: number = 0;

  constructor(private router: Router) {
    // Listen to route changes and update selected tab accordingly
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTabSelection(event.urlAfterRedirects);
      }
    });
  }

  updateTabSelection(url: string) {
    if (url.includes('directory')) {
      this.selectedTab = 0;
    } else if (url.includes('nearest')) {
      this.selectedTab = 1;
    } else if (url.includes('reviews')) {
      this.selectedTab = 2;
    }
  }

  onTabChange(index: number) {
    // Navigate to the corresponding route when tab is changed
    const routes = ['directory', 'nearest', 'reviews'];
    this.router.navigate([routes[index]]);
  }
  
  
  navigate(view: string) {
    this.router.navigate([`/toilet/${view}`]);  // Ensure it stays within /tracker
  }
}
