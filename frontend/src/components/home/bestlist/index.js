import React, { Component } from 'react';
import { connect } from 'react-redux';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { appendNewsItems, loadBestlist, subscribeToNewsUpdate } from '../../../redux/actions';
import Avatar from '@material-ui/core/Avatar';
import Tooltip from '@material-ui/core/Tooltip';


class Bestlist extends Component {
  componentWillMount() {
    this.props.loadBestlist();
  }

  render() {
    return (
      <Table >
        <TableHead>
          <TableRow>
            <TableCell>No.</TableCell>
            <TableCell align="right">Name</TableCell>
            <TableCell align="right">Biere</TableCell>
            <TableCell align="right">Cocktails</TableCell>
            <TableCell align="right">Shots</TableCell>
            <TableCell align="right">Softdrinks</TableCell>
            <TableCell align="right">Achievements</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {this.props.bestlist.map((row, index) => (
            <TableRow key={row.id}>
              <TableCell component="th" scope="row">
                {index + 1}.
              </TableCell>
              <TableCell component="th" scope="row">
                {row.user.name}
              </TableCell>
              <TableCell align="right">{row.beerCount}</TableCell>
              <TableCell align="right">{row.cocktailCount}</TableCell>
              <TableCell align="right">{row.shotCount}</TableCell>
              <TableCell align="right">{row.softdrinkCount}</TableCell>
              <TableCell align="right">{row.achievements.map(payload => (<Tooltip key={payload.id} title={`${payload.name}: ${payload.description}`}>
                <Avatar alt={payload.name} src={payload.imagePath} sizes="xs"/>
              </Tooltip>))}</TableCell>
            </TableRow>
                ))}
        </TableBody>
      </Table>
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
export default connect(mapStateToProps, mapDispatchToProps)(Bestlist);
