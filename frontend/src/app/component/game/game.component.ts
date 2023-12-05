import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ReactiveFormsModule} from "@angular/forms";
import {CellComponent} from "../cell/cell.component";
import {GameService} from "../../service/game-service/game.service";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CellComponent, RouterLink],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent{
  gameService = inject(GameService)
  router = inject(Router)

  onCellClick(pos: number): void{
    if(this.gameService.gameState()?.state === 'IN_PROGRESS')
      this.gameService.move(pos)
  }

  constructor() {
    let gameId = this.router.getCurrentNavigation()?.extras.state?.['gameId']
    this.gameService.subscribe(gameId)
  }

}
