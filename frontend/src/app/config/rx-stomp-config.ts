import {RxStompConfig} from "@stomp/rx-stomp";

export const STOMP_CONFIG: RxStompConfig = {
  brokerURL: `ws://${window.location.hostname}:8080/stomp`,
  reconnectDelay: 500
}
