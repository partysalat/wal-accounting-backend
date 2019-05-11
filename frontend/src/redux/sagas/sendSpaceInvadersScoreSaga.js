import { call, put, takeEvery } from 'redux-saga/effects';
import getInstance from './services/httpService';
import { SEND_SPACE_INVADERS_SCORE, sendSpaceInvadersScoreFailure, sendSpaceInvadersScoreSuccess } from './../actions';

function* sendSpaceInvadersScoreSaga({ score, userId }) {
  try {
    const client = yield call(getInstance);
    yield call(client.post, '/api/users/space-invaders', {
      score,
      userId,
    });
    yield put(sendSpaceInvadersScoreSuccess());
  } catch (e) {
    yield put(sendSpaceInvadersScoreFailure(e.message));
  }
}

export default function* () {
  yield takeEvery(SEND_SPACE_INVADERS_SCORE, sendSpaceInvadersScoreSaga);
}
