import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private stompClient: Client;

  constructor() {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('https://poopal-server-production.up.railway.app/chat'),
      debug: (str) => console.log(str),
      reconnectDelay: 5000
    });
  
    this.stompClient.onConnect = () => {
      this.stompClient.subscribe('/topic/messages', (message: Message) => {
        const body = JSON.parse(message.body);
        this.messageHandler?.(body);
      });
    };
  
    this.stompClient.activate();
  }
  
  private messageHandler?: (msg: any) => void;
  
  subscribeToMessages(callback: (msg: any) => void) {
    this.messageHandler = callback;
  }

  sendMessage(message: any) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/chat',
        body: JSON.stringify(message)
      });
    } else {
      console.error('WebSocket is not connected. Message not sent.');
    }
  }
}
