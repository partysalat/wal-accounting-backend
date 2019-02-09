
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