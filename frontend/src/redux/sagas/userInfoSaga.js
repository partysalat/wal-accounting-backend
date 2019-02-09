import { call, put, throttle } from 'redux-saga/effects';
import getInstance from './services/httpService';
import { LOAD_USER, loadUserFailure, loadUserSuccess } from './../actions';

function* fetchUser() {
  try {
    const client = yield call(getInstance);
    const user = yield call(client.get, '/api/users');
    yield put(loadUserSuccess(user.data));
  } catch (e) {
    yield put(loadUserFailure(e.message));
  }
}

export default function* () {
  yield throttle(60 * 1000, LOAD_USER, fetchUser);
}
