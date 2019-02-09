import { fork, all } from 'redux-saga/effects';
import userInfoSaga from './userInfoSaga';


export default function* rootSaga() {
  yield all([
    fork(userInfoSaga),
    // fork(createFogSaga),
    // fork(mutexSaga),
    // fork(bestlistInfoSaga),
  ]);
}
