import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PooPalAiService } from '../../../services/poopal.ai.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-poopal-ai',
  standalone: false,
  templateUrl: './poopal-ai.component.html',
  styleUrl: './poopal-ai.component.css'
})

export class PoopalAiComponent implements OnInit {
  messages: { sender: string, senderName: string, text: string }[] = [];
  userInput: string = '';
  loading: boolean = false;
  userId!: string;
  userName!: string;
  botName: string = 'PooPal AI';

  constructor(private httpclient: HttpClient, private pas: PooPalAiService, private gauth: AuthService) {}

  ngOnInit(): void {
    this.gauth.getUserId().then(userId => {
      this.userId = userId || '';
    }).catch(error => {
      console.error('Error getting user ID:', error);
    });

    this.gauth.getUser().subscribe(user => {
      this.userName = user?.displayName || '';
    });
  }

  sendMessage() {
    if (!this.userInput.trim()) return;
  
    this.messages.push({ sender: 'user', senderName: this.userName, text: this.userInput });
    this.loading = true;
  
    this.pas.getResponse(this.userInput, this.userId).subscribe(
      (response) => {
        this.messages.push({ sender: 'bot', senderName: this.botName, text: response.response });
        this.loading = false;
      },
      (error) => {
        console.error('Chat API error:', error);
        this.messages.push({ sender: 'bot', senderName: this.botName, text: "Oops! Something went wrong. Try again later." });
        this.loading = false;
      }
    );
  
    this.userInput = '';
  }
  
}
