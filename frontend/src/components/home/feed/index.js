import React, { Component } from 'react';
import { connect } from 'react-redux';
import Infinite from 'react-infinite';
import { withStyles } from '@material-ui/core';
import FeedItem from './feedItem';
import { appendNewsItems, loadNewsItems, subscribeToNewsUpdate } from '../../../redux/actions';

const styles = theme => ({
  root: {

  },
});

class Feed extends Component {
  constructor(props) {
    super(props);
    this.state = {
      offset: 0,
      isInfiniteLoading: false,
    };
  }
  componentWillMount() {
    this.props.loadNewsItems(0);
    this.props.subscribeToNewsUpdate();
  }

  handleInfiniteLoad = () => {
    console.log('On infinite load');
    // this.props.appendNewsItems(this.state.offset + 20);
    if (this.props.news.length > 19) {
      this.props.appendNewsItems(this.props.news.length);
    }
    this.setState(({ offset }) => ({
      // offset: offset + 20,
      isInfiniteLoading: true,
    }));
  }

  render() {
    return (
      <div>
        <Infinite
          elementHeight={80}
          // containerHeight={500}
          useWindowAsScrollContainer
          infiniteLoadBeginEdgeOffset={0}
          onInfiniteLoad={this.handleInfiniteLoad}
          isInfiniteLoading={this.props.loading || this.props.lastLoadEmpty}
        >{this.props.news.map(newsItem => (
          <FeedItem news={newsItem} key={newsItem.news.id} className="animated lightSpeedIn" />
          ))}
        </Infinite>
      </div>
    );
  }
}

const mapDispatchToProps = {
  loadNewsItems,
  appendNewsItems,
  subscribeToNewsUpdate,
};

function getNews(state, props) {
  const news = state.news.data;
  if (!news) {
    return [];
  }
  const from = props.from || 0;
  const until = props.until || news.length;

  return state.news.data.slice(from, until);
}

const mapStateToProps = (state, props) => ({
  news: getNews(state, props),
  loading: state.news.loading || false,
  lastLoadEmpty: state.news.lastLoadEmpty,

});
export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(Feed));
