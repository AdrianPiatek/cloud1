import {GameState} from "./game-state";
import {GameResult} from "./game-result";

export interface Result {
  gameDTO: GameState,
  results: Map<string, GameResult>
}
