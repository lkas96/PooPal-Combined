import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Toilet } from '../../../models/toilet';
import { ToiletService } from '../../../services/toilet.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-browse-all-toilets',
  standalone: false,
  templateUrl: './browse-all-toilets.component.html',
  styleUrl: './browse-all-toilets.component.css',
})
export class BrowseAllToiletsComponent implements OnInit {

  dataSource = new MatTableDataSource<Toilet>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  expandedToiletId: string | null = null;

  constructor(private toiletSvc: ToiletService, private router: Router, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.getAllToilets();
  }

  getAllToilets() {
    this.toiletSvc.getAllToilets().subscribe((toilets) => {
      this.dataSource.data = toilets;
      
      if (this.paginator) {
        this.dataSource.paginator = this.paginator;
      }
      if (this.sort) {
        this.dataSource.sort = this.sort;
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getEmojiRating(rating: number): string {
    const stars = ['⭐', '⭐⭐', '⭐⭐⭐', '⭐⭐⭐⭐', '⭐⭐⭐⭐⭐'];
    return stars[rating - 1] || '⭐'; // Default to 1 star if undefined
  }

  openGoogleMaps(location: string): void {
    const url = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
      location
    )}`;
    window.open(url, '_blank');
  }

  reviewToilet(toiletId: string) {
    // Navigate to the review form, passing the toiletId as a route parameter
    this.router.navigate(['/review', toiletId]);
  }

  viewToilet(toiletId: string) {
    this.router.navigate(['/review/details/' + toiletId]);
  }

  getGoogleMapUrl(toilet: Toilet): SafeResourceUrl {

    console.log(toilet.placeId);

    const placeId = encodeURIComponent(`${toilet.placeId}`);
    const url = `https://www.google.com/maps/embed/v1/place?key=${environment.MAPS_API}&q=place_id:${placeId}`;

    console.log(url);

    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

}
