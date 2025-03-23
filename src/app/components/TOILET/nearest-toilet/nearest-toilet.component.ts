import { Component, OnInit, ViewChild } from '@angular/core';
import { ToiletService } from '../../../services/toilet.service';
import { MatTableDataSource } from '@angular/material/table';
import { NearestToilet, Toilet } from '../../../models/toilet';
import { MatPaginator } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { MatSort } from '@angular/material/sort';

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

  dataSource = new MatTableDataSource<NearestToilet>([]);

  constructor(private ts: ToiletService, private router: Router) {}

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
}
