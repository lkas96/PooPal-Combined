import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../../services/web.socket.service';

@Component({
  selector: 'app-chat',
  standalone: false,
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit {
  
  messages: any[] = [];
  messageText!: string;
  sender!: string;

  constructor(private wss: WebSocketService) {
    this.generateRandomName();
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
      content: this.messageText,
      timestamp: Date.now()
    };
    this.wss.sendMessage(msg);
    this.messageText = '';
  }
}
