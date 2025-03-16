import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../pages/home/home.component';
import { LoginComponent } from '../pages/login/login.component';
import { AuthGuard } from '../services/auth.guard';
import { TrackerComponent } from '../pages/tracker/tracker.component';
import { HomeSummaryComponent } from '../pages/home-summary/home-summary.component';
import { ChatComponent } from '../pages/chat/chat.component';
import { TrendComponent } from '../pages/trend/trend.component';
import { ToiletComponent } from '../pages/toilet/toilet.component';
import { PooformComponent } from '../components/TRACKER/pooform/pooform.component';
import { TrackerSummaryComponent } from '../components/TRACKER/summary/summary.component';
import { RecordsComponent } from '../components/TRACKER/records/records.component';
import { EditPooformComponent } from '../components/TRACKER/editpooform/editpooform.component';
import { BrowseAllToiletsComponent } from '../components/TOILET/browse-all-toilets/browse-all-toilets.component';
import { NearestToiletComponent } from '../components/TOILET/nearest-toilet/nearest-toilet.component';
import { DashboardComponent } from '../components/TRENDS/dashboard/dashboard.component';
import { AiComponent } from '../pages/ai/ai.component';
import { PoopalAiComponent } from '../components/AI/poopal-ai/poopal-ai.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeSummaryComponent, canActivate: [AuthGuard] }, //authenticated home page on login
  {
    path: 'tracker', component: TrackerComponent, canActivate: [AuthGuard],
    children: [ 
                { path: '', redirectTo: 'summary', pathMatch: 'full' }, 
                { path: 'summary', component: TrackerSummaryComponent}, 
                { path: 'new',component: PooformComponent},
                { path: 'records',component: RecordsComponent},
                { path: 'records/edit/:id',component: EditPooformComponent}
              ],
  },
  {
    path: 'trends', component: TrendComponent, canActivate: [AuthGuard],
    children: [
                { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
                { path: 'dashboard', component: DashboardComponent },
              ],
  },
  {    path: 'ai', component: AiComponent, canActivate: [AuthGuard],
    children: [
                { path: '', redirectTo: 'chat', pathMatch: 'full' },
                { path: 'chat', component: PoopalAiComponent },
              ],
  },
  {
    path: 'toilets', component: ToiletComponent, canActivate: [AuthGuard],
    children: [
                { path: '', redirectTo: 'directory', pathMatch: 'full' },
                { path: 'directory', component: BrowseAllToiletsComponent },
                { path: 'nearest', component: NearestToiletComponent },
                // { path: 'reviews', component: ToiletReviewsComponent },
              ],
  },
  {
    path: 'chat', component: ChatComponent, canActivate: [AuthGuard],
    children: [],
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
