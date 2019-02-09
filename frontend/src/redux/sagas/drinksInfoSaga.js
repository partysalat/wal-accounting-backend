import {call, put, takeEvery} from 'redux-saga/effects';
import getInstance from './services/httpService';
import {LOAD_DRINKS, loadDrinksFailure, loadDrinksSuccess} from './../actions';

function* fetchUser({drinkType}) {
  try {
    const client = yield call(getInstance);
    const user = yield call(client.get, `/api/drinks/${drinkType}`);
    yield put(loadDrinksSuccess(drinkType, user.data));
  } catch (e) {
    yield put(loadDrinksFailure(e.message));
  }
}

export default function* () {
  yield takeEvery(LOAD_DRINKS, fetchUser);
}
