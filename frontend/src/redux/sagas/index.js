import { fork, all } from 'redux-saga/effects';
import userInfoSaga from './userInfoSaga';
import drinksInfoSaga from './drinksInfoSaga';
import bookUserDrinkSaga from './bookUserDrinkSaga';


export default function* rootSaga() {
  yield all([
    fork(userInfoSaga),
    fork(drinksInfoSaga),
    fork(bookUserDrinkSaga),
    // fork(mutexSaga),
    // fork(bestlistInfoSaga),
  ]);
}
