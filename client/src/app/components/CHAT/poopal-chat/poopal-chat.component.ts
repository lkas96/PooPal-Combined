import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../../../services/web.socket.service';

@Component({
  selector: 'app-poopal-chat',
  standalone: false,
  templateUrl: './poopal-chat.component.html',
  styleUrl: './poopal-chat.component.css'
})
export class PoopalChatComponent implements OnInit {
  
  messages: any[] = [];
  messageText!: string;
  sender!: string;

  constructor(private wss: WebSocketService) {
    this.generateRandomName();
    this.setViewportHeight();
  }

  generateRandomName() {
    const part1 = ['Squishy', 'Tooty', 'Stinky', 'Sloppy', 'Bubbly'];
    const part2 = ['Poop', 'Toot', 'Log', 'Poozilla', 'Turd'];
    this.sender = `${this.pick(part1)}${this.pick(part2)}${Math.floor(Math.random() * 1000)}`;
  }

  pick(arr: string[]) {
    return arr[Math.floor(Math.random() * arr.length)];
  }

  ngOnInit() {
    this.wss.subscribeToMessages((msg) => {
      this.messages.push(msg);
    });
  }

  send() {
    const msg = {
      sender: this.sender,
      message: this.messageText,
      timestamp: Date.now()
    };
    this.wss.sendMessage(msg);
    this.messageText = '';
  }

  setViewportHeight = () => {
    const vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty('--vh', `${vh}px`);
  }
}
