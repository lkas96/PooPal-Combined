import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToiletService } from '../../../services/toilet.service';
import { Toilet } from '../../../models/toilet';
import { Review } from '../../../models/review';

@Component({
  selector: 'app-review-details',
  standalone: false,
  templateUrl: './review-details.component.html',
  styleUrl: './review-details.component.css'
})
export class ReviewDetailsComponent implements OnInit {
  
  constructor(private gauth: AuthService, private router: Router, private route: ActivatedRoute, private ts: ToiletService) { }

  toiletId!: string;
  toilet!: Toilet;
  reviews: Review[] = [];

  ngOnInit() {
    this.toiletId = this.route.snapshot.params['id'];

    //get all reviews for this toilet
    this.ts.getToiletReviews(this.toiletId).subscribe((data) => {
      this.reviews = data;
    });

    //get the details of the toilet
    this.ts.getToiletDetails(this.toiletId).subscribe((toilet) => {
      this.toilet = toilet;
    });

  }
  
  getEmojiRating(rating: number): string {
    const stars = ['1⭐', '2⭐', '3⭐', '4⭐', '5⭐'];
    return stars[rating - 1] || '⭐';
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
}
