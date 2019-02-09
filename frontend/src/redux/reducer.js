import { combineReducers } from 'redux';
import { LOAD_DRINKS_SUCCESS, LOAD_USER_SUCCESS } from './actions';

function usersReducer(state = [], action) {
  switch (action.type) {
    case LOAD_USER_SUCCESS:
      return action.data;
    default:
      return state;
  }
}
function drinksReducer(state = [], action) {
  switch (action.type) {
    case LOAD_DRINKS_SUCCESS:
      return {
        ...state,
        [action.drinkType]: action.data,
      };
    default:
      return state;
  }
}

export default combineReducers({
  users: usersReducer,
  drinks: drinksReducer,

});
