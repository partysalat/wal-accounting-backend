
export const LOAD_USER = 'LOAD_USER';
export const LOAD_USER_SUCCESS = 'LOAD_USER_SUCCESS';
export const LOAD_USER_FAILED = 'LOAD_USER_FAILED';
export function loadUser() {
  return {
    type: LOAD_USER,
  };
}
export function loadUserSuccess(userData) {
  return {
    type: LOAD_USER_SUCCESS,
    data: userData,
  };
}
export function loadUserFailure(message) {
  return {
    type: LOAD_USER_FAILED,
    error: message,
  };
}

export const LOAD_DRINKS = 'LOAD_DRINKS';
export const LOAD_DRINKS_SUCCESS = 'LOAD_DRINKS_SUCCESS';
export const LOAD_DRINKS_FAILED = 'LOAD_DRINKS_FAILED';
export function loadDrinks(drinkType) {
  return {
    type: LOAD_DRINKS,
    drinkType,
  };
}
export function loadDrinksSuccess(drinkType, drinks) {
  return {
    type: LOAD_DRINKS_SUCCESS,
    data: drinks,
    drinkType,
  };
}
export function loadDrinksFailure(message) {
  return {
    type: LOAD_DRINKS_FAILED,
    error: message,
  };
}

export const BOOK_USER_DRINK = 'BOOK_USER_DRINK';
export const BOOK_USER_DRINK_SUCCESS = 'BOOK_USER_DRINK_SUCCESS';
export const BOOK_USER_DRINK_FAILED = 'BOOK_USER_DRINK_FAILED';
export function bookUserDrink(drink, users) {
  return {
    type: BOOK_USER_DRINK,
    drink,
    users,
  };
}
export function bookUserDrinkSuccess() {
  return {
    type: BOOK_USER_DRINK_SUCCESS,
  };
}
export function bookUserDrinkFailure(message) {
  return {
    type: BOOK_USER_DRINK_FAILED,
    error: message,
  };
}

export const SYNC_ALL = 'SYNC_ALL';
export function syncAll() {
  return {
    type: SYNC_ALL,
  };
}

export const LOAD_NEWS = 'LOAD_NEWS';
export const LOAD_NEWS_SUCCESS = 'LOAD_NEWS_SUCCESS';
export const LOAD_NEWS_FAILED = 'LOAD_NEWS_SUCCESS';
export function loadNews(offset) {
  return {
    type: LOAD_NEWS,
    offset,
  };
}
export function loadNewsSuccess(news) {
  return {
    type: LOAD_NEWS_SUCCESS,
    data: news,
  };
}
export function loadNewsFailure(message) {
  return {
    type: LOAD_NEWS_FAILED,
    error: message,
  };
}
export const APPEND_NEWS = 'APPEND_NEWS';
export const APPEND_NEWS_SUCCESS = 'APPEND_NEWS_SUCCESS';
export const APPEND_NEWS_FAILED = 'APPEND_NEWS_SUCCESS';
export function appendNews(offset) {
  return {
    type: APPEND_NEWS,
    offset,
  };
}
export function appendNewsSuccess(news) {
  return {
    type: APPEND_NEWS_SUCCESS,
    data: news,
  };
}
export function appendNewsFailure(message) {
  return {
    type: APPEND_NEWS_FAILED,
    error: message,
  };
}
