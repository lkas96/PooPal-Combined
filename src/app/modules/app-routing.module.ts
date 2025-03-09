import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../pages/home/home.component';
import { LoginComponent } from '../pages/login/login.component';
import { AuthGuard } from '../services/auth.guard';
import { TrackerComponent } from '../pages/tracker/tracker.component';
import { HomeSummaryComponent } from '../pages/home-summary/home-summary.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeSummaryComponent, canActivate: [AuthGuard] }, // Protected
  { path: 'tracker', component: TrackerComponent, canActivate: [AuthGuard] }, // Protected
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
