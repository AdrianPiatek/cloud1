import {inject, Injectable} from '@angular/core';
import {RxStompService} from "./rx-stomp.service";
import {Message} from "@stomp/stompjs";
@Injectable({
  providedIn: 'root'
})
export class GameService {

  private stompService = inject(RxStompService)

  ping(): void{
    this.stompService.watch(`/topic/1`).subscribe((msg: Message) => console.log(msg.body))
    this.stompService.publish({
      destination: `/app/game/1/ping`
    })
  }
}
