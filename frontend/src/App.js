import React, {Component} from 'react';
import './App.css';
import Accounting from "./components/accounting";
import {Provider} from 'react-redux';
import getStore from "./redux"

class App extends Component {
  render() {
    return (
      <Provider store={getStore()}>
        <Accounting/>
      </Provider>
    );
  }
}

export default App;
