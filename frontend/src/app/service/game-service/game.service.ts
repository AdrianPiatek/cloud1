import {computed, inject, Injectable, signal, Signal, WritableSignal} from '@angular/core';
import {RxStompService} from "../rx-stomp-service/rx-stomp.service";
import {Message} from "@stomp/stompjs";
import {GameState} from "../../entity/game-state";
import {Subscription} from "rxjs";
import {Result} from "../../entity/result";
@Injectable({
  providedIn: 'root'
})
export class GameService {

  private stompService = inject(RxStompService)
  private subscriptions: Subscription[] = []
  private gameStateMessage: WritableSignal<Message | undefined> = signal(undefined)
  private resultMessage: WritableSignal<Message | undefined> = signal(undefined)
  gameState: Signal<GameState | undefined> = computed(() => {
    let game = this.gameStateMessage()
    if (game != undefined)
     return JSON.parse(game.body)
  })
  result: Signal<Result | undefined> = computed(() => {
    let resultDTO = this.resultMessage()
    if(resultDTO != undefined)
      return JSON.parse(resultDTO.body)
  })

  ping(): void{
    let sub = this.stompService.watch(`/topic/1`).subscribe((msg: Message) => console.log(msg.body))
    this.stompService.publish({
      destination: `/app/game/1/ping`
    })
    this.subscriptions.push(sub)
  }

  private gameStateObservable(id: number): void{
    let gameStateSubscription = this.stompService.watch(`/topic/${id}/game-state`).subscribe(this.gameStateMessage.set)
    this.stompService.publish({
      destination: `/app/game/${id}`
    })
    this.subscriptions.push(gameStateSubscription)
  }

  private errorObservable(id: number): void{
    let errorSubscription = this.stompService.watch(`/topic/${id}/error`).subscribe(console.log)
    this.subscriptions.push(errorSubscription)
  }

  private resultObservable(id: number): void{
    let gameStateSubscription = this.stompService.watch(`/topic/${id}/result`).subscribe(this.resultMessage.set)
    this.subscriptions.push(gameStateSubscription)
  }

  subscribe(id: number): void{
    this.gameStateObservable(id)
    this.resultObservable(id)
    this.errorObservable(id)
  }

  unSubscribe() {
    this.subscriptions.forEach(x => x.unsubscribe())
  }

  move(username: string, position: number) {
    this.stompService.publish({
      destination: `app/game/${this.gameState()?.gameId}`,
      body: JSON.stringify({username: username, position: position})
    })
  }
}
