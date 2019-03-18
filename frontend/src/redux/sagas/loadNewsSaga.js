import { all, call, fork, put, takeEvery } from 'redux-saga/effects';
import {
  appendNewsItemsFailure,
  appendNewsItemsSuccess,
  APPEND_NEWS_ITEMS,
  LOAD_NEWS,
  loadNewsItemsFailure,
  loadNewsItemsSuccess,
} from './../actions';
import getInstance from './services/httpService';

function* loadDrinkNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news/${offset}`);
    yield put(loadNewsItemsSuccess(news.data));
  } catch (e) {
    yield put(loadNewsItemsFailure(e.message));
  }
}
function* appendNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news/${offset}`);
    yield put(appendNewsItemsSuccess(news.data));
  } catch (e) {
    yield put(appendNewsItemsFailure(e.message));
  }
}


export default function* () {
  yield all([
    fork(function* () {
      yield takeEvery(LOAD_NEWS, loadDrinkNewsSaga);
    }), fork(function* () {
      yield takeEvery(APPEND_NEWS_ITEMS, appendNewsSaga);
    }),
  ]);
}
