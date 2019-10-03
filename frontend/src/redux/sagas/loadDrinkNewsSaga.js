import { all, call, fork, put, takeEvery } from 'redux-saga/effects';
import { toast } from 'react-toastify';
import {
  appendNewsSuccess,
  APPEND_NEWS,
  appendNewsFailure,
  LOAD_DRINK_NEWS,
  loadNewsFailure,
  loadNewsSuccess, REMOVE_NEWS, removeNewsSuccess, removeNewsFailure,
} from './../actions';
import getInstance from './services/httpService';

function* loadDrinkNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news?offset=${offset}&filter=drinks`);
    yield put(loadNewsSuccess(news.data));
  } catch (e) {
    yield put(loadNewsFailure(e.message));
  }
}

function* appendNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news?offset=${offset}&filter=drinks`);
    yield put(appendNewsSuccess(news.data));
  } catch (e) {
    yield put(appendNewsFailure(e.message));
  }
}

function* removeNewsSaga({ newsId }) {
  try {
    const client = yield call(getInstance);
    yield call(client.delete, `/api/news/${newsId}`);
    yield put(removeNewsSuccess(newsId));
  } catch (e) {
    toast.error('Fehler beim News entfernen');
    yield put(removeNewsFailure(e.message));
  }
}


export default function* () {
  yield all([
    fork(function* () {
      yield takeEvery(LOAD_DRINK_NEWS, loadDrinkNewsSaga);
    }),
    fork(function* () {
      yield takeEvery(APPEND_NEWS, appendNewsSaga);
    }),
    fork(function* () {
      yield takeEvery(REMOVE_NEWS, removeNewsSaga);
    }),
  ]);
}
