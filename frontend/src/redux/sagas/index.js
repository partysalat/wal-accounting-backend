import { fork, all } from 'redux-saga/effects';
import userInfoSaga from './userInfoSaga';
import drinksInfoSaga from './drinksInfoSaga';
import bookUserDrinkSaga from './bookUserDrinkSaga';
import syncAllSaga from './syncAllSaga';
import loadDrinkNewsSaga from './loadDrinkNewsSaga';


export default function* rootSaga() {
  yield all([
    fork(userInfoSaga),
    fork(drinksInfoSaga),
    fork(bookUserDrinkSaga),
    fork(syncAllSaga),
    fork(loadDrinkNewsSaga),
    // fork(mutexSaga),
    // fork(bestlistInfoSaga),
  ]);
}
