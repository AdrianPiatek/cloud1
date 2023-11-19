import {Sign} from "./sign";
import {State} from "./state";

export interface GameState {
  gameId: number
  grid: Sign[],
  playerTurn: string,
  state: State,
  player1: string,
  player2?: string
}
