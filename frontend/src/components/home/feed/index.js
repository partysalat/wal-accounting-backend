import React, { Component } from 'react';
import FeedItem from './feedItem';

class Feed extends Component {
  render() {
    return (
      <div>
        <FeedItem />
        <FeedItem />
        <FeedItem />
        <FeedItem />
      </div>
    );
  }
}
export default Feed;
