import { call, put, takeEvery } from 'redux-saga/effects';
import { toast } from 'react-toastify';
import getInstance from './services/httpService';
import { BOOK_USER_DRINK, bookUserDrinkFailure, bookUserDrinkSuccess } from './../actions';

function* bookUserDrink({ drink, users }) {
  try {
    toast.info('Speichere Buchung...');
    const client = yield call(getInstance);
    yield call(client.post, '/api/users/drink', {
      drinkId: drink.id,
      users: users.map(user => ({ userId: `${user.userId}`, amount: user.amount })),
    });
    toast.info('Erfolg!');
    yield put(bookUserDrinkSuccess());
  } catch (e) {
    toast.error(`Error while saving booking: ${e.message}`);
    yield put(bookUserDrinkFailure(e.message));
  }
}

export default function* () {
  yield takeEvery(BOOK_USER_DRINK, bookUserDrink);
}
