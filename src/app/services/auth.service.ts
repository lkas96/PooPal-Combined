import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { GoogleAuthProvider } from 'firebase/auth';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private afAuth: AngularFireAuth, private router: Router) {}

  async signInWithGoogle() {
    try {
      await this.afAuth.signInWithPopup(new GoogleAuthProvider());
      this.router.navigate(['/home']); // Redirect to the main app summary page after login
    } catch (error) {
      console.error("Google Sign-in Error:", error);
    }
  }

  async signOut() {
    await this.afAuth.signOut();
    this.router.navigate(['/login']);
  }

  getUser() {
    return this.afAuth.authState;
  }

  getUserId() {
    return this.afAuth.currentUser.then((user) => {
      return user?.uid;
    });
  }
}
