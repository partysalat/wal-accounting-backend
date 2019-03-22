import React, {Component} from 'react';
import {connect} from 'react-redux';
import Avatar from '@material-ui/core/Avatar';
import Tooltip from '@material-ui/core/Tooltip';
import {loadBestlist} from '../../../redux/actions';
import './bestlist.css';

class Bestlist extends Component {
  componentWillMount() {
    this.props.loadBestlist();
  }

  render() {
    return (
      <table>
        <tr>
          <th>No.</th>
          <th>Name</th>
          <th>Biere</th>
          <th>Cocktails</th>
          <th>Shots</th>
          <th>Softdrinks</th>
          <th>Achievements</th>
        </tr>
        {this.props.bestlist.map((row, index) => (
          <tr key={row.id}>
            <td component="th" scope="row">
              {index + 1}.
            </td>
            <td component="th" scope="row">
              {row.user.name}
            </td>
            <td align="right">{row.beerCount}</td>
            <td align="right">{row.cocktailCount}</td>
            <td align="right">{row.shotCount}</td>
            <td align="right">{row.softdrinkCount}</td>
            <td align="right">{row.achievements.map(payload => (
              <Tooltip key={payload.id} title={`${payload.name}: ${payload.description}`}>
                <Avatar className="avatar-small" alt={payload.name} src={payload.imagePath} sizes="xs"/>
              </Tooltip>))}</td>
          </tr>
        ))}
      </table>
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
