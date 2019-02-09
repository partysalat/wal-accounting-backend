import { fork, all } from 'redux-saga/effects';
import userInfoSaga from './userInfoSaga';
import drinksInfoSaga from './drinksInfoSaga';


export default function* rootSaga() {
  yield all([
    fork(userInfoSaga),
    fork(drinksInfoSaga),
    // fork(createFogSaga),
    // fork(mutexSaga),
    // fork(bestlistInfoSaga),
  ]);
}
