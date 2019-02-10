import { all, call, fork, put, takeEvery } from 'redux-saga/effects';
import { APPEND_NEWS, appendNewsFailure, LOAD_NEWS, loadNewsFailure, loadNewsSuccess } from './../actions';
import getInstance from './services/httpService';
import { appendNewsSuccess } from '../actions';

function* loadNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news/${offset}`);
    yield put(loadNewsSuccess(news.data));
  } catch (e) {
    yield put(loadNewsFailure(e.message));
  }
}
function* appendNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news/${offset}`);
    yield put(appendNewsSuccess(news.data));
  } catch (e) {
    yield put(appendNewsFailure(e.message));
  }
}


export default function* () {
  yield all([
    fork(function* () {
      yield takeEvery(LOAD_NEWS, loadNewsSaga);
    }),
    fork(function* () {
      yield takeEvery(APPEND_NEWS, appendNewsSaga);
    }),
  ]);
}
