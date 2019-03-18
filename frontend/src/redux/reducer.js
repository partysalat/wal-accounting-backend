import { combineReducers } from 'redux';
import {
  APPEND_NEWS_SUCCESS,
  LOAD_DRINKS_SUCCESS,
  LOAD_DRINK_NEWS_SUCCESS,
  LOAD_USER_SUCCESS,
  LOAD_DRINK_NEWS,
  APPEND_NEWS,
  REMOVE_NEWS,
  REMOVE_NEWS_SUCCESS,
  APPEND_NEWS_ITEMS,
  APPEND_NEWS_ITEMS_SUCCESS,
  LOAD_NEWS_SUCCESS, LOAD_NEWS,
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
    case REMOVE_NEWS_SUCCESS:
      return {
        loading: state.loading,
        data: state.data.filter(item => item.news.id !== action.newsId),
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
function newsReducer(state = [], action) {
  switch (action.type) {
    case LOAD_NEWS:
      return {
        loading: true,
        lastLoadEmpty: false,
        data: [],
      };

    case LOAD_NEWS_SUCCESS:
      return {
        loading: false,
        data: action.data,
      };

    case APPEND_NEWS_ITEMS:
      return {
        loading: true,
        data: state.data,
      };
    case APPEND_NEWS_ITEMS_SUCCESS:
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
  news: newsReducer,

});
