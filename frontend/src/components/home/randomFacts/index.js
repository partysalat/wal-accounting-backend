import React, { Component } from 'react';
import Paper from '@material-ui/core/Paper';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import facts from './facts.json';

const FACT_SWITCH_INTERVAL = 60000;

const styles = theme => ({
  root: {
    width: '100%',
    // marginTop: theme.spacing.unit * 3,
    overflowX: 'hidden',
    backgroundColor: 'transparent',
    border: `1px solid ${theme.palette.background.default}`,
  },
});

class RandomFacts extends Component {
  constructor(props) {
    super(props);
    this.gambleNews = this.gambleNews.bind(this);
    this.state = {
      sentence: '',
      animation: '',
    };
  }

  gambleNews() {
    const len = facts.length;
    const randomIndex = Math.floor(Math.random() * len);
    this.setState({
      sentence: facts[randomIndex],
      animation: 'animated jackInTheBox',
    });
    setTimeout(() => {
      this.setState({ animation: '' });
    },1000);
  }
  componentWillMount() {

    setInterval(this.gambleNews, FACT_SWITCH_INTERVAL);
    this.gambleNews();
  }


  render() {
    const { classes } = this.props;
    return (
      <Card className={classes.root}>
        <CardHeader title="Random Facts" />
        <CardContent className={this.state.animation}>
          <Typography variant="body2" component="p" >
            {this.state.sentence}
          </Typography>

        </CardContent>
      </Card>
    );
  }
}


export default withStyles(styles)(RandomFacts);
