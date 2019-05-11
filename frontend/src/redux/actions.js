
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

export const LOAD_DRINK_NEWS = 'LOAD_DRINK_NEWS';
export const LOAD_DRINK_NEWS_SUCCESS = 'LOAD_DRINK_NEWS_SUCCESS';
export const LOAD_DRINK_NEWS_FAILED = 'LOAD_DRINK_NEWS_FAILED';
export function loadNews(offset) {
  return {
    type: LOAD_DRINK_NEWS,
    offset,
  };
}
export function loadNewsSuccess(news) {
  return {
    type: LOAD_DRINK_NEWS_SUCCESS,
    data: news,
  };
}
export function loadNewsFailure(message) {
  return {
    type: LOAD_DRINK_NEWS_FAILED,
    error: message,
  };
}

export const APPEND_NEWS = 'APPEND_NEWS';
export const APPEND_NEWS_SUCCESS = 'APPEND_NEWS_SUCCESS';
export const APPEND_NEWS_FAILED = 'APPEND_NEWS_FAILURE';
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

export const REMOVE_NEWS = 'REMOVE_NEWS';
export const REMOVE_NEWS_SUCCESS = 'REMOVE_NEWS_SUCCESS';
export const REMOVE_NEWS_FAILED = 'REMOVE_NEWS_SUCCESS';
export function removeNews(newsId) {
  return {
    type: REMOVE_NEWS,
    newsId,
  };
}
export function removeNewsSuccess(newsId) {
  return {
    type: REMOVE_NEWS_SUCCESS,
    newsId,
  };
}
export function removeNewsFailure(message) {
  return {
    type: REMOVE_NEWS_FAILED,
    error: message,
  };
}


export const ADD_USER = 'ADD_USER';
export const ADD_USER_SUCCESS = 'ADD_USER_SUCCESS';
export const ADD_USER_FAILED = 'ADD_USER_SUCCESS';
export function addUser(userName) {
  return {
    type: ADD_USER,
    userName,
  };
}
export function addUserSuccess() {
  return {
    type: ADD_USER_SUCCESS,
  };
}
export function addUserFailure(message) {
  return {
    type: ADD_USER_FAILED,
    error: message,
  };
}

export const ADD_DRINK = 'ADD_DRINK';
export const ADD_DRINK_SUCCESS = 'ADD_DRINK_SUCCESS';
export const ADD_DRINK_FAILED = 'ADD_DRINK_SUCCESS';
export function addDrink(drinkName, drinkType) {
  return {
    type: ADD_DRINK,
    drinkType,
    drinkName,
  };
}
export function addDrinkSuccess() {
  return {
    type: ADD_DRINK_SUCCESS,
  };
}
export function addDrinkFailure(message) {
  return {
    type: ADD_DRINK_FAILED,
    error: message,
  };
}
export const SEND_SPACE_INVADERS_SCORE = 'SEND_SPACE_INVADERS_SCORE';
export const SEND_SPACE_INVADERS_SCORE_SUCCESS = 'SEND_SPACE_INVADERS_SCORE_SUCCESS';
export const SEND_SPACE_INVADERS_SCORE_FAILED = 'SEND_SPACE_INVADERS_SCORE_SUCCESS';
export function sendSpaceInvadersScore(userId, score) {
  return {
    type: SEND_SPACE_INVADERS_SCORE,
    userId,
    score,
  };
}
export function sendSpaceInvadersScoreSuccess() {
  return {
    type: SEND_SPACE_INVADERS_SCORE_SUCCESS,
  };
}
export function sendSpaceInvadersScoreFailure(message) {
  return {
    type: SEND_SPACE_INVADERS_SCORE_FAILED,
    error: message,
  };
}


export const LOAD_NEWS = 'LOAD_NEWS';
export const LOAD_NEWS_SUCCESS = 'LOAD_NEWS_SUCCESS';
export const LOAD_NEWS_FAILED = 'LOAD_NEWS_FAILED';
export function loadNewsItems(offset) {
  return {
    type: LOAD_NEWS,
    offset,
  };
}
export function loadNewsItemsSuccess(news) {
  return {
    type: LOAD_NEWS_SUCCESS,
    data: news,
  };
}
export function loadNewsItemsFailure(message) {
  return {
    type: LOAD_NEWS_FAILED,
    error: message,
  };
}
export const APPEND_NEWS_ITEMS = 'APPEND_NEWS_ITEMS';
export const APPEND_NEWS_ITEMS_SUCCESS = 'APPEND_NEWS_ITEMS_SUCCESS';
export const APPEND_NEWS_ITEMS_FAILED = 'APPEND_NEWS_ITEMS_FAILED';
export function appendNewsItems(offset) {
  return {
    type: APPEND_NEWS_ITEMS,
    offset,
  };
}
export function appendNewsItemsSuccess(news) {
  return {
    type: APPEND_NEWS_ITEMS_SUCCESS,
    data: news,
  };
}
export function appendNewsItemsFailure(message) {
  return {
    type: APPEND_NEWS_ITEMS_FAILED,
    error: message,
  };
}
export const SUBSCRIBE_NEWS_UPDATE = 'SUBSCRIBE_NEWS_UPDATE';
export const PREPEND_NEWS_ITEM = 'PREPEND_NEWS_ITEM';
export function subscribeToNewsUpdate() {
  return {
    type: SUBSCRIBE_NEWS_UPDATE,
  };
}
export function prependNewsItem(newsItem) {
  return {
    type: PREPEND_NEWS_ITEM,
    data: newsItem,
  };
}


export const LOAD_BESTLIST = 'LOAD_BESTLIST';
export const LOAD_BESTLIST_SUCCESS = 'LOAD_BESTLIST_SUCCESS';
export const LOAD_BESTLIST_FAILED = 'LOAD_BESTLIST_FAILED';
export function loadBestlist() {
  return {
    type: LOAD_BESTLIST,
  };
}
export function loadBestlistSuccess(bestlist) {
  return {
    type: LOAD_BESTLIST_SUCCESS,
    data: bestlist,
  };
}
export function loadBestlistFailure(message) {
  return {
    type: LOAD_BESTLIST_FAILED,
    error: message,
  };
}

