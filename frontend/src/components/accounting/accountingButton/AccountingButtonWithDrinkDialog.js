import React, {Component} from 'react';
import {AccountingButton} from './AccountingButton';
import DrinkDialog from './../drinkDialog/DrinkDialog';

export default class AccountingButtonWithDrinkDialog extends Component {
  constructor(props) {
    super(props);
    this.state = { isOpen: false };
    this.open = this.open.bind(this);
    this.close = this.close.bind(this);
  }
  open() {
    this.setState({ isOpen: true });
  }
  close() {
    this.setState({ isOpen: false });
  }
  render() {
    const { drinkType, children, ...rest } = this.props;
    return (
      <React.Fragment>
        <AccountingButton {...rest} onClick={this.open}>
          {children}
        </AccountingButton>
        <DrinkDialog drinkType={drinkType} open={this.state.isOpen} onClose={this.close} />
      </React.Fragment>
    );
  }
}

