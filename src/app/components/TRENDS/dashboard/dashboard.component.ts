import { Component, OnInit } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { PooService } from '../../../services/poo.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  bowelTrends = [
    { name: 'Type 3', value: 2 },
    { name: 'Type 4', value: 1 } //value is the count, take from database then assign back. 
  ];

  colorScheme = {
    name: 'cool',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  pooColorScheme = {
    name: 'cool',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  constructor(private ps: PooService, private router: Router, private gauth: AuthService) {}

  userId!: string;

  ngOnInit(): void {
    this.gauth.getUserId().then(userId => {
      this.userId = userId || '';
      if (this.userId) {
        this.getTrends();
      }
    }).catch(error => {
      console.error('Error getting user ID:', error);
    });
  }

  pooWhere: any[] = [];
  pooType: any[] = [];
  pooColor: any[] = [];
  pooPainBefore: any[] = [];
  pooPainDuring: any[] = [];
  pooPainAfter: any[] = [];
  pooUrgency: any[] = [];
  pooLaxative: any[] = [];
  pooBleeding: any[] = [];
  pooSatisfaction: any[] = [];



  async getTrends() {

    this.ps.pooWhereTrends(this.userId).subscribe((data: any) => {
      this.pooWhere = Object.keys(data).map(key => ({
        name: key,  // Assign key as name
        value: data[key] // Assign corresponding value
      }));
    });

    const orderedTypes = ['Type 1', 'Type 2', 'Type 3', 'Type 4', 'Type 5', 'Type 6', 'Type 7'];
    this.ps.pooTypeTrends(this.userId).subscribe((data: any) => {
      this.pooType = orderedTypes.map(type => ({
        name: type,
        value: data[type] || 0 // default to 0 if missing
      }));
    });
       
    this.ps.pooTypeTrends(this.userId).subscribe((data: any) => {
      this.pooType = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.pooColorTrends(this.userId).subscribe((data: any) => {
      this.pooColor = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.painLevelBeforeTrends(this.userId).subscribe((data: any) => {
      this.pooPainBefore = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.painLevelDuringTrends(this.userId).subscribe((data: any) => {
      this.pooPainDuring = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.painLevelAfterTrends(this.userId).subscribe((data: any) => {
      this.pooPainAfter = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.urgentPooTrends(this.userId).subscribe((data: any) => {
      this.pooUrgency = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.laxativePooTrends(this.userId).subscribe((data: any) => {
      this.pooLaxative = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.bleedingPooTrends(this.userId).subscribe((data: any) => {
      this.pooBleeding = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

    this.ps.satisfactionLevelTrends(this.userId).subscribe((data: any) => {
      this.pooSatisfaction = Object.keys(data).map(key => ({
        name: key,  // Assign key as name 
        value: data[key] // Assign corresponding value
      }));
    });

  }



}
