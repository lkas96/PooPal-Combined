import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './modules/app-routing.module';
import { AppComponent } from './app.component';
import { AngularMaterialModule } from './modules/material.module';
import { HomeComponent } from './pages/home/home.component';
import { BottomNavComponent } from './components/bottom-nav/bottom-nav.component';
import { LoginComponent } from './pages/login/login.component';
import { TrackerComponent } from './pages/tracker/tracker.component';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getAuth, provideAuth } from '@angular/fire/auth';
import { environment } from '../environments/environment';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireAuthModule } from '@angular/fire/compat/auth';
import { HomeSummaryComponent } from './pages/home-summary/home-summary.component';
import { TopNavComponent } from './components/top-nav/top-nav.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { ChatComponent } from './pages/chat/chat.component';
import { TrendComponent } from './pages/trend/trend.component';
import { ToiletComponent } from './pages/toilet/toilet.component';
import { PooformComponent } from './components/TRACKER/pooform/pooform.component';
import { TrackerSummaryComponent } from './components/TRACKER/summary/summary.component';
import { RecordsComponent } from './components/TRACKER/records/records.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { EditPooformComponent } from './components/TRACKER/editpooform/editpooform.component';
import { DatePipe } from '@angular/common';
import { BrowseAllToiletsComponent } from './components/TOILET/browse-all-toilets/browse-all-toilets.component';
import { NearestToiletComponent } from './components/TOILET/nearest-toilet/nearest-toilet.component';
import { DashboardComponent } from './components/TRENDS/dashboard/dashboard.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BottomNavComponent,
    LoginComponent,
    TrackerComponent,
    HomeSummaryComponent,
    TopNavComponent,
    ChatComponent,
    TrendComponent,
    ToiletComponent,
    PooformComponent,
    TrackerSummaryComponent,
    RecordsComponent, 
    TrendComponent,
    EditPooformComponent,
    BrowseAllToiletsComponent,
    NearestToiletComponent,
    DashboardComponent,    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    ReactiveFormsModule,
    HttpClientModule,
    AngularMaterialModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    NgxChartsModule
  ],
  providers: [
    provideFirebaseApp(() => initializeApp(environment.firebase)),
    provideAuth(() => getAuth()), 
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
