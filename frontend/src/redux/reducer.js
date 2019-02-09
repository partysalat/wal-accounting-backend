import {combineReducers} from 'redux';
import {LOAD_USER_SUCCESS,} from './actions';

function usersReducer(state = [], action) {
  switch (action.type) {
    case LOAD_USER_SUCCESS:
      return {
        ...state,
        users: action.data,
      };
    default:
      return state;
  }
}

export default combineReducers({
  users: usersReducer,

});
