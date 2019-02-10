import React from 'react';
import './AccountingButton.css';
import Button from '@material-ui/core/Button';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export function AccountingButton(props) {
  const {
    color, icon, children, className, ...rest
  } = props;
  return (
    <Button variant="contained" {...rest} color={color} className={`full-height ${className}`}>
      <div>
        <FontAwesomeIcon icon={icon} className="accounting-button" size="4x" />

        <div>{children}</div>
      </div>
    </Button>
  );
}

