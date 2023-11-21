import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ReactiveFormsModule} from "@angular/forms";
import {CellComponent} from "../cell/cell.component";
import {GameService} from "../../service/game-service/game.service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CellComponent, RouterLink],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent{
  gameService = inject(GameService)

  onCellClick(pos: number): void{
    this.gameService.move(pos)
  }

  constructor() {
    this.gameService.subscribe(2)
  }

}
