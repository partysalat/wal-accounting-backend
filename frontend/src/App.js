import React, { Component } from 'react';
import { Provider } from 'react-redux';
import './App.css';
import Accounting from './components/accounting';
import getStore from './redux';

class App extends Component {
  render() {
    return (
      <Provider store={getStore()}>
        <Accounting />
      </Provider>
    );
  }
}

export default App;
