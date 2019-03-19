import React, { Component } from 'react';
import FeedItem from './feedItem';
import { connect } from 'react-redux';
import Infinite from 'react-infinite';
import { appendNewsItems, loadNewsItems, subscribeToNewsUpdate } from '../../../redux/actions';

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
          // containerHeight={}
          useWindowAsScrollContainer
          infiniteLoadBeginEdgeOffset={0}
          onInfiniteLoad={this.handleInfiniteLoad}
          isInfiniteLoading={this.props.loading || this.props.lastLoadEmpty}
        >{this.props.news.map(newsItem => (<FeedItem news={newsItem} key={newsItem.news.id} />))}
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

const mapStateToProps = state => ({
  news: state.news.data || [],
  loading: state.news.loading || false,
  lastLoadEmpty: state.news.lastLoadEmpty,

});
export default connect(mapStateToProps, mapDispatchToProps)(Feed);
