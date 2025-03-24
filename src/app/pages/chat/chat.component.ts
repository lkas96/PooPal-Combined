import { Component, OnInit } from '@angular/core';
import { JedisChatService } from '../../services/jedis.chat.service';

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

  constructor(private jcs: JedisChatService) {
    //load username first
    //usla is pull from gauth but no
    //wait random chat better anon
    this.generateRandomName();
  }
  
  generateRandomName() {
    //generate random poopy themed names
    const part1 = [
      'Squishy', 'Tooty', 'Stinky', 'Sloppy', 'Bubbly',
      'Gassy', 'Soggy', 'Crusty', 'Lumpy', 'Sneaky'
    ];
  
    const part2 = [
      'Poop', 'Dumper', 'Toot', 'Log', 'Nugget',
      'Poozilla', 'BootyBlast', 'FlushMaster', 'Stinker', 'Turd'
    ];
  
    const randomFront = part1[Math.floor(Math.random() * part1.length)];
    const randomBack = part2[Math.floor(Math.random() * part2.length)];
    const randomNumber = Math.floor(Math.random() * 1000);
    
    //set random gen mame
    this.sender = `${randomFront}${randomBack}${randomNumber}`;
  }

  ngOnInit() {
    this.loadMessages();
    setInterval(() => this.loadMessages(), 1000); // polling, pulling new messages ever 3 second
  }

  loadMessages() {
    this.jcs.getMessages().subscribe(data => this.messages = data);
  }

  send() {
    const msg = {
      sender: this.sender,
      content: this.messageText,
      timestamp: Date.now()
    };
    this.jcs.sendMessage(msg).subscribe(() => {
      this.messageText = '';
      this.loadMessages();
    });
  }
}
