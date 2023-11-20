import {Component, computed, inject, Input, numberAttribute, OnInit, Signal, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GameService} from "../../service/game-service/game.service";
import {Sign} from "../../entity/sign";

@Component({
  selector: 'app-cell',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cell.component.html',
  styleUrl: './cell.component.css'
})
export class CellComponent implements OnInit {
  @Input({required: true, transform: numberAttribute}) position!: number
  classes: Record<string, boolean> = {}
  gameService = inject(GameService)
  sign: Signal<Sign | undefined> = signal(undefined)

  updateClasses() {
    this.classes = {
      'border-top-0': [0, 1, 2].includes(this.position),
      'border-bottom-0': [6, 7, 8].includes(this.position),
      'border-start-0': [0, 3, 6].includes(this.position),
      'border-end-0': [2, 5, 8].includes(this.position)
    }
  }

  ngOnInit(): void {
    this.updateClasses()
    this.sign = computed(() => this.gameService.gameState()?.grid[this.position])
  }

}
