import { put, takeEvery } from 'redux-saga/effects';
import { loadDrinks, SYNC_ALL, loadUser } from './../actions';

function* sync() {
  yield put(loadUser());
  yield put(loadDrinks('COCKTAIL'));
  yield put(loadDrinks('BEER'));
  yield put(loadDrinks('SHOT'));
  yield put(loadDrinks('SOFTDRINK'));
}

export default function* () {
  yield takeEvery(SYNC_ALL, sync);
}
