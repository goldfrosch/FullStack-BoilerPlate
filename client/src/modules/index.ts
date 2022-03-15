import { combineReducers } from "redux";
import { all } from "redux-saga/effects";

const rootStore = combineReducers({});

export function* rootSaga() {
  yield all([]);
}

export default rootStore;