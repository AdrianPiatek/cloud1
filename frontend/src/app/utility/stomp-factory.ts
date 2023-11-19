import {STOMP_CONFIG} from "../config/rx-stomp-config";
import {RxStompService} from "../service/rx-stomp-service/rx-stomp.service";

export function StompFactory(){
  const rxStomp = new RxStompService();
  rxStomp.configure(STOMP_CONFIG);
  rxStomp.activate();
  return rxStomp;
}
