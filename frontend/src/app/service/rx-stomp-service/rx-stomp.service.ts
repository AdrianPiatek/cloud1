import {Injectable} from '@angular/core';
import {RxStomp} from "@stomp/rx-stomp";
import {StompFactory} from "../../utility/stomp-factory";

@Injectable({
  providedIn: 'root',
  useFactory: StompFactory
})
export class RxStompService extends RxStomp {
}
