import React from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Avatar from '@material-ui/core/Avatar';
import { format } from 'date-fns';
import './FeedItem.css';

const ACHIEVEMENT = 'ACHIEVEMENT';
function AchievementItem(news) {
  const newsMeta = news.news;
  const payload = news.payload.AchievementPayload;
  const user = news.user;

  return (<Card className="feedItem">
    <CardHeader
      avatar={
        <Avatar alt={payload.name} src={payload.imagePath} />
      }
      title={`${user.name} hat "${payload.name}" erreicht`}
      subheader={format(new Date(newsMeta.createdAt), 'DD.MM.YYYY HH:mm')}
    />

  </Card>);
}
function DrinkItem(news) {
  const newsMeta = news.news;
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
      subheader={format(new Date(newsMeta.createdAt), 'DD.MM.YYYY HH:mm')}
    />

  </Card>);
}

export default function FeedItem(props) {
  return props.news.news.newsType === ACHIEVEMENT ?
    AchievementItem(props.news) : DrinkItem(props.news);
}
