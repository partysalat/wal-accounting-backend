import React from 'react';
import './AccountingButton.css';
import Button from '@material-ui/core/Button';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


export function AccountingButton({color, icon, children}) {

  return (
    <Button variant="contained" color={color} className="full-height">
      <div>
        <FontAwesomeIcon icon={icon} className="accounting-button" size="4x"/>

        <div>{children}</div>
      </div>
    </Button>
  )

}

