import { fork, all } from 'redux-saga/effects';
import userInfoSaga from './userInfoSaga';
import drinksInfoSaga from './drinksInfoSaga';
import bookUserDrinkSaga from './bookUserDrinkSaga';
import syncAllSaga from './syncAllSaga';
import loadDrinkNewsSaga from './loadDrinkNewsSaga';
import createNewEntitySaga from './createNewEntitySaga';
import loadNewsSaga from './loadNewsSaga';
import subscribeSaga from './subscribeSaga';
import bestlistSaga from './bestlistSaga';


export default function* rootSaga() {
  yield all([
    fork(userInfoSaga),
    fork(drinksInfoSaga),
    fork(bookUserDrinkSaga),
    fork(syncAllSaga),
    fork(loadDrinkNewsSaga),
    fork(loadNewsSaga),
    fork(createNewEntitySaga),
    fork(subscribeSaga),
    fork(bestlistSaga),
    // fork(mutexSaga),
    // fork(bestlistInfoSaga),
  ]);
}
