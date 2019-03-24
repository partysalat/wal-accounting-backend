import React, { Component } from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { MuiThemeProvider } from '@material-ui/core/styles';
import 'animate.css/animate.css';
import Accounting from './components/accounting';
import Home from './components/home';
import getStore from './redux';
import theme from './theme';

class App extends Component {
  render() {
    return (
      <Provider store={getStore()}>
        <MuiThemeProvider theme={theme}>
          <Router>
            <Route path="/" exact component={Home} />
            <Route path="/abrechnung" component={Accounting} />
          </Router>
        </MuiThemeProvider>
      </Provider>
    );
  }
}

export default App;
