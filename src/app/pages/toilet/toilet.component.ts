import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-toilet',
  standalone: false,
  templateUrl: './toilet.component.html',
  styleUrl: './toilet.component.css'
})
export class ToiletComponent implements OnInit {

  selectedTab: number = 0;

  tabRoutes = [
    { label: 'Toilet Directory', route: 'directory' },
    { label: 'Nearest Toilet', route: 'nearest' },
    { label: 'Public Reviews', route: 'reviews' }
  ];

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTabSelection(event.urlAfterRedirects);
      }
    });
  }

  ngOnInit() {
    if (this.router.url === '/toilets') {
      this.router.navigate(['/toilets/directory']); // Ensure default redirection
    }
  }

  updateTabSelection(url: string) {
    if (url.includes('/toilets') || url.includes('/toilets/directory')) {
      this.selectedTab = 0;
    } else if (url.includes('/toilets/nearest')) {
      this.selectedTab = 1;
    } else if (url.includes('/toilets/reviews')) {
      this.selectedTab = 2;
    }
  }

  onTabChange(index: number) {
    this.router.navigate([`/toilets/${this.tabRoutes[index].route}`]);
  }
  
}
