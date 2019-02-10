import React from 'react';
import { connect } from 'react-redux';
import { faSync } from '@fortawesome/free-solid-svg-icons';
import { AccountingButton } from '../accountingButton/AccountingButton';


class SyncButton extends React.Component {
  render() {
    return (
      <AccountingButton icon={faSync}>
        Sync Drinks
      </AccountingButton>
    );
  }
}

const mapDispatchToProps = {
};

export default connect(null, mapDispatchToProps)(SyncButton);
