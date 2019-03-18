import React from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Avatar from '@material-ui/core/Avatar';
import './FeedItem.css';

const ACHIEVEMENT = 'ACHIEVEMENT';
function AchievementItem(news) {
  const payload = news.payload.AchievementPayload;
  const user = news.user;

  return (<Card className="feedItem">
    <CardHeader
      avatar={
        <Avatar aria-label="Recipe" >
          OPA
        </Avatar>
      }
      title={`${user.name} hat "${payload.name}" erreicht`}
      subheader="September 14, 2016"
    />

  </Card>);
}
function DrinkItem(news) {
  const drinkPayload = news.payload.DrinkPayload;
  const user = news.user;
  return (<Card className="feedItem">
    <CardHeader
      avatar={
        <Avatar aria-label="Recipe" >
          OPA
        </Avatar>
      }
      title={`${user.name} hat ${drinkPayload.name} bestellt`}
      subheader="September 14, 2016"
    />

  </Card>);
}

export default function FeedItem(props) {
  return props.news.news.newsType === ACHIEVEMENT ?
    AchievementItem(props.news) : DrinkItem(props.news);
}
