import { call, put, takeEvery } from 'redux-saga/effects';
import getInstance from './services/httpService';
import { LOAD_BESTLIST, loadBestlistFailure, loadBestlistSuccess } from './../actions';

function* loadBestlistSaga() {
  try {
    const client = yield call(getInstance);
    const res = yield call(client.get, '/api/users/bestlist');
    yield put(loadBestlistSuccess(res.data));
  } catch (e) {
    yield put(loadBestlistFailure(e.message));
  }
}

export default function* () {
  yield takeEvery(LOAD_BESTLIST, loadBestlistSaga);
}
