import React, { Component } from 'react';
import { connect } from 'react-redux';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Avatar from '@material-ui/core/Avatar';
import Tooltip from '@material-ui/core/Tooltip';
import { withStyles } from '@material-ui/core/styles';
import { appendNewsItems, loadBestlist, subscribeToNewsUpdate } from '../../../redux/actions';
import './bestlist.css';

const styles = theme => ({
  root: {
    // width: '100%',
    // marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    // width: '100%',

    boxSizing: 'border-box',
    // padding: '0',
  },
  row: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.background.default,
    },
  },
  smallCell: {
    padding: 0,
  },
});
const CustomTableCell = withStyles(theme => ({
  head: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  root: {
    paddingLeft: '5px',
    paddingRight: '5px',

  },
  body: {
    fontSize: 14,
  },
}))(TableCell);

class Bestlist extends Component {
  componentWillMount() {
    this.props.loadBestlist();
  }

  render() {
    const { classes } = this.props;
    return (
      <Paper className={classes.root}>
        <Table className={classes.table}>
          <TableHead>
            <TableRow>
              <CustomTableCell>No.</CustomTableCell>
              <CustomTableCell>Name</CustomTableCell>
              <CustomTableCell align="right">Biere</CustomTableCell>
              <CustomTableCell align="right">Cocktails</CustomTableCell>
              <CustomTableCell align="right">Shots</CustomTableCell>
              <CustomTableCell align="right">Softdrinks</CustomTableCell>
              <CustomTableCell align="right">Space Invaders Score</CustomTableCell>
              <CustomTableCell align="right">Achievements</CustomTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {this.props.bestlist.map((row, index) => (
              <TableRow key={row.id} className={classes.row}>
                <CustomTableCell>{index + 1}.</CustomTableCell>
                <CustomTableCell>{row.user.name}</CustomTableCell>
                <CustomTableCell className={classes.smallCell} align="right">{row.beerCount}</CustomTableCell>
                <CustomTableCell className={classes.smallCell} align="right">{row.cocktailCount}</CustomTableCell>
                <CustomTableCell className={classes.smallCell} align="right">{row.shotCount}</CustomTableCell>
                <CustomTableCell className={classes.smallCell} align="right">{row.softdrinkCount}</CustomTableCell>
                <CustomTableCell className={classes.smallCell} align="right">{row.spaceInvadersScore}</CustomTableCell>
                <CustomTableCell className={classes.smallCell} align="right">{row.achievements.map(payload => (<Tooltip key={payload.id} title={`${payload.name}:${payload.description}`}>
                  <Avatar alt={payload.name} src={payload.imagePath} className="avatar-small animated jackInTheBox" />
                </Tooltip>))}</CustomTableCell>
              </TableRow>
                ))}
          </TableBody>
        </Table>
      </Paper>
    );
  }
}


const mapDispatchToProps = {
  loadBestlist,
  // subscribeToNewsUpdate,
};

const mapStateToProps = state => ({
  bestlist: state.bestlist.data || [],

});
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(Bestlist));
