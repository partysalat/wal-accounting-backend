import React, { Component } from 'react';
import { Provider } from 'react-redux';
import Accounting from './components/accounting';
import Home from './components/home';
import getStore from './redux';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

class App extends Component {
  render() {
    return (
      <Provider store={getStore()}>
        <Router>
          <Route path="/" exact component={Home} />
          <Route path="/abrechnung" component={Accounting} />
        </Router>
      </Provider>
    );
  }
}

export default App;
