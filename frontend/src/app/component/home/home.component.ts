import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {GameService} from "../../service/game.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{

  private gameService = inject(GameService)

  ngOnInit(): void {
    this.gameService.ping()
  }
}
