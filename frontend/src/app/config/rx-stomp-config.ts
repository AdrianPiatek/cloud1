import {RxStompConfig} from "@stomp/rx-stomp";

export const STOMP_CONFIG: RxStompConfig = {
  brokerURL: 'ws://127.0.0.1:8080/stomp',
  reconnectDelay: 500
}
