import { call, put, takeEvery, all } from 'redux-saga/effects';
import { toast } from 'react-toastify';
import getInstance from './services/httpService';
import { ADD_USER, addUserFailure, addUserSuccess, ADD_DRINK, addDrinkSuccess, addDrinkFailure } from './../actions';
import { syncAll } from '../actions';

function* addUser({ userName }) {
  try {
    toast.info('Speichere neuen Trinker...');
    const client = yield call(getInstance);
    yield call(client.post, '/api/users', {
      name: userName,
    });
    toast.info('Erfolg!');
    yield put(addUserSuccess());
    yield put(syncAll());
  } catch (e) {
    toast.error(`Error while saving booking: ${e.message}`);
    yield put(addUserFailure(e.message));
  }
}

function* addDrink({ drinkType, drinkName }) {
  try {
    toast.info('Speichere neuen Drink...');
    const client = yield call(getInstance);
    yield call(client.post, '/api/drinks', {
      name: drinkName,
      type: drinkType,
    });
    toast.info('Erfolg!');
    yield put(addDrinkSuccess());
    yield put(syncAll());
  } catch (e) {
    toast.error(`Error while saving booking: ${e.message}`);
    yield put(addDrinkFailure(e.message));
  }
}

export default function* () {
  yield all([
    takeEvery(ADD_USER, addUser),
    takeEvery(ADD_DRINK, addDrink),
  ]);
}
