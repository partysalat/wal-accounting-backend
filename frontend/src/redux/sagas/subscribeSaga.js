import { call, put, take, takeEvery } from 'redux-saga/effects';
import { websocketToChannel } from './services/websocketClient';
import { prependNewsItem, SUBSCRIBE_NEWS_UPDATE } from '../actions';


function* subscribeWebsockets() {
  const chan = yield call(websocketToChannel);
  while (true) {
    const data = yield take(chan);
    yield put(prependNewsItem(data));
  }
}


export default function* () {
  yield takeEvery(SUBSCRIBE_NEWS_UPDATE, subscribeWebsockets);
}

