import _ from 'lodash';
import { eventChannel } from 'redux-saga';

function WebSocketClient() {
  const connection = null;
  let firstMessageConsumed = false;
  const handlers = [];
  function subscribe(cb) {
    handlers.push(cb);
  }
  function unsubscribe(cb) {
    _.remove(handlers, cb);
  }
  function getWebsocketUrl() {
    const hostName = window.location.hostname;
    if (hostName.indexOf('localhost') > -1) {
      return `ws://${hostName}:8080/api/news/ws`;
    }
    return `ws://${hostName}/api/news/ws`;
  }
  function connectWebsocket() {
    try {
      const connection = new window.WebSocket(getWebsocketUrl());
      connection.onmessage = (data) => {
        if (firstMessageConsumed) {
          handlers.forEach(cb => cb(JSON.parse(data.data)));
        } else {
          firstMessageConsumed = true;
        }
      };
      connection.onclose = function () {
        console.log('ws connection error, retry in 5s...');
        firstMessageConsumed = false;
        setTimeout(connectWebsocket, 5000);
      };

      connection.onerror = function (evt) {
        if (connection.readyState === 1) {
          console.log(`ws normal error: ${evt.type}`);
        }
      };
    } catch (e) {
      console.log('ws connection error, retry in 5s...');
      firstMessageConsumed = false;
      setTimeout(connectWebsocket, 5000);
    }
  }

  connectWebsocket();

  return {
    subscribe,
    unsubscribe,
  };
}


const client = new WebSocketClient();

function websocketToChannel() {
  return eventChannel((emitter) => {
    const handler = (update) => {
      if (update) {
        emitter(update);
      }
    };
    client.subscribe(handler);
    return () => {
      client.unsubscribe(handler);
    };
  });
}


export {
  websocketToChannel,
};

