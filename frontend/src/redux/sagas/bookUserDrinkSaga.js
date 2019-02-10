import {call, put, takeEvery} from 'redux-saga/effects';
import getInstance from './services/httpService';
import {BOOK_USER_DRINK, bookUserDrinkFailure, bookUserDrinkSuccess,} from './../actions';

function* bookUserDrink({ drink, users }) {
  try {
    const client = yield call(getInstance);
    yield call(client.post, '/api/users/drink', {
      drinkId: drink.id,
      users: users.map(user => ({ userId: `${user.id}`, amount: 1 })),
    });
    yield put(bookUserDrinkSuccess());
  } catch (e) {
    yield put(bookUserDrinkFailure(e.message));
  }
}

export default function* () {
  yield takeEvery(BOOK_USER_DRINK, bookUserDrink);
}
