import { all, call, fork, put, takeEvery } from 'redux-saga/effects';
import { appendNewsSuccess, APPEND_NEWS, appendNewsFailure, LOAD_DRINK_NEWS, loadNewsFailure, loadNewsSuccess } from './../actions';
import getInstance from './services/httpService';

function* loadDrinkNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news/${offset}/drinks`);
    yield put(loadNewsSuccess(news.data));
  } catch (e) {
    yield put(loadNewsFailure(e.message));
  }
}
function* appendNewsSaga({ offset }) {
  try {
    const client = yield call(getInstance);
    const news = yield call(client.get, `/api/news/${offset}/drinks`);
    yield put(appendNewsSuccess(news.data));
  } catch (e) {
    yield put(appendNewsFailure(e.message));
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
  ]);
}
