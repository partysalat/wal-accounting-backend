import { eventChannel } from 'redux-saga';
import _ from 'lodash';


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

  function connectWebsocket() {
    try {
      const connection = new window.WebSocket(`ws://${window.location.hostname}:8080/api/news/ws`);
      connection.onmessage = (data) => {
        if (firstMessageConsumed) {
          handlers.forEach(cb => cb(JSON.parse(data.data)));
        } else {
          firstMessageConsumed = true;
        }
      };
      connection.onclose = function () {
        console.log('ws connection error, retry in 5s...');
        setTimeout(connectWebsocket, 5000);
      };

      connection.onerror = function (evt) {
        if (connection.readyState === 1) {
          console.log(`ws normal error: ${evt.type}`);
        }
      };
    } catch (e) {
      console.log('ws connection error, retry in 5s...');
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

