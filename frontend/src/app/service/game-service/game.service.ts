import {computed, effect, inject, Injectable, signal, Signal, WritableSignal} from '@angular/core';
import {RxStompService} from "../rx-stomp-service/rx-stomp.service";
import {Message} from "@stomp/stompjs";
import {GameState} from "../../entity/game-state";
import {Result} from "../../entity/result";
import {HttpClient} from "@angular/common/http";
import {CognitoService} from "../cognito-service/cognito.service";
import {UserInfo} from "../../entity/user-info";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private API_URL = 'http://localhost:8080'

  private stompService = inject(RxStompService)
  private cognitoService = inject(CognitoService)
  private http = inject(HttpClient)
  private router = inject(Router)
  private subList: Subscription[] = []

  private gameStateMessage: WritableSignal<Message | undefined> = signal(undefined)
  private resultMessage: WritableSignal<Message | undefined> = signal(undefined)
  private errorMessage: WritableSignal<Message | undefined> = signal(undefined)
  gameState: Signal<GameState | undefined> = computed(() => {
    let game = this.gameStateMessage()
    if (game != undefined)
      return JSON.parse(game.body)
  })
  result: Signal<Result | undefined> = computed(() => {
    let resultDTO = this.resultMessage()
    if (resultDTO != undefined) {
      let res = JSON.parse(resultDTO.body)
      res.results = new Map(Object.entries(res.results))
      return res
    }
  })
  playerResult= computed(() => {
    let username = this.userInfo()?.username
    console.log(this.result()?.results)
    return username ? this.result()?.results.get(username) : undefined
  })
  userInfo: WritableSignal<UserInfo | undefined> = signal(undefined)
  isPlayerTurn = computed(() => this.userInfo()?.username === this.gameState()?.playerTurn)

  private getGameState(id: number): void {
    this.stompService.publish({
      destination: `/app/game/${id}`
    })
  }

  surrender(){
    let username = this.userInfo()?.username
    let gameId = this.gameState()?.gameId
    if (!gameId || !username)
      return
    this.stompService.publish({
      destination: `/app/game/${gameId}/surrender`,
      body: username
    })
  }
  subscribe(id: number): void {
    this.subList.push(this.stompService.watch(`/topic/${id}/game-state`).subscribe(this.gameStateMessage.set))
    this.subList.push(this.stompService.watch(`/topic/${id}/result`).subscribe(this.resultMessage.set))
    this.subList.push(this.stompService.watch(`/topic/${id}/error`).subscribe(this.errorMessage.set))
    effect(() => console.log(this.errorMessage()?.body))
    this.getGameState(id)
  }

  move(position: number): void {
    let username = this.userInfo()?.username
    let gameId = this.gameState()?.gameId
    if (!gameId || !username)
      return
    this.stompService.publish({
      destination: `/app/game/${gameId}/move`,
      body: JSON.stringify({username: username, position: position})
    })
  }

  getUserStats() {
    let headers = this.cognitoService.accessHeader()
    if (headers)
      this.http.get<UserInfo>(this.API_URL + '/game/user-stats', {headers: headers}).subscribe(this.userInfo.set)
  }

  joinGame() {
    let headers = this.cognitoService.accessHeader()
    if (headers)
      this.http.post<number>(this.API_URL + '/game/join', null,{headers: headers}).subscribe(num => {
        this.router.navigate(['game'], {state: {gameId: num}})
      })
  }

  clearGameState() {
    this.subList.forEach(sub => sub.unsubscribe())
  }
}
