import {computed, effect, inject, Injectable, signal, Signal} from '@angular/core';
import {RxStompService} from "../rx-stomp-service/rx-stomp.service";
import {Message} from "@stomp/stompjs";
import {GameState} from "../../entity/game-state";
import {Result} from "../../entity/result";
import {toSignal} from "@angular/core/rxjs-interop";
@Injectable({
  providedIn: 'root'
})
export class GameService {

  private stompService = inject(RxStompService)
  private gameStateMessage: Signal<Message | undefined> = signal(undefined)
  private resultMessage: Signal<Message | undefined> = signal(undefined)
  private errorMessage: Signal<Message | undefined> = signal(undefined)
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

  private getGameState(id: number): void{
    this.stompService.publish({
      destination: `/app/game/${id}`
    })
  }

  subscribe(id: number): void{
    this.gameStateMessage = toSignal(this.stompService.watch(`/topic/${id}/game-state`))
    this.resultMessage = toSignal(this.stompService.watch(`/topic/${id}/result`))
    this.errorMessage = toSignal(this.stompService.watch(`/topic/${id}/error`))
    effect(() => console.log(this.errorMessage()?.body))
  }

  move(username: string, position: number) {
    this.stompService.publish({
      destination: `app/game/${this.gameState()?.gameId}`,
      body: JSON.stringify({username: username, position: position})
    })
  }
}
