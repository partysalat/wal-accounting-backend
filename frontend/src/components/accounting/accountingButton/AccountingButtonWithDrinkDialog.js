import React, { Component } from 'react';
import { AccountingButton } from './AccountingButton';

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
    const {
      drinkType, children, dialogComponent, ...rest
    } = this.props;
    const Dialog = dialogComponent;
    return (
      <React.Fragment>
        <AccountingButton {...rest} onClick={this.open}>
          {children}
        </AccountingButton>
        <Dialog drinkType={drinkType} open={this.state.isOpen} onClose={this.close} />
      </React.Fragment>
    );
  }
}

