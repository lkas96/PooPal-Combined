import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-tracker',
  standalone: false,
  templateUrl: './tracker.component.html',
  styleUrl: './tracker.component.css'
})
export class TrackerComponent implements OnInit{

  selectedTab: number = 0;
  
  tabRoutes = [
    { label: 'Summary', route: 'summary' },
    { label: 'Log A Poo', route: 'new' },
    { label: 'My Poo History', route: 'records' }
  ];

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTabSelection(event.urlAfterRedirects);
      }
    });
  }

  ngOnInit() {
    if (this.router.url === '/tracker') {
      this.router.navigate(['/tracker/summary']); // Ensure default redirection
    }
  }

  updateTabSelection(url: string) {
    if (url === '/tracker' || url === '/tracker/summary') {
      this.selectedTab = 0;
    } else if (url === '/tracker/new') {
      this.selectedTab = 1;
    } else if (url === '/tracker/records') {
      this.selectedTab = 2;
    } else {
      // Not a tab-mapped route (e.g. edit mode), don't select a tab
      this.selectedTab = -1;
    }
  }
  onTabChange(index: number) {
    this.router.navigate([`/tracker/${this.tabRoutes[index].route}`]);
  }
}
