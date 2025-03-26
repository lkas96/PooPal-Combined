import { Component, OnInit, ViewChild } from '@angular/core';
import { ToiletService } from '../../../services/toilet.service';
import { MatTableDataSource } from '@angular/material/table';
import { NearestToilet, Toilet } from '../../../models/toilet';
import { MatPaginator } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { MatSort } from '@angular/material/sort';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-nearest-toilet',
  standalone: false,
  templateUrl: './nearest-toilet.component.html',
  styleUrl: './nearest-toilet.component.css',
})
export class NearestToiletComponent implements OnInit {
  latitude!: number;
  longitude!: number;

  currentLocation!: string;
  expandedToiletId: string | null = null;

  dataSource = new MatTableDataSource<NearestToilet>([]);

  constructor(private ts: ToiletService, private router: Router, private sanitizer: DomSanitizer) {}

  //always get location on component load
  ngOnInit(): void {
    this.getUserLocation();
  }

  //send the lat lon to server
  getNearestToilets(latitude: number, longitude: number) {
    this.ts.getNearestToilets(latitude, longitude).subscribe((toilets) => {
      this.dataSource.data = toilets;
    });
  }

  //use build in browse geolocation feature
  getUserLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.latitude = position.coords.latitude;
          this.longitude = position.coords.longitude;

          //once get the lon lat
          //throw the lat long to backend server
          //places/maps API get name of place and send back
          this.getNearestToilets(this.latitude, this.longitude);

        },
        (error) => {
          console.error('Error getting location:', error);
        }
      );
    } else {
      console.error('Geolocation is not available on this browser.');
    }
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
    const url = `https://www.google.com/maps/embed/v1/directions?key=${environment.MAPS_API}&destination=place_id:${placeId}&origin=${this.latitude},${this.longitude}&mode=transit`;

    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }  
}
