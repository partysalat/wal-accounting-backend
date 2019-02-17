import { combineReducers } from 'redux';
import {
  APPEND_NEWS_SUCCESS,
  LOAD_DRINKS_SUCCESS,
  LOAD_DRINK_NEWS_SUCCESS,
  LOAD_USER_SUCCESS,
  LOAD_DRINK_NEWS, APPEND_NEWS,
} from './actions';

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
function drinkNewsReducer(state = [], action) {
  switch (action.type) {
    case LOAD_DRINK_NEWS:
      return {
        loading: true,
        lastLoadEmpty: false,
        data: [],
      };

    case LOAD_DRINK_NEWS_SUCCESS:
      return {
        loading: false,
        data: action.data,
      };

    case APPEND_NEWS:
      return {
        loading: true,
        data: state.data,
      };
    case APPEND_NEWS_SUCCESS:
      return {
        loading: false,
        lastLoadEmpty: action.data.length === 0,
        data: [...state.data, ...action.data],

      };
    default:
      return state;
  }
}

export default combineReducers({
  users: usersReducer,
  drinks: drinksReducer,
  drinkNews: drinkNewsReducer,

});
