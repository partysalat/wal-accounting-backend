import { call, put, take, takeEvery } from 'redux-saga/effects';
import { websocketToChannel } from './services/websocketClient';
import { loadBestlist, loadNewsItems, prependNewsItem, SUBSCRIBE_NEWS_UPDATE } from '../actions';


function* subscribeWebsockets() {
  const chan = yield call(websocketToChannel);
  while (true) {
    const data = yield take(chan);
    if (data.JoinedNews) {
      yield put(prependNewsItem(data.JoinedNews));
    } else if (data.RemoveNews) {
      // Reload when news was removed
      yield put(loadNewsItems(0));
    }

    yield put(loadBestlist());
  }
}


export default function* () {
  yield takeEvery(SUBSCRIBE_NEWS_UPDATE, subscribeWebsockets);
}

