import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterLink} from "@angular/router";
import {CognitoService} from "../../service/cognito-service/cognito.service";
import {GameService} from "../../service/game-service/game.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  cognitoService = inject(CognitoService)
  gameService = inject(GameService)

  ngOnInit(): void {

  }

  constructor() {
    this.gameService.getUserStats()
  }


}
